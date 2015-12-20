package cn.edu.tsinghua.cess.modelfile.parser.handler.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.edu.tsinghua.cess.component.remote.RemoteServiceFactory;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileList;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileManageService;

@Component("remoteHandler")
public class RemoteHandler extends AbstractBatchHandler {
	
	private static Logger log = Logger.getLogger(RemoteHandler.class);

    @Autowired DeploymentService deploymentService;
    @Autowired RemoteServiceFactory remoteServiceFactory;

    @Override
    protected void handle(List<ModelFile> modelFileList) {
    	long begin = System.currentTimeMillis();
    	String result = null;
    	
    	try {
    		Deployment deployment = deploymentService.get();
    		
	        ModelFileList list = new ModelFileList();
	        list.nodeId = deployment.getNodeId();
	        list.list = modelFileList;
	        
	        ModelFileManageService modelFileManageService = remoteServiceFactory.getRemoteService(deployment.getCentralServer(), ModelFileManageService.class);
	        modelFileManageService.insertModelFileList(list);
	        
	        result = "succeeded";
    	} catch (Exception e) {
    		String message = "error occured while handling modelFileList remotely:" + e.getMessage();
    		log.error(message, e);
    		result = e.getMessage();
		} finally {
			long end = System.currentTimeMillis();
			
			StringBuilder builder = new StringBuilder();
			builder.append("post count=" + modelFileList.size() + " messages, elapsed=" + (end - begin) + ", result=" + result);
			
			log.info(builder);
		}
    }

}
