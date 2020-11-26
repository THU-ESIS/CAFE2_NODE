package cn.edu.tsinghua.cess.modelfile.parser.handler.impl;

import cn.edu.tsinghua.cess.modelfile.dao.ModelFileDao;
import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("databaseHandler")
public class DatabaseHandler extends AbstractBatchHandler {

    @Autowired
    private ModelFileDao modelFileDao;

    @Override
    protected void handle(List<ModelFile> modelFileList) {
        modelFileDao.insertModelFileList(modelFileList);
    }

}
