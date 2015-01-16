package cn.edu.cqupt.nmid.headline.support.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;

import cn.edu.cqupt.nmid.headline.support.task.callback.WebContentGetTaskCallback;

/**
 * Created by leon on 1/16/15.
 */
public class WebContentGetTask extends AsyncTask<String, Void, String> {

    private boolean isLog = false;

    private Context mContext;
    private WebContentGetTaskCallback mCallback;

    public WebContentGetTask(Context mContext, WebContentGetTaskCallback mCallback) {
        this.mContext = mContext;
        this.mCallback = mCallback;
    }

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        String url = params[0];
        Request request = new Request.Builder()
                .url(url)
                .build();
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
        Log.d(getClass().getSimpleName(),s);
        if (s != null){
            if (mCallback != null ) {
                mCallback.onSuccess(s);
            }
        }
    }
}
