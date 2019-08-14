package com.gdms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gdms.messaging.LearningJmsSender;
import com.gdms.model.EvaluationResponse;
import com.gdms.model.LearningMessageTypes;
import com.gdms.model.LearningResponse;
import com.gdms.model.LoadResponse;
import com.gdms.repository.LearningResponseRepository;
import com.gdms.util.ByteUtil;
import com.gdms.util.ImageUtil;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
	
	static final Logger LOG = LoggerFactory.getLogger(DemoServiceImpl.class);
	
	@Value("${learning.response-timeout}")
    private int TIMEOUT;
	
	@Value("${learning.epochs}")
    private int EPOCHS;
	
	@Value("${learning.training-file}")
    private String TRAINING_FILE;
	
	@Value("${learning.testing-file}")
    private String TEST_FILE;
	
	@Value("${learning.pics-per-row}")
    private int PICS_PER_ROW;
	
	@Autowired
	private LearningJmsSender sender;
	
	@Autowired
	private LearningResponseRepository responseRepository;

	@Override
	public boolean runDemo() {
		
		this.responseRepository.clearResponses();
		
		LOG.info("Sending Request to load data");
		this.sender.sendLoadRequest();
		
		LoadResponse loadResponse = (LoadResponse) this.getResponse(LearningMessageTypes.LOAD_DATA);
		
		if (loadResponse == null) {
			return false;
		}
		
		LOG.info("Got load response: " + loadResponse);
		
		String[] classNames = loadResponse.getClassNames();
		int trainingSize = loadResponse.getTrainingImagesSize();
		int testSize = loadResponse.getTestImagesSize();
		
//		byte[] trainingLabelBytes = ByteUtil.readBytes(loadResponse.getTrainingLabelImages());
		byte[] trainingImageBytes = ByteUtil.readBytes(loadResponse.getTrainingImages());
		byte[] testLabelBytes = ByteUtil.readBytes(loadResponse.getTestingLabelImages());
		byte[] testImageBytes = ByteUtil.readBytes(loadResponse.getTestingImages());
		
//		int[] trainingLabelsNumbers = ByteUtil.convertLabelBytes(trainingLabelBytes);
		int[] testingLabelsNumbers = ByteUtil.convertLabelBytes(testLabelBytes);
		
		int[][][] trainingImages = ByteUtil.convertImageBytes(trainingImageBytes, trainingSize, trainingSize);
		int[][][] testingImages = ByteUtil.convertImageBytes(testImageBytes, testSize, testSize);
		
		LOG.info("Writing training pictures to file: " + TRAINING_FILE);
		ImageUtil.writeImage(trainingImages, TRAINING_FILE, PICS_PER_ROW);		
		
		LOG.info("Sending request to train data");
		this.sender.sendTrainRequest(EPOCHS);
		
		LearningResponse trainingResponse = this.getResponse(LearningMessageTypes.TRAIN_DATA);
		
		if (trainingResponse == null) {
			return false;
		}
		
		LOG.info("Got training response: " + trainingResponse);
		
		LOG.info("Sending evaluation request");
		this.sender.sendEvaluationRequest(loadResponse.getTestingImages(), testingImages.length, 
				loadResponse.getTestImagesSize());
		
		EvaluationResponse evaluationResponse = (EvaluationResponse) this.getResponse(LearningMessageTypes.EVALUATE_IMAGES);
		
		if (evaluationResponse == null) {
			return false;
		}
		
		LOG.info("Got evaluation response: " + evaluationResponse);
		
		double accuracy = evaluationResponse.getAccuracy();
		double loss = evaluationResponse.getLoss();
		
		String resultsFile = evaluationResponse.getLocation();
		byte[] resultBytes = ByteUtil.readBytes(resultsFile);
		double[][] results = ByteUtil.convertResultBytes(resultBytes, classNames.length);
		int[] resultLabels = this.getResultLabels(results);
		
		int badResults = 0;
		for (int i=0; i<resultLabels.length; i++) {
			if (testingLabelsNumbers[i] != resultLabels[i]) {
				badResults++;
				String badLabel = this.getLabel(resultLabels, i, classNames);
				String goodLabel = this.getLabel(testingLabelsNumbers, i, classNames);
				LOG.error("Bad prediction at index: " + i + ", Prediction: " + badLabel + ", Actual: " + goodLabel);
			}
		}
		
		double goodResults = resultLabels.length - badResults;
		double numResults = (double) resultLabels.length;
		double actualAccuracy = goodResults / numResults;
		
		LOG.info("Predicted Accuracy / Loss: " + accuracy + " / " + loss);
		LOG.info("Actual Accuracy: " + actualAccuracy);
		
		LOG.info("Writing testing pictures to file: " + TEST_FILE);
		ImageUtil.writeImage(testingImages, TEST_FILE, PICS_PER_ROW, testingLabelsNumbers, resultLabels, classNames);
		
		return true;
		
	}
	
	private LearningResponse getResponse(LearningMessageTypes type) {
		
		LearningResponse response = null;
		int count = 0;
		while (count<TIMEOUT && response == null) {
			LOG.info("Waiting for Response from Deep Learning Software: " + count);
			
			response = this.responseRepository.getResponse(type.toString());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
			
			count++;
		}
		
		if (response == null) {
			LOG.error("Timeout waiting for response for: " + type);
		}
		
		return response;		
		
	}
	
	private String getLabel(int[] labels, int index, String[] classNames) {
		
		return classNames[labels[index]];
		
	}
	
	private int[] getResultLabels(double[][] results) {
		
		int[] resultLabels = new int[results.length];
		for (int i=0; i<results.length; i++) {
			resultLabels[i] = this.getMaxIndex(results[i]);
		}
		
		return resultLabels;
		
	}
	
	private int getMaxIndex(double[] array) {
		
		Double maxValue = Double.MAX_VALUE*-1;
		int saveIndex = -1;
		for (int i=0; i<array.length; i++) {
			if (array[i]>maxValue) {
				maxValue = array[i];
				saveIndex = i;
			}
		}
		
		return saveIndex;
		
	}

}
