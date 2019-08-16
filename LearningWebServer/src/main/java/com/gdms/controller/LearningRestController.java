package com.gdms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gdms.controller.model.EvaluationReponseGui;
import com.gdms.controller.model.EvaluationRequestGui;
import com.gdms.controller.model.LoadResponseGui;
import com.gdms.controller.model.TrainingRequestGui;
import com.gdms.controller.model.TrainingResponseGui;
import com.gdms.service.GuiService;

/**
 * Controller used to process URL REST requests in Spirit application
 *
 */
@RestController("learningRestController")
public class LearningRestController {

    private static final String LOAD_URL = "/loadData";
    private static final String TRAIN_URL = "/trainData";
    private static final String EVALUATE_URL = "/evaluateData";  

    static final Logger LOG = LoggerFactory.getLogger(LearningRestController.class);

    @Autowired
    private GuiService guiService;

    /**
     * Loads data from python server. Returns file locations and image sizes
     *
     * @return file locations and image sizes
     */
    @GetMapping(LOAD_URL)
    public LoadResponseGui loadData() {

        LOG.info("GET from GUI: loadData");        
        return this.guiService.loadData();
    }
    
    /**
     * Trains data set
     *
     * @param request contains the number of epochs to use in training data set
     *
     * @return object with boolean denoting success
     */
    @PostMapping(TRAIN_URL)
    public TrainingResponseGui trainData(@RequestBody TrainingRequestGui request) {

        LOG.info("GET from GUI: trainData");
        return this.guiService.trainData(request);
    }
    
    /**
     * Evaluates data set
     *
     * @param request the testing image file and the size of an image in the file
     *
     * @return object with the accuracy, loss, and location of testing results
     */
    @PostMapping(EVALUATE_URL)
    public EvaluationReponseGui evaluateData(@RequestBody EvaluationRequestGui request) {

        LOG.info("GET from GUI: evaluateData");
        return this.guiService.evaluateImages(request);
    }

}
