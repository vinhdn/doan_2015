package bk.vinhdo.taxiads;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.Marker;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.utils.bitmap.BitmapResourceLoader;
import bk.vinhdo.taxiads.utils.bitmap.BitmapUtil;
import bk.vinhdo.taxiads.utils.sdcard.FileManager;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.HashMap;

import bk.vinhdo.taxiads.models.Address;

/**
 * Created by Vinh on 1/21/15.
 */
public class TaxiApplication extends Application {

    private static Context mContext;
    private ArrayList<Address> listAddress;

    private HashMap<String, Marker> listMarker;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        this.mContext = getApplicationContext();
        this.listAddress = new ArrayList<>();
        this.listMarker = new HashMap<>();
        initApplicationFolder();
        initImageLoader(this);
        initUtils();
    }

    public static Context getAppContext(){
        return mContext;
    }

    public HashMap<String, Marker> getListMarker() {
        return listMarker;
    }

    public void setListMarker(HashMap<String, Marker> listMarker) {
        this.listMarker = listMarker;
    }

    public ArrayList<Address> getListAddress() {
        return listAddress;
    }

    public void setListAddress(ArrayList<Address> listAddress) {
        this.listAddress = listAddress;
    }

    /**
     * init image loader
     *
     * @param context
     */
    public void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.drawable.ic_stub)// resource or drawable
//                .showImageForEmptyUri(R.drawable.ic_empty) // resource or
//                        // drawable
//                .showImageOnFail(R.drawable.ic_error) // resource or drawable
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .defaultDisplayImageOptions(options).build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * create all folder of application
     */
    public void initApplicationFolder() {
        // root folder
        FileManager.createNewFolder(Key.FOLDER_ROOT_NAME);
        // data folder
        FileManager.createNewFolder(Key.FOLDER_ROOT_PATH, Key.FOLDER_DATA_NAME);
        // image folder
        FileManager
                .createNewFolder(Key.FOLDER_ROOT_PATH, Key.FOLDER_IMAGE_NAME);
    }


    public void initUtils() {
        BitmapUtil.initializeStaticContext(this);
        BitmapResourceLoader.initializeStaticContext(this);
        BitmapResourceLoader.initializeLruCache();
    }
}
