# Project:
This framework tests weather conditions using Rest APIs.

## Tools and Frameworks used
```
* BDD cucumber
* TestNG integration
* Cucumber Json reports
* log4j 
```

## Libraries Used:

* RestAssured
* Java
* TestNG 
* Maven
* Cucumber
* log4j


## Prerequisites Installations:
```
* JAVA 1.8 - Install Java and set the JAVA_HOME path on your machine.
* Rest Assured library
* Logging
* Maven
* Cucumber -html -report
```

## How This Framework Works:

We have cucumber "TestRunner" file which has links to test features and step definitions.
It can also pick only specified test tags for running.
* Test uses global properties specified in config.properties file
* Here are the minimal things you have to do:
* Create your tests
* Create your Page Object class w.r.t test that you have written, if not created already (Take the reference from src/test/java/Features.


## Ways to invoke the tests
1- From ide you can invoke the TestRunner file which has links to feature file and the corresponding step definition.
![](https://github.com/sangeeta-chakalabbi/RESTAssuredTest/blob/main/mediaFiles/InvokeFromIDE.png)

2- From command line on either Windows or Linux, one could use maven command.
To maven many different kinds of options can be passed a sample one is given here. Go through maven tutorials for more https://maven.apache.org/ref/3.1.0/maven-embedder/cli.html

=> mvn test —Durl =“http://api.weatherbit.io/v2.0/forecast/daily”  Dcucumber.options="--tags @Regression"



## Reporting and Logging
**Reporting Path**
```
./target/report/cucumber-html-reports/
```
![](https://github.com/sangeeta-chakalabbi/RESTAssuredTest/blob/main/mediaFiles/Report.png)


**Logging Path**
```
./src/test/java/Logs/ApplicationLog
```
![](https://github.com/sangeeta-chakalabbi/RESTAssuredTest/blob/main/mediaFiles/Logs.png)


## Assumptions

```
1.	User wants would like to surf only for 2 days a week
2.	Sunday and Saturday is the only non working day
3.	The tear up and tear down information will also appear on logs
4.   The short listed beaches appear in the log
5.  Week is specified in the full Name and not short form likes Mon 
```

## Happy Path

```
1.As a surfer, I get a list of short listed beaches to consider on a given day
```


## Error Prone Scenarios

```
1. 	UV Index value being negative
2. 	Day of the week misspelled

```

## Challenges faced and solutions

```
1. 	To come up with Step definitions which can be re-used
2.   Coming up with design to encapsulate TestInput and OutPut.
	Solution - Create different classes to hold testInput (weather criteria) and testOutput (short listed beach details) 
2. 	To process the JSON reply in an efficient time saving manner.
    Solution: Used JsonPath to filter out unwanted JSON entries (this increased efficiency by making sure there is now much smaller data to parse through for the test suite)
    Solution2 - Used POJO (Plane Old Java Objects) to hold json responses', key value pairs

```


## Documentation at

*https://github.com/sangeeta-chakalabbi/RESTAssuredTest	*


