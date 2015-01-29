package cn.edu.cqupt.nmid.headline.support.db.tasks;

import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Datum;
import cn.edu.cqupt.nmid.headline.support.db.DatabaseManager;
import cn.edu.cqupt.nmid.headline.support.db.tasks.callback.GetFeedsCallback;
import java.util.ArrayList;

public class GetFeedsFromDbTask extends AsyncTask<Integer, Integer, ArrayList<Datum>> {

  private GetFeedsCallback mCallbacks;
  private int category;
  private int feed_limit;

  public GetFeedsFromDbTask(int category, int feed_limit, GetFeedsCallback mCallbacks) {
    this.feed_limit = feed_limit;
    this.category = category;
    this.mCallbacks = mCallbacks;
  }

  public void executeSerial() {
    execute();
  }

  public void executeParallell() {
    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override
  protected ArrayList<Datum> doInBackground(Integer... params) {

    return DatabaseManager.getFeeds(category, feed_limit);
  }

  @Override
  protected void onPostExecute(ArrayList<Datum> result) {
    super.onPostExecute(result);
    if (mCallbacks != null) {
      mCallbacks.onRefreshData(result);
    }
  }
}
