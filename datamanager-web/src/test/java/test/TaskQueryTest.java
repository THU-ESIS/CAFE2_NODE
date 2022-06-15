package test;

import cn.edu.tsinghua.cess.task.entity.dto.SubTaskResult;
import cn.edu.tsinghua.cess.task.service.TaskQueryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskQueryTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        SubTaskResult[] results = LocalTestUtil.getService(LocalTestUtil.cessWorker, TaskQueryService.class).querySubTaskResult(new Integer[]{
                21
        });

        SubTaskResult r = results[0];
        System.out.println(r.getClass());
        System.out.println();
    }

}
