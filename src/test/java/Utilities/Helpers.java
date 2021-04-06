package Utilities;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import POJO.BeachWeather;
import POJO.Data;
import StepDefinitions.SuitableBeachSteps;
import io.cucumber.datatable.DataTable;
import io.restassured.parsing.Parser;
import io.restassured.specification.ResponseSpecification;

public class Helpers {
	public static Properties prop = new Properties();
	
    static final Logger log = Logger.getLogger(Helpers.class);


	public String getPropertyFromConfig(String key) {
		String configFilePath = "./src/test/resources";
		try {
			FileInputStream config = new FileInputStream(configFilePath + "/config.properties");
			prop.load(config);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String value = prop.getProperty(key);
		return value;
	}
	
	public DayOfWeek getDayOfTheWeek(String date) {
		DayOfWeek theDay = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE).getDayOfWeek();
		return theDay;
	}
	
	public void filterOutSuitableBeachesBasedOnTheCriteria (BeachCriteria beachCriteria , DataTable dataTable) {
		ResponseSpecification responseSpecification;

		
		String baseUrl = Helpers.prop.getProperty("url");
		String key = Helpers.prop.getProperty("key");
		List<String> beachPostCodes =  dataTable.asList(String.class);
		BeachDetails beachDetails = new BeachDetails();
					        		
		responseSpecification = 
				 given()
				.queryParam("key", key)
				.queryParam("days", beachCriteria.getNumberOfDays())
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
						.getList("data.findAll{it.temp > " + beachCriteria.getMinTemp() + " && it.temp < " + beachCriteria.getMaxTemp() + " }"
								+ ".findAll{it.uv <= " + beachCriteria.getUvIndexNumber() + "}"
								+ ".findAll{it.wind_spd > " + beachCriteria.getMinTemp() + " && it.wind_spd < " + beachCriteria.getMaxTemp() +"}", 
								Data.class);
			if(dataList.size()==0) {
				log.warn("Weather data for the post code :"+ tmpBeachPostCode + "is " + dataList.size());
			}
			
		
			boolean alreadyBeachIsAddedUnderpreferredDay1List = false;
			boolean alreadyBeachIsAddedUnderpreferredDay2List = false;

			//Iterate through all days to filter those matching preferredDay1 or preferredDay2
			for(Data tmp : dataList) {
				//Club all the beaches together in a list if they can be visited on preferredDay1
				String nameOfTheDay = getDayOfTheWeek(tmp.getDatetime()).name();
				if(nameOfTheDay.equalsIgnoreCase(beachCriteria.getPreferredDay1())) {
					if(alreadyBeachIsAddedUnderpreferredDay1List == false) {
						alreadyBeachIsAddedUnderpreferredDay1List=true;
						beachCriteria.beachesSuitableForPreferredDay1.add(beachDetails);
					}
					updateTheSufChart(beachCriteria.surfDetailsForPreferredDay1, tmp.getDatetime(), beachCriteria.beachesSuitableForPreferredDay1);
				}
				else if(nameOfTheDay.equalsIgnoreCase(beachCriteria.getPreferredDay2())) {
					
					if(alreadyBeachIsAddedUnderpreferredDay2List == false) {
						alreadyBeachIsAddedUnderpreferredDay2List=true;
						beachCriteria.beachesSuitableForPreferredDay2.add(beachDetails);
					}
				}
				
				//Prepare a char/table which maps a preferred dates against shorted/suitable beaches to go on that date
				updateTheSufChart(beachCriteria.surfDetailsForPreferredDay2, tmp.getDatetime(), beachCriteria.beachesSuitableForPreferredDay2);
			}//end of inner for
		}// end of outer for

	}
	
	public List<BeachDetails> viewCordinates(BeachCriteria beachCriteria) {
		BeachDetails beach1 = new BeachDetails();
		BeachDetails beach2 = new BeachDetails();
		
		if( !  beachCriteria.beachesSuitableForPreferredDay1.isEmpty() ) {
			 beach1 = getCordinates(beachCriteria.beachesSuitableForPreferredDay1);
		}
		else {
			 log.warn(" Cannot find a beach that meets your criteria on  " + beachCriteria.getPreferredDay1() );
		}
		
		if( !  beachCriteria.beachesSuitableForPreferredDay2.isEmpty() ) {
			 beach2 = getCordinates(beachCriteria.beachesSuitableForPreferredDay2);
		}
		else {
			 log.warn(" Cannot find a beach that meets your criteria on  " + beachCriteria.getPreferredDay2() );
		}
		
		if(beach1 == null || beach2==null) {
			assertTrue(beachCriteria.beachesSuitableForPreferredDay1.isEmpty() || beachCriteria.beachesSuitableForPreferredDay2.isEmpty());
		}		
		
		List<BeachDetails> beachDetailsTmp = new ArrayIndex<BeachDetails>();
		beachDetailsTmp.add(beach1);
		beachDetailsTmp.add(beach2);
		
		return beachDetailsTmp;
		
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
