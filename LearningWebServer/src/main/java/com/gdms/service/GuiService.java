package com.gdms.service;

import com.gdms.model.EvaluationResponse;
import com.gdms.model.LoadResponse;

public interface GuiService {
	
	public LoadResponse loadData();
	
	public boolean trainData();
	
	public boolean terminate();
	
	public EvaluationResponse evaluateImages();

}
