package bk.vinhdo.taxiads.utils.hardware;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import bk.vinhdo.taxiads.models.utils.DeviceInfoModel;


/**
 * Get information of device
 * 
 * @author khanhnv
 * 
 */
public class DeviceUtil {

	private static float screenInches = 0;

	private static DeviceUtil instance;
	private static DeviceInfoModel deviceInfo;
	private Context context;

	public DeviceUtil(Context context) {
		this.context = context;
	}

	public static DeviceUtil getInstance(Context context) {
		if (instance == null) {
			instance = new DeviceUtil(context);
		}
		return instance;
	}

	/**
	 * check device is tablet
	 * 
	 * @param context
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public boolean isTablet() {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		return (xlarge || large);
	}

	/**
	 * check device is handler normal or small screen
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNormalOrSmall() {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL);
		return (xlarge || large);
	}

	/**
	 * get device information
	 * 
	 * @param activity
	 * @return
	 */
	public DeviceInfoModel getDeviceInfo() {
		if (deviceInfo == null) {
			/* First, get the Display from the WindowManager */
			Display display = ((WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();

			/* Now we can retrieve all display-related infos */
			int width = display.getWidth();
			int height = display.getHeight();
			int orientation = getOrientation(context);
			deviceInfo = new DeviceInfoModel();
			deviceInfo.mWidth = width;
			deviceInfo.mHeight = height;
			deviceInfo.mOrientation = orientation;
			deviceInfo.mIsTablet = isTablet();
		}
		return deviceInfo;
	}

	/**
	 * Get pixel width of device
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int getWidth() {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * Get pixel height of device
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int getHeight() {
		Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * get orientation of activity
	 * 
	 * @param activity
	 * @return ORIENTATION_LANDSCAPE or ORIENTATION_PORTRAIT
	 */
	public static int getOrientation(Context context) {
		Configuration config = context.getResources().getConfiguration();
		return config.orientation;
	}

	/**
	 * 
	 * @return serial number device
	 */
	public static String getSerialNumber(Context context) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = telephonyManager.getDeviceId();
			return imei;
		} catch (Exception e) {
			String serialNum;
			try {
				Class<?> classLoader = Class
						.forName("android.os.SystemProperties");
				Method method = classLoader.getMethod("get", String.class,
						String.class);
				serialNum = (String) (method.invoke(classLoader, "ro.serialno"));
			} catch (Exception exception) {
				serialNum = "W80082PE8YA";
			}
			return serialNum;
		}
	}

	/**
	 * The name of the industrial design.
	 * 
	 * @return
	 */
	public static String getDeviceName() {
		return Build.DEVICE;
	}

	/**
	 * get model & product of device
	 * 
	 * @return
	 */
	public static String getModelAndProduct() {
		return Build.MODEL + " (" + Build.PRODUCT + ")";
	}

	/**
	 * Get device ID of each device
	 * 
	 * @param context
	 * @return number device id
	 */
	public static String getDeviceId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(),
				Settings.Secure.ANDROID_ID);
	}

	/**
	 * Get size of physical screen (inch)
	 * 
	 * @return
	 */
	public static float getPhysicalSize(Activity activity) {
		if (screenInches == 0) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			DisplayMetrics metrics = new DisplayMetrics();
			display.getMetrics(metrics);
			int[] resolutions = getResolutionPhysical(activity);
			double x = Math.pow(resolutions[0] / metrics.xdpi, 2);
			double y = Math.pow(resolutions[1] / metrics.ydpi, 2);
			screenInches = (float) Math.sqrt(x + y);
		}
		return screenInches;
	}

	/**
	 * 
	 * @return
	 */
	private static int[] getResolutionPhysical(Activity activity) {
		int[] resolution = new int[2];
		Display display = activity.getWindowManager().getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		try {
			Method methodGetRawWidth = Display.class.getMethod("getRawWidth");
			Method methodGetRawHeight = Display.class.getMethod("getRawHeight");
			resolution[0] = (Integer) methodGetRawWidth.invoke(display);
			resolution[1] = (Integer) methodGetRawHeight.invoke(display);
		} catch (IllegalArgumentException e) {
			resolution[0] = metrics.widthPixels;
			resolution[1] = metrics.heightPixels;
		} catch (NoSuchMethodException e) {
			resolution[0] = metrics.widthPixels;
			resolution[1] = metrics.heightPixels;
		} catch (IllegalAccessException e) {
			resolution[0] = metrics.widthPixels;
			resolution[1] = metrics.heightPixels;
		} catch (InvocationTargetException e) {
			resolution[0] = metrics.widthPixels;
			resolution[1] = metrics.heightPixels;
		}
		return resolution;
	}
}
