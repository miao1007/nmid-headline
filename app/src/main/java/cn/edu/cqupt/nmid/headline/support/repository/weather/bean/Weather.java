
package cn.edu.cqupt.nmid.headline.support.repository.weather.bean;

import java.util.HashMap;
import java.util.Map;

public class Weather {

    private int status;
    private WweatherData wweatherData;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The status
     */
    public int getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The data
     */
    public WweatherData getWweatherData() {
        return wweatherData;
    }

    /**
     * 
     * @param wweatherData
     *     The data
     */
    public void setWweatherData(WweatherData wweatherData) {
        this.wweatherData = wweatherData;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
