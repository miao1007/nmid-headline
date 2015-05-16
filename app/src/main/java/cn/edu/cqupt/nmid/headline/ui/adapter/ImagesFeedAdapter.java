package cn.edu.cqupt.nmid.headline.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import cn.edu.cqupt.nmid.headline.support.pref.ThemePref;
import cn.edu.cqupt.nmid.headline.ui.activity.PhotoViewActivity;
import cn.edu.cqupt.nmid.headline.utils.TimeUtils;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.RetrofitUtils;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.picasso.CircleTransformation;
import cn.edu.cqupt.nmid.headline.utils.thirdparty.picasso.GradientTransformation;
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

public class ImagesFeedAdapter extends RecyclerView.Adapter<ImagesFeedAdapter.StreamViewHolder> {

  List<ImageInfo> knoImageList;
  boolean showLoadingView = false;

  private final Map<RecyclerView.ViewHolder, AnimatorSet> likeAnimations = new HashMap<>();
  private final ArrayList<Integer> likedPositions = new ArrayList<>();

  private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR =
      new DecelerateInterpolator();
  private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR =
      new AccelerateInterpolator();
  private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4);

  private static final int VIEW_TYPE_DEFAULT = 1;
  private static final int VIEW_TYPE_UPLOADING = 2;

  private boolean isLike = true;

  public ImagesFeedAdapter(List<ImageInfo> knoImageList) {
    this.knoImageList = knoImageList;
  }

  @Override public StreamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_stream, parent, false);
    StreamViewHolder viewHolder = new StreamViewHolder(v);
    viewHolder.mCv.setCardBackgroundColor(v.getContext()
        .getApplicationContext()
        .getResources()
        .getColor(ThemePref.getItemBackgroundResColor(v.getContext())));

    return viewHolder;
  }

  @Override public void onBindViewHolder(final StreamViewHolder viewHolder, final int position) {

    bindLoadingFeedItem(viewHolder, position);
    //if (getItemViewType(position) == VIEW_TYPE_UPLOADING){
    //  setUpUploadingAnimation(viewHolder);
    //}
  }

  private void bindLoadingFeedItem(StreamViewHolder viewHolder, int position) {
    final ImageInfo imageInfo = knoImageList.get(position);
    Picasso.with(viewHolder.mIv_stream_previous.getContext())
        .load(imageInfo.getPrevirousurl())
        .placeholder(R.drawable.ic_default_bg)
        .resize(0, 400)
        .transform(new GradientTransformation())
        .into(viewHolder.mIv_stream_previous);
    viewHolder.likesCount.setText(imageInfo.getCount_like() + "人 觉得赞");

    viewHolder.nickName.setText(
        imageInfo.getNickname() + " 发表于 " + TimeUtils.getTimeFormatDate(imageInfo.getUploadtime()));

    if (imageInfo.getAvatar() != null) {
      Picasso.with(viewHolder.mIv_avater.getContext())
          .load(imageInfo.getAvatar())
          .transform(new CircleTransformation())
          .into(viewHolder.mIv_avater);
    }

    if (imageInfo.isHaveClickLike()) {
      //Log.d("StreamAdapter", "isHaveClickLike");
      viewHolder.mBtn_like.setImageResource(R.drawable.ic_heart_red);
    } else {

      //Log.d("StreamAdapter", "!isHaveClickLike");
      viewHolder.mBtn_like.setImageResource(R.drawable.ic_heart_outline_grey);
    }
    disPatchOnClick(viewHolder, position, imageInfo);
  }

  private void disPatchOnClick(final StreamViewHolder viewHolder, final int position,
      final ImageInfo imageInfo) {

    viewHolder.mBtn_like.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(final View v) {

        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(HeadlineService.END_POINT)
            .build();
        adapter.create(ImageService.class)
            .likeImage(knoImageList.get(position).getIdmember(),
                imageInfo.isHaveClickLike() ? 0 : 1, new Callback<ImageLikeResult>() {
                  @Override
                  public void success(ImageLikeResult imageLikeResult, Response response) {
                    if (imageLikeResult.status == 1) {
                      RetrofitUtils.disMsg(v.getContext(),
                          !imageInfo.isHaveClickLike() ? "Success!" : "取消成功");
                      int currentLike =
                          imageInfo.getCount_like() + (imageInfo.isHaveClickLike() ? (0) : (1));
                      viewHolder.likesCount.setText(currentLike + "人 觉得赞");
                      updateHeartButton(viewHolder, true, !imageInfo.isHaveClickLike());
                      imageInfo.setIsLike(!imageInfo.isHaveClickLike());
                    }
                  }

                  @Override public void failure(RetrofitError error) {
                    RetrofitUtils.disErr(v.getContext(), error);
                  }
                });
      }
    });

    viewHolder.mIv_stream_previous.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), PhotoViewActivity.class);
        intent.putExtra(PhotoViewActivity.IMAGE_SIEZ_FULL, imageInfo.getImageurl());
        intent.putExtra(PhotoViewActivity.IMAGE_SIEZ_PREVIOUS, imageInfo.getPrevirousurl());
        v.getContext().startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return knoImageList.size();
  }

  public static class StreamViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.item_stream_imageview) ImageView mIv_stream_previous;
    @InjectView(R.id.item_stream_likes_count) TextView likesCount;
    @InjectView(R.id.item_stream_nick_name) TextView nickName;
    @InjectView(R.id.stream_btnLike) ImageButton mBtn_like;
    @InjectView(R.id.card_view) CardView mCv;
    @InjectView(R.id.iv_stream_avatar) ImageView mIv_avater;

    public StreamViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }

  private void updateHeartButton(final StreamViewHolder holder, boolean animated,
      final boolean isLike) {
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
            if (isLike) {
              holder.mBtn_like.setImageResource(R.drawable.ic_heart_red);
            } else {
              holder.mBtn_like.setImageResource(R.drawable.ic_heart_outline_grey);
            }
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

  @Override public int getItemViewType(int position) {
    if (showLoadingView && position == 0) {
      return VIEW_TYPE_UPLOADING;
    } else {
      return VIEW_TYPE_DEFAULT;
    }
  }

  public void showLoadingView() {
    showLoadingView = true;
    notifyItemChanged(0);
  }

  private void resetLikeAnimationState(StreamViewHolder holder) {
    likeAnimations.remove(holder);
  }
}
