package cn.edu.cqupt.nmid.headline.support.task;

import android.os.AsyncTask;
import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import cn.edu.cqupt.nmid.headline.support.task.callback.WebContentGetTaskCallback;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import java.io.File;
import java.io.IOException;

/**
 * Created by leon on 1/16/15.
 */
public class WebContentGetTask extends AsyncTask<String, Void, String> {

  private WebContentGetTaskCallback mCallback;

  public WebContentGetTask(WebContentGetTaskCallback mCallback) {
    this.mCallback = mCallback;
  }

  @Override
  protected String doInBackground(String... params) {
    OkHttpClient client = new OkHttpClient();
    Cache cache;
    File tmpFile = new File(GlobalContext.getInstance().getCacheDir().getPath(),".webview_cache");
    try {

      cache = new Cache(tmpFile,10*60*60);
      client.setCache(cache);
    } catch (Exception e){

    }

    if (params[0] == null){
      throw new IllegalStateException("set url before execute!");
    }
    Request request = new Request.Builder().url(params[0]).build();
    try {
      return client.newCall(request).execute().body().string();
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  protected void onPreExecute() {
    super.onPreExecute();
    if (mCallback != null) {
      mCallback.onPreExcute();
    }
  }

  @Override
  protected void onPostExecute(String s) {
    super.onPostExecute(s);
    if (s != null) {
      if (mCallback != null) {
        mCallback.onSuccess(s);
      }
    }
  }


  public void executeSerial() {
    execute();
  }

  public void executeParallell() {
    executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
  }
}
