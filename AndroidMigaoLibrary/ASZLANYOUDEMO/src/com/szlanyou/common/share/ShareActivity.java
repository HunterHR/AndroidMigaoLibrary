package com.szlanyou.common.share;

import com.szlanyou.common.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class ShareActivity extends Activity {

	private ImageView iv_show;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_crop_image);
		
		
		iv_show = (ImageView) findViewById(R.id.iv_show);
	}

	//触发事件，这里是一个点击按钮事件触发
	public void OK(View view) {
		// 测试系统分享功能
		
		 ShareBySystem sbs = new ShareBySystem(this);
		  
		  sbs.savePic(); sbs.shareMsg( "FF");
		

	}
}
