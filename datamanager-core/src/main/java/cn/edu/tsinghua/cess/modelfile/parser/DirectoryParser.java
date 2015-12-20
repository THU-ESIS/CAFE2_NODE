package cn.edu.tsinghua.cess.modelfile.parser;

import java.io.File;

class DirectoryParser {
	
	private FileParser fileParser = new FileParser();
	
	public void parseDirectory(File directory, ParserContext context) {
		context.push(directory.getName());
		context.increaseTraversedDirectoryCount();

		try {
			File[] subFiles = directory.listFiles();
			
			if (subFiles == null || subFiles.length == 0) {
				return;
			}

			boolean conformWithPattern = context.conformWithPattern();
			boolean continueDrillingIn = context.continueDrillingIn();
			
			if (conformWithPattern) {
				context.increaseMatchedDirectoryCount();
			}

			for (File subFile : subFiles) {
				if (subFile.isFile()) {
					if (conformWithPattern) {
						fileParser.parseFile(subFile, context);
					}
				} else {
					if (!conformWithPattern && continueDrillingIn) {
						this.parseDirectory(subFile, context);
					}
				}
			}
		} finally {
			context.pop();
		}
	}

}
