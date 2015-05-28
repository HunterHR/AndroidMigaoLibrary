package com.szlanyou.common.cropimage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.szlanyou.common.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 弹出对话框，选择获取图片的方式，裁剪图片，获取结果
 * 
 *Added (Fragment and Activity）在Fragment和Activity中使用，返回结果在相应的Fragment和Activity中使用结果
 *Added 保存文件
 *
 *扩充：可以将放置裁剪文件图片的地方限制放置三张、或者大于三张图片，到时可以直接从中选取，
 *<p>如果是重新裁剪的，则另存为，不是直接使用。。。。其中的算法，具体结构以后再看看（没时间弄了，day：2014-12-29）
 * @author R.W.
 *
 */
public class CropImage {
	/**
	 * 相机拍照保存的文件名
	 */
	private static String CROP_IMAGE_NAME = "crop_new_photo.jpg";
	/**
	 * 裁剪文件名
	 */
	public static String CROP_IMAGE = "crop_image.jpg";

	/**
	 * From gallery
	 */
	public static int REQUEST_CODE_FROM_GALLERY = 1;
	/**
	 * from camera
	 */
	public static int REQUEST_CODE_FROM_CAMERA = 2;
	/**
	 * 裁剪后返回的请求码
	 */
	public static int REQUEST_CODE_CROP_RESULT = 3;
	/**
	 * 裁剪后的需要显示的图片形式
	 */
	private Bitmap cropBitmap;
	/**
	 * 上一个Activity
	 */
	private Activity activity;

	private CropOKListener cropOKListener;
	/**
	 * 至于，对话框有很多种方式（对话框的操作）
	 */
	private AlertDialog.Builder cropDialog;

	private File photoFile;

	public CropImage(Activity activity, CropOKListener cropOKListener) {
		this.activity = activity;
		this.cropOKListener = cropOKListener;

		init();
	}

	private Fragment fragment;

	public CropImage(Fragment fragment, CropOKListener cropOKListener) {
		this.fragment = fragment;
		this.cropOKListener = cropOKListener;

		init();
	}

	private Activity context;

	private void init() {
		if (fragment != null) {
			context = this.fragment.getActivity();
		}

		if (activity != null) {
			context = this.activity;
		}
	}

	/**
	 * 显示头像设置对话框 以AlertDialog简单的方式（按照需要，进行定制Dialog）
	 */
	public void createCropDialog() {

		cropDialog = new AlertDialog.Builder(context);

		// 设置AlertDialog的内容
		cropDialog
				.setTitle(R.string.crop_image_title)
				.setMessage(R.string.crop_image_message)
				.setPositiveButton(R.string.crop_image_from_camera,
						new OnClickListener() {// From camera
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// 通过相机
								cameraPicture();
							}
						})
				.setNegativeButton(R.string.crop_image_from_gallery,
						new OnClickListener() {// From gallery

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// 通过本地图片库
								galleryPicture();
							}
						}).create().show();

	}

	/**
	 * 调用相机拍照
	 */
	private void cameraPicture() {
		try {

			String photoPath = ImageCachePathUtil.getImageCachePath(context);

			photoFile = new File(photoPath, CROP_IMAGE_NAME);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

			startActivityForResult(intent, REQUEST_CODE_FROM_CAMERA);

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "打开相机失败！", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 调用图库
	 */
	private void galleryPicture() {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");

			startActivityForResult(intent, REQUEST_CODE_FROM_GALLERY);
		} catch (Exception e) {// 什么错误引起的错误
			Toast.makeText(context, "打开图库失败！", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 需要在Activity中使用
	 * 
	 * @param requestCode
	 *            请求码
	 * @param resultCode
	 *            返回结果码
	 * @param data
	 *            数据
	 */
	public void onActivityCropResult(int requestCode, int resultCode,
			Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CODE_FROM_CAMERA) {
				if (photoFile != null)
					doCropImage(Uri.fromFile(photoFile));

			}
			if (requestCode == REQUEST_CODE_FROM_GALLERY && data != null) {
				doCropImage(data.getData());
			}

			if (requestCode == REQUEST_CODE_CROP_RESULT && data != null) {
				// 裁剪后的结果根据需求，进行处理
				// 1、处理结果以Bitmap形式
				// 2、保存裁剪文件
				cropBitmap = cropBitmapResult(data);

				saveBitmap(cropBitmap,ImageCachePathUtil.getImageCachePath(context)+CROP_IMAGE);

				cropOKListener.setOnCropOKListener(cropBitmap);
			}
		}
	}

	/**
	 * 裁剪以Bitmap的形式保存
	 * 
	 * @param data
	 * @return
	 */
	private Bitmap cropBitmapResult(Intent data) {
		return data.getExtras().getParcelable("data");
	}

	/**
	 * 利用系统的去剪切图片
	 * 
	 * @param uri
	 */
	private void doCropImage(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 5);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, REQUEST_CODE_CROP_RESULT);
	}

	private void startActivityForResult(Intent intent, int questCode) {
		if (fragment != null) {
			this.fragment.startActivityForResult(intent, questCode);
		}

		if (activity != null) {
			this.activity.startActivityForResult(intent, questCode);
		}
	}

	// 判断是否有被裁剪的图片存在，存在则返回Bitmap类型出去，交给ImageView显示，没有则加载默认的头像
	private static boolean isCropFileExit(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 展示那个裁剪图
	 * 
	 * @param view
	 *            ImageView
	 * @param defaultID
	 *            默认的头像ID
	 */
	public static void showCropImage(String filePath,ImageView view, int defaultID) {
		if (!isCropFileExit(filePath)) {
			view.setImageResource(defaultID);
		} else {
			// 若该文件存在
			Bitmap bitmap = BitmapFactory
					.decodeFile(filePath);
			view.setImageBitmap(bitmap);
		}
	}

	// 将bitmap保存本地
	/** 保存方法 */
	private void saveBitmap(Bitmap bitmap,String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}

		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public interface CropOKListener {
		void setOnCropOKListener(Bitmap bitmap);
	}
}
