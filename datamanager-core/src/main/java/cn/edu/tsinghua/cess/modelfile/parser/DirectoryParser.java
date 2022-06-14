package cn.edu.tsinghua.cess.modelfile.parser;

import java.io.File;

class DirectoryParser {
	// use FileParser class to parse filename
	private FileParser fileParser = new FileParser();

	// function to control the recursive parsing of current directory. Parsercontext: store directory stack, retrieve information, check condition, logging
	public void parseDirectory(File directory, ParserContext context) {
		// add current directory to directory stack
		context.push(directory.getName());
		// for logging purpose
		context.increaseTraversedDirectoryCount();
		// conformWithPattern: defined in ParserContext, check if current directory stack follows pattern
		boolean conformWithPattern = context.conformWithPattern();
		if (conformWithPattern) {
			// for logging purpose
			context.increaseMatchedDirectoryCount();
		}

		// 2 types of results in a directory: empty / not empty(have folders or files or both)
		try {
			File[] subFiles = directory.listFiles();
			// if empty, return
			if (subFiles == null || subFiles.length == 0) {
				return;
			}
			// if not empty, for each folder or file
			for (File subFile : subFiles) {
				// if is file, and if current directory stack follows CMIP5/6 DRS pattern/user-defined pattern, and parse the file info
				if (subFile.isFile()) {
					// conformWithPattern: obtained above
					if (conformWithPattern) {
						// get filename information
						fileParser.parseFile(subFile, context);
					}
				} else { // if is folder, continue parse this folder until directory stack meets stopping conditions. 
					// continueDrillingIn is defined in ParserContext
					boolean continueDrillingIn = context.continueDrillingIn();
					if (continueDrillingIn) {
						this.parseDirectory(subFile, context);
					}
				}
			}
		} finally {
			context.pop();
		}
	}

}
