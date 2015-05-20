
package cn.edu.cqupt.nmid.headline.support.repository.weather.bean;

import java.util.HashMap;
import java.util.Map;


public class Data {

    private String temperature;
    private String pm255;
    private String daypictureurl;
    private String title;
    private String date;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The temperature
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * 
     * @param temperature
     *     The temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 
     * @return
     *     The pm255
     */
    public String getPm255() {
        return pm255;
    }

    /**
     * 
     * @param pm255
     *     The pm_255
     */
    public void setPm255(String pm255) {
        this.pm255 = pm255;
    }

    /**
     * 
     * @return
     *     The daypictureurl
     */
    public String getDaypictureurl() {
        return daypictureurl;
    }

    /**
     * 
     * @param daypictureurl
     *     The daypictureurl
     */
    public void setDaypictureurl(String daypictureurl) {
        this.daypictureurl = daypictureurl;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
