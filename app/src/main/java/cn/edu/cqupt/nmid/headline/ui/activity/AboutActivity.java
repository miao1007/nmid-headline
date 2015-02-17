package cn.edu.cqupt.nmid.headline.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.BuildConfig;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.update.UpdateService;
import cn.edu.cqupt.nmid.headline.support.api.update.UpdateResult;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.widget.swipebacklayout.SwipeBackActivity;
import cn.edu.cqupt.nmid.headline.utils.DownloadUtils;
import cn.edu.cqupt.nmid.headline.utils.RetrofitUtils;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.RestAdapter;

public class AboutActivity extends SwipeBackActivity {

  @InjectView(R.id.toolbar) Toolbar mToolbar;
  @InjectView(R.id.about_version) TextView mTextView_vision;

  @OnClick(R.id.about_nexus_img) void onClick() {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.addCategory(Intent.CATEGORY_BROWSABLE);
    intent.setData(Uri.parse(getString(R.string.settings_about_url)));
    startActivity(intent);
  }

  MaterialDialog dialog;

  @OnClick(R.id.about_version) void about_version() {
    dialog = new MaterialDialog(this);

    new AsyncTask<Void, Void, UpdateResult>() {
      @Override protected UpdateResult doInBackground(Void... params) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(UpdateService.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();

        return adapter.create(UpdateService.class)
            .checkforupdate(BuildConfig.APPLICATION_ID,BuildConfig.VERSION_NAME);
      }

      @Override protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("Check for update")
            .setContentView(
                LayoutInflater.from(AboutActivity.this).inflate(R.layout.include_progressbar, null))
            .show();
      }

      @Override protected void onPostExecute(final UpdateResult updateResult) {
        super.onPostExecute(updateResult);
        dialog.dismiss();
        dialog = new MaterialDialog(AboutActivity.this);
        if (updateResult == null) {
          RetrofitUtils.disMsg(AboutActivity.this, "Network Error!");
          return;
        }
        if (updateResult.getStatus() == 1) {
          dialog.setMessage("Update available, update now?")
              .setPositiveButton("OK", new View.OnClickListener() {
                @Override public void onClick(View v) {
                  DownloadUtils.DownloadWithProgress(AboutActivity.this, updateResult.getUrl());
                  dialog.dismiss();
                }
              })
              .setNegativeButton("CANCEL", new View.OnClickListener() {
                @Override public void onClick(View v) {
                  dialog.dismiss();
                }
              });
        } else {
          dialog.setMessage("No update available")
              .setPositiveButton("OK", new View.OnClickListener() {
                @Override public void onClick(View v) {
                  dialog.dismiss();
                }
              })
              .setNegativeButton("CANCEL", new View.OnClickListener() {
                @Override public void onClick(View v) {
                  dialog.dismiss();
                }
              });
        }
        dialog.show();
      }
    }.execute();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    ButterKnife.inject(this);
    mToolbar.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(this));
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mTextView_vision.setText(BuildConfig.VERSION_NAME);
  }
}
