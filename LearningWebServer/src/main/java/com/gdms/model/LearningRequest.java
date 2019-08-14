package com.gdms.model;

import java.io.Serializable;

public class LearningRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1907890943438141891L;
	
	protected String type;

	public LearningRequest(String type) {
		super();
		this.type = type;
	}

	public LearningRequest() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "LearningRequest [type=" + type + "]";
	}

}
