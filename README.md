# DeepLearning
Project to detect clothing items using Deep Learning

The processing algorithms for this project are found in the DeepLearner project. 
It is run at server startup with the unix learning service
This is a python project which uses the keras/tensorflow APIs to compute Deep Learning on the TX2 GPU.
It listens for request from the java webserver using activemq. This is achieved with the STMOP protocol.
It also sends responses back using the same STOMP / activemq.
The main services provided are:
1. Load data (this is the built in fashion data set)
2. Create model / train data. The model is created with the 60K training images and class names.
3. Evaluation of test data. 10K test images are classified with the algorithm. In general, I am getting about 87% accuracy.

The webserver for this project is found in LearningWebServer.
It is started with the demo unix command.
This is a spring boot application which sets up a REST interface with the GUI.
This app also handles all internal server messaging with its ActiveMQ API.
This app also converts all the byte arrays received from the python app and writes output as .png files which are read by the gui.
The demonstration begins at server startup. The output files are located in /usr/learner/ (all png files)

The GUI for this project is found in LearningGui
This is an angular front end application which is automatically served up by the java webserver.
Connect to it just by typing the TX2 external IP address in the browser (after the demo command has been run)
From the GUI, you can Load Data, Train Data, and Evaluate Test Data. 
You can view the raw Training and Testing Data with this GUI.
Finally, after running the evaluation routine, you can view the Evaluation results.
All pics correctly classified have a green border, and all pics incorrectly classified have a red border. 
The accuracyy is about 87%.

The webserver and python logs can be found at /home/learner/webserver.log and /home/learner/python_learning.log
If desired, you can modify the webserver file paths and training epochs at /home/learner/LearningWebServer/src/main/resources/application.properties
Rebuild the webserver with the following 2 commands:
1. cd /home/learner/LearningWebServer/
2. mvn clean install

The main software items installed to the TX2 to facillitate the demo are maven (build), apcahe2 (web server), and activemq (messaging)

A lot of the GUI and webserver stuff was just to demonstrate some of my strengths. I have written a few of these types of applications
in the last couple of years.

The keras / tensorflow stuff was new to me. I had a lot of fun learning with it! I was able to confirm that everything is running 
on the GPU.

In the future I would optimize the app by allowing users to select different learning models, selecting meta-data such as epochs,
and also of course make the actual data selectable so we aren't running of of canned data provided from the software.
I would also like to implement new algorithms such as the GANS deep learning algorithm.

Here are some resources I used to construct the app:

https://www.tensorflow.org/tutorials/keras/basic_classification (provided the basic idea of the project as well as keras / tensorflow
usage)

https://www.dyclassroom.com/image-processing-project/how-to-create-a-random-pixel-image-in-java (copied some code from 
here in order to generate png files from python byte files)

https://simplesassim.wordpress.com/2014/03/14/how-to-send-a-message-to-an-apache-activemq-queue-with-python/
https://simplesassim.wordpress.com/2014/03/14/how-to-receive-a-message-from-an-apache-activemq-queue-with-python/
I used these links to figure out the python / ActiveMQ / STOMP interface

I have lots to learn with deep learning, but I had a great time with this!
