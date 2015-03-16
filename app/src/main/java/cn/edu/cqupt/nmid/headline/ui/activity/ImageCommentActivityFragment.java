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

    Log.d(TAG, "onSendClickListener");
    if (!validateComment()) {
      Log.e(TAG, "validateComment faid");
      return;
    }
    btnSendComment.setCurrentState(SendCommentButton.STATE_SEND);
    RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
        .setEndpoint(HeadlineService.END_POINT)
        .build();
    restAdapter.create(ImageService.class)
        .commentImage(id, ShareSDK.getPlatform(v.getContext().getApplicationContext(), QZone.NAME)
            .getDb()
            .getUserName(), ShareSDK.getPlatform(v.getContext().getApplicationContext(), QZone.NAME)
            .getDb()
            .getUserIcon(), etComment.getText().toString(), new Callback<ImageLikeResult>() {
          @Override public void success(ImageLikeResult imageLikeResult, Response response) {
            if (imageLikeResult.status == 1) {
              RetrofitUtils.disMsg(v.getContext(), "success!");
              ImageComment imageComment = new ImageComment();
              imageComment.setComment(etComment.getText().toString());
              imageComments.add(imageComment);
              mCommentAdapter.notifyDataSetChanged();
              etComment.setText(null);
              btnSendComment.setCurrentState(SendCommentButton.STATE_DONE);
            }
          }

          @Override public void failure(RetrofitError error) {
            RetrofitUtils.disErr(v.getContext(), error);
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

    @Override public void onBindViewHolder(CommentsViewHolder holder, int position) {
      holder.mTv_comment.setText(imageComments.get(position).getComment());
      Picasso.with(holder.mIv_user_avater.getContext())
          .load(imageComments.get(position).getAvatar())
          .into(holder.mIv_user_avater);
    }

    @Override public int getItemCount() {
      return imageComments.size();
    }
  }

  public static class CommentsViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.iv_imagecomment_users_avater) ImageView mIv_user_avater;
    @InjectView(R.id.tv_imagecomment_commet) TextView mTv_comment;

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
