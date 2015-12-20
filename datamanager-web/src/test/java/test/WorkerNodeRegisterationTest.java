package test;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import cn.edu.tsinghua.cess.workernode.service.WorkerNodeManageService;

public class WorkerNodeRegisterationTest {
	
	public static void main(String[] args) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		WorkerNode node = new WorkerNode();
		node.setIp("166.111.7.72");
		node.setPort(8080);
		node.setRootPath("datamanager-web-qa-worker");
		
		Integer id = LocalTestUtil.getService(LocalTestUtil.qaCentral, WorkerNodeManageService.class).register(node);
		System.out.println(id);
	}

}
