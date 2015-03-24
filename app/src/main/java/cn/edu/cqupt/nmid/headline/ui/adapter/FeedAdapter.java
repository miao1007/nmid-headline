package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.bean.Feed;
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.activity.DetailedActivity;
import cn.edu.cqupt.nmid.headline.utils.LogUtils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by leon on 1/19/15.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ContentViewHolder> {

  private static final String TAG = LogUtils.makeLogTag(FeedAdapter.class);
  private ArrayList<Feed> mNewsBeans;

  public FeedAdapter(ArrayList<Feed> newsBeans) {
    this.mNewsBeans = newsBeans;
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_content, parent, false);
    ContentViewHolder viewHolder = new ContentViewHolder(view);
    viewHolder.mCardView.setCardBackgroundColor(parent.getContext()
        .getResources()
        .getColor(ThemePref.getItemBackgroundResColor(parent.getContext())));
    return viewHolder;
  }

  @Override public void onBindViewHolder(ContentViewHolder holder, int position) {
    //
    //holder.mCardView.setCardBackgroundColor(holder.mCardView.getResources()
    //    .getColor(ThemePref.getItemBackgroundResColor(holder.mCardView.getContext())));
    final Feed newsBean = mNewsBeans.get(position);
    holder.title.setText(newsBean.getTitle());
    holder.time.setText(newsBean.getTimeRelease());
    holder.excerpt.setText(newsBean.getSimple_content());

    //XX0
    if (newsBean.getImage1().trim().length() == 0 || newsBean.getImage2().trim().length() == 0) {
      //100 010
      holder.threeImages.setVisibility(View.GONE);
      //000
      if (newsBean.getImage1().trim().length() == 0) {
        holder.image1.setVisibility(View.GONE);
      } else {
        //100
        Picasso.with(holder.image1.getContext()).load(newsBean.getImage1()).into(holder.image1);
      }
    } else {
      //11x
      holder.image1.setVisibility(View.GONE);
      Picasso.with(holder.image2.getContext()).load(newsBean.getImage1()).into(holder.image2);
      Picasso.with(holder.image3.getContext()).load(newsBean.getImage2()).into(holder.image3);
      if (newsBean.getImage3().trim().length() == 0) {
        //110
        holder.image3.setVisibility(View.GONE);
      } else {
        //111
        Picasso.with(holder.image4.getContext()).load(newsBean.getImage3()).into(holder.image4);
      }
    }

    //MessageQueue messageQueue

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        //Log.d(TAG, "title" + newsBean.getTitle() + "id" + newsBean.getIdmember());
        Intent intent = new Intent(v.getContext(), DetailedActivity.class);
        intent.putExtra(DetailedActivity.PARCELABLE_KEY, newsBean);
        v.getContext().startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return mNewsBeans.size();
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

