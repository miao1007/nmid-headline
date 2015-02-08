package cn.edu.cqupt.nmid.headline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.stream.UploadImageService;
import cn.edu.cqupt.nmid.headline.support.api.stream.bean.UploadResult;
import cn.edu.cqupt.nmid.headline.ui.widget.ProgressBarCircular;
import cn.edu.cqupt.nmid.headline.ui.widget.swipebacklayout.SwipeBackActivity;
import cn.edu.cqupt.nmid.headline.utils.FileUtils;
import cn.edu.cqupt.nmid.headline.utils.ImageUtils;
import cn.edu.cqupt.nmid.headline.utils.RetrofitUtils;
import cn.edu.cqupt.nmid.headline.utils.picasso.BlurTransformation;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class UploadActivity extends SwipeBackActivity {

  private static int THUMBNAIL_SIZE = 300;
  private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 232;

  @InjectView(R.id.progressBarCircularIndeterminate) ProgressBarCircular
      mProgressBarCircularIndeterminate;
  @InjectView(R.id.upload_toolbar) Toolbar mToolbar;
  @InjectView(R.id.activity_upload_select) ImageView mActivityUploadSelect;
  @InjectView(R.id.ivLike) ImageView mIvLike;
  @InjectView(R.id.activity_upload_upload) Button mActivityUploadUpload;

  @OnClick(R.id.activity_upload_select) void click() {
    dispatchSelectImage();
  }

  @OnClick(R.id.activity_upload_upload) void upload() {
    tryUploadFrimUri(outputFileUri);
  }

  private static Uri outputFileUri;
  private Bitmap bmp;
  private MaterialDialog mMaterialDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload);
    ButterKnife.inject(this);
    trySetupToolbar(mToolbar);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (bmp != null && !bmp.isRecycled()) {
      bmp.recycle();
      bmp = null;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    try {
      if (resultCode == Activity.RESULT_OK) {
        if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
          if (data != null) {
            outputFileUri = data.getData();
          }
          bmp = ImageUtils.getThumbnail(this, outputFileUri, THUMBNAIL_SIZE);
          outputFileUri = FileUtils.saveImageFile(this, outputFileUri);
          Picasso.with(this)
              .load(outputFileUri)
              .transform(new BlurTransformation(this))
              .into(mActivityUploadSelect);
          mMaterialDialog = new MaterialDialog(this);
          mMaterialDialog.setCanceledOnTouchOutside(false);
          mMaterialDialog.setTitle("是否上传？")
              .setMessage("请确认内容符合相关规定")
              .setPositiveButton("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  tryUploadFrimUri(outputFileUri);
                }
              })
              .setNegativeButton("CANCEL", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mMaterialDialog.dismiss();
                }
              });

          mMaterialDialog.show();
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private void tryUploadFrimUri(Uri mImageUri) {
    if (mImageUri != null) {
      String nickname;
      if (ShareSDK.getPlatform(QZone.NAME).isValid()) {
        nickname = ShareSDK.getPlatform(QZone.NAME).getDb().getUserName();
      } else {
        nickname = "手机用户";
      }
      mProgressBarCircularIndeterminate.setVisibility(View.VISIBLE);
      RetrofitUtils.disMsg(UploadActivity.this, "上传中!");
      new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
          .setLogLevel(RestAdapter.LogLevel.FULL)
          .build()
          .create(UploadImageService.class)
          .updateImage(new TypedFile("image/*", new File(mImageUri.getPath())),
              new TypedString(nickname), new TypedString(Build.MODEL),
              new Callback<UploadResult>() {
                @Override public void success(UploadResult uploadResult, Response response) {
                  mMaterialDialog.dismiss();
                  mProgressBarCircularIndeterminate.setVisibility(View.GONE);
                  if (uploadResult.getStatus() == 1) {
                    RetrofitUtils.disMsg(UploadActivity.this, "upload successfully!");
                  } else {
                    RetrofitUtils.disMsg(UploadActivity.this, "upload failed!");
                  }
                  bmp.recycle();
                  UploadActivity.this.finishActivity(3);
                }

                @Override public void failure(RetrofitError error) {
                  mMaterialDialog.dismiss();
                  RetrofitUtils.disErr(UploadActivity.this, error);
                  mProgressBarCircularIndeterminate.setVisibility(View.GONE);
                  bmp.recycle();
                }
              });
    } else {
      mMaterialDialog.dismiss();
      RetrofitUtils.disMsg(UploadActivity.this, "null image!");
      bmp.recycle();
    }
  }

  private void dispatchSelectImage() {
    // Determine Uri of camera mActivityMainImage to save.
    FileUtils.createDefaultFolder(UploadActivity.this);
    final File file = FileUtils.createFile(FileUtils.IMAGE_FILE);
    outputFileUri = Uri.fromFile(file);

    // Camera.
    final Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

    final Intent galleryIntent =
        new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    galleryIntent.setType("image/*");

    final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Image");
    // Add the camera options.
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { captureIntent });
    startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
  }
}
