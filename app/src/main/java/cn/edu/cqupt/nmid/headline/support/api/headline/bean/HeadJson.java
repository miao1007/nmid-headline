package cn.edu.cqupt.nmid.headline.support.api.headline.bean;
import java.util.ArrayList;
import java.util.List;

public class HeadJson {
    private Integer status;
    private List<Feed> data = new ArrayList<Feed>();

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

}
