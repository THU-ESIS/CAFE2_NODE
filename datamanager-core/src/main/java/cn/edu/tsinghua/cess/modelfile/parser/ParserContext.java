package cn.edu.tsinghua.cess.modelfile.parser;

import java.util.Stack;

import cn.edu.tsinghua.cess.modelfile.entity.Model;

class ParserContext {
	
	/** below 4 properties are presented here for logging purpose */
	private int matchedFileCount;
	private int traversedFileCount;
	private int matchedDirectoryCount;
	private int traversedDirectoryCount;
	
	private Stack<String> directoryStack = new Stack<String>();
	private ModelFileHandler modelFileHandler;
	
	public ParserContext(ModelFileHandler modelFileHandler) {
		this.modelFileHandler = modelFileHandler;
	}
	
	public void push(String directory) {
		directoryStack.push(directory);
	}
	
	public String pop() {
		return directoryStack.pop();
	}

	public void fillPathProperties(Model modelEntity) {

		String institute = directoryStack.get(3);
		String model = directoryStack.get(4);
		String experiment = directoryStack.get(5);
		String frequency = directoryStack.get(9);
		String modelingRealm = directoryStack.get(10);
		String variableName = directoryStack.get(8);
		String ensembleMember = directoryStack.get(6);
		
		
		modelEntity.setInstitute(institute);
		modelEntity.setModel(model);
		modelEntity.setExperiment(experiment);
		modelEntity.setFrequency(frequency);
		modelEntity.setModelingRealm(modelingRealm);
		modelEntity.setEnsembleMember(ensembleMember);
		modelEntity.setVariableName(variableName);
	}
	
	
	/**
	 * ��ǰĿ¼�㼶�Ƿ�Ϊ�Ϸ���ģʽ�ļ�Ŀ¼�ṹ
	 * @return
	 */
	public boolean conformWithPattern() {
		if ( directoryStack.size() == 11) {
			return true;
		}
		return false;
	}
	
	public boolean continueDrillingIn() {
		return directoryStack.size() < 12;
	}

	public ModelFileHandler getModelFileHandler() {
		return modelFileHandler;
	}

	public void setModelFileHandler(ModelFileHandler modelFileHandler) {
		this.modelFileHandler = modelFileHandler;
	}

	public int getMatchedFileCount() {
		return matchedFileCount;
	}

	public int getTraversedFileCount() {
		return traversedFileCount;
	}

	public int getMatchedDirectoryCount() {
		return matchedDirectoryCount;
	}

	public int getTraversedDirectoryCount() {
		return traversedDirectoryCount;
	}
	
	public void increaseMatchedFileCount() {
		matchedFileCount++;
	}

	public void increaseTraversedFileCount() {
		traversedFileCount++;
	}

	public void increaseMatchedDirectoryCount() {
		matchedDirectoryCount++;
	}

	public void increaseTraversedDirectoryCount() {
		traversedDirectoryCount++;
	}

}
