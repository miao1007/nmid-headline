package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.stream.bean.Datum;
import cn.edu.cqupt.nmid.headline.utils.picasso.GradientTransformation;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by leon on 2/2/15.
 */
public class StreamAdapter extends HeaderFooterRecyclerViewAdapter {

  Context context;
  List<Datum> knoImageList;

  public StreamAdapter(Context context, List<Datum> knoImageList) {
    this.context = context;
    this.knoImageList = knoImageList;
  }

  @Override protected int getHeaderItemCount() {
    return 0;
  }

  @Override protected int getFooterItemCount() {
    return 1;
  }

  @Override protected int getContentItemCount() {
    return knoImageList.size();
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
    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_stream, parent, false);
    return new ContentViewHolder(v);
  }

  @Override protected void onBindHeaderItemViewHolder(RecyclerView.ViewHolder headerViewHolder,
      int position) {

  }

  @Override protected void onBindFooterItemViewHolder(RecyclerView.ViewHolder footerViewHolder,
      int position) {

  }

  @Override protected void onBindContentItemViewHolder(RecyclerView.ViewHolder contentViewHolder,
      int position) {
    Datum datum = knoImageList.get(position);
    ContentViewHolder viewHolder = (ContentViewHolder) contentViewHolder;
    Picasso.with(context)
        .load(datum.getPrevirousurl())
        .placeholder(R.drawable.lorempixel)
        .transform(new GradientTransformation())
        .into(viewHolder.image);
    viewHolder.device_info.setText(datum.getDeviceinfo());
    viewHolder.nickName.setText(datum.getNickname());
    viewHolder.upload_time.setText(datum.getUploadtime());
  }

  public static class ContentViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_stream_imageview) ImageView image;
    @InjectView(R.id.item_device_info) TextView device_info;
    @InjectView(R.id.item_nick_name) TextView nickName;
    @InjectView(R.id.item_upload_time) TextView upload_time;

    public ContentViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }
}
