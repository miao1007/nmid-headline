package cn.edu.cqupt.nmid.headline.utils;

import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import java.io.File;

public class FileUtils {

  public static File createImageFile() {
    // Create an image file name

    File file = new File(GlobalContext.getInstance().getCacheDir().getPath(), "._tmp_pic");
    //try {
    //  File image = File.createTempFile(imageFileName,  /* prefix */
    //      ".jpg",         /* suffix */
    //      file      /* directory */);
    //  return image;
    //} catch (IOException e) {
    //  //do noting
    //  return null;
    //}
    return file;
  }
}