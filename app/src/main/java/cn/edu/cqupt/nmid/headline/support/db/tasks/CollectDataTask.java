package cn.edu.cqupt.nmid.headline.support.db.tasks;

import android.content.Context;
import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Datum;
import cn.edu.cqupt.nmid.headline.support.db.DataBaseHelper;
import cn.edu.cqupt.nmid.headline.support.db.tasks.callback.CollectDataTaskCallback;
import java.util.ArrayList;

public class CollectDataTask extends AsyncTask<Void, Integer, ArrayList<Datum>> {
  private Context context;
  private CollectDataTaskCallback mCallbacks;

  public CollectDataTask(Context context, CollectDataTaskCallback callbacks) {
    this.context = context;
    this.mCallbacks = callbacks;
  }

  @Override
  protected ArrayList<Datum> doInBackground(Void... params) {
    return DataBaseHelper.getInstance(context).collectData();
  }

  @Override
  protected void onPostExecute(ArrayList<Datum> result) {
    super.onPostExecute(result);
    if (mCallbacks != null) {
      mCallbacks.onCollectData(result);
    }
  }
}
