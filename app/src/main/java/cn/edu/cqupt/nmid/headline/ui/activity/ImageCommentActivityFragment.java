package cn.edu.cqupt.nmid.headline.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.image.ImageService;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageComment;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageCommnetReslut;
import cn.edu.cqupt.nmid.headline.support.api.image.bean.ImageLikeResult;
import cn.edu.cqupt.nmid.headline.utils.RetrofitUtils;
import cn.edu.cqupt.nmid.headline.utils.TimeUtils;
import cn.edu.cqupt.nmid.headline.utils.picasso.CircleTransformation;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import com.squareup.picasso.Picasso;
import io.github.froger.instamaterial.ui.view.SendCommentButton;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageCommentActivityFragment extends Fragment
    implements SendCommentButton.OnSendClickListener {

  public static final String TAG = "ImageCommentFragment";

  @InjectView(R.id.rvComments) RecyclerView rvComments;
  @InjectView(R.id.etComment) EditText etComment;
  @InjectView(R.id.btnSendComment) SendCommentButton btnSendComment;
  @InjectView(R.id.llAddComment) LinearLayout llAddComment;
  @InjectView(R.id.contentRoot) LinearLayout contentRoot;

  List<ImageComment> imageComments = new ArrayList<>();
  LinearLayoutManager manager;
  CommentsAdapter mCommentAdapter;
  int id;

  public ImageCommentActivityFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_image_comment, container, false);
    ButterKnife.inject(this, view);
    manager = new LinearLayoutManager(getActivity());
    mCommentAdapter = new CommentsAdapter(imageComments);
    rvComments.setLayoutManager(manager);
    rvComments.setAdapter(mCommentAdapter);

    id = getActivity().getIntent().getIntExtra("id", 1);
    RestAdapter adapter = new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT).build();
    adapter.create(ImageService.class).getImageComments(id, new Callback<ImageCommnetReslut>() {
      @Override public void success(ImageCommnetReslut imageCommnetReslut, Response response) {
        if (imageCommnetReslut.getStatus() == 0) {
          return;
        }
        imageComments.addAll(imageCommnetReslut.getData());
        mCommentAdapter.notifyDataSetChanged();
      }

      @Override public void failure(RetrofitError error) {

      }
    });
    btnSendComment.setOnSendClickListener(this);
    return view;
  }

  @Override public void onSendClickListener(final View v) {

    doComment(v, true);
  }

  private void doComment(final View v, boolean isComment) {
    int cmd = isComment ? 1 : 0;
    Log.d(TAG, "onSendClickListener");
    if (!validateComment()) {
      Log.e(TAG, "validateComment faid");
      return;
    }
    ((SendCommentButton) v).setCurrentState(SendCommentButton.STATE_SEND);
    RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
        .setEndpoint(HeadlineService.END_POINT)
        .build();
    restAdapter.create(ImageService.class)
        .commentImage(id, ShareSDK.getPlatform(v.getContext().getApplicationContext(), QZone.NAME)
            .getDb()
            .getUserName(), ShareSDK.getPlatform(v.getContext().getApplicationContext(), QZone.NAME)
            .getDb()
            .getUserIcon(), etComment.getText().toString(), cmd, new Callback<ImageLikeResult>() {
          @Override public void success(ImageLikeResult imageLikeResult, Response response) {
            if (imageLikeResult.status == 1) {
              RetrofitUtils.disMsg(v.getContext(), "success!");
              ImageComment imageComment = new ImageComment();
              imageComment.setComment(etComment.getText().toString());
              imageComment.setAvatar(
                  ShareSDK.getPlatform(v.getContext().getApplicationContext(), QZone.NAME)
                      .getDb()
                      .getUserIcon());
              imageComment.setIsCancelable(true);
              imageComments.add(0, imageComment);
              mCommentAdapter.notifyItemInserted(0);
              etComment.setText(null);
            }
            ((SendCommentButton) v).setCurrentState(SendCommentButton.STATE_DONE);
          }

          @Override public void failure(RetrofitError error) {
            RetrofitUtils.disErr(v.getContext(), error);
            ((SendCommentButton) v).setCurrentState(SendCommentButton.STATE_DONE);
          }
        });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.reset(this);
  }

  public static class CommentsAdapter extends RecyclerView.Adapter<CommentsViewHolder> {

    private List<ImageComment> imageComments;

    public CommentsAdapter(List<ImageComment> imageComments) {
      this.imageComments = imageComments;
    }

    @Override public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.adapter_imagecomment, parent, false);
      return new CommentsViewHolder(view);
    }

    @Override public void onBindViewHolder(CommentsViewHolder holder, final int position) {
      final ImageComment comment = imageComments.get(position);
      holder.mTv_comment.setText(imageComments.get(position).getComment());
      Picasso.with(holder.mIv_user_avater.getContext())
          .load(comment.getAvatar())
          .transform(new CircleTransformation())
          .into(holder.mIv_user_avater);
      holder.mTv_imagecomment_release_time.setText(TimeUtils.getTimeFormatText(comment.getTime()));
      if (comment.isCancelable()) {
        holder.mIb_imagecomment_commet_cancel.setVisibility(View.VISIBLE);
        holder.mIb_imagecomment_commet_cancel.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
                .build()
                .create(ImageService.class)
                .commentImage(comment.getCommentId(), "", "", "", 0,
                    new Callback<ImageLikeResult>() {
                      @Override
                      public void success(ImageLikeResult imageLikeResult, Response response) {
                        imageComments.remove(0);
                        notifyItemRemoved(position);
                      }

                      @Override public void failure(RetrofitError error) {

                      }
                    });
          }
        });
      }
    }

    @Override public int getItemCount() {
      return imageComments.size();
    }
  }

  public static class CommentsViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.iv_imagecomment_users_avater) ImageView mIv_user_avater;
    @InjectView(R.id.tv_imagecomment_commet) TextView mTv_comment;
    @InjectView(R.id.ib_imagecomment_commet_cancel) ImageButton mIb_imagecomment_commet_cancel;
    @InjectView(R.id.tv_imagecomment_release_time) TextView mTv_imagecomment_release_time;

    public CommentsViewHolder(View itemView) {
      super(itemView);
      ButterKnife.inject(this, itemView);
    }
  }

  private boolean validateComment() {
    if (TextUtils.isEmpty(etComment.getText())) {
      btnSendComment.startAnimation(
          AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error));
      return false;
    }

    return true;
  }
}
