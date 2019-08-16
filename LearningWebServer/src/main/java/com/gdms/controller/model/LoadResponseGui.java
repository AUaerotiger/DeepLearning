package com.gdms.controller.model;

public class LoadResponseGui extends GuiMessage {

	private static final long serialVersionUID = -7126810306943433460L;
	
	private String trainingFile;
	
	private String testingFile;
	
	private int trainingSize;
	
	private int testingSize;
	
	private boolean success;

	public LoadResponseGui() {
	}

	public LoadResponseGui(String trainingFile, String testingFile, int trainingSize, int testingSize,
			boolean success) {
		super();
		this.trainingFile = trainingFile;
		this.testingFile = testingFile;
		this.trainingSize = trainingSize;
		this.testingSize = testingSize;
		this.success = success;
	}

	public LoadResponseGui(boolean success) {
		super();
		this.success = success;
	}

	public String getTrainingFile() {
		return trainingFile;
	}

	public void setTrainingFile(String trainingFile) {
		this.trainingFile = trainingFile;
	}

	public String getTestingFile() {
		return testingFile;
	}

	public void setTestingFile(String testingFile) {
		this.testingFile = testingFile;
	}

	public int getTrainingSize() {
		return trainingSize;
	}

	public void setTrainingSize(int trainingSize) {
		this.trainingSize = trainingSize;
	}

	public int getTestingSize() {
		return testingSize;
	}

	public void setTestingSize(int testingSize) {
		this.testingSize = testingSize;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "LoadResponseGui [trainingFile=" + trainingFile + ", testingFile=" + testingFile + ", trainingSize="
				+ trainingSize + ", testingSize=" + testingSize + ", success=" + success + "]";
	}

}
