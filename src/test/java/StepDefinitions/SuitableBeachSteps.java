package StepDefinitions;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import POJO.Data;
import POJO.BeachWeather;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.parsing.Parser;
import io.restassured.specification.ResponseSpecification;
import Utilities.BeachCriteria;
import Utilities.BeachDetails;
import Utilities.ConstantsUsedHere;
import Utilities.Helpers;

public class SuitableBeachSteps {

	BeachCriteria beachCriteria = new BeachCriteria();
	Helpers helper = new Helpers();

    static final Logger log = Logger.getLogger(SuitableBeachSteps.class);

	
	@Given("I like to consider two among the top ten beaches in Sydney for the next {string}")
	public void I_like_to_consider_two_among_the_top_ten_beaches_in_sydney_for_the_next(String numberOfDays) {
		beachCriteria.setNumberOfDays(numberOfDays);

	}

	@Given("I check if the temperature is between {string} and {string}")
	public void i_check_if_the_temperature_is_between_and(String minTemp, String maxTemp) {
		beachCriteria.setMinTemp(minTemp);
		beachCriteria.setMaxTemp(maxTemp);

	}

	@Given("I check wind speed to be between {string} and {string}")
	public void i_check_wind_speed_to_be_between_and(String minWindSpeed, String maxWindSpeed) {
		
		beachCriteria.setMaxWindSpeed(maxWindSpeed);
		beachCriteria.setMinWindSpeed(minWindSpeed);

	}

	@Given("I check to see if UV index is less than or equal to {string}")
	public void i_check_to_see_if_uv_index_is(String uvIndexNumber) {
		beachCriteria.setUvIndexNumber(uvIndexNumber);

	}

	@Given("I like to surf on only {string} and {string}")
	public void i_like_to_surf_on_only_and(String preferredDay1, String preferredDay2) {
		beachCriteria.setPreferredDay1(preferredDay1);
		beachCriteria.setPreferredDay2(preferredDay2);

	}

	@When("I look up the the weather forecast for the above preference for the following post codes")
	public void i_look_up_the_the_weather_forecast_for_the_above_preference_for_the_following_postal_codes(DataTable dataTable) {
		
		helper.filterOutSuitableBeachesBasedOnTheCriteria(beachCriteria, dataTable);

	}

	@When("I can get the list of suitable beaches against each chosen day")
	public void I_can_get_the_list_of_suitable_beaches_against_each_chosen_day() {
		
		;

	}

	@When("I can view the coordinates of the shortlisted beaches")
	public void i_can_view_the_coordinates_of_the_shortlisted_beaches() {
		
		List<BeachDetails> beachDetails = helper.viewCordinates(beachCriteria);
		
		if(beachDetails.size() == 0) {
			Assert.fail("Cannot find any beaches which means the given criteria");
		}
		
		
		for(BeachDetails beachDetail : beachDetails) {
			log.info("Beach Post Code   :" + beachDetail.getPostal_code() );
			log.info("Beach lat    :" + beachDetail.getLat());
			log.info("Beach long    :" +beachDetail.getLon());
		}
	}

	
	
	
	
	
	
}
