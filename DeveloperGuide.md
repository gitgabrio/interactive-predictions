Developer Guide
===============

Overall Components
------------------
The main *functional* components of the Interactive Prediction System are
1. the prediction engine
2. the explainability engine
3. the Dialogue engine
4. the User Interface

**Prediction engine** is the component that actually receives some input data and submit them to a prediction model, returning the predicted vale

**Explainability engine** is the component that submit a series of input to the **Prediction engine** and returns an *explanation* of the meaning of the results

**Dialogue engine** is the component that provides an iterative *dialogue* between the final user and the **Prediction engine**

**User interface** is the visual component directly used by the final user to interact with the system.

Design Principles
-----------------

The application will be developed in an iterative way, featuring modules that already exists (e.g. *Prediction* and *Explainability*) or creating new from scratch (*Interaction* and *UI*).
All this component must be decoupled as much as possible, to allow for easy maintenance and refactoring. If ever needed, moving modules to different repositories, or also replacing completely some of them, should be easy and possible without any impact on the others.
The very first implementation will be a monolithic application, but the possibility to switch to a different style (i.e. microservices) must be kept in mind from the very beginning, and that's another reason for keeping the components decoupled.
Another aspect to keep in mind from the beginning is that the first implementations may be thrown away (see Throw-away architecture) if requests or needs changes. To avoid wasting the already/done work, code must be well encapsulated (see SOLID principles) to allow as much as possible reuse.
An already planned future refactoring regard the **User interface**: the first implementation will be a textual, old-RPG-style console, that will present the user with a series of pre-defined *questions*. Further on, that interface will be improved both on visual terms (i.e. graphical interface), on design term (i.e. web interface) and on functionality term (i.e. Natural-Language based interaction); possibly, also voice-recognition engine could be included, at later stage. 

The "Clean Architecture" principles will be followed to achieve that. The **core** components are the ones that actually perform the business-logic (e.g. **Prediction**); beside them, there are some services whose responsibility is to allow interaction between users and business-logic and, indirectly, between other services. 

The main goal to pursue is to have only *inwards* relationship between components, i.e. the **core** components should not have any *knowledge* of services built around them; at the same time, different services should not have any *knowledge* between them. This will be realized defining a series of *interfaces* in the core api, and it is through them that different services/implementations will communicate.
The simplest way to verify and keep that requirement under control is to check in the **pom**s of the modules if any unwanted dependency leak from one module to another.

Conventions
-----------
To avoid name clashing, all DTO should be prefixed with *IP* and, possibly, end with a word describing the context in which they are used; e.g.

1. IPInputPrediction: the input for the prediction engine
2. IPOutputExplainability: output produced by explainability engine

Whenever possible, use immutable classes.
It should be better to prefer a *functional-style* approach, whenever possible. This does not mean necessary to use only functions and lambda, but also *static* method from *stateless* classes.


Bean discovery
--------------

The modules in this application should be framework-agnostic, i.e. they should not depend on any IoC container (Spring, Quarkus). Implementations discovery should be demanded to plain-java SPI. 





