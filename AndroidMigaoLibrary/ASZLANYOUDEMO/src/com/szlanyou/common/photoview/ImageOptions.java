package com.szlanyou.common.photoview;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.szlanyou.common.R;


public class ImageOptions {
	private static DisplayImageOptions optionsCorner;
	private static DisplayImageOptions optionsNormal;
	private static DisplayImageOptions optionsNormal1;
	private static DisplayImageOptions optionsSurface;
	private static DisplayImageOptions optionsSize;

	public static DisplayImageOptions getCornerOption(int nCorner) {
		if (optionsCorner == null) {
			optionsCorner = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true)
					.considerExifParams(true)
					.displayer(new RoundedBitmapDisplayer(20)).build();
		}
		return optionsCorner;
	}

	public static DisplayImageOptions getNormalOption() {
		if (optionsNormal == null) {
			optionsNormal = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true)
					.considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
		}
		return optionsNormal;
	}

	public static DisplayImageOptions getNormalOption1() {
		if (optionsNormal1 == null) {
			optionsNormal1 = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true)
					.considerExifParams(true)
					.showImageOnLoading(R.drawable.ic_launcher)
					.showImageOnFail(R.drawable.ic_launcher)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
		}
		return optionsNormal1;
	}

	public static DisplayImageOptions getSizeOption() {
		if (optionsSize == null) {
			optionsSize = new DisplayImageOptions.Builder().cacheInMemory(true)
					.cacheOnDisc(true).considerExifParams(true)
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
		}
		return optionsSize;
	}

	public static DisplayImageOptions getSurfaceOption() {
		if (optionsSurface == null) {
			optionsSurface = new DisplayImageOptions.Builder()
					.cacheInMemory(true).cacheOnDisc(true)
					.considerExifParams(true)
					.showImageOnLoading(R.drawable.ic_launcher)
					.showImageForEmptyUri(R.drawable.ic_launcher)
					.showImageOnFail(R.drawable.ic_launcher)
					.bitmapConfig(Bitmap.Config.RGB_565).build();
		}
		return optionsSurface;
	}
}
