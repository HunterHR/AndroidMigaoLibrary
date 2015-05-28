package com.szlanyou.common.cropimage;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * 自动获取图片缓存的方式
 * @author R.W.
 *
 */
public class ImageCachePathUtil {

	public static String getImageCachePath(Context context) {
		
		String sdCardPath = "/Android/data/";
		String dataPath = "/data/";
		String cache = "/cache/";
		
		String packageName = context.getPackageName();
		
		File fileDir = null;
		String filePath = null;
	
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			fileDir = Environment.getExternalStorageDirectory();//  sdcard
			filePath = sdCardPath+packageName+"/";// /Android/data/packageName/
		} else {
			fileDir = Environment.getDataDirectory();// data
			filePath = dataPath+packageName+cache;  //   /data/packageName/cache/
		}
		
		StringBuffer path = new StringBuffer();
		
		StringBuffer cachePath = new StringBuffer();
		
		path.append(fileDir.getPath()).append(filePath);//
		
		cachePath.append(path.toString()).append(".cache");
		
		File fileCache = new File(cachePath.toString());
		if (!fileCache.exists()) {
			fileCache.mkdirs();
		}
		return path.toString();
	}
}
