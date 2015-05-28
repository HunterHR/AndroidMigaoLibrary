/**
 * 
 */
package com.szlanyou.common.photoview;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.szlanyou.common.R;
import com.szlanyou.common.views.photoview.PhotoView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author R.W.
 * 
 * 查看图片
 *
 */
public class ViewPhotoActivity extends Activity{	

	public static final int NORMAL_PIC = 1;
	public static final int NORMAL_SURFACE = 2;
	private String[] ImageUrls = {"http://s2.t.itc.cn/mblog/pic/201103/15/0/m_13001189990762.jpg","http://s2.t.itc.cn/mblog/pic/201103/15/0/m_13001189990762.jpg"};
	private int nPicType = NORMAL_PIC;
	private View Vtopbar;

	private ViewPager mViewPager;
	private ImageLoader imageLoader;
	public static final String PHOTO_VIEW_IMAGEURLS = "photo_view_imageurls";
	public static final String PHOTO_VIEW_POSITION = "photo_view_POSITION";
	public static final String PHOTO_TYPE = "PHOTO_TYPE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_photoview);

		mViewPager = (HackyViewPager) findViewById(R.id.view_pager1);
		// setContentView(mViewPager);
//		Intent intent = getIntent();
//		
//		int position = intent.getIntExtra(PHOTO_VIEW_POSITION, 0);
//		try {
//			nPicType = intent.getIntExtra(PHOTO_TYPE, 0);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		ImageUrls = intent.getStringArrayExtra(PHOTO_VIEW_IMAGEURLS);
		
		
		mViewPager.setAdapter(new SamplePagerAdapter(ImageUrls, this, null));
		mViewPager.setCurrentItem(0);

		imageLoader = ImageLoader.getInstance();

	}

	class SamplePagerAdapter extends PagerAdapter {
		private String[] ImageUrls;
		// private LoadImage loadimage;
		private Activity mContext;
		private View mVtopBar;
		private boolean isVisi = false;

		public SamplePagerAdapter(String[] imageurls, Activity context,
				View VtopBar) {
			mContext = context;
			ImageUrls = imageurls;
			mVtopBar = VtopBar;
		}

		@Override
		public int getCount() {
			return ImageUrls == null ? 1 : ImageUrls.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, final int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			if (ImageUrls != null && ImageUrls[position] != null
					&& !"".equals(ImageUrls[position])) {				
				Log.d("dd",ImageUrls[position]);
				imageLoader.displayImage(ImageUrls[position], photoView,
						ImageOptions.getNormalOption());
			} else {
//				photoView
//						.setImageResource(nPicType == NORMAL_SURFACE ? R.drawable.register_logo
//								: R.drawable.default_pic_white);
			}
			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);

			photoView.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					Toast.makeText(ViewPhotoActivity.this, "長安下載", Toast.LENGTH_LONG).show();
					String currentUrl = ImageUrls[position];
					
					
					
					return false;
				}
			});

			photoView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					
				}
			});
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

}
