package com.gdms.messaging.model;

import java.io.Serializable;

public class LearningResponse implements Serializable {

	private static final long serialVersionUID = 9056932323000235933L;
	
	protected String type;
	
	protected boolean status;

	public LearningResponse() {

	}

	public LearningResponse(String type, boolean status) {
		super();
		this.type = type;
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LearningResponse [type=" + type + ", status=" + status + "]";
	}

}
