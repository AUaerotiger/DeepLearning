package com.gdms.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gdms.controller.model.EvaluationReponseGui;
import com.gdms.controller.model.EvaluationRequestGui;
import com.gdms.controller.model.LoadResponseGui;
import com.gdms.controller.model.TrainingRequestGui;
import com.gdms.controller.model.TrainingResponseGui;
import com.gdms.messaging.LearningJmsSender;
import com.gdms.messaging.model.EvaluationResponse;
import com.gdms.messaging.model.LearningMessageTypes;
import com.gdms.messaging.model.LearningResponse;
import com.gdms.messaging.model.LoadResponse;
import com.gdms.repository.LearningResponseRepository;
import com.gdms.util.ByteUtil;
import com.gdms.util.ImageUtil;
import com.gdms.util.ServiceUtil;

@Service("guiService")
public class GuiServiceImpl implements GuiService {

	static final Logger LOG = LoggerFactory.getLogger(GuiServiceImpl.class);

	@Value("${learning.image-path}")
	private String IMAGE_PATH;

	@Value("${learning.image-url}")
	private String IMAGE_URL;

	@Value("${learning.testing-file}")
	private String TEST_FILE;

	@Value("${learning.training-file}")
	private String TRAINING_FILE;

	@Value("${learning.results-file}")
	private String RESULTS_FILE;	
	
	@Value("${learning.pics-per-row}")
    private int PICS_PER_ROW;
	
	@Autowired
	private LearningJmsSender sender;
	
	@Autowired
	private LearningResponseRepository responseRepository;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	private LoadResponse loadResponse = null;

	@Override
	public LoadResponseGui loadData() {

		this.responseRepository.clearResponses();

		LOG.info("Sending Request to load data");
		this.sender.sendLoadRequest();

		loadResponse = (LoadResponse) this.serviceUtil.getResponse(LearningMessageTypes.LOAD_DATA);

		if (loadResponse == null) {
			return new LoadResponseGui(false);
		}

		LOG.info("Got load response: " + loadResponse);
		
		int trainingSize = loadResponse.getTrainingImagesSize();
		int testSize = loadResponse.getTestImagesSize();

		byte[] trainingImageBytes = ByteUtil.readBytes(loadResponse.getTrainingImages());		
		byte[] testImageBytes = ByteUtil.readBytes(loadResponse.getTestingImages());		

		int[][][] trainingImages = ByteUtil.convertImageBytes(trainingImageBytes, trainingSize, trainingSize);
		int[][][] testingImages = ByteUtil.convertImageBytes(testImageBytes, testSize, testSize);

		String trainingFile = IMAGE_PATH + TRAINING_FILE;
		LOG.info("Writing training pictures to file: " + trainingFile);
		ImageUtil.writeImage(trainingImages, trainingFile, PICS_PER_ROW);	
		
		String testingFile = IMAGE_PATH + TEST_FILE;
		LOG.info("Writing testing images to file: " + testingFile);
		ImageUtil.writeImage(testingImages, testingFile, PICS_PER_ROW);
		
		String guiTrainingFile = IMAGE_URL + "/" + TRAINING_FILE;
		String guiTestingFile = IMAGE_URL + "/" + TEST_FILE;
		
		return new LoadResponseGui(guiTrainingFile, guiTestingFile, trainingSize, testSize, true);
	}

	@Override
	public TrainingResponseGui trainData(TrainingRequestGui request) {
		
		LOG.info("Sending request to train data");
		this.sender.sendTrainRequest(request.getEphochs());
		
		LearningResponse trainingResponse = this.serviceUtil.getResponse(LearningMessageTypes.TRAIN_DATA);
		
		if (trainingResponse == null) {
			return new TrainingResponseGui(false);
		}
		
		LOG.info("Got training response: " + trainingResponse);
		return new TrainingResponseGui(true);
	}

	@Override
	public EvaluationReponseGui evaluateImages(EvaluationRequestGui request) {
		
		int imageSize = request.getImageSize();
//		String imageFile = request.getTestFile();
		
		byte[] testImageBytes = ByteUtil.readBytes(loadResponse.getTestingImages());
		int[][][] testingImages = ByteUtil.convertImageBytes(testImageBytes, imageSize, imageSize);
		
		LOG.info("Sending evaluation request");
		this.sender.sendEvaluationRequest(loadResponse.getTestingImages(), testingImages.length, 
				imageSize);
		
		EvaluationResponse evaluationResponse = (EvaluationResponse) this.serviceUtil.getResponse(LearningMessageTypes.EVALUATE_IMAGES);
		
		if (evaluationResponse == null) {
			new EvaluationReponseGui(false);
		}
		
		LOG.info("Got evaluation response: " + evaluationResponse);
		
		double accuracy = evaluationResponse.getAccuracy();
		double loss = evaluationResponse.getLoss();
		String resultsFile = evaluationResponse.getLocation();
		
		
		String[] classNames = loadResponse.getClassNames();
		byte[] testLabelBytes = ByteUtil.readBytes(loadResponse.getTestingLabelImages());
		int[] testingLabelsNumbers = ByteUtil.convertLabelBytes(testLabelBytes);
		byte[] resultBytes = ByteUtil.readBytes(resultsFile);
		double[][] results = ByteUtil.convertResultBytes(resultBytes, classNames.length);
		int[] resultLabels = this.serviceUtil.getResultLabels(results);
		
		int badResults = 0;
		List<String> expected = new ArrayList<>();
		List<String> actual = new ArrayList<>();
		for (int i=0; i<resultLabels.length; i++) {
			if (testingLabelsNumbers[i] != resultLabels[i]) {
				badResults++;
				String badLabel = this.serviceUtil.getLabel(resultLabels, i, classNames);
				String goodLabel = this.serviceUtil.getLabel(testingLabelsNumbers, i, classNames);
				LOG.debug("Bad prediction at index: " + i + ", Prediction: " + goodLabel + ", Actual: " + badLabel);
				
				expected.add(goodLabel);
				actual.add(badLabel);
			}
		}
		
		double goodResults = resultLabels.length - badResults;
		double numResults = (double) resultLabels.length;
		double actualAccuracy = goodResults / numResults;
		
		LOG.info("Predicted Accuracy / Loss: " + accuracy + " / " + loss);
		LOG.info("Actual Accuracy: " + actualAccuracy);
		
		String resultsImageFile = IMAGE_PATH + RESULTS_FILE;
		LOG.info("Writing Results pictures to file: " + resultsImageFile);
		ImageUtil.writeImage(testingImages, resultsImageFile, PICS_PER_ROW, 
				testingLabelsNumbers, resultLabels, classNames);
		
		String location = IMAGE_URL + "/" + RESULTS_FILE;
		return new EvaluationReponseGui(accuracy, loss, location, expected, actual, true);
				
	}

}
