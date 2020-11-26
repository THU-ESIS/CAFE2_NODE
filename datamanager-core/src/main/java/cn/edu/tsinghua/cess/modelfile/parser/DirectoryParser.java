package cn.edu.tsinghua.cess.modelfile.parser;

import java.io.File;

// This is the actual parser
class DirectoryParser {

    private FileParser fileParser = new FileParser();

    public void parseDirectory(File directory, ParserContext context) {
        // first directorystack number: 0, whose value = path submitted to frontend
        // therefore actual model file information starts from stack<1>
        context.push(directory.getName());
        context.increaseTraversedDirectoryCount();

        try {
            File[] subFiles = directory.listFiles();

            if (subFiles == null || subFiles.length == 0) {
                return;
            }

            // determine whether or not to continue drill to child dir
            // see definition in ParserContext.java
            boolean conformWithPattern = context.conformWithPattern();
            boolean continueDrillingIn = context.continueDrillingIn();

            // if content in current working dir are file(s) and are in the right level of dirs, use fileParser
            // if content in current working dir is a child dir and we haven't reached the max levels of
            // CIMP5/6 hierarchy levels, continue to the child dir
            for (File subFile : subFiles) {
                if (subFile.isFile()) {
                    if (conformWithPattern) {
                        context.increaseMatchedDirectoryCount();
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
