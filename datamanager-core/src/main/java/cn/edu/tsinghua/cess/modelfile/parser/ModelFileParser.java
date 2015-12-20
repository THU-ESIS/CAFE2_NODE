package cn.edu.tsinghua.cess.modelfile.parser;

import java.io.File;

import org.apache.log4j.Logger;

public class ModelFileParser {
	
	private static Logger log = Logger.getLogger(ModelFileParser.class);
	
	private DirectoryParser directoryParser = new DirectoryParser();
	private ModelFileHandler modelFileHandler;
	
	public void parse(String path) {
		File directory = new File(path);
		
		if (directory.isFile()) {
			log.error("the [path=" + path + "] is not a directory");
			throw new IllegalArgumentException();
		}
		
		long begin = System.currentTimeMillis();
		log.info("will begin to traverse and parse model file(s) under [path=" + path + "]");
		
		ParserContext context = new ParserContext(modelFileHandler);
		
		try {
			directoryParser.parseDirectory(directory, context);
			modelFileHandler.flush();
		} finally {
			long elapsed = System.currentTimeMillis() - begin;
			StringBuilder builder = new StringBuilder();
			builder.append("finished parsing under [path=").append(path).append("], ")
				.append("[elapsed=").append(elapsed).append("], ")
				.append("[traversed directory count=").append(context.getTraversedDirectoryCount()).append("], ")
				.append("[matched directory count=").append(context.getMatchedDirectoryCount()).append("], ")
				.append("[traversed file count=").append(context.getTraversedFileCount()).append("], ")
				.append("[matched file count=").append(context.getMatchedFileCount()).append("]");
				
			log.info(builder.toString());
		}
	}

	public ModelFileHandler getModelFileHandler() {
		return modelFileHandler;
	}

	public void setModelFileHandler(ModelFileHandler modelFileHandler) {
		this.modelFileHandler = modelFileHandler;
	}

}
