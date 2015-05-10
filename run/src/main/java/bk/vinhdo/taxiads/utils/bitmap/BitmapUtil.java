package bk.vinhdo.taxiads.utils.bitmap;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore.Images;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import bk.vinhdo.taxiads.utils.Log;

/**
 * Bitmap util class
 *
 * @author khanhnv
 */
public class BitmapUtil {

    public final static int MAX_WIDTH_IMAGE_ALLOW = 2048;
    public final static int MAX_HEIGHT_IMAGE_ALLOW = 2048;

    private static Bitmap mLoadingBitmap;
    private static volatile Context staticContext;

    public static Context getStaticContext() {
        return staticContext;
    }

    public static void initializeStaticContext(Context currentContext) {
        if ((currentContext != null) && (staticContext == null)) {
            Context applicationContext = currentContext.getApplicationContext();
            staticContext = (applicationContext != null) ? applicationContext
                    : currentContext;
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId,
                                                  int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inScaled = false;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeBitmapFromFile(String path, int reqWidth,
                                              int reqHeight) {

        Uri uri = Uri.parse(path);
        float rotation = getRotationInDegreeOfImage(staticContext, uri);

        Matrix matrix = new Matrix();
        if (rotation != 0f) {
            matrix.preRotate(rotation);
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        if (rotation != 0f) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }

    /**
     * get bitmap from image path
     *
     * @param scale
     * @param imagePath
     * @return
     */
    public static Bitmap decodeBitmapFromFile(Context context, int scale,
                                              String imagePath) {

        Uri uri = Uri.parse(imagePath);

        float rotation = getRotationInDegreeOfImage(context, uri);

        Matrix matrix = new Matrix();
        if (rotation != 0f) {
            matrix.preRotate(rotation);
        }

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

        if (rotation != 0f) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }

        return bitmap;
    }

    public static Bitmap cropBitmap(Context context, Bitmap source, int left,
                                    int top, int right, int bottom) {
        Bitmap result = null;
        try {
            Paint paint = new Paint();
            paint.setFilterBitmap(true);

            int targetWidth = source.getWidth();
            int targetHeight = source.getHeight();

            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                    targetHeight, Config.ARGB_8888);

            Canvas canvas = new Canvas(targetBitmap);
            canvas.drawBitmap(source,
                    new Rect(0, 0, source.getWidth(), source.getHeight()),
                    new Rect(0, 0, targetWidth, targetHeight), paint);

            Matrix matrix = new Matrix();
            matrix.postScale(1f, 1f);
            result = Bitmap.createBitmap(targetBitmap, left, top, right,
                    bottom, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Bitmap scaleCenterCrop(Bitmap source, int newHeight,
                                         int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width,
        // respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap
        // will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top
                + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our
        // new,
        // scaled bitmap onto it.
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight,
                source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }

    public static Bitmap drawShadow(Bitmap bitmap, int leftRightThk,
                                    int bottomThk, int padTop) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int newW = w - (leftRightThk * 2);
        int newH = h - (bottomThk + padTop);

        Config conf = Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(w, h, conf);
        Bitmap sbmp = Bitmap.createScaledBitmap(bitmap, newW, newH, false);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas c = new Canvas(bmp);

        // Left
        int leftMargin = (leftRightThk + 7) / 2;
        Shader lshader = new LinearGradient(0, 0, leftMargin, 0,
                Color.TRANSPARENT, Color.BLACK, TileMode.CLAMP);
        paint.setShader(lshader);
        c.drawRect(0, padTop, leftMargin, newH, paint);

        // Right
        Shader rshader = new LinearGradient(w - leftMargin, 0, w, 0,
                Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
        paint.setShader(rshader);
        c.drawRect(newW, padTop, w, newH, paint);

        // Bottom
        Shader bshader = new LinearGradient(0, newH, 0, bitmap.getHeight(),
                Color.BLACK, Color.TRANSPARENT, TileMode.CLAMP);
        paint.setShader(bshader);
        c.drawRect(leftMargin - 3, newH, newW + leftMargin + 3,
                bitmap.getHeight(), paint);
        c.drawBitmap(sbmp, leftRightThk, 0, null);

        return bmp;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffffffff;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getCircleBitmap(Bitmap bmp, int radius, int borderColor) {
        Bitmap bitmap = Bitmap.createScaledBitmap(bmp, radius * 2, radius * 2,
                false);
        int w = radius * 2;
        int h = radius * 2;

        Bitmap output = Bitmap.createBitmap(w + 8, h + 8, Config.ARGB_8888);

        Paint p = new Paint();
        p.setAntiAlias(true);

        Canvas c = new Canvas(output);
        c.drawARGB(0, 0, 0, 0);
        p.setStyle(Style.FILL);
        p.setColor(Color.WHITE);
        c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);

        p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        c.drawBitmap(bitmap, 4, 4, p);
        p.setXfermode(null);
        p.setStyle(Style.STROKE);
        p.setColor(borderColor);
        p.setStrokeWidth(3);
        c.drawCircle((w / 2) + 4, (h / 2) + 4, radius, p);

        return output;
    }

    /**
     * Convert Bitmap to String
     *
     * @param bm
     * @param quality
     * @return
     * @throws OutOfMemoryError
     */
    public static String convertBitmapToString(Bitmap bm, int quality)
            throws OutOfMemoryError {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(CompressFormat.PNG, quality, baos);
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }

    /**
     * Convert String to Bitmap
     *
     * @param encodedImage
     * @return
     * @throws OutOfMemoryError
     */
    public static Bitmap convertStringToBitmap(String encodedImage)
            throws OutOfMemoryError {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
        return bitmap;
    }

    /**
     * @param context
     * @return
     */
    public static long getAvailableMemory(Context context) {
        MemoryInfo mi = new MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        return mi.availMem;
    }

    /**
     * get orientation of bitmap
     *
     * @param exifOrientation
     * @return
     */
    public static float exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * rotate bitmap
     *
     * @param context
     * @param uri
     * @return
     */
    public static float getRotationInDegreeOfImage(Context context, Uri uri) {
        if (uri.getScheme().equals("content")) {
            String[] projection = {Images.ImageColumns.ORIENTATION};
            Cursor c = context.getContentResolver().query(uri, projection,
                    null, null, null);
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
        } else if (uri.getScheme().equals("file")) {
            try {
                ExifInterface exif = new ExifInterface(uri.getPath());
                float rotation = exifOrientationToDegrees(exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL));
                return rotation;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0f;
    }

    /**
     * get bitmap from image view
     *
     * @param imageView
     * @return
     */
    public static Bitmap getBitmapFromImageView(ImageView imageView) {
        Bitmap bitmap = null;
        bitmap = Bitmap.createBitmap(imageView.getWidth(),
                imageView.getHeight(), Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.BLACK);
        imageView.draw(canvas);
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
    public static Bitmap getVideoFrame(String uri) {
        Log.d("uri video: " + uri);
        if (Build.VERSION.SDK_INT <= 10) {
            return null;
        }
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(uri);
            return retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            Log.e(e.toString());
        } catch (RuntimeException e) {
            Log.e(e.toString());
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                Log.e(e.toString());
            }
        }
        return null;
    }

    /**
     * Get thumb of video
     *
     * @param uri path to file video on sdcard
     * @return
     */
    public static Bitmap getVideoThumb(String uri) {
        return ThumbnailUtils.createVideoThumbnail(uri,
                Images.Thumbnails.FULL_SCREEN_KIND);
    }

    /**
     * Convert byte array to bitmap
     *
     * @param array
     * @return
     */
    public static Bitmap getByteArrayAsBitmap(byte[] array) {
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }

    /**
     * Write bitmap from drawable to file sdcard
     *
     * @param context
     * @param nameResource
     * @param pathOfImageToWrite
     * @throws IOException
     */
    public static void writeBitmapFromDrawable(Context context,
                                               String nameResource, String pathOfImageToWrite) throws IOException {
        // get id of bitmap from drawable folder
        int id = context.getResources().getIdentifier(nameResource, "drawable",
                context.getPackageName());
        // get bitmap from id
        Bitmap bitmap = BitmapFactory
                .decodeResource(context.getResources(), id);
        if (bitmap != null) {
            // write bitmap
            writeBitmap(bitmap, pathOfImageToWrite, 100);
        }
    }

    public static void writeBitmapFromDrawable(Context context, int resId,
                                               String pathOfImageToWrite) throws IOException {

        // get bitmap from id
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resId);
        if (bitmap != null) {
            // write bitmap
            writeBitmap(bitmap, pathOfImageToWrite, 100);
        }
    }

    /**
     * Write bitmap to file sdcard
     *
     * @param bitmap
     * @param pathOfImage
     * @param quality
     * @throws IOException
     */
    public static void writeBitmap(Bitmap bitmap, String pathOfImage,
                                   int quality) throws IOException {
        // create new file in sd card
        File fileImage = new File(pathOfImage);
        fileImage.createNewFile();
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, quality, arrayOutputStream);

        FileOutputStream fos = new FileOutputStream(fileImage);
        fos.write(arrayOutputStream.toByteArray());
        fos.close();
    }

    /**
     * Convert bitmap to byte array, used for save bitmap to database
     */
    public static byte[] convertBitmapToBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /**
     * Convert bitmap to string base64
     *
     * @param bitmap
     * @return
     */
    public static String convertBitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        byte[] byteArrayImage = convertBitmapToBytes(bitmap);
        String encodedImage = Base64.encodeToString(byteArrayImage,
                Base64.DEFAULT);
        return encodedImage;
    }

    /**
     * Convert string base64 to bitmap
     *
     * @param input
     * @return
     */
    public static Bitmap convertBase64ToBitmap(String input) {
        if (TextUtils.isEmpty(input)) {
            return null;
        }
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * Limit size of bitmap to draw is 2048x2048. With bitmap width or height
     * >2048, use this function before set bitmap to view
     *
     * @param bitmap
     * @return
     */
    public static Bitmap limitSizeBitmap(Bitmap bitmap) {
        return limitSizeBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight());
    }

    public static Bitmap limitSizeBitmap(Bitmap bitmap, int width, int height) {
        float ratio = (width * 1.0f) / height;
        if (width > MAX_WIDTH_IMAGE_ALLOW) {
            width = MAX_WIDTH_IMAGE_ALLOW;
            height = (int) ratio * height;
        }
        if (height > MAX_HEIGHT_IMAGE_ALLOW) {
            width = (int) (width * MAX_HEIGHT_IMAGE_ALLOW / height);
            height = MAX_HEIGHT_IMAGE_ALLOW;

        }
        try {
            Bitmap scaleBmp = Bitmap.createScaledBitmap(bitmap, width, height,
                    false);
            return scaleBmp;
        } catch (OutOfMemoryError e) {
            Log.e(e.toString());
        }
        return null;
    }

    /**
     * get bitmap from image path
     *
     * @param scale
     * @param imagePath
     * @return
     */
    public static Bitmap getBitmapFromPath(Context context, int scale,
                                           String imagePath) {

        Uri uri = Uri.parse(imagePath);

        float rotation = getRotationInDegreeOfImage(context, uri);

        Matrix matrix = new Matrix();
        if (rotation != 0f) {
            matrix.preRotate(rotation);
        }

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = scale;

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

        if (rotation != 0f) {
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }

        return bitmap;
    }

    /**
     * Set placeholder bitmap that shows when the the background thread is
     * running.
     *
     * @param bitmap
     */
    public void setLoadingImage(Bitmap bitmap) {
        mLoadingBitmap = bitmap;
    }

    /**
     * Set placeholder bitmap that shows when the the background thread is
     * running.
     *
     * @param resId
     */
    public void setLoadingImage(int resId) {
        mLoadingBitmap = BitmapFactory.decodeResource(
                staticContext.getResources(), resId);
    }


}
