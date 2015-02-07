package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Datum;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.activity.DetailedActivity;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by leon on 1/19/15.
 */
public class FeedAdapter extends HeaderFooterRecyclerViewAdapter {

  private static final String TAG = LogUtils.makeLogTag(FeedAdapter.class);
  private ArrayList<Datum> mNewsBeans;
  private Context mContext;

  public FeedAdapter(Context mContext, ArrayList<Datum> newsBeans) {
    this.mContext = mContext;
    this.mNewsBeans = newsBeans;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override protected int getHeaderItemCount() {
    return 0;
  }

  @Override protected int getFooterItemCount() {
    //TODO
    return 0;
  }

  @Override protected int getContentItemCount() {
    return mNewsBeans.size();
  }

  @Override protected RecyclerView.ViewHolder onCreateHeaderItemViewHolder(ViewGroup parent,
      int headerViewType) {
    return null;
  }

  @Override protected RecyclerView.ViewHolder onCreateFooterItemViewHolder(ViewGroup parent,
      int footerViewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.include_progressbar, parent, false);
    return new FooterViewHolder(view);
  }

  @Override protected RecyclerView.ViewHolder onCreateContentItemViewHolder(ViewGroup parent,
      int contentViewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_content, parent, false);
    return new ContentViewHolder(view);
  }

  @Override protected void onBindHeaderItemViewHolder(RecyclerView.ViewHolder headerViewHolder,
      int position) {

  }

  @Override protected void onBindFooterItemViewHolder(RecyclerView.ViewHolder footerViewHolder,
      int position) {
    Log.d(TAG, "onBindFooterItemViewHolder" + position);
  }

  @Override protected void onBindContentItemViewHolder(RecyclerView.ViewHolder holder,
      int position) {

    ContentViewHolder holder_typed = (ContentViewHolder) holder;

    holder_typed.mCardView.setCardBackgroundColor(
        mContext.getResources().getColor(ThemePref.getItemBackgroundResColor(mContext)));
    final Datum newsBean = mNewsBeans.get(position);
    holder_typed.title.setText(newsBean.getTitle());
    holder_typed.time.setText(newsBean.getTimeRelease());
    holder_typed.excerpt.setText(newsBean.getSimpleContent());

    //XX0
    if (newsBean.getImage1().trim().length() == 0 || newsBean.getImage2().trim().length() == 0) {
      //100 010
      holder_typed.threeImages.setVisibility(View.GONE);
      //000
      if (newsBean.getImage1().trim().length() == 0) {
        holder_typed.image1.setVisibility(View.GONE);
      } else {
        //100
        Picasso.with(mContext).load(newsBean.getImage1()).into(holder_typed.image1);
      }
    } else {
      //11x
      holder_typed.image1.setVisibility(View.GONE);
      Picasso.with(mContext).load(newsBean.getImage1()).into(holder_typed.image2);
      Picasso.with(mContext).load(newsBean.getImage2()).into(holder_typed.image3);
      if (newsBean.getImage3().trim().length() == 0) {
        //110
        holder_typed.image3.setVisibility(View.GONE);
      } else {
        //111
        Picasso.with(mContext).load(newsBean.getImage3()).into(holder_typed.image4);
      }
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mContext, DetailedActivity.class);
        intent.putExtra("id", newsBean.getId());
        intent.putExtra("category", newsBean.getCategory());
        mContext.startActivity(intent);
      }
    });
  }

  public static class ContentViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_card_holder) CardView mCardView;
    @InjectView(R.id.item_title) TextView title;
    @InjectView(R.id.item_time) TextView time;
    @InjectView(R.id.item_excerpt) TextView excerpt;
    @InjectView(R.id.item_image1) ImageView image1;
    @InjectView(R.id.item_image2) ImageView image2;
    @InjectView(R.id.item_image3) ImageView image3;
    @InjectView(R.id.item_image4) ImageView image4;
    @InjectView(R.id.item_three_images_layout) LinearLayout threeImages;

    public ContentViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }

}

