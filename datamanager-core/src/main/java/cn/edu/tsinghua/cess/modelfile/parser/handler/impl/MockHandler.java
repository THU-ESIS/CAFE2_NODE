package cn.edu.tsinghua.cess.modelfile.parser.handler.impl;

import cn.edu.tsinghua.cess.modelfile.entity.ModelFile;
import cn.edu.tsinghua.cess.modelfile.parser.ModelFileHandler;

public class MockHandler implements ModelFileHandler {

	@Override
	public void handle(ModelFile modelFile) {
		System.out.println(modelFile.getFullPath());
	}

	@Override
	public void flush() {
		// do nothing
	}

}
