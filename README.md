# Project:
This framework tests weather conditions using Rest APIs.

## Tools and Frameworks used

* BDD cucumber
* TestNG integration
* Cucumber Json reports
* log4j 

## Libraries Used:

* RestAssured
* Java
* TestNG 
* Maven
* Cucumber
* log4j


## Prerequisites Installations:

* JAVA 1.8 - Install Java and set the JAVA_HOME path on your machine.
* Rest Assured library
* Logging
* Maven
* Cucumber -html -report


## How This Framework Works:

We have cucumber "TestRunner" file which has links to test features and step definitions.
It can also pick only specified test tags for running.
* Test uses global properties specified in config.properties file
* Here are the minimal things you have to do:
* Create your tests
* Create your Page Object class w.r.t test that you have written, if not created already (Take the reference from src/test/java/Features.



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
1.As a surfer, for I will get a list of short listed beaches on a given day
```


## Error Prone Scenarios

```
1. 	UV Index value being negative
2. 	Day of the week misspelled

```

## Documentation at

*https://github.com/sangeeta-chakalabbi/RESTAssured*


