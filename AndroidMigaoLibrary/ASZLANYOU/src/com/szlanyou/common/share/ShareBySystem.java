package com.szlanyou.common.share;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import com.szlanyou.common.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.View;

/**
 * 系统分享
 * <p>使用方式：
		<p>ShareBySystem sbs = new ShareBySystem(this);
		
		<p>sbs.savePic();
		<p>sbs.shareMsg( "FF");
		<p>在实际情况中FF字段，也就是分享的内容需要自己去拼接各种字段，自己去弄，到时直接替换“FF”即可
 * @author R.W.
 *
 */
public class ShareBySystem {	
	private static String TAG = ShareBySystem.class.getSimpleName();
	/**
	 * 存放截图的路径
	 */
	private static String mPath = Environment.getExternalStorageDirectory().getPath()
			+ "/carItImage/";
	/**
	 * 包含文件名的路径mPath+name
	 */
	private String fullPath;
	/**
	 * 文件名
	 */
	private String name;
	/**
	 * 需要分享的窗体
	 */
	private Activity activity;

	
	public  ShareBySystem(Activity activity) {
			this.activity = activity;

	}

	/**
	 * 保存截屏图片，调用该方法后需要调用分享的方法，进行分享
	 */
	public void savePic() {
		//文件已JPG的格式保存
		name = DateFormat.format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA))+ ".jpg";

		//调用系统的截屏功能，并且保存图片为Bitmap
		Bitmap bitmap = shotScreen();

		FileOutputStream b = null;

		File file = new File(mPath);		
		file.mkdirs();// 创建文件夹
		
		fullPath = mPath + name;		

		Log.d(TAG,fullPath);
		
		try {
			b = new FileOutputStream(fullPath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				b.flush();
				b.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 截屏方法,调用系统的截屏功能，并且保存图片为Bitmap
	 * 
	 * @return bmp 截屏的bitmap形式
	 */
	private Bitmap shotScreen() {
		View view = this.activity.getWindow().getDecorView();
		Display display = this.activity.getWindowManager().getDefaultDisplay();
		
		view.layout(0, 0, display.getWidth(), display.getHeight());
		// 允许当前窗口保存缓存信息，这样getDrawingCache()方法才会返回一个Bitmap
		view.setDrawingCacheEnabled(true);
		Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache());
		
		return bmp;
	}
	/**
	 * 分享功能
	 * 
	 * @param msgText
	 *            消息内容
	 * @param imgPath
	 *            图片路径，不分享图片则传null
	 */
	public void shareMsg( String msgText,
			String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, getEXTRA_SUBJECT());
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.activity.startActivity(Intent.createChooser(intent, getShareTitle()));
	}
	/**
	 * 	 @param msgText
	 * 分享的内容
	 */
	public void shareMsg(String msgText) {		
		String imgPath = fullPath;
		shareMsg(msgText,imgPath);}
	
	/**
	 *获取分享的EXTRA_SUBJECT
	 * @return
	 */
	private String getEXTRA_SUBJECT() {
		return this.activity.getString(R.string.app_name);
	}
	/**
	 * 获取分享的标题，消息标题
	 * @return
	 */
	private String getShareTitle() {
		return this.activity.getString(R.string.share_by_system);
	}
}
