package TestRunner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions (
			features = {"src/test/java/Features"},
			glue = {"StepDefinitions"},
			plugin = {
					  "json:target/cucumber-reports/Cucumber-report.json",
					  "junit:target/cucumber-reports/Cucumber-report.xml",
					  "html:target/cucumber-reports/Cucumber-report.html" ,
					  "usage"
					  },
			dryRun = false,
			tags="@Regression"
			
		)
public class TestRunner {

}
