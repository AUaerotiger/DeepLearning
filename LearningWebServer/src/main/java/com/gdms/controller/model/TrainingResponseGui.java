package com.gdms.controller.model;

public class TrainingResponseGui extends GuiMessage {

	private static final long serialVersionUID = -2683785613545476632L;
	
	private boolean success;

	public TrainingResponseGui() {}

	public TrainingResponseGui(boolean success) {
		super();
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "TrainingResponseGui [success=" + success + "]";
	}

}
