package bk.vinhdo.taxiads.config;

import java.io.File;

import bk.vinhdo.taxiads.utils.sdcard.FileManager;

/**
 * Created by vinhdo on 4/30/15.
 */
public class Key {
    /*
     * folder root
     */
    public static final String FOLDER_ROOT_NAME = ".Quanhday";
    public static final String FOLDER_ROOT_PATH = FileManager.EXTERNAL_PATH
            + File.separator + FOLDER_ROOT_NAME;

    /*
     * folder data
     */
    public static final String FOLDER_DATA_NAME = "data";

    public static final String FOLDER_DATA_PATH = FOLDER_ROOT_PATH
            + File.separator + FOLDER_DATA_NAME;

    /*
     * folder image
     */
    public static final String FOLDER_IMAGE_NAME = "images";

    // folder image path
    public static final String FOLDER_IMAGE_PATH = FOLDER_ROOT_PATH
            + File.separator + FOLDER_IMAGE_NAME;

    public static final String FILE_TEMP_NAME = "temp.jpg";
    public static final String FILE_VEHICLE_NAME = "vehicle.jpg";
    public static final String FILE_LICENSE_NAME = "license.jpg";

    public static final String FILE_TEMP_PATH = FOLDER_IMAGE_PATH
            + File.separator + FILE_TEMP_NAME;

    // photo property path
    public static final String FILE_IMAGE_PATH = FOLDER_IMAGE_PATH
            + File.separator + "%s.jpg";

    /*
     * folder image
     */
    public static final String EXTRA_ACTION = "action";
    public static final String KEY_NEARBY = "nearby";
    public static final String KEY_SHOP = "shop";
    public static final String KEY_RESTAURANT = "restaurant";
    public static final String KEY_HEATH = "heath";
    public static final String KEY_RESIDENCE = "residence";
    public static final String KEY_CAFE = "cafe";

    public static final String KEY_CATE_ID_RESIDENCE = "4e67e38e036454776db1fb3a";
    public static final String KEY_CATE_ID_CAFE = "4bf58dd8d48988d16d941735";
    public static final String KEY_CATE_ID_RESTAURANT = "4bf58dd8d48988d1c4941735";
    public static final String KEY_CATE_ID_SHOP_AND_SERVICE = "4d4b7105d754a06378d81259";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_RECEIVER = "receiver";
    public static final int REQUEST_CODE_LOGIN = 101;
    public static final String ACTION_LOGIN_TO_ACCESS = "ACTION_LOGIN_TO_ACCESS";
    public static final int REQUEST_CODE_CHECKIN = 102;
}
