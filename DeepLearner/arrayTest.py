#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Tue Aug 13 15:34:33 2019

@author: learner
"""

from tensorflow import keras
import numpy as np

fashion_mnist = keras.datasets.fashion_mnist

(train_images, train_labels), (test_images, test_labels) = fashion_mnist.load_data()
fileLocation = "/home/learner/testFile.dat"
train_images.astype('int16').tofile(fileLocation)

images = np.fromfile(fileLocation,  dtype=np.int16)
images = images.reshape(60000, 28, 28)
