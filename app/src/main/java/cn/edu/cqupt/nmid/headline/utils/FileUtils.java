package cn.edu.cqupt.nmid.headline.utils;

import cn.edu.cqupt.nmid.headline.support.GlobalContext;
import java.io.File;

public class FileUtils {

  private static final String AVATAR_CAMARA_CACHE_NAME = "/camara_cache";

  public static File createCachedFile() {
    // Create an image file name

    File file = new File(
        GlobalContext.getInstance().getCacheDir().getAbsolutePath() + AVATAR_CAMARA_CACHE_NAME,
        "._tmp_pic");
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (Exception e) {

      }
    }
    return file;
  }
}