package cn.edu.tsinghua.cess.datamanager.api.v1;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import cn.edu.tsinghua.cess.datamanager.api.ApiResult;
import cn.edu.tsinghua.cess.datamanager.api.ApiUtil;
import cn.edu.tsinghua.cess.workernode.entity.WorkerNode;
import cn.edu.tsinghua.cess.workernode.service.WorkerNodeManageService;

@Controller
@RequestMapping("/workernode")
public class WorkerNodeRegisterationApi {
	
	@Autowired WorkerNodeManageService workerNodeManageService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult register(@RequestBody final WorkerNode body) {
		return ApiUtil.execute(new Callable<Object>() {
			
			@Override
			public Object call() throws Exception {
				return workerNodeManageService.register(body);
			}
		});
	}
	
	@RequestMapping(value = "/deregister", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult deregister(@RequestBody final WorkerNode body) {
		return ApiUtil.execute(new Callable<Object>() {
			
			@Override
			public Object call() throws Exception {
				workerNodeManageService.deregister(body);
				return null;
			}
		});
	}

	@RequestMapping(value = "/query_by_id", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult queryById(@RequestParam("nodeId") final Integer nodeId) {
		return ApiUtil.execute(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return workerNodeManageService.queryById(nodeId);
			}
		});

	}

}
