package cn.edu.tsinghua.cess.datamanager.api.v1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.tsinghua.cess.datamanager.api.ApiResult;
import cn.edu.tsinghua.cess.datamanager.api.ApiUtil;
import cn.edu.tsinghua.cess.datamanager.nclscript.NclScriptContext;
import cn.edu.tsinghua.cess.task.entity.SubTaskResultFile;
import cn.edu.tsinghua.cess.task.entity.dto.TaskSubmition;
import cn.edu.tsinghua.cess.task.service.TaskQueryService;
import cn.edu.tsinghua.cess.task.service.impl.TaskSubmitionServiceFactory;

@Controller
@RequestMapping("/task")
public class TaskApi {
	
	private Logger log = Logger.getLogger(TaskApi.class);
	
	@Autowired TaskSubmitionServiceFactory serviceFactory;
    @Autowired TaskQueryService taskQueryService;

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult submit(@RequestBody final TaskSubmition submition) {
		return ApiUtil.execute(new Callable<Object>() {
			
			@Override
			public Object call() throws Exception {
				return serviceFactory.getService().submitTask(submition);
			}
		});
	}

    @RequestMapping(value = "/subtask/submit", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult submitSubTask(@RequestBody final TaskSubmition submition) {
        return ApiUtil.execute(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return serviceFactory.getService().submitSubTask(submition);
            }
        });
    }

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult queryTask(@RequestParam final String taskId) {
        return ApiUtil.execute(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return taskQueryService.queryTaskResult(taskId);
            }
        });
    }

    @RequestMapping(value = "/subtask/query", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult querySubTask(@RequestParam final Integer[] subTaskId) {
        return ApiUtil.execute(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return taskQueryService.querySubTaskResult(subTaskId);
            }
        });
    }
    
    @RequestMapping("/resultfile/{subTaskId}/{resultFileId}")
    public void getResultFile(HttpServletResponse response, @PathVariable Integer subTaskId, @PathVariable Integer resultFileId) throws Exception {
		OutputStream os = null;
		InputStream is = null;

		try {
	    	SubTaskResultFile file = taskQueryService.querySubTaskResultFile(subTaskId, resultFileId);
			File f = new File(file.getPath());

            response.addHeader("Content-Disposition", "attachment;filename=" + new String(f.getName().getBytes()));
            response.addHeader("Content-Length", "" + f.length());
			response.setContentType(this.getMimeType(file.getType()));
			os = response.getOutputStream();
			is = new FileInputStream(f);

			byte[] buffer = new byte[2048];
			int bytesRead = 0;
			while ((bytesRead = is.read(buffer)) > 0) {
				os.write(buffer, 0, bytesRead);
			}
			os.flush();
		} catch (Exception e) {
			log.error("an error occured while getting resultFile, subTaskId=" + subTaskId + ", resultFileId=" + resultFileId, e);
			throw e;
		} finally {
			try {
                if (is != null) {
                    is.close();
                }

                if (os != null) {
                    os.close();
                }
			} catch (IOException e) {
			}
		}
	}
    
    private String getMimeType(String resultFileType) {
    	if (NclScriptContext.RESULT_TYPE_FIG.equals(resultFileType)) {
    		return "image/png";
    	}
    	if (NclScriptContext.RESULT_TYPE_NC.equals(resultFileType)) {
    		return "application/x-netcdf";
    	}
    	if (NclScriptContext.RESULT_TYPE_TXT.equals(resultFileType)) {
    		return "text/plain";
    	}
    	
    	throw new IllegalArgumentException("invalid resultFileType=" + resultFileType);
    }
	
}
