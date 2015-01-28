package cn.edu.cqupt.nmid.headline.support.db.tasks;

import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.db.DatabaseManager;
import cn.edu.cqupt.nmid.headline.support.db.tasks.callback.SetFavoriteIntoDbTaskCallback;

public class SetFavoriteIntoDbTask extends AsyncTask<Integer, Integer, Void> {

  private SetFavoriteIntoDbTaskCallback mCallbacks;
  private String tablename;
  private int id;
  private boolean isLikeit;

  public SetFavoriteIntoDbTask(String tablename, int id, Boolean isLikeit,
      SetFavoriteIntoDbTaskCallback mCallbacks) {
    this.tablename = tablename;
    this.id = id;
    this.isLikeit = isLikeit;
    this.mCallbacks = mCallbacks;
  }

  public void executeSerial() {
    execute();
  }

  public void executeParallell() {
    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }

  @Override
  protected Void doInBackground(Integer... params) {

    DatabaseManager.isLikeIt(tablename, id, isLikeit);
    return null;
  }

  @Override protected void onPostExecute(Void aVoid) {
    super.onPostExecute(aVoid);
    if (mCallbacks != null) {
      mCallbacks.onRefreshData();
    }
  }
}
