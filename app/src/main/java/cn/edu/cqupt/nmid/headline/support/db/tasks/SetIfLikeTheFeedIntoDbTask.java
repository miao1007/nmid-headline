package cn.edu.cqupt.nmid.headline.support.db.tasks;

import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.db.DatabaseManager;
import cn.edu.cqupt.nmid.headline.support.db.tasks.callback.SetIfLikeTheFeedIntoDbTaskCallback;

public class SetIfLikeTheFeedIntoDbTask extends AsyncTask<Integer, Integer, Boolean> {

  private SetIfLikeTheFeedIntoDbTaskCallback mCallbacks;
  private String tablename;
  private int id;

  public SetIfLikeTheFeedIntoDbTask(int id,
      SetIfLikeTheFeedIntoDbTaskCallback mCallbacks) {
    this.id = id;
    this.mCallbacks = mCallbacks;
  }

  public void executeSerial() {
    execute();
  }

  public void executeParallell() {
    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override
  protected Boolean doInBackground(Integer... params) {

    return DatabaseManager.toggleLikeIt(id);
  }

  @Override protected void onPostExecute(Boolean b) {
    super.onPostExecute(b);
    if (mCallbacks != null) {
      mCallbacks.onRefreshData(b);
    }
  }
}
