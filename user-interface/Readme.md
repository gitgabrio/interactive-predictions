User Interface
==============

The current implementation offers both a CLI and a GUI interface.

To use CLI, simply start the *org.kie.interactivepredictions.user.itf.Main* class.

To start the GUI, issue 

    `mvn clean javafx:run`

from `user-interface` directory

To start the GUI in debug mode, issue

    `mvn clean javafx:run@debug`

from `user-interface` directory, and then attach a remote-debug to port 8000


HEART_TREE model
================

The **HEART_TREE.pmml** is a slightly modified version of [HEART_TREE.xml](https://dmg.org/pmml/pmml_examples/archive/HEART_TREE.xml) available from dmg archive.

Original data comes from the [Heart Disease Data Set](http://archive.ics.uci.edu/ml/datasets/Heart+Disease) and may be found [here](http://archive.ics.uci.edu/ml/machine-learning-databases/heart-disease/).
[heart-disease.names](./src/main/resources/hearttree/heart-disease.names) contains explanation and description of all the possible fields.


