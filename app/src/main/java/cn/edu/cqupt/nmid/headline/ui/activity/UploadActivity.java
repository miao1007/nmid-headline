package cn.edu.cqupt.nmid.headline.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.edu.cqupt.nmid.headline.R;
import cn.edu.cqupt.nmid.headline.support.api.headline.HeadlineService;
import cn.edu.cqupt.nmid.headline.support.api.stream.UploadImageService;
import cn.edu.cqupt.nmid.headline.support.api.stream.bean.UploadResult;
import cn.edu.cqupt.nmid.headline.ui.widget.swipebacklayout.SwipeBackActivity;
import cn.edu.cqupt.nmid.headline.utils.FileUtils;
import cn.edu.cqupt.nmid.headline.utils.ImageUtils;
import cn.edu.cqupt.nmid.headline.utils.RetrofitUtils;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qzone.QZone;
import java.io.File;
import java.io.IOException;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedString;

public class UploadActivity extends SwipeBackActivity {

  private static int THUMBNAIL_SIZE = 300;
  private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 232;
  @InjectView(R.id.activity_main_text) TextView mActivityMainText;
  @InjectView(R.id.activity_main_checkbox) CheckBox mActivityMainCheckbox;
  @InjectView(R.id.activity_main_image) ImageView mActivityMainImage;

  @OnClick(R.id.activity_upload_select) void click() {
    dispatchSelectImage();
  }

  @OnClick(R.id.activity_upload_upload) void upload() {
    tryUploadFrimUri(outputFileUri);
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

  private static Uri outputFileUri;
  private Bitmap bmp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload);
    ButterKnife.inject(this);
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
          mActivityMainImage.setImageBitmap(bmp);
          outputFileUri = FileUtils.saveImageFile(this, outputFileUri);
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

      RetrofitUtils.disSnackMsg(UploadActivity.this,"上传中!");
      new RestAdapter.Builder().setEndpoint(HeadlineService.END_POINT)
          .setLogLevel(RestAdapter.LogLevel.FULL)
          .build()
          .create(UploadImageService.class)
          .updateImage(new TypedFile("image/*", new File(mImageUri.getPath())),
              new TypedString(nickname), new TypedString(Build.MODEL),
              new Callback<UploadResult>() {
                @Override public void success(UploadResult uploadResult, Response response) {
                  if (uploadResult.getStatus() == 1) {
                    RetrofitUtils.disSnackMsg(UploadActivity.this,"upload successfully!");
                  } else {
                    RetrofitUtils.disSnackMsg(UploadActivity.this,"upload failed!");
                  }
                }

                @Override public void failure(RetrofitError error) {
                  RetrofitUtils.disErr(UploadActivity.this, error);
                }
              });
    } else {
      RetrofitUtils.disMsg(UploadActivity.this, "null image!");
    }
  }
}
