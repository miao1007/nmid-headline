package cn.edu.cqupt.nmid.headline.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.Constant;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.widget.SlidingTabLayout;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import java.util.ArrayList;

/**
 * Created by leon on 1/16/15.
 */
public class FeedsFragment extends Fragment {

  static final String TAG = LogUtils.makeLogTag(FeedsFragment.class);

  @InjectView(R.id.slidingtab) SlidingTabLayout mTabLayout;

  @InjectView(R.id.viewpager) ViewPager mViewPager;

  ArrayList<FeedFragment> fragments = new ArrayList<>();
  PagerAdapter mPagerAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
    ButterKnife.inject(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    Log.d(TAG, "onViewCreated");
    fragments.add(FeedFragment.newInstance("学院风采", Constant.TYPE_COLLEGE));
    fragments.add(FeedFragment.newInstance("青春通信", Constant.TYPE_YOUTH));
    fragments.add(FeedFragment.newInstance("科技创新", Constant.TYPE_SCIENTIFIC));
    fragments.add(FeedFragment.newInstance("通信校友", Constant.TYPE_CLASSMATE));
    Log.d(TAG, "setViewPager");
    mTabLayout.setBackgroundResource(ThemePref.getToolbarBackgroundResColor(getActivity()));
    mTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
      @Override
      public int getIndicatorColor(int position) {
        return Color.WHITE;
      }
    });

    mPagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), fragments);
    //mViewPager.setOffscreenPageLimit(fragments.size());
    mViewPager.setAdapter(mPagerAdapter);

    mTabLayout.setViewPager(mViewPager);
  }

  @Override public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop");
  }

  @Override public void onDetach() {
    Log.d(TAG, "onDetach");
    super.onDetach();
  }

  @Override public void onDestroyView() {
    Log.d(TAG, "onDestroyView");
    super.onDestroyView();
  }

  @Override public void onDestroy() {
    Log.d(TAG, "onDestroy");
    super.onDestroy();
  }


  /**
   * It's better to use FragmentStatePagerAdapter instead of {@link android.support.v4.view.PagerAdapter}
   * Less memory associated when invisible.
   */
  public static class PagerAdapter extends FragmentStatePagerAdapter {

    private String TAG = LogUtils.makeLogTag(PagerAdapter.class);

    private ArrayList<FeedFragment> fragments;

    public PagerAdapter(FragmentManager fm, ArrayList<FeedFragment> fragments) {
      super(fm);
      this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int pos) {
      return fragments.get(pos);
    }

    @Override
    public int getCount() {
      return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return fragments.get(position).getArguments().getString("title");
    }
  }
}
