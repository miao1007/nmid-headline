package cn.edu.cqupt.nmid.headline.support.db.tasks;

import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.db.DatabaseManager;
import cn.edu.cqupt.nmid.headline.support.db.tasks.callback.GetIsFavoriteFeedFromDbTaskCallback;

public class GetIsFavoriteFeedFromDbTask extends AsyncTask<Void, Integer, Boolean> {
  private int id;
  private GetIsFavoriteFeedFromDbTaskCallback mCallbacks;

  public GetIsFavoriteFeedFromDbTask(int id,
      GetIsFavoriteFeedFromDbTaskCallback mCallbacks) {
    this.id = id;
    this.mCallbacks = mCallbacks;
  }

  public void executeSerial() {
    execute();
  }

  public void executeParallell() {
    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override protected Boolean doInBackground(Void... params) {
    return DatabaseManager.isLikeIt(id);
  }

  @Override protected void onPreExecute() {
    super.onPreExecute();
  }

  @Override protected void onPostExecute(Boolean aBoolean) {
    super.onPostExecute(aBoolean);
    if (mCallbacks != null) {
      mCallbacks.onComplete(aBoolean);
    }
  }
}


