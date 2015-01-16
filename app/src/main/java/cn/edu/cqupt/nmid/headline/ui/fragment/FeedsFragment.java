package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.Constant;
import cn.edu.cqupt.nmid.headline.ui.adapter.PagerAdapter;
import cn.edu.cqupt.nmid.headline.ui.widget.SlidingTabLayout;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import cn.edu.cqupt.nmid.headline.utils.ThemeUtils;

/**
 * Created by leon on 1/16/15.
 */
public class FeedsFragment extends Fragment {

    static final String TAG = LogUtils.makeLogTag(FeedsFragment.class);

    @InjectView(R.id.slidingtab)
    SlidingTabLayout mTabLayout;

    @InjectView(R.id.viewpager)
    ViewPager mViewPager;

    ArrayList<FeedFragment> fragments = new ArrayList<>();
    PagerAdapter mPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        ButterKnife.inject(this,view);
        Log.d(TAG,"onCreateView");
        fragments.add(FeedFragment.newInstance("学院风采", Constant.TYPE_COLLEGE));
        fragments.add(FeedFragment.newInstance("青春通信", Constant.TYPE_YOUTH));
        fragments.add(FeedFragment.newInstance("科技创新", Constant.TYPE_SCIENTIFIC));
        fragments.add(FeedFragment.newInstance("通信校友", Constant.TYPE_CLASSMATE));
        if (ThemeUtils.isNightMode(getActivity())) {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.primary_night));
        } else {
            mTabLayout.setBackgroundColor(getResources().getColor(R.color.primary));
        }
        mTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });

        mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        return view;
    }
}
