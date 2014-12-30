package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

import static cn.edu.cqupt.nmid.headline.utils.LogUtils.LOGD;

/**
 *
 */
public class NavigationDawerFragment extends Fragment {

    NavigationDrawerCallBack mCallback;
    String TAG = LogUtils.makeLogTag(NavigationDawerFragment.class);


    /**
     * drawer_header
     */
    @OnClick(R.id.drawer_header)
    void onclick_header() {
        mCallback.onNaviItemClick(0);
    }

    @InjectView(R.id.item_header_imageView)
    ImageView mImageview_header;

    @InjectView(R.id.item_header_textview)
    TextView mTextView_avatar;


    /**
     * drawer_home
     */
    @InjectView(R.id.drawer_home)
    TextView mTextView_home;

    @OnClick(R.id.drawer_home)
    void drawer_home() {
        mCallback.onNaviItemClick(1);
    }

    /**
     * drawer_strem
     */
    @InjectView(R.id.drawer_strem)
    TextView mTextView_strem;

    @OnClick(R.id.drawer_strem)
    void drawer_strem() {
        mCallback.onNaviItemClick(2);
    }

    /**
     * drawer_favorite
     */
    @InjectView(R.id.drawer_favorite)
    TextView mTextView_favorite;

    @OnClick(R.id.drawer_favorite)
    void onclick_favorite() {
        mCallback.onNaviItemClick(3);
    }

    /**
     * drawer_star
     */
    @InjectView(R.id.drawer_star)
    TextView mTextView_star;

    @OnClick(R.id.drawer_star)
    void drawer_star() {
        mCallback.onNaviItemClick(4);
    }


    @OnClick(R.id.drawer_settings)
    void drawer_settings(){
        mCallback.onNaviItemClick(5);
    }

    @OnClick(R.id.drawer_night)
    void drawer_night(){
        mCallback.onNaviItemClick(6);
    }



    public NavigationDawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_navigation_dawer, container, false);
        ButterKnife.inject(this, rootView);
        setItem(mTextView_favorite, R.drawable.ic_favorite_grey600_24dp, "喜爱");
        setItem(mTextView_home, R.drawable.ic_home_grey600_24dp, "主页");
        setItem(mTextView_star, R.drawable.ic_star_grey600_24dp, "收藏");
        setItem(mTextView_strem, R.drawable.ic_camera_grey600_24dp, "风景");
        Platform platform = ShareSDK.getPlatform(getActivity(), SinaWeibo.NAME);
        String avatar = platform.getDb().get("avatar");
        String name = platform.getDb().get("name");
        LOGD(TAG,name);
        LOGD(TAG,avatar);
//        if (!(avatar.isEmpty() && avatar == null)) {
//            Picasso.with(getActivity()).load(avatar).into(mImageview_header);
//        }
//        if (name != null) {
//            mTextView_avatar.setText(name);
//        }


        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (NavigationDrawerCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NavigationDrawerCallBack");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public interface NavigationDrawerCallBack {
        public void onNaviItemClick(int resId);
    }


    private void setItem(TextView textView, int id, String title) {
        textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(id), null, null, null);
        textView.setText(title);
    }


}
