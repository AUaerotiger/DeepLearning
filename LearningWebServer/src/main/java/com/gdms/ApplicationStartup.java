package com.gdms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.gdms.service.DemoService;

/**
 * Contains logic to run at application initialization
 *
 */
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    static final Logger LOG = LoggerFactory.getLogger(ApplicationStartup.class);

    @Autowired
    private DemoService demoService;

    /**
     * Runs the demo service. This will load data into the python app, train the data, and the evaluate some test images
     *
     * @param event
     *            - Event that signals that application context has been loaded
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
    	
    	LOG.info("===============================================");
    	
    	LOG.info("RUNNING DEMO");
    	boolean demoCheck = this.demoService.runDemo();

        if (demoCheck) {
        	LOG.info("SUCCESSFUL DEMO");
        } else {
        	LOG.error("DEMO FAILED");
        }
        
        LOG.info("===============================================");       

    }
}
