package POJO;

import java.util.List;

public class BeachWeather {
	private List<Data> data;

	private String country_code;

    private String city_name;

    private String timezone;

    private String lon;

    private String state_code;

    private String lat;

    public String getCountry_code ()
    {
        return country_code;
    }

    public void setCountry_code (String country_code)
    {
        this.country_code = country_code;
    }

    public String getCity_name ()
    {
        return city_name;
    }

    public void setCity_name (String city_name)
    {
        this.city_name = city_name;
    }

    public List<Data> getData ()
    {
        return data;
    }

    public void setData (List<Data> data)
    {
        this.data = data;
    }

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getState_code ()
    {
        return state_code;
    }

    public void setState_code (String state_code)
    {
        this.state_code = state_code;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [country_code = "+country_code+", city_name = "+city_name+", data = "+data+", timezone = "+timezone+", lon = "+lon+", state_code = "+state_code+", lat = "+lat+"]";
    }
}
