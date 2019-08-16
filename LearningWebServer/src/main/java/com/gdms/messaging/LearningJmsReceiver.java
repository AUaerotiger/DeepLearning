package com.gdms.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.gdms.messaging.model.LearningResponse;
import com.gdms.repository.LearningResponseRepository;

/**
 * Class to receive JMS messages from AMQ broker
 *
 */
@Component
public class LearningJmsReceiver {
    static final Logger LOG = LoggerFactory.getLogger(LearningJmsReceiver.class);

    @Autowired
    private LearningResponseRepository responseRepository;

    /**
     * Receives a unit configuration message
     *
     * @param response
     *            - The configuration response.
     */
    @JmsListener(destination = Queues.LEARNING_RESPONSE, containerFactory = MessagingConfiguration.MY_LOCAL_FACTORY)
    public void receiveResponse(LearningResponse response) {
        
        this.responseRepository.addResponse(response);
        

    }

}
