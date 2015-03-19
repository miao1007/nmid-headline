package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;

/*
* Some source code comes from {@link:http://stackoverflow.com/questions/5991319/capture-image-from-camera-and-display-in-activity}
* */
public class UploadFragment extends Fragment {

  private static String TAG = LogUtils.makeLogTag(UploadFragment.class);



  @InjectView(R.id.upload_btn_upload) Button mButton_upload_btn_commit;
  @InjectView(R.id.upload_img_preview) ImageView a;



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_upload, container, false);
    ButterKnife.inject(this, view);
    return view;
  }





}
