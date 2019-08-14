#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Mon Aug 12 16:50:40 2019

@author: learner
"""

import Services as services
import Messaging as messaging
import time

print("STARTING DEEP LEARNING APPLICATION")

learningService = services.LearningService()
predictionService = services.PredictionService(learningService)

messenger = messaging.Messenger(learningService, predictionService)

print("DEEP LEARNING APPLICATION INITIALIZED")
while messenger.isRunning():
    time.sleep(1) 

print("CLOSING MESSENGER")
messenger.close()

print("FINISHED")
