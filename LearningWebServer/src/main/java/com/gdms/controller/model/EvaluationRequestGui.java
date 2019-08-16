package com.gdms.controller.model;

public class EvaluationRequestGui extends GuiMessage {

	private static final long serialVersionUID = 6154537211671541187L;
	
	private String testFile;
	
	private int imageSize;

	public EvaluationRequestGui() {}

	public EvaluationRequestGui(String testFile, int imageSize) {
		super();
		this.testFile = testFile;
		this.imageSize = imageSize;
	}

	public String getTestFile() {
		return testFile;
	}

	public void setTestFile(String testFile) {
		this.testFile = testFile;
	}

	public int getImageSize() {
		return imageSize;
	}

	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	@Override
	public String toString() {
		return "EvaluationRequestGui [testFile=" + testFile + ", imageSize=" + imageSize + "]";
	}

}
