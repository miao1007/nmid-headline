package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.edu.cqupt.nmid.headline.BuildConfig;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.update.UpdateResult;
import cn.edu.cqupt.nmid.headline.support.api.update.UpdateService;
import cn.edu.cqupt.nmid.headline.utils.DownloadUtils;
import cn.edu.cqupt.nmid.headline.utils.RetrofitUtils;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.RestAdapter;

public class AboutFragment extends Fragment {

  TextView mTextView_vision;

  MaterialDialog dialog;

  void about_version() {
    dialog = new MaterialDialog(getActivity());

    new AsyncTask<Void, Void, UpdateResult>() {
      @Override protected UpdateResult doInBackground(Void... params) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(UpdateService.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .build();

        return adapter.create(UpdateService.class)
            .checkforupdate(BuildConfig.APPLICATION_ID, BuildConfig.VERSION_NAME);
      }

      @Override protected void onPreExecute() {
        super.onPreExecute();
        dialog.setTitle("Check for update")
            .setContentView(
                LayoutInflater.from(getActivity()).inflate(R.layout.include_progressbar, null))
            .show();
      }

      @Override protected void onPostExecute(final UpdateResult updateResult) {
        super.onPostExecute(updateResult);
        dialog.dismiss();
        dialog = new MaterialDialog(getActivity());
        if (updateResult == null) {
          RetrofitUtils.disMsg(getActivity(), "Network Error!");
          return;
        }
        if (updateResult.getStatus() == 1) {
          dialog.setMessage("Update available, update now?")
              .setPositiveButton("OK", new View.OnClickListener() {
                @Override public void onClick(View v) {
                  DownloadUtils.DownloadWithProgress(getActivity(), updateResult.getUrl());
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

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.frag_about, container, false);
    mTextView_vision = (TextView) view.findViewById(R.id.about_version);
    mTextView_vision.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        about_version();
      }
    });
    mTextView_vision.setText(BuildConfig.VERSION_NAME);

    view.findViewById(R.id.about_nexus_img).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(getString(R.string.settings_about_url)));
        startActivity(intent);
      }
    });
    return view;
  }

}
