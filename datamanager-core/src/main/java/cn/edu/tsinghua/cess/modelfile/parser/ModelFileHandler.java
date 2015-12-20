package cn.edu.tsinghua.cess.modelfile.parser;

import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;

public interface ModelFileHandler {
	
	public void handle(ModelFile modelFile);
	
	public void flush();

}
