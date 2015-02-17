
package cn.edu.cqupt.nmid.headline.support.api.headline.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeadJson {

    private int status;
    private List<Feed> data = new ArrayList<Feed>();
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
    public List<Feed> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Feed> data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
