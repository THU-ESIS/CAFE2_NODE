package cn.edu.tsinghua.cess.datamanager.api.v1;

import cn.edu.tsinghua.cess.component.remote.RemoteServiceFactory;
import cn.edu.tsinghua.cess.datamanager.api.ApiResult;
import cn.edu.tsinghua.cess.datamanager.api.ApiUtil;
import cn.edu.tsinghua.cess.deployment.entity.DeployMode;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.modelfile.dto.ModelQueryParam;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFileFilter;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileQueryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
public class ModelFileQueryApi {

	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired ModelFileQueryService service;
	@Autowired DeploymentService deploymentService;
	@Autowired RemoteServiceFactory remoteServiceFactory;

	@RequestMapping(value = "/modelfile/query/filter", method = { RequestMethod.GET, RequestMethod.POST } )
	@ResponseBody
	public ApiResult queryFilter() {
		return ApiUtil.execute(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				ModelFileFilter filter = getModelFileQueryService().listFilter();
				return filter;
			}
		});
	}

	private ModelFileQueryService getModelFileQueryService() {
		Deployment deployment = deploymentService.get();

		if (deployment.getMode() == DeployMode.DISTRIBUTED_CENTRAL || deployment.getMode() == DeployMode.LOCAL) {
			return service;
		} else {
			ModelFileQueryService remoteModelFileQueryService = remoteServiceFactory.getRemoteService(deployment.getCentralServer(), ModelFileQueryService.class);
			return remoteModelFileQueryService;
		}
	}

	@RequestMapping(value = "/modelfile/query_related_workernode", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult queryRelatedWorkerNode(@RequestBody final Model[] modelList) {
		return ApiUtil.execute(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return service.queryRelatedNodes(modelList);
			}
		});

	}
	
	@RequestMapping(value = "/modelfile/query", method = RequestMethod.GET)
	@ResponseBody
	public ApiResult query(
				@RequestParam("page") final Integer page,
				@RequestParam("pageSize") final Integer pageSize,
				@RequestParam(value = "institute", required = false )		final List<String> institute,
				@RequestParam(value = "model", required = false )			final List<String> model,
				@RequestParam(value = "experiment", required = false )		final List<String> experiment,
				@RequestParam(value = "frequency", required = false )		final List<String> frequency,
				@RequestParam(value = "modelingRealm", required = false )	final List<String> modelingRealm,
				@RequestParam(value = "ensembleMember", required = false )	final List<String> ensembleMember,
				@RequestParam(value = "variableName", required = false )	final List<String> variableName,
				@RequestParam(value = "temporalStart", required = false )	final String temporalStart,
				@RequestParam(value = "temporalEnd", required = false )	final String temporalEnd) {
		return ApiUtil.execute(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				ModelFileFilter filter = createFilter(institute, model, experiment, frequency, modelingRealm, ensembleMember, variableName, temporalStart, temporalEnd);
				ModelQueryParam param = new ModelQueryParam(filter, page, pageSize);

				return getModelFileQueryService().queryModel(param);
			}
		});
	}

	@RequestMapping(value = "/modelfile/query_by_dto", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult queryByDto(@RequestBody final ModelQueryParam modelQueryParam) {
		logger.info("param=" + modelQueryParam);

		return ApiUtil.execute(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return service.queryModel(modelQueryParam);
			}
		});
	}
	
	private ModelFileFilter createFilter(
			List<String> institute,
			List<String> model,
			List<String> experiment,
			List<String> frequency,
			List<String> modelingRealm,
			List<String> ensembleMember,
			List<String> variableName,
			String temporalStart,
			String temporalEnd) {
		ModelFileFilter filter = new ModelFileFilter();
		
		filter.setInstitute(institute);
		filter.setModel(model);
		filter.setExperiment(experiment);
		filter.setFrequency(frequency);
		filter.setModelingRealm(modelingRealm);
		filter.setEnsembleMember(ensembleMember);
		filter.setVariableName(variableName);
		filter.setTemporalStart(temporalStart);
		filter.setTemporalEnd(temporalEnd);
		
		return filter;
	}
	
}
