package com.szlanyou.common.musicplayer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * 调用系统音乐播放器
 * 
 * @author R.W.
 * 
 */
public class MusicPlayer {
	
	private static String NET_KUGOU_URL = "http://web.kugou.com/";

	/**
	 * invokeSystemMP
	 * <p>
	 * 调用系统的音乐播放器
	 * 
	 * @param context
	 */
	public static void invokeSystemMP(Context context) {
		Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");// 调用系统的

		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * invokeNetMP
	 * <p>
	 * 调用网络的音乐播放器
	 * 
	 * @param context
	 */
	public static void invokeNetMP(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(NET_KUGOU_URL));
		intent.setClassName("com.android.browser",
				"com.android.browser.BrowserActivity");
		context.startActivity(intent);
	}

	/**
	 * invokeThirdMP
	 * <p>
	 * 调用第三方的音乐播放器
	 * 怎么获取手机中的第三方的音乐播放器呢？
	 * 
	 * @param context
	 */
	public static void invokeThirdMP(Context context) {
		
//		ArrayList<PackageInfo> appList = (ArrayList<PackageInfo>) getAllApps(context);
		
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);//隐式打开		
		Uri uri=Uri.parse("file://"+"*.mp3");

		intent.setDataAndType(uri, "audio/*");
		
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		
	}
	
	/**
	 * 查询手机内非系统应用
	 * @param context
	 * @return
	 */ 
	private static List<PackageInfo> getAllApps(Context context) { 
	    List<PackageInfo> apps = new ArrayList<PackageInfo>(); 
	    PackageManager pManager = context.getPackageManager(); 
	    //获取手机内所有应用 
	    List<PackageInfo> paklist = pManager.getInstalledPackages(0); 
	    for (int i = 0; i < paklist.size(); i++) { 
	        PackageInfo pak = (PackageInfo) paklist.get(i); 
	        //判断是否为非系统预装的应用程序 
	        if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) { 
	            // customs applications 
	            apps.add(pak); 
	        } 
	    } 
	    return apps; 
	}
}
