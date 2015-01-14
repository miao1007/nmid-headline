package cn.edu.cqupt.nmid.headline.support.api.weather.bean;

/**
 * Created by leon on 1/14/15.
 */
public class Data {

    private String temperature;
    private String pm_255;
    private String daypictureurl;
    private String title;
    private String date;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPm_255() {
        return pm_255;
    }

    public void setPm_255(String pm_255) {
        this.pm_255 = pm_255;
    }

    public String getDaypictureurl() {
        return daypictureurl;
    }

    public void setDaypictureurl(String daypictureurl) {
        this.daypictureurl = daypictureurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
