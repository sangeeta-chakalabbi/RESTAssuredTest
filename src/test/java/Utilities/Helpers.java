package Utilities;

import java.io.FileInputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Helpers {
	public static Properties prop = new Properties();

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

}
