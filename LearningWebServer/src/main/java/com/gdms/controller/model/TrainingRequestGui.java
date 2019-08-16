package com.gdms.controller.model;

public class TrainingRequestGui extends GuiMessage {

	private static final long serialVersionUID = 2918710880568347710L;
	
	private int ephochs;

	public TrainingRequestGui() {
	}

	public TrainingRequestGui(int ephochs) {
		super();
		this.ephochs = ephochs;
	}

	public int getEphochs() {
		return ephochs;
	}

	public void setEphochs(int ephochs) {
		this.ephochs = ephochs;
	}

	@Override
	public String toString() {
		return "TrainingRequestGui [ephochs=" + ephochs + "]";
	}

}
