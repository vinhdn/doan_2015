package bk.vinhdo.taxiads.utils.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImageUtil {

    public static String TAG = ImageUtil.class.getName();

	@SuppressWarnings("deprecation")
	public static void scaleImage(ImageView view, int boundBoxInDp) {
		// Get the ImageView and its bitmap
		Drawable drawing = view.getDrawable();
		Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

		// Get current dimensions
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		// Determine how much to scale: the dimension requiring less scaling is
		// closer to the its side. This way the image always stays inside your
		// bounding box AND either x/y axis touches it.
		float xScale = ((float) boundBoxInDp) / width;
		float yScale = ((float) boundBoxInDp) / height;
		float scale = (xScale <= yScale) ? xScale : yScale;

		// Create a matrix for the scaling and add the scaling data
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);

		// Create a new bitmap and convert it to a format understood by the
		// ImageView
		Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		BitmapDrawable result = new BitmapDrawable(scaledBitmap);
		width = scaledBitmap.getWidth();
		height = scaledBitmap.getHeight();

		// Apply the scaled bitmap
		view.setImageDrawable(result);

		// Now change ImageView's dimensions to match the scaled image
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view
				.getLayoutParams();
		params.width = width;
		params.height = height;
		view.setLayoutParams(params);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap scaleImage(Resources res, int resId, int reqWidth,
			int reqHeight) {
		// create bitmap options
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static Bitmap scaleImageToSizeSpecificed(Resources res, int resId,
			int reqWidth, int reqHeight) {
		Bitmap bmp = scaleImage(res, resId, reqWidth, reqHeight);
		return Bitmap.createScaledBitmap(bmp, reqWidth, reqHeight, false);
	}

	public static Bitmap getThumbnailImage(String filePath) throws IOException {
		FileOutputStream out;
		// image file use to create image u can give any path.
		File land = new File(Environment.getExternalStorageDirectory()
				.getAbsoluteFile() + "/portland.jpg");

		// filePath is your video file path.
		Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath,
				MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		out = new FileOutputStream(land.getPath());
		out.write(byteArray);
		out.close();

		return bitmap;

	}

	public static Bitmap getVideoFrame(String uri) {
		Log.d(TAG, "uri");
		if (android.os.Build.VERSION.SDK_INT <= 10) {
			return null;
		}
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(uri);
			return retriever.getFrameAtTime();
		} catch (IllegalArgumentException e) {
			Log.e(TAG,e.toString());
		} catch (RuntimeException e) {
			Log.e(TAG,e.toString());
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException e) {
				Log.e(TAG,e.toString());
			}
		}
		return null;
	}
}
