package com.gdms.messaging.model;

public class EvaluationResponse extends LearningResponse {

	private static final long serialVersionUID = 7136020804793442204L;
	
	private String location;
	
	private double loss;
	
	private double accuracy;

	public EvaluationResponse() {
		super();
	}

	public EvaluationResponse(String type, boolean status, String location, int loss, int accuracy) {
		super(type, status);
		this.location = location;
		this.loss = loss;
		this.accuracy = accuracy;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public String toString() {
		return "EvaluationResponse [location=" + location + ", loss=" + loss + ", accuracy=" + accuracy + ", type="
				+ type + ", status=" + status + "]";
	}

}
