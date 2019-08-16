#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Aug 12 16:51:19 2019

@author: learner
"""

import stomp
import json
import numpy as np 

class LearningListener(object):
    
    def __init__(self, learningService, predictionService, messenger):
        self.learningService = learningService
        self.predictionService = predictionService
        self.messenger = messenger    
    
    def on_message(self, headers, msg):
        
        print(headers)
        print(msg)
          
        mappedObject = json.loads(msg)
        
        responseHeader = {"myLocalFactory": 'com.gdms.messaging.model.LearningResponse'}
        
        messageType = None
        if 'type' not in mappedObject:
            print('No Type in Message')
            returnObject = {"type": 'UNKNOWN',"status": False}
            self.messenger.send('learning-response', returnObject)
            return
        
        messageType = mappedObject['type']
        print('message type: ', messageType)    
        
        returnObject = {"type": messageType,"status": False}
        
        if messageType == "TERMINATION":
            self.messenger.setRunning(False)
            returnObject.update( {'status' : True} )
        elif messageType == "LOAD_DATA":
            (trainImageLocation, trainLabelLocation,
                testImageLocation, testLabelLocation, 
                trainingImagesSize, testImagesSize,
                classNames) = self.learningService.loadData()
            returnObject.update( {'status' : True} )
            
            returnObject.update( {'trainingImages' : trainImageLocation} )
            returnObject.update( {'trainingLabelImages' : trainLabelLocation} )
            returnObject.update( {'testingImages' : testImageLocation} )
            returnObject.update( {'testingLabelImages' : testLabelLocation} )
            
            returnObject.update( {'trainingImagesSize' : trainingImagesSize} )
            returnObject.update( {'testImagesSize' : testImagesSize} )
            returnObject.update( {'classNames' : classNames} )
            
            responseHeader.update( {"myLocalFactory": 'com.gdms.messaging.model.LoadResponse'} )
            
        elif messageType == "TRAIN_DATA":
            
            if 'epochs' not in mappedObject:
                print('No epochs in training data message')
                self.messenger.send('learning-response', returnObject)
                return
            
            ephochs = mappedObject['epochs']
            self.learningService.defineModel()
            self.learningService.trainData(ephochs)
            returnObject.update( {'status' : True} ) 
            
        elif messageType == "EVALUATE_IMAGES":
            
            if 'imageLocation' not in mappedObject:
                print('No imageLocation in EVALUATE_IMAGES message')
                self.messenger.send('learning-response', returnObject)
                return
            
            if 'numImages' not in mappedObject:
                print('No numImages in EVALUATE_IMAGES message')
                self.messenger.send('learning-response', returnObject)
                return
            
            if 'imageSize' not in mappedObject:
                print('No imageSize in EVALUATE_IMAGES message')
                self.messenger.send('learning-response', returnObject)
                return
            
            imageLocation = mappedObject['imageLocation']
            numImages = mappedObject['numImages']
            imageSize = mappedObject['imageSize']
            images = np.fromfile(imageLocation,  dtype=np.int16)
            images = images.reshape(numImages, imageSize, imageSize)
            self.predictionService.setImages(images)
            (test_loss, test_acc) = self.predictionService.evaluateResults()
            resultsLocation = self.predictionService.getResultsLocation()
            
            returnObject.update( {'status' : True} )
            returnObject.update( {'location' : resultsLocation} )
            returnObject.update( {'loss' : str(test_loss)} )
            returnObject.update( {'accuracy' : str(test_acc)} )
            
            responseHeader.update( {"myLocalFactory": 'com.gdms.messaging.model.EvaluationResponse'} )
            
        else:
            print('Unrecognized message type: ', messageType)
            
        self.messenger.send('learning-response', returnObject, responseHeader)

class Messenger:
    def __init__(self, learningService, predictionService):
        
        self.learningService = learningService
        self.predictionService = predictionService
        self.connection = stomp.Connection10()
        
        listener = LearningListener(learningService, predictionService, self)
        
        self.connection.set_listener('LearningListener', listener)
        self.connection.start()
        self.connection.connect()
        self.connection.subscribe('learning-queue')
        
        self.running = True
        
        
    def close(self):
        self.connection.disconnect()
        
    def send(self, queueName, messageObject, headerObject):
        message = json.dumps(messageObject)
#        header = json.dumps(headerObject)
        self.connection.send(queueName, message, headers=headerObject)
        
    def isRunning(self):
        return self.running
    
    def setRunning(self, running):
        self.running = running
        