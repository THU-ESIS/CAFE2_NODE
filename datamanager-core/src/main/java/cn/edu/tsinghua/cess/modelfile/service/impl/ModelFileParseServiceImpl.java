package cn.edu.tsinghua.cess.modelfile.service.impl;

import cn.edu.tsinghua.cess.component.exception.ExceptionHandler;
import cn.edu.tsinghua.cess.deployment.entity.DeployMode;
import cn.edu.tsinghua.cess.deployment.entity.Deployment;
import cn.edu.tsinghua.cess.deployment.service.DeploymentService;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import cn.edu.tsinghua.cess.modelfile.parser.ModelFileHandler;
import cn.edu.tsinghua.cess.modelfile.parser.ModelFileParser;
import cn.edu.tsinghua.cess.modelfile.service.ModelFileParseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.Callable;

@Component
public class ModelFileParseServiceImpl implements ModelFileParseService {

    private final static Logger log = Logger.getLogger(ModelFileParseServiceImpl.class);

    @Autowired
    DeploymentService deploymentService;

    @Autowired
    @Qualifier("databaseHandler")
    ModelFileHandler databaseHandler;

    @Autowired
    @Qualifier("remoteHandler")
    ModelFileHandler remoteHandler;


    @Override
    public void parse(final String path) {
        File f = new File(path);
        if (!f.exists()) {
            throw new IllegalArgumentException("path=" + path + " does not exist");
        }
        if (!f.isDirectory()) {
            throw new IllegalArgumentException("path=" + path + " is not a directory");
        }

        Runnable r = new Runnable() {

            @Override
            public void run() {
                try {
                    // The father class that invokes the actual parser and generate logs
                    // first: getting the handler for the parser
                    // then: parse the path and get files
                    ModelFileParser parser = new ModelFileParser();
                    parser.setModelFileHandler(getHandler());
                    parser.parse(path);
                } catch (Exception e) {
                    log.error("error occured while parsing [path=" + path + "], [exception=" + e.getMessage() + "]", e);
                }
            }
        };
        new Thread(r).start();
    }

    // different handler settings
    // 2 handlers are defined in handler.impl: remote handler and database handler, which are extended from  AbstractBatchHandler
    // this getHandler() function is meant to provide the proper handle() type: If the node is deployed LOCALLY, only use database handler to save parsed
    // model file data to local DB. If the node is deployed as a WORKER, database handler is used to save parsed data to LOCAL WORKER NODE DB, and remote handler is
    // also invoked to save model file data to CENTRAL NODE DB
    private ModelFileHandler getHandler() {
        Deployment deployment = deploymentService.get();

        if (deployment.getMode() == DeployMode.LOCAL) {
            return databaseHandler;
        }
        if (deployment.getMode() == DeployMode.DISTRIBUTED_WORKER) {
            return new ModelFileHandler() {

                @Override
                public void handle(final ModelFile modelFile) {
                    databaseHandler.handle(modelFile);
                    ExceptionHandler.exceptionFree(new Callable<Object>() {

                        @Override
                        public Object call() throws Exception {
                            remoteHandler.handle(modelFile);
                            return null;
                        }
                    });

                }

                @Override
                public void flush() {
                    databaseHandler.flush();
                    ExceptionHandler.exceptionFree(new Callable<Object>() {

                        @Override
                        public Object call() throws Exception {
                            remoteHandler.flush();
                            return null;
                        }
                    });
                }
            };
        }

        throw new IllegalStateException();
    }

}
