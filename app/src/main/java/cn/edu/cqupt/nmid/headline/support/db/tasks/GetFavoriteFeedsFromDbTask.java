package cn.edu.cqupt.nmid.headline.support.db.tasks;

import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Datum;
import cn.edu.cqupt.nmid.headline.support.db.DatabaseManager;
import cn.edu.cqupt.nmid.headline.support.db.tasks.callback.GetFavoriteFeedsFromDbTaskCallback;
import java.util.ArrayList;

public class GetFavoriteFeedsFromDbTask extends AsyncTask<Void, Integer, ArrayList<Datum>> {
  private String tablename;
  private int feed_limit;
  private GetFavoriteFeedsFromDbTaskCallback mCallbacks;

  public GetFavoriteFeedsFromDbTask(String tablename, int feed_limit,
      GetFavoriteFeedsFromDbTaskCallback mCallbacks) {
    this.tablename = tablename;
    this.feed_limit = feed_limit;
    this.mCallbacks = mCallbacks;
  }

  public void executeSerial() {
    execute();
  }

  public void executeParallell() {
    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override
  protected ArrayList<Datum> doInBackground(Void... params) {
    return DatabaseManager.getFavoriteList();
  }

  @Override
  protected void onPostExecute(ArrayList<Datum> result) {
    super.onPostExecute(result);
    if (mCallbacks != null) {
      mCallbacks.onCollectData(result);
    }
  }
}
