package com.gdms.messaging.model;

import java.util.Arrays;

public class LoadResponse extends LearningResponse {

	private static final long serialVersionUID = -7864063825385180605L;
	
	private String trainingImages;
	
	private String trainingLabelImages;
	
	private String testingImages;
	
	private String testingLabelImages;
	
	private String[] classNames;
	
	private int trainingImagesSize;
	
	private int testImagesSize;

	public LoadResponse() {
		super();
	}
	
	

	public LoadResponse(String trainingImages, String trainingLabelImages, String testingImages,
			String testingLabelImages, String[] classNames, int trainingImagesSize,
			int testImagesSize) {
		super();
		this.trainingImages = trainingImages;
		this.trainingLabelImages = trainingLabelImages;
		this.testingImages = testingImages;
		this.testingLabelImages = testingLabelImages;
		this.classNames = classNames;
		this.trainingImagesSize = trainingImagesSize;
		this.testImagesSize = testImagesSize;
	}

	public String getTrainingImages() {
		return trainingImages;
	}

	public void setTrainingImages(String trainingImages) {
		this.trainingImages = trainingImages;
	}

	public String getTrainingLabelImages() {
		return trainingLabelImages;
	}

	public void setTrainingLabelImages(String trainingLabelImages) {
		this.trainingLabelImages = trainingLabelImages;
	}

	public String getTestingImages() {
		return testingImages;
	}

	public void setTestingImages(String testingImages) {
		this.testingImages = testingImages;
	}

	public String getTestingLabelImages() {
		return testingLabelImages;
	}

	public void setTestingLabelImages(String testingLabelImages) {
		this.testingLabelImages = testingLabelImages;
	}

	public int getTrainingImagesSize() {
		return trainingImagesSize;
	}

	public void setTrainingImagesSize(int trainingImagesSize) {
		this.trainingImagesSize = trainingImagesSize;
	}

	public int getTestImagesSize() {
		return testImagesSize;
	}

	public void setTestImagesSize(int testImagesSize) {
		this.testImagesSize = testImagesSize;
	}

	public String[] getClassNames() {
		return classNames;
	}

	public void setClassNames(String[] classNames) {
		this.classNames = classNames;
	}

	@Override
	public String toString() {
		return "LoadResponse [trainingImages=" + trainingImages + ", trainingLabelImages=" + trainingLabelImages
				+ ", testingImages=" + testingImages + ", testingLabelImages=" + testingLabelImages + ", classNames="
				+ Arrays.toString(classNames) + ", trainingImagesSize="
				+ trainingImagesSize + ", testImagesSize=" + testImagesSize
				+ ", type=" + type + ", status=" + status + "]";
	}

}
