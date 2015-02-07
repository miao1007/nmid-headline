package cn.edu.cqupt.nmid.headline.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {
	public static final String IMAGE_FILE = "sample_file.jpg";
	private static String APPLICATION_TAG = "GALLERY_CAMERA_DEMO";
	private static String path;
	
	private static void initPath(Context context) {
		
		// Find the dir to save cached images
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GalleryCameraDemo/images";
		} else {
			path = context.getCacheDir().getAbsolutePath();
		}
	}
	
	public static String getPath(String folderName) {
		return path + folderName + "/";
	}
	
	public static void createDefaultFolder(Context context) {
		initPath(context);
		File file = new File(path);
		boolean success = file.mkdirs();
		Log.i(APPLICATION_TAG, success + "");
	}
	
	public static void createFolder(String folder) {
		// initPath(folder);
		File file = new File(path + "/" + folder);
		boolean success = file.mkdirs();
		Log.i(APPLICATION_TAG, success + "");
	}
	
	public static File createFile(String filename) {
		File file = new File(path + "/" + filename);
		return file;
	}
	
	public static File createFile(String folderName, String filename) {
		File file = new File(path + folderName, filename);
		
		return file;
	}
	
	public static String[] getAllFiles(String folderName) {
		File file = new File(path + "/" + folderName);
		return file.list();
	}
	
	public static Uri saveImageFile(Context context, Uri uri) {
		OutputStream fOut = null;
		Bitmap bitmap = null;
		File file = new File(path + "/" + IMAGE_FILE);
		try {
			bitmap = ImageUtils.getThumbnail(context, uri, 500);
			
			fOut = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
			fOut.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
      return null;
		}finally {
			try {
				if(fOut != null) {
					fOut.close();
				}
				if(bitmap != null) {
					bitmap.recycle(); bitmap = null;
				}
        return Uri.fromFile(file);
			} catch (IOException e) {
				e.printStackTrace();
        return null;
			}
		}
	}
	
}
