package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.image.ImageService;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageInfo;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageLikeResult;
import cn.edu.cqupt.nmid.headline.ui.activity.ImageCommentActivity;
import cn.edu.cqupt.nmid.headline.utils.RetrofitUtils;
import cn.edu.cqupt.nmid.headline.utils.TimeUtils;
import cn.edu.cqupt.nmid.headline.utils.picasso.GradientTransformation;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by leon on 2/2/15.
 */
public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.StreamViewHolder> {

  List<ImageInfo> knoImageList;

  private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
  private final ArrayList<Integer> likedPositions = new ArrayList<>();

  private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR =
      new DecelerateInterpolator();
  private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR =
      new AccelerateInterpolator();
  private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

  public StreamAdapter(List<ImageInfo> knoImageList) {

    this.knoImageList = knoImageList;
  }

  @Override public StreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_stream, parent, false);
    StreamViewHolder viewHolder = new StreamViewHolder(v);
    return viewHolder;
  }

  @Override public void onBindViewHolder(final StreamViewHolder viewHolder, final int position) {
    final ImageInfo imageInfo = knoImageList.get(position);
    Picasso.with(viewHolder.image.getContext())
        .load(imageInfo.getPrevirousurl())
        .placeholder(R.drawable.lorempixel)
        .transform(new GradientTransformation())
        .into(viewHolder.image);
    viewHolder.likesCount.setText(imageInfo.getCount_praise() + "人 觉得赞");
    viewHolder.nickName.setText(
        imageInfo.getNickname() + " 发表于 " + TimeUtils.getTimeFormatText(imageInfo.getUploadtime()));
    viewHolder.upload_time.setText(TimeUtils.getTimeFormatText(imageInfo.getUploadtime()));
    viewHolder.mBtn_comments.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent;
        intent = new Intent(v.getContext(), ImageCommentActivity.class);
        intent.putExtra("id", knoImageList.get(position).getId());
        v.getContext().startActivity(intent);
      }
    });
    viewHolder.mBtn_like.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {
        RestAdapter adapter =
            new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT).build();
        adapter.create(ImageService.class)
            .likeImage(knoImageList.get(position).getId(), new Callback<ImageLikeResult>() {
              @Override public void success(ImageLikeResult imageLikeResult, Response response) {
                if (imageLikeResult.status == 1) {
                  RetrofitUtils.disMsg(v.getContext(), "Success!");
                  int currentLike = imageInfo.getCount_praise() + 1;
                  viewHolder.likesCount.setText(currentLike + "人 觉得赞");
                  updateHeartButton(viewHolder,true);
                }
              }

              @Override public void failure(RetrofitError error) {
                RetrofitUtils.disErr(v.getContext(), error);
              }
            });
      }
    });
  }

  @Override public int getItemCount() {
    return knoImageList.size();
  }

  public static class StreamViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_stream_imageview) ImageView image;
    @InjectView(R.id.item_stream_likes_count) TextView likesCount;
    @InjectView(R.id.item_stream_nick_name) TextView nickName;
    @InjectView(R.id.item_stream_upload_time) TextView upload_time;
    @InjectView(R.id.stream_btnComments) ImageButton mBtn_comments;
    @InjectView(R.id.stream_btnLike) ImageButton mBtn_like;

    public StreamViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }

  private void updateHeartButton(final StreamViewHolder holder, boolean animated) {
    if (animated) {
      if (!likeAnimations.containsKey(holder)) {
        AnimatorSet animatorSet = new AnimatorSet();
        likeAnimations.put(holder, animatorSet);

        ObjectAnimator rotationAnim =
            ObjectAnimator.ofFloat(holder.mBtn_like, "rotation", 0f, 360f);
        rotationAnim.setDuration(300);
        rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(holder.mBtn_like, "scaleX", 0.2f, 1f);
        bounceAnimX.setDuration(300);
        bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);

        ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(holder.mBtn_like, "scaleY", 0.2f, 1f);
        bounceAnimY.setDuration(300);
        bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
        bounceAnimY.addListener(new AnimatorListenerAdapter() {
          @Override public void onAnimationStart(Animator animation) {
            holder.mBtn_like.setImageResource(R.drawable.ic_heart_red);
          }
        });

        animatorSet.play(rotationAnim);
        animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);

        animatorSet.addListener(new AnimatorListenerAdapter() {
          @Override public void onAnimationEnd(Animator animation) {
            resetLikeAnimationState(holder);
          }
        });

        animatorSet.start();
      }
    } else {
      if (likedPositions.contains(holder.getPosition())) {
        holder.mBtn_like.setImageResource(R.drawable.ic_heart_red);
      } else {
        holder.mBtn_like.setImageResource(R.drawable.ic_heart_outline_grey);
      }
    }
  }

  private void resetLikeAnimationState(StreamViewHolder holder) {
    likeAnimations.remove(holder);
  }
}
