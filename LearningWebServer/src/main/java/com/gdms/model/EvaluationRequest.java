package com.gdms.model;

public class EvaluationRequest extends LearningRequest {

	private static final long serialVersionUID = -6802103683492694927L;
	
	private String imageLocation;
	
	private int numImages;
	
	private int imageSize;

	public EvaluationRequest(String type, String imageLocation, int numImages, int imageSize) {
		super(type);
		this.imageLocation = imageLocation;
		this.numImages = numImages;
		this.imageSize = imageSize;
	}

	public EvaluationRequest() {
		super();
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public int getNumImages() {
		return numImages;
	}

	public void setNumImages(int numImages) {
		this.numImages = numImages;
	}

	public int getImageSize() {
		return imageSize;
	}

	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}

	@Override
	public String toString() {
		return "EvaluationRequest [imageLocation=" + imageLocation + ", numImages=" + numImages + ", imageSize="
				+ imageSize + ", type=" + type + "]";
	}

}
