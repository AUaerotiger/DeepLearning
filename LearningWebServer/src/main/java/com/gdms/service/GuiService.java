package com.gdms.service;

import com.gdms.controller.model.EvaluationReponseGui;
import com.gdms.controller.model.EvaluationRequestGui;
import com.gdms.controller.model.LoadResponseGui;
import com.gdms.controller.model.TrainingRequestGui;
import com.gdms.controller.model.TrainingResponseGui;

public interface GuiService {
	
	public LoadResponseGui loadData();
	
	public TrainingResponseGui trainData(TrainingRequestGui request);
	
	public EvaluationReponseGui evaluateImages(EvaluationRequestGui request);

}
