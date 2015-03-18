package cn.edu.cqupt.nmid.headline.utils;

import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

  public static File createImageFile() {
    // Create an image file name
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/headline");
    try {
      File image = File.createTempFile(imageFileName,  /* prefix */
          ".jpg",         /* suffix */
          file      /* directory */);
      return image;
    } catch (IOException e) {
      //do noting
      return null;
    }
  }
}