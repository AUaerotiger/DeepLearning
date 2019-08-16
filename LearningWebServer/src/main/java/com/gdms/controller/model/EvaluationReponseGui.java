package com.gdms.controller.model;

import java.util.List;

public class EvaluationReponseGui extends GuiMessage {

	private static final long serialVersionUID = 6783989230269871414L;
	
	private double accuracy;
	
	private double loss;
	
	private String location;
	
	private List<String> expected;
	
	private List<String> actual;
	
	private boolean success;

	public EvaluationReponseGui() {}

	public EvaluationReponseGui(double accuracy, double loss, String location, List<String> expected,
			List<String> actual, boolean success) {
		super();
		this.accuracy = accuracy;
		this.loss = loss;
		this.location = location;
		this.expected = expected;
		this.actual = actual;
		this.success = success;
	}

	public EvaluationReponseGui(boolean success) {
		super();
		this.success = success;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<String> getExpected() {
		return expected;
	}

	public void setExpected(List<String> expected) {
		this.expected = expected;
	}

	public List<String> getActual() {
		return actual;
	}

	public void setActual(List<String> actual) {
		this.actual = actual;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "EvaluationReponseGui [accuracy=" + accuracy + ", loss=" + loss + ", location=" + location
				+ ", expected=" + expected + ", actual=" + actual + ", success=" + success + "]";
	}

}
