package StepDefinitions;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.apache.log4j.Logger;
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
import Utilities.BeachDetails;
import Utilities.ConstantsUsedHere;
import Utilities.Helpers;

public class SuitableBeachSteps {


	String preferredDay1 = new String();
	String preferredDay2 = new String();
	String minTemp = new String();
	String maxTemp = new String();
	String minWindSpeed = new String();
	String maxWindSpeed = new String();
	String uvIndexNumber = new String();
	String numberOfDays = new String();
	Helpers helper = new Helpers();


	ResponseSpecification responseSpecification;
	
    static final Logger log = Logger.getLogger(SuitableBeachSteps.class);

	
	//Lists to maintain the days and the beaches info
	public static HashSet<BeachDetails> beachesSuitableForPreferredDay1 = new HashSet<BeachDetails>();
	public static HashSet<BeachDetails> beachesSuitableForPreferredDay2 = new HashSet<BeachDetails>();
	public static HashMap<String, HashSet<BeachDetails>> surfDetailsForPreferredDay1 = new HashMap<String,  HashSet<BeachDetails>>();
	public static HashMap<String, HashSet<BeachDetails>> surfDetailsForPreferredDay2 = new HashMap<String,  HashSet<BeachDetails>>();

	
	/***************/
	
	
	
	
	@Given("I like to consider two among the top ten beaches in Sydney for the next {string}")
	public void I_like_to_consider_two_among_the_top_ten_beaches_in_sydney_for_the_next(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	@Given("I check if the temperature is between {string} and {string}")
	public void i_check_if_the_temperature_is_between_and(String minTemp, String maxTemp) {
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
	}

	@Given("I check wind speed to be between {string} and {string}")
	public void i_check_wind_speed_to_be_between_and(String minWindSpeed, String maxWindSpeed) {
		this.minWindSpeed = minWindSpeed;
		this.maxWindSpeed = maxWindSpeed;
	}

	@Given("I check to see if UV index is less than or equal to {string}")
	public void i_check_to_see_if_uv_index_is(String uvIndexNumber) {
		this.uvIndexNumber = uvIndexNumber;
	}

	@Given("I like to surf on only {string} and {string}")
	public void i_like_to_surf_on_only_and(String preferredDay1, String preferredDay2) {
	    this.preferredDay1 = preferredDay1;
	    this.preferredDay2 = preferredDay2;
	}

	@When("I look up the the weather forecast for the above preference for the following post codes")
	public void i_look_up_the_the_weather_forecast_for_the_above_preference_for_the_following_postal_codes(DataTable dataTable) {
		
		String baseUrl = Helpers.prop.getProperty("url");
		String key = Helpers.prop.getProperty("key");
		List<String> beachPostCodes =  dataTable.asList(String.class);
		BeachDetails beachDetails = new BeachDetails();
					        		
		responseSpecification = 
				 given()
				.queryParam("key", key)
				.queryParam("days", this.numberOfDays)
				.expect()
				.defaultParser(Parser.JSON);
		

		for(String tmpBeachPostCode: beachPostCodes) {
			
			// Fetch  weather details of a beach to capture its co-ordinates
			BeachWeather beachWeather =responseSpecification
					.given()
						.queryParam("postal_code", tmpBeachPostCode)
					.when()
						.get(baseUrl)
					.then()
						.assertThat().statusCode(200)
						.extract()
						.response()
						.as(BeachWeather.class);
			
			// Store the details
			beachDetails.setLat(beachWeather.getLat()); 
			beachDetails.setLon(beachWeather.getLon()); 
			beachDetails.setPostal_code(tmpBeachPostCode);
			// Filter the days that meet the criteria
			List<Data> dataList = responseSpecification
					.given()
					.when()
						.get(baseUrl)
					.then()
						.extract()
						.jsonPath()
						.getList("data.findAll{it.temp > " + this.minTemp + " && it.temp < " + this.maxTemp + " }"
								+ ".findAll{it.uv <= " + this.uvIndexNumber + "}"
								+ ".findAll{it.wind_spd > " + this.minWindSpeed + " && it.wind_spd < " + this.maxTemp +"}", 
								Data.class);
			if(dataList.size()==0) {
				log.warn("Weather data for the post code :"+ tmpBeachPostCode + "is " + dataList.size());
			}
			
		
			boolean alreadyBeachIsAddedUnderpreferredDay1List = false;
			boolean alreadyBeachIsAddedUnderpreferredDay2List = false;

			//Iterate through all days to filter those matching preferredDay1 or preferredDay2
			for(Data tmp : dataList) {
				//Club all the beaches together in a list if they can be visited on preferredDay1
				String nameOfTheDay = helper.getDayOfTheWeek(tmp.getDatetime()).name();
				if(nameOfTheDay.equalsIgnoreCase(this.preferredDay1)) {
					if(alreadyBeachIsAddedUnderpreferredDay1List == false) {
						alreadyBeachIsAddedUnderpreferredDay1List=true;
						beachesSuitableForPreferredDay1.add(beachDetails);
					}
					updateTheSufChart(surfDetailsForPreferredDay1, tmp.getDatetime(), beachesSuitableForPreferredDay1);
				}
				else if(nameOfTheDay.equalsIgnoreCase(this.preferredDay2)) {
					
					if(alreadyBeachIsAddedUnderpreferredDay2List == false) {
						alreadyBeachIsAddedUnderpreferredDay2List=true;
						beachesSuitableForPreferredDay2.add(beachDetails);
					}
				}
				
				//Prepare a char/table which maps a preferred dates against shorted/suitable beaches to go on that date
				updateTheSufChart(surfDetailsForPreferredDay2, tmp.getDatetime(), beachesSuitableForPreferredDay2);
			}//end of inner for
		}// end of outer for

	}

	@When("I can get the list of suitable beaches against each chosen day")
	public void I_can_get_the_list_of_suitable_beaches_against_each_chosen_day() {
		
		log.info("*********Total beaches for" + this.preferredDay1 +    ":   "+ beachesSuitableForPreferredDay1.size()  );
		log.info("*********Total beaches for" + this.preferredDay2 +    ":   "+ beachesSuitableForPreferredDay2.size()  );

	}

	@When("I can view the coordinates of the shortlisted beaches")
	public void i_can_view_the_coordinates_of_the_shortlisted_beaches() {
		
		BeachDetails beach1 = new BeachDetails();
		BeachDetails beach2 = new BeachDetails();
		
		if( !  beachesSuitableForPreferredDay1.isEmpty() ) {
			 beach1 = getCordinates(beachesSuitableForPreferredDay1);
		}
		else {
			 log.warn(" Cannot find a beach that meets your criteria on  " + this.preferredDay1 );
		}
		
		if( !  beachesSuitableForPreferredDay2.isEmpty() ) {
			 beach2 = getCordinates(beachesSuitableForPreferredDay2);
		}
		else {
			 log.warn(" Cannot find a beach that meets your criteria on  " + this.preferredDay2 );
		}
		
		if(beach1 == null || beach2==null) {
			assertTrue(beachesSuitableForPreferredDay1.isEmpty() || beachesSuitableForPreferredDay2.isEmpty());
		}		
	}

	private BeachDetails getCordinates(HashSet<BeachDetails> shortListedBeaches) {
		
		for(BeachDetails tmp: shortListedBeaches) {
			log.info("Beach :: postal code : "+ tmp.getPostal_code()+"  Lat:  "+tmp.getLat()+"Lon:  "+tmp.getLon());
			return tmp;
		}
		return new BeachDetails();
	}
	
	private void updateTheSufChart(HashMap<String, HashSet<BeachDetails>> surfChart,String date,  HashSet<BeachDetails> suitableBeachesList) {
		surfChart.put(date, suitableBeachesList);
		return;
	}
	
	
	
}
