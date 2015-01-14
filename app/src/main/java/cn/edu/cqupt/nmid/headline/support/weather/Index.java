
package cn.edu.cqupt.nmid.headline.support.weather;

import java.util.HashMap;
import java.util.Map;


public class Index {

    private String title;
    private String zs;
    private String tipt;
    private String des;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     *     The zs
     */
    public String getZs() {
        return zs;
    }

    /**
     * 
     * @param zs
     *     The zs
     */
    public void setZs(String zs) {
        this.zs = zs;
    }

    /**
     * 
     * @return
     *     The tipt
     */
    public String getTipt() {
        return tipt;
    }

    /**
     * 
     * @param tipt
     *     The tipt
     */
    public void setTipt(String tipt) {
        this.tipt = tipt;
    }

    /**
     * 
     * @return
     *     The des
     */
    public String getDes() {
        return des;
    }

    /**
     * 
     * @param des
     *     The des
     */
    public void setDes(String des) {
        this.des = des;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
