package com.szlanyou.common.cropimage;

import com.szlanyou.common.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class CropImageActivity extends Activity {

	private ImageView iv_show;
	
	private CropImage cropImage;
	private CropImage.CropOKListener croOKListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_crop_image);
		
		
		iv_show = (ImageView) findViewById(R.id.iv_show);
		
		croOKListener = new CropImage.CropOKListener() {			
			@Override
			public void setOnCropOKListener(Bitmap bitmap) {
				
				Log.d("cropok","在此处进行你想要的操作。比如：显示图片；将图片资源转为byte数组上传服务器等等。");
				iv_show.setImageBitmap(bitmap);
				
//				byte[] bitmapBytes = ImageUtil.ImageUtils().Bitmap2Bytes(bitmap);				
//				if(bitmapBytes!=null){
				//对于，现在不需要将图片上传至服务器，那么，需要去需找图片的位置，将其显示出来即可！具体图片的放置位置还需要进行全局静态的，在加载应用的时候就要获取。
				//没有，则显示一张默认的图片。这张默认的图片，是放在资源res中。
//					new uploadCropImageTask();
//				}
			}
		};		
		//在findViewByID的后面使用，创建了croOKListener对象之后
		cropImage = new CropImage(this,croOKListener);
	}

	//触发事件，这里是一个点击按钮事件触发
	public void OK(View view) {
		// 测试系统分享功能
		/*
		 * ShareBySystem sbs = new ShareBySystem(this);
		 * 
		 * sbs.savePic(); sbs.shareMsg( "FF");
		 */

		// 测试头像截取功能
		// 步骤：1、弹出对话框；2、处理；3、结果，结果返回在监听器中的Bitmap bitmap
		// 
		cropImage.createCropDialog();

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);		
		cropImage.onActivityCropResult(requestCode,resultCode,data);
	}
}
