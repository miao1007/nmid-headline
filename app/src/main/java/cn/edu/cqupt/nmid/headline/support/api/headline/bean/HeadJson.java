package cn.edu.cqupt.nmid.headline.support.api.headline.bean;
import java.util.ArrayList;
import java.util.List;

public class HeadJson {
    private Integer status;
    private List<Datum> data = new ArrayList<Datum>();

    /**
     *
     * @return
     *     The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

}
