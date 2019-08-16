package com.gdms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gdms.messaging.model.LearningMessageTypes;
import com.gdms.messaging.model.LearningResponse;
import com.gdms.repository.LearningResponseRepository;

@Component
public class ServiceUtil {
	
	static final Logger LOG = LoggerFactory.getLogger(ServiceUtil.class);
	
	@Value("${learning.response-timeout}")
    private int TIMEOUT;
	
	@Autowired
	private LearningResponseRepository responseRepository;

	public LearningResponse getResponse(LearningMessageTypes type) {

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

	public String getLabel(int[] labels, int index, String[] classNames) {

		return classNames[labels[index]];

	}

	public int[] getResultLabels(double[][] results) {

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
