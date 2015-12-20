package cn.edu.tsinghua.cess.modelfile.parser.handler.impl;

import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import cn.edu.tsinghua.cess.modelfile.parser.ModelFileHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurt on 2014/8/8.
 */
public abstract class AbstractBatchHandler implements ModelFileHandler {

    private Integer batchSize = 200;
    private List<ModelFile> list = new ArrayList<ModelFile>();

    @Override
    public void handle(ModelFile modelFile) {
        synchronized (list) {
            list.add(modelFile);
            if (list.size() == batchSize) {
                this.handle(list);
                list.clear();
            }
        }

    }

    @Override
    public void flush() {
		synchronized (list) {
			if (list.size() > 0) {
				this.handle(list);
				list.clear();
			}
		}
    }
    
    public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	protected abstract void handle(List<ModelFile> modelFileList);

}
