package POJO;

public class Weather {
	private String code;

    private String icon;

    private String description;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", icon = "+icon+", description = "+description+"]";
    }
}
