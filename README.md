# Burn Plan Evaluation App

The Burn Plan Evaluation App assesses suggested burn plans to determine their viability, based on available supplies, land fuel types, and weather forecasts. The application follows the Prescribed Burning Procedures to guarantee safe and efficient burning practices and retrieves weather information using the OpenWeather REST API. In order to assist users in making well-informed decisions regarding burn plans.

Project Status: **feature-complete, fully tested, no test failures, and no known
bugs**

Authors: 
* Narayana Pujara <npujara2@unl.edu>
* Jake Sanford <jsanford28@unl.edu>
* Grant <grohrbaugh3@unl.edu>
* Dan Vo <dvo3@unl.edu>

## Dependencies 

The Burn Plan Evaluation app depends on -
 
1. the OpenWeather REST connector
(git@git.unl.edu:soft-core/soft-160/openweather-rest-and-file-connector.git).

The REST connector must be in the project's build path during development and
the project's classpath when the app is run.

2. Testing Framework 

* JUnit 4 is required to run the project's unit tests.

## Building and Running the project 

You can clone the project using the following link - 

`git@git.unl.edu:soft-core/soft-160/semesters/fall2024/capstone/capstone-group07.git`

After performing the above step follow these steps - 

1. Set up the OpenWeather connector.

2. clone the OpenWeather Connector into your git folder (or another folder) 

`git@git.unl.edu:soft-core/soft-160/openweather-rest-and-file-connector.git`

on your virtual machine. Do not clone it into your project repository. Do not copy the OpenWeather Connector's files into your project repository.
3. From IntelliJ IDEA's hamburger menu, select `File` → `Project Structure...`
4. In the Project Structure Window → Under `Project Settings`, select `Modules`
5. Click on the `+`to add a module.
6. Select `Import Module`
7. Select `/home/username/git/openweather-rest-and-file-connector` and click `OK`.
In the "Import Module" window, Select `Import module from external model` and select `Maven.` Click `Create`.
8. in the "Project Structure" window, click `OK`.
9. After several seconds, `openweather-rest-and-file-connector` will be added to the project as a module.
10. Note that at a future date, when you start to use the classes defined in the OpenWeather Connector, you will need to `Add dependency on module 'rest_and_file_connector'`

After performing the all the above steps you can run the program by clicking on the play button on the top-right part of intelliJ present in - 

`BurnPlanEvaluationApp.java`

## Software Architecture 

The Burn Plan Evaluation App is organized into three tiers: 

1. **REST Backend:** 

*Connects to OpenWeather and retrieves forecasts data. 
*Consists of JSON classes that help in providing data, are titled as `redflag.json` & `weather.json` in the forecast folder present in - `src/main/resources/forecasts`
2. **Burn Plan Evaluation:** Implements the Prescribed Burning Procedures algorithm to evaluate plans, present in the file - 
* `BurnplanEvaluationAlgorithm.java`
3. **CLI Interface:** Handles user interaction and report generation, present in the file and it contains the main method - 
* `BurnPlanEvaluationApp.java`

## Class Diagram

Our team created a class diagram for this project titled as `Classes.png` in the `documentation` folder.

## Test & Test Results 

The Burn Plan Evaluation app has been verified with automated unit tests
of the Burn Plan Evaluation algorithm. Tests were designed using the specifications provided to derive categories and partitions to support category-partition testing. The test suites were then augmented to achieve 100% code coverage. The screenshot of the code coverage of test results is titled as `burnplanEvaluationAlgorithmCoverage.png`, which is in the `documentation` folder.

The approach we used to design test cases was the category partition method by which we made some categories(such as supplies, wind speeds, fuel types, and red flag conditions, etc..) to keep in mind while creating the tests 

Unit tests are available in five classes in the `test` folder:

* `BackfireTest.java`
* `BlacklineTest.java`
* `CommonTest.java`
* `EvaluateSuppliesTest.java`
* `HeadfireTest.java`

The unit tests require JUnit 4.

The system tests do not require any special tools or software.

No UI testing, security testing, or load/stress testing was performed for this
milestone.

The app is passing all unit-level. The unit-level tests achieve 100% statement coverage of the code developed by our team. 
