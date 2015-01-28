package cn.edu.cqupt.nmid.headline.support.db.tasks.callback;

import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Datum;
import java.util.ArrayList;

public interface GetFeedsCallback {
  public void onRefreshData(ArrayList<Datum> listbeans);
}
