package cn.edu.cqupt.nmid.headline.model.weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result {

    private String currentCity;
    private String pm25;
    private List<Index> index = new ArrayList<Index>();
    private List<WeatherDatum> weather_data = new ArrayList<WeatherDatum>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The currentCity
     */
    public String getCurrentCity() {
        return currentCity;
    }

    /**
     * @param currentCity The currentCity
     */
    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    /**
     * @return The pm25
     */
    public String getPm25() {
        return pm25;
    }

    /**
     * @param pm25 The pm25
     */
    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    /**
     * @return The index
     */
    public List<Index> getIndex() {
        return index;
    }

    /**
     * @param index The index
     */
    public void setIndex(List<Index> index) {
        this.index = index;
    }

    /**
     * @return The weather_data
     */
    public List<WeatherDatum> getWeather_data() {
        return weather_data;
    }

    /**
     * @param weather_data The weather_data
     */
    public void setWeather_data(List<WeatherDatum> weather_data) {
        this.weather_data = weather_data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
