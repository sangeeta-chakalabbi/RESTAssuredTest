package Utilities;

import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import io.restassured.specification.ResponseSpecification;

public class BeachCriteria {
	
	
	
	String preferredDay1;
	String preferredDay2;
	String minTemp;
	String maxTemp;
	String minWindSpeed;
	String maxWindSpeed;
	String uvIndexNumber;
	String numberOfDays;
	Helpers helper;


	
	
    static final Logger log = Logger.getLogger(BeachCriteria.class);

	
	//Lists to maintain the days and the beaches info
	public static HashSet<BeachDetails> beachesSuitableForPreferredDay1 = new HashSet<BeachDetails>();
	public static HashSet<BeachDetails> beachesSuitableForPreferredDay2 = new HashSet<BeachDetails>();
	public static HashMap<String, HashSet<BeachDetails>> surfDetailsForPreferredDay1 = new HashMap<String,  HashSet<BeachDetails>>();
	public static HashMap<String, HashSet<BeachDetails>> surfDetailsForPreferredDay2 = new HashMap<String,  HashSet<BeachDetails>>();
	
	
	public String getPreferredDay1() {
		return preferredDay1;
	}
	public void setPreferredDay1(String preferredDay1) {
		this.preferredDay1 = preferredDay1;
	}
	public String getPreferredDay2() {
		return preferredDay2;
	}
	public void setPreferredDay2(String preferredDay2) {
		this.preferredDay2 = preferredDay2;
	}
	public String getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(String minTemp) {
		this.minTemp = minTemp;
	}
	public String getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(String maxTemp) {
		this.maxTemp = maxTemp;
	}
	public String getMinWindSpeed() {
		return minWindSpeed;
	}
	public void setMinWindSpeed(String minWindSpeed) {
		this.minWindSpeed = minWindSpeed;
	}
	public String getMaxWindSpeed() {
		return maxWindSpeed;
	}
	public void setMaxWindSpeed(String maxWindSpeed) {
		this.maxWindSpeed = maxWindSpeed;
	}
	public String getUvIndexNumber() {
		return uvIndexNumber;
	}
	public void setUvIndexNumber(String uvIndexNumber) {
		this.uvIndexNumber = uvIndexNumber;
	}
	public String getNumberOfDays() {
		return numberOfDays;
	}
	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

}
