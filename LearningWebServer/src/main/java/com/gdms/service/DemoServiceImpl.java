package com.gdms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gdms.messaging.LearningJmsSender;
import com.gdms.messaging.model.EvaluationResponse;
import com.gdms.messaging.model.LearningMessageTypes;
import com.gdms.messaging.model.LearningResponse;
import com.gdms.messaging.model.LoadResponse;
import com.gdms.repository.LearningResponseRepository;
import com.gdms.util.ByteUtil;
import com.gdms.util.ImageUtil;
import com.gdms.util.ServiceUtil;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
	
	static final Logger LOG = LoggerFactory.getLogger(DemoServiceImpl.class);
	
	@Value("${learning.response-timeout}")
    private int TIMEOUT;
	
	@Value("${learning.epochs}")
    private int EPOCHS;
	
	@Value("${learning.image-path}")
    private String IMAGE_PATH;
	
	@Value("${learning.testing-file}")
    private String TEST_FILE;
	
	@Value("${learning.training-file}")
    private String TRAINING_FILE;
	
	@Value("${learning.pics-per-row}")
    private int PICS_PER_ROW;
	
	@Autowired
	private LearningJmsSender sender;
	
	@Autowired
	private LearningResponseRepository responseRepository;
	
	@Autowired
	private ServiceUtil serviceUtil;

	@Override
	public boolean runDemo() {
		
		this.responseRepository.clearResponses();
		
		LOG.info("Sending Request to load data");
		this.sender.sendLoadRequest();
		
		LoadResponse loadResponse = (LoadResponse) this.serviceUtil.getResponse(LearningMessageTypes.LOAD_DATA);
		
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
		
		String trainingFile = IMAGE_PATH + TRAINING_FILE;
		LOG.info("Writing training pictures to file: " + trainingFile);
		ImageUtil.writeImage(trainingImages, trainingFile, PICS_PER_ROW);		
		
		LOG.info("Sending request to train data");
		this.sender.sendTrainRequest(EPOCHS);
		
		LearningResponse trainingResponse = this.serviceUtil.getResponse(LearningMessageTypes.TRAIN_DATA);
		
		if (trainingResponse == null) {
			return false;
		}
		
		LOG.info("Got training response: " + trainingResponse);
		
		LOG.info("Sending evaluation request");
		this.sender.sendEvaluationRequest(loadResponse.getTestingImages(), testingImages.length, 
				loadResponse.getTestImagesSize());
		
		EvaluationResponse evaluationResponse = (EvaluationResponse) this.serviceUtil.getResponse(LearningMessageTypes.EVALUATE_IMAGES);
		
		if (evaluationResponse == null) {
			return false;
		}
		
		LOG.info("Got evaluation response: " + evaluationResponse);
		
		double accuracy = evaluationResponse.getAccuracy();
		double loss = evaluationResponse.getLoss();
		
		String resultsFile = evaluationResponse.getLocation();
		byte[] resultBytes = ByteUtil.readBytes(resultsFile);
		double[][] results = ByteUtil.convertResultBytes(resultBytes, classNames.length);
		int[] resultLabels = this.serviceUtil.getResultLabels(results);
		
		int badResults = 0;
		for (int i=0; i<resultLabels.length; i++) {
			if (testingLabelsNumbers[i] != resultLabels[i]) {
				badResults++;
				String badLabel = this.serviceUtil.getLabel(resultLabels, i, classNames);
				String goodLabel = this.serviceUtil.getLabel(testingLabelsNumbers, i, classNames);
				LOG.error("Bad prediction at index: " + i + ", Prediction: " + badLabel + ", Actual: " + goodLabel);
			}
		}
		
		double goodResults = resultLabels.length - badResults;
		double numResults = (double) resultLabels.length;
		double actualAccuracy = goodResults / numResults;
		
		LOG.info("Predicted Accuracy / Loss: " + accuracy + " / " + loss);
		LOG.info("Actual Accuracy: " + actualAccuracy);
		
		String testFile = IMAGE_PATH + TEST_FILE;
		LOG.info("Writing testing pictures to file: " + testFile);
		ImageUtil.writeImage(testingImages, testFile, PICS_PER_ROW, testingLabelsNumbers, resultLabels, classNames);
		
		return true;
		
	}

}
