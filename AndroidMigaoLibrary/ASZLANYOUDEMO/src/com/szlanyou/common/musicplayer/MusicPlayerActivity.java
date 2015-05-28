package com.szlanyou.common.musicplayer;

import com.szlanyou.common.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MusicPlayerActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_musicplayer);

	}

	// 触发事件，这里是一个点击按钮事件触发
	public void OK(View view) {
//		MusicPlayer.invokeSystemMP(this);
//		MusicPlayer.invokeNetMP(this);
		MusicPlayer.invokeThirdMP(this);
	}
}