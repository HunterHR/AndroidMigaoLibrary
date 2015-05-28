package com.szlanyou.common.cropimage;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
/**
 * 
 * @author R.W.
 *
 */
public class ImageUtil {
	
	private static ImageUtil mImageUtil;
	private ImageUtil() {};
	public static ImageUtil ImageUtils() {
		if (mImageUtil == null) {
			mImageUtil = new ImageUtil();
		}
		return mImageUtil;
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	
	/**
     * 保持长宽比缩小Bitmap
     *
     * @param bitmap
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {

        int originWidth  = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        // no need to resize 
        if (originWidth < maxWidth && originHeight < maxHeight) {
            return bitmap;
        }

        int width  = originWidth;
        int height = originHeight;

        // 若图片过宽, 则保持长宽比缩放图片
        if (originWidth > maxWidth) {
            width = maxWidth;

            double i = originWidth * 1.0 / maxWidth;
            height = (int) Math.floor(originHeight / i);

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }

        // 若图片过长, 则从上端截取
        if (height > maxHeight) {
            height = maxHeight;
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        }
        return bitmap;
    }
}
