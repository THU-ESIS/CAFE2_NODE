package cn.edu.tsinghua.cess.datamanager.api.v1;

import cn.edu.tsinghua.cess.datamanager.api.ApiResult;
import cn.edu.tsinghua.cess.datamanager.api.ApiUtil;
import cn.edu.tsinghua.cess.modelfile.dto.ModelQueryParam;
import cn.edu.tsinghua.cess.modelfile.entity.Model;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileQueryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.Callable;

/**
 * http service which serve remote call only
 * Created by yanke on 2016-10-22 19:33
 */
@Controller
public class ModelFileRemoteServiceApi {

    @Autowired
    ModelFileQueryService service;
    private Logger logger = Logger.getLogger(getClass());

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

}
