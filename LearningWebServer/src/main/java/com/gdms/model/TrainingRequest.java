package com.gdms.model;

public class TrainingRequest extends LearningRequest {
	
	private static final long serialVersionUID = 6916120716954952343L;
	
	private int epochs;

	public TrainingRequest(String type, int epochs) {
		super(type);
		this.epochs = epochs;
	}

	public TrainingRequest() {
		super();		
	}

	public int getEpochs() {
		return epochs;
	}

	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}

	@Override
	public String toString() {
		return "TrainingRequest [epochs=" + epochs + ", type=" + type + "]";
	}	

}
