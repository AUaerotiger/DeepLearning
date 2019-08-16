package com.gdms.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gdms.messaging.model.LearningResponse;

@Component
public class LearningResponseRepository {
	
	private Map<String, LearningResponse> responses = new HashMap<>();

	public LearningResponseRepository() {
	}
	
	public synchronized void addResponse(LearningResponse response) {
		responses.put(response.getType(), response);
	}
	
	public synchronized LearningResponse getResponse(String type) {
		
		LearningResponse response = null;
		if (responses.containsKey(type)) {
			response = responses.get(type);
		}
		
		return response;
		
	}
	
	public synchronized void clearResponses() {
		this.responses.clear();
	}

}
