package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.controller.bean.NewsBean;
import cn.edu.cqupt.nmid.headline.ui.activity.DetailedActivity;

public class FeedAdapter extends GenericAdapter<NewsBean> {


    public FeedAdapter(LinkedList<NewsBean> list, Context context) {
        super(list, context);
    }

    class ViewHolder {
        TextView title;
        TextView time;
        TextView simpleContent;
        ImageView image1;
        ImageView image2;
        ImageView image3;
        ImageView image4;
        LinearLayout threeImage;

        public void initView() {
            if (simpleContent != null)
                simpleContent.setVisibility(View.VISIBLE);
            if (image1 != null)
                image1.setVisibility(View.VISIBLE);
            if (threeImage != null)
                threeImage.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public View getDataRow(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = ((Activity) context).getLayoutInflater().inflate(R.layout.adapter, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.simpleContent = (TextView) convertView.findViewById(R.id.simpleContent);
            holder.image1 = (ImageView) convertView.findViewById(R.id.image1);
            holder.image2 = (ImageView) convertView.findViewById(R.id.image2);
            holder.image3 = (ImageView) convertView.findViewById(R.id.image3);
            holder.image4 = (ImageView) convertView.findViewById(R.id.image4);
            holder.threeImage = (LinearLayout) convertView.findViewById(R.id.threeImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.initView();
        }
        final NewsBean bean = super.dataList.get(position);
        holder.title.setText(bean.getTitle());
        holder.time.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        if ("".equals(bean.getImage1())) {
            holder.image1.setVisibility(View.GONE);
            holder.threeImage.setVisibility(View.GONE);
            holder.simpleContent.setText(bean.getSimpleContent());
        } else if ("".equals(bean.getImage2())) {
            holder.threeImage.setVisibility(View.GONE);
            Picasso.with(context).load(bean.getImage1()).into(holder.image1);
            holder.simpleContent.setText(bean.getSimpleContent());
        } else if ("".equals(bean.getImage3())) {
            holder.image1.setVisibility(View.GONE);
            holder.threeImage.setVisibility(View.GONE);
            holder.simpleContent.setText(bean.getSimpleContent());
        } else {
            holder.image1.setVisibility(View.GONE);
            holder.simpleContent.setVisibility(View.GONE);
            Picasso.with(context).load(bean.getImage1())
                    .into(holder.image2);
            Picasso.with(context).load(bean.getImage2())
                    .into(holder.image3);
            Picasso.with(context).load(bean.getImage3())
                    .into(holder.image4);
        }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailedActivity.class);
                //http://127.0.0.1/txtt/public/api/android/newscontent?id=2&category=1
                intent.putExtra("category", bean.getCategory());
                intent.putExtra("id", bean.get_id());
                intent.putExtra("title", bean.getTitle());
                intent.putExtra("excerpt", bean.getSimpleContent());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public View getFooterView(int position, View convertView,
                              ViewGroup parent) {
        if (position >= serverListSize && serverListSize > 0) {
            // the ListView has reached the last row
            TextView tvLastRow = new TextView(context);
            tvLastRow.setHint("Reached the last row.");
            tvLastRow.setGravity(Gravity.CENTER);
            return tvLastRow;
        }

        if (convertView == null) {
            return ((Activity) context).getLayoutInflater().inflate(R.layout.include_progressbar, null);
        } else {
            return convertView;
        }

    }
}
