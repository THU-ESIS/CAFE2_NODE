package cn.edu.tsinghua.cess.datamanager.api.v1;


import cn.edu.tsinghua.cess.datamanager.api.ApiResult;
import cn.edu.tsinghua.cess.datamanager.api.ApiUtil;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileList;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

@Controller
public class ModelFilePersistApi {

    @Autowired ModelFileManageService modelFileManageService;

    @RequestMapping(
    			value = "/modelfile/persist",
    			method = RequestMethod.POST, 
    			consumes = "application/json",
    			produces = "application/json"
    		)
    @ResponseBody
    public ApiResult persist(@RequestBody final ModelFileList modelFileList) {
    	return ApiUtil.execute(new Callable<Object>() {
			
			@Override
			public Object call() throws Exception {
		        modelFileManageService.insertModelFileList(modelFileList);
				return null;
			}
		});
    	
    }

}
