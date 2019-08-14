#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import tensorflow as tf
from tensorflow import keras

class LearningService:
    def __init__(self):
        self.class_names = None
        self.model = None        
        self.train_images = None
        self.train_labels = None
        self.test_images = None
        self.test_labels = None
        
        self.trainImageLocation = '/home/learner/trainimages.dat'
        self.trainLabelLocation = '/home/learner/trainlabels.dat'
        self.testImageLocation = '/home/learner/testimages.dat'
        self.testLabelLocation = '/home/learner/testlabels.dat'
        
    def getModel(self):
        return self.model
    
    def getTrainingImages(self):
        return (self.train_images, self.train_labels)
    
    def getTestImages(self):
        return (self.test_images, self.test_labels)
    
    def getFileLocations(self):
        return (self.trainImageLocation, self.trainLabelLocation,
                self.testImageLocation, self.testLabelLocation)
    
    def loadData(self):
        print('Loading Data')
        fashion_mnist = keras.datasets.fashion_mnist
        (train_images, self.train_labels), (test_images, self.test_labels) = fashion_mnist.load_data()        
        
        print('Saving Files')
        train_images.astype('int16').tofile(self.trainImageLocation)
        self.train_labels.astype('int16').tofile(self.trainLabelLocation)
        test_images.astype('int16').tofile(self.testImageLocation)
        self.test_labels.astype('int16').tofile(self.testLabelLocation)
        
        self.train_images = train_images / 255.0
        self.test_images = test_images / 255.0
        print('Finished Loading Data')
        
        trainImagesSize = len(train_images[0])
        
        testImagesSize = len(test_images[0])
        
        self.class_names = ['T-shirt/top', 'Trouser', 'Pullover', 'Dress', 'Coat',
               'Sandal', 'Shirt', 'Sneaker', 'Bag', 'Ankle boot']
        
        return (self.trainImageLocation, self.trainLabelLocation,
                self.testImageLocation, self.testLabelLocation, 
                trainImagesSize, testImagesSize, self.class_names)
        
    def defineModel(self):
        print('Defining Model')
        self.model = keras.Sequential([
                keras.layers.Flatten(input_shape=(28, 28)),
                keras.layers.Dense(128, activation=tf.nn.relu),
                keras.layers.Dense(10, activation=tf.nn.softmax)
                ])
            
        self.model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])
        
    def trainData(self, numEpochs):
        print('Training Data using epoch value: ', numEpochs)
        self.model.fit(self.train_images, self.train_labels, epochs=numEpochs)

class PredictionService:
    def __init__(self, learningService):
        self.ls = learningService
        self.images = None
        self.resultsLocation = '/home/learner/results.dat'
        
    def setImages(self, images):
        print('Setting Images')
        self.images = images        
        
    def evaluateResults(self):
        print('Evaluating Results')
        (test_images, test_labels) = self.ls.getTestImages()
        test_loss, test_acc = self.ls.getModel().evaluate(self.images, test_labels)
        predictions = self.ls.getModel().predict(self.images)
        predictions.astype('float64').tofile(self.resultsLocation)
        
        return (test_loss, test_acc)
        
    def getResultsLocation(self):
        return self.resultsLocation
        

