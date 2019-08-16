package com.gdms.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.gdms.messaging.model.EvaluationRequest;
import com.gdms.messaging.model.LearningMessageTypes;
import com.gdms.messaging.model.LearningRequest;
import com.gdms.messaging.model.TrainingRequest;

/**
 * Class to send JMS messages to AMQ broker
 *
 */
@Component
public class LearningJmsSender {

    static final Logger LOG = LoggerFactory.getLogger(LearningJmsSender.class);

    @Autowired
    JmsTemplate localJmsTemplate;
    
    public void sendLoadRequest() {
    	
    	String type = LearningMessageTypes.LOAD_DATA.toString();
    	LearningRequest request = new LearningRequest(type);
    	this.sendRequest(request);
    	
    }
    
    public void sendTrainRequest(int epochs) {
    	
    	String type = LearningMessageTypes.TRAIN_DATA.toString();
    	TrainingRequest request = new TrainingRequest(type, epochs);
    	this.sendRequest(request);
    	
    }
    
    public void sendTerminationRequest() {
    	String type = LearningMessageTypes.TERMINATION.toString();
    	LearningRequest request = new LearningRequest(type);
    	this.sendRequest(request);
    }
    
    public void sendEvaluationRequest(String imageLocation, int numImages, int imageSize) {
    	String type = LearningMessageTypes.EVALUATE_IMAGES.toString();
    	EvaluationRequest request = new EvaluationRequest(type, imageLocation, numImages, imageSize);
    	this.sendRequest(request);
    }

    /**
     * Sends a Configuration Request to all EMS units
     *
     */
    private void sendRequest(LearningRequest request) {
    	
    	this.localJmsTemplate.convertAndSend(Queues.LEARNING_REQUEST, request);
    

    }

}