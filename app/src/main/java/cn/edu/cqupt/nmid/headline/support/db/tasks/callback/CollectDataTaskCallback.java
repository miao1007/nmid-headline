package cn.edu.cqupt.nmid.headline.support.db.tasks.callback;

import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Datum;
import java.util.ArrayList;

public interface CollectDataTaskCallback {
  public void onCollectData(ArrayList<Datum> listbeans);
}
