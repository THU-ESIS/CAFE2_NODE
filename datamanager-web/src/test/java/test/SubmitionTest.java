package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.task.entity.ScriptArgument;
import cn.edu.tsinghua.cess.task.entity.SubTaskStatus;
import cn.edu.tsinghua.cess.task.entity.dto.SubTaskFile;
import cn.edu.tsinghua.cess.task.entity.dto.SubTaskResult;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;
import cn.edu.tsinghua.cess.task.service.TaskQueryService;
import cn.edu.tsinghua.cess.task.service.TaskSubmitionService;

public class SubmitionTest {
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        String[] scripts = new String[] {
            "AnnualTS",
            "GlobalContourEOF",
            "GlobalContourLTM",
            "GlobalContourTrend",
            "PolarNHEOF",
            "PolarNHLTM",
            "PolarNHTrend",
            "PolarSHEOF",
            "PolarSHLTM",
            "PolarSHTrend",
            "SeasonalTS"
        };
        
//		ctx.hashCode();

        for (String script : scripts) {
            System.out.println(script);
            TaskSubmition submition = getSubmition(script);
            String id = LocalTestUtil.getService(LocalTestUtil.cessWorker, TaskSubmitionService.class).submitTask(submition);

            Thread.sleep(3000L);

            SubTaskResult[] result = null;
            while (true) {
                boolean finished = true;
                result = LocalTestUtil.getService(LocalTestUtil.cessWorker, TaskQueryService.class).queryTaskResult(id);
                for (SubTaskResult subTaskResult : result) {
                    boolean subFinished = (subTaskResult.getStatus() == SubTaskStatus.finished || subTaskResult.getStatus() == SubTaskStatus.failed);
                    if (!subFinished) {
                        finished = false;
                    }
                }

                if (finished) {
                    break;
                }
                Thread.sleep(30000L);
            }

            System.out.println(script);
            System.out.println(id);
            for (SubTaskResult subTaskResult : result) {
                for (SubTaskFile file : subTaskResult.getResultFile()) {
                    System.out.println(file.getUrl());
                }
            }


            System.out.println();
        }
	}

    private static TaskSubmition getSubmition(String nclScript) {
        Model model = new Model();
        model.setInstitute("LASG-CESS");
        model.setModel("FGOALS-g2");
        model.setExperiment("decadal1955");
        model.setModelingRealm("seaIce");
        model.setVariableName("transix");
        model.setEnsembleMember("r1i1p1");
        model.setFrequency("mon");
        
        Model model2 = new Model();
        model2.setInstitute("BCC");
        model2.setModel("bcc-csm1-1");
        model2.setExperiment("1pctCO2");
        model2.setModelingRealm("seaIce");
        model2.setVariableName("sit");
        model2.setEnsembleMember("r1i1p1");
        model2.setFrequency("mon");

        ScriptArgument argument = new ScriptArgument();
        argument.setLatMax(20);
        argument.setLonMax(20);
        argument.setLatMin(10);
        argument.setLonMin(10);
        argument.setTemporalStart("195601");
        argument.setTemporalEnd("195612");
        argument.setName(nclScript);

//		argument.setName("GlobalContourLTM");
//        argument.setName("GlobalContourEOF");

        TaskSubmition submition = new TaskSubmition();
        submition.setNclScript(argument);
        submition.setModels(new ArrayList<Model>(Arrays.asList(model, model2)));

        return submition;
    }


//	private static List<String> toList(String str) {
//		return new ArrayList<String>(Arrays.asList(new String[] { str }));
//	}

}
