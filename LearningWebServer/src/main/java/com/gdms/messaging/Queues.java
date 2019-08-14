package com.gdms.messaging;

/**
 * Queue names for Spirit Messaging
 *
 */
public class Queues {
	
	/**
     * Queue used for Spirit to request a configuration update from EMS boxes
     */
    public static final String LEARNING_REQUEST = "learning-queue";
	
	/**
     * Queue used for EMS boxes to send their configuration to Interceptor
     */
    public static final String LEARNING_RESPONSE = "learning-response";

}
