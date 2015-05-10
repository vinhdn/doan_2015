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
    public static final String EXTRA_ACTION = "action";
    public static final String KEY_NEARBY = "nearby";
    public static final String KEY_SHOP = "shop";
    public static final String KEY_RESTAURANT = "restaurant";
    public static final String KEY_HEATH = "heath";
    public static final String KEY_REPAIR = "repair";
    public static final String KEY_CAFE = "cafe";
}
