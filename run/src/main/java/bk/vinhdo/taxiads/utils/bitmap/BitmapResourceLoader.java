package bk.vinhdo.taxiads.utils.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.utils.Log;

public class BitmapResourceLoader {

    private static Bitmap mLoadingBitmap;
    private static LruCache<String, Bitmap> mMemoryCache;
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

    public static void executeDecodeBitmapFromResource(ImageView imageView,
                                                       int resId, int reqWidth, int reqHeight) {
        final String imageKey = String.valueOf(resId);
        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            Log.d("load bitmap from memory");
            imageView.setImageBitmap(bitmap);
        } else {
            Log.d("decode bitmap");
            imageView.setImageResource(R.drawable.ic_launcher);
            if (cancelPotentialWork(resId, imageView)) {
                final BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(
                        imageView, reqWidth, reqHeight);
                final AsyncDrawable asyncDrawable = new AsyncDrawable(
                        staticContext.getResources(), mLoadingBitmap,
                        bitmapWorkerTask);
                imageView.setImageDrawable(asyncDrawable);
                bitmapWorkerTask.execute(resId);
            }
        }
    }

    public static boolean cancelPotentialWork(int resId, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.resId;
            // If bitmapData is not yet set or it differs from the new data
            if (bitmapData == 0 || bitmapData != resId) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }

        // No task associated with the ImageView, or an existing task was
        // cancelled
        return true;
    }

    public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    public static void initializeLruCache() {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    static class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private WeakReference<ImageView> imageViewReference;
        private int resId = 0;
        private int reqWidth;
        private int reqHeight;

        public BitmapWorkerTask(ImageView imageView, int reqWidth, int reqHeight) {
            imageViewReference = new WeakReference<ImageView>(imageView);
            this.reqWidth = reqWidth;
            this.reqHeight = reqHeight;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            resId = params[0];
            final Bitmap bitmap = BitmapUtil.decodeBitmapFromResource(
                    staticContext.getResources(), resId, reqWidth, reqHeight);
            addBitmapToMemoryCache(String.valueOf(resId), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (isCancelled()) {
                result = null;
            }

            if (imageViewReference != null && result != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(result);
                }
            }
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(
                    bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

}
