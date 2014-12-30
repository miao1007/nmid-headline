
package cn.edu.cqupt.nmid.headline.model.weather;

import java.util.HashMap;
import java.util.Map;


public class WeatherDatum {

    private String date;
    private String dayPictureUrl;
    private String nightPictureUrl;
    private String weather;
    private String wind;
    private String temperature;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    /**
     * 
     * @return
     *     The dayPictureUrl
     */
    public String getDayPictureUrl() {
        return dayPictureUrl;
    }

    /**
     * 
     * @param dayPictureUrl
     *     The dayPictureUrl
     */
    public void setDayPictureUrl(String dayPictureUrl) {
        this.dayPictureUrl = dayPictureUrl;
    }

    /**
     * 
     * @return
     *     The nightPictureUrl
     */
    public String getNightPictureUrl() {
        return nightPictureUrl;
    }

    /**
     * 
     * @param nightPictureUrl
     *     The nightPictureUrl
     */
    public void setNightPictureUrl(String nightPictureUrl) {
        this.nightPictureUrl = nightPictureUrl;
    }

    /**
     * 
     * @return
     *     The weather
     */
    public String getWeather() {
        return weather;
    }

    /**
     * 
     * @param weather
     *     The weather
     */
    public void setWeather(String weather) {
        this.weather = weather;
    }

    /**
     * 
     * @return
     *     The wind
     */
    public String getWind() {
        return wind;
    }

    /**
     * 
     * @param wind
     *     The wind
     */
    public void setWind(String wind) {
        this.wind = wind;
    }

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
