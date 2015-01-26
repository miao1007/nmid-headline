package cn.edu.cqupt.nmid.headline.support.db.tasks;

import android.content.Context;
import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Datum;
import cn.edu.cqupt.nmid.headline.support.db.DataBaseHelper;
import cn.edu.cqupt.nmid.headline.support.db.tasks.callback.RefreshDataTaskCallback;
import java.util.ArrayList;

public class RefreshDataTask extends AsyncTask<Integer, Integer, ArrayList<Datum>> {
  private Context context;
  private RefreshDataTaskCallback mCallbacks;
  private String tablename;

  public RefreshDataTask(Context context, String tablename, RefreshDataTaskCallback callbacks) {
    this.tablename = tablename;
    this.context = context;
    this.mCallbacks = callbacks;
  }

  @Override
  protected ArrayList<Datum> doInBackground(Integer... params) {
    int newID = params[0];
    int category = params[1];
    int limit = params[2];
    return DataBaseHelper.getInstance(context).getDataById(tablename, limit);
  }

  @Override
  protected void onPostExecute(ArrayList<Datum> result) {
    super.onPostExecute(result);
    if (mCallbacks != null) {
      mCallbacks.onRefreshData(result);
    }
  }
}
