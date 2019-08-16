package com.gdms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class aids the user in manipulating application.properties spirit properties
 *
 */
@Configuration
@ConfigurationProperties("learning")
public class LearningConfigProperties {
    
    /**
     * The number of seconds before timing out for a response
     */
    private int responseTimeout;
    
    /**
     * The number of epoch revolutions to try in Deep Learning Fit
     */
    private int epochs;
    
    /**
     * File path to save all images
     */
    private String imagePath;
    
    /**
     * URL for GUI to access images
     */
    private String imageUrl;

    /**
     * File name to save training images
     */
    private String trainingFile;

    /**
     * File name to save testing images
     */
    private String testingFile;
    
    /**
     * File name to save results images
     */
    private String resultsFile;

    /**
     * The number of pictures to write per row
     */
    private int picsPerRow;

	public int getResponseTimeout() {
		return responseTimeout;
	}

	public void setResponseTimeout(int responseTimeout) {
		this.responseTimeout = responseTimeout;
	}

	public int getEpochs() {
		return epochs;
	}

	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}

	public String getTrainingFile() {
		return trainingFile;
	}

	public void setTrainingFile(String trainingFile) {
		this.trainingFile = trainingFile;
	}

	public String getTestingFile() {
		return testingFile;
	}

	public void setTestingFile(String testingFile) {
		this.testingFile = testingFile;
	}

	public int getPicsPerRow() {
		return picsPerRow;
	}

	public void setPicsPerRow(int picsPerRow) {
		this.picsPerRow = picsPerRow;
	}

	public String getResultsFile() {
		return resultsFile;
	}

	public void setResultsFile(String resultsFile) {
		this.resultsFile = resultsFile;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
    
}
