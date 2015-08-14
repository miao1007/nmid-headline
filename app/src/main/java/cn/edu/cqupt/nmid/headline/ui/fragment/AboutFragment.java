package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.BuildConfig;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.FIRUtils;

public class AboutFragment extends Fragment {

  @InjectView(R.id.about_version) TextView mTextView_vision;

  @OnClick(R.id.about_version) void about_version() {
    FIRUtils.checkForUpdate(getActivity(),true);
  }

  @OnClick(R.id.about_nexus_img) void about_nexus_img() {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    intent.addCategory(Intent.CATEGORY_BROWSABLE);
    intent.setData(Uri.parse(getString(R.string.settings_about_url)));
    startActivity(intent);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.frag_about, container, false);
    ButterKnife.inject(this, view);
    mTextView_vision.setText(BuildConfig.VERSION_NAME);
    return view;
  }
}
