# Interactive Predictions
Interactive predictions is a project aimed at improving interaction between final users and predition models.
It follows suggestion from the following papers:
  1. [The Mythos of Model Interpretability](https://dl.acm.org/doi/pdf/10.1145/3236386.3241340)
  2. [Rethinking Explainability as a Dialogue: A Practitioner’s Perspective](https://arxiv.org/pdf/2202.01875.pdf)

and it is specifically aimed at medical usage.

The main goal of the model interaction is to provide a better understanding, from the final user point of view, of the reason behind a given prediction, allowing an iterative interaction between user and the prediction engine.
The main components of this approach are:
  1. the prediction engine itself
  2. the user interface, that must provide an easy way to interact with the former

## Prediction engine
The prediction engine is based on a prediction model, and should be able to
  1. provide predictions based on given input data
  2. provide further predictions based on modification of original data
  3. provide "explanation" about returned prediction

## User Interface
The user interface should be able to:
  1. accept input data from user
  2. send data to prediction engine and returns predicted value
  3. allow further refinement of input data and show eventual "explanation"

The original paper suggested the use of Natural Language Processing to ease as much as possible the use interaction. While this is the final goal, it must be acknoledge that it is a big task by itself, involving ML usage and tweaking. The current project split this requirement in two basic step:
  1. implement a textual driven interaction (ancient role-play style) where the user interface provide a list of possible "question" that the user may send to the prediction engine
  2. implement a Natural Language based interaction, with keyboard input
  3. eventually improve it with voice-recognition engines



