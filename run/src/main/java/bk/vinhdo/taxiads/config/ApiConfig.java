package bk.vinhdo.taxiads.config;

import com.loopj.android.http.RequestParams;

import java.util.HashMap;

/**
 * Created by vinhdo on 4/30/15.
 */
public class ApiConfig {
//    private static final String URL_BASE = "http://192.168.0.104/doan/";
    private static final String URL_BASE = "http://128.199.69.38/doan/";
    public static final String URL_CREATE_ADDRESS = URL_BASE + "address/create";
    public static final String URL_GET_INFO_ADDRESS = URL_BASE + "address/getinfo";
    public static final String URL_UPDATE_ADDRESS = URL_BASE + "address/update";
    public static final String URL_APPROVE_ADDRESS = URL_BASE + "address/approve";
    public static final String URL_REPORT_ADDRESS = URL_BASE + "address/report";
    public static final String URL_LIKE_ADDRESS = URL_BASE + "address/like";
    public static final String URL_DISLIKE_ADDRESS = URL_BASE + "address/dislike";
    public static final String URL_GET_LIST_ADDRESS = URL_BASE + "address/getlist";

    public static final String URL_CREATE_POST = URL_BASE + "post/create";
    public static final String URL_EDIT_POST = URL_BASE + "post/edit";
    public static final String URL_DELETE_POST = URL_BASE + "post/delete";
    public static final String URL_GET_INFO_POST = URL_BASE + "post/getinfo";

    public static final String URL_LOGIN = URL_BASE + "user/login";
    public static final String URL_LOGOUT = URL_BASE + "user/logout";
    public static final String URL_REGISTER = URL_BASE + "user/register";
    public static final String URL_EDIT_PROFILE = URL_BASE + "user/editprofile";
    public static final String URL_FORGOT_PASSWORD = URL_BASE + "user/forgotpassword";
    public static final String URL_GET_PROFILE = URL_BASE + "user/getprofile";

    public static final String PARAM_LAT = "lat";
    public static final String PARAM_LNG = "lng";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_ACCESS_TOKEN = "access_token";

    public static final String URL_LOGIN_WITH_FACEBOOK = URL_BASE + "user/loginWithFacebook";;
    public static final String PARAM_ID = "id";
    public static final String PARAM_ADDRESS_ID = "address_id";
    public static final String PARAM_CONTENT = "content";
    public static final String PARAM_IMAGE = "image";

    public static final String PARAM_VENUES = "venues";
    public static final String PARAM_TIPS = "tips";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_OFFSET = "offset";
    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String PARAM_CLIENT_SECRET = "client_secret";
    public static final String PARAM_LL = "ll";

    public static final String FS_CLIENT_ID = "JCB1YGORS2P4HU5NM3TONSJGENFED54JS4F21CC1KVNFPMEZ";
    public static final String FS_CLIENT_SECRET = "N5QCN4MYPBEV0JDQ2TFQYJCYSXZZ04ZCTGDZVNLHLQYJH2C4";

    public static final RequestParams FS_KEY = new RequestParams(new HashMap<String,String>(){{
        put(PARAM_CLIENT_ID,FS_CLIENT_ID);
        put(PARAM_CLIENT_SECRET,FS_CLIENT_SECRET);
        put("v","20150505");
    }});

    public static final String FS_URL_BASE = "https://api.foursquare.com/v2/";
    public static final String FS_URL_VENUES = FS_URL_BASE + "venues/";
    public static final String PARAM_CATEGORY_FS_ID = "categoryId";
    public static final String FS_URL_SEARCH_ADDRESS = FS_URL_VENUES + "search";
    public static final String PARAM_HOURS = "hours";
    public static final String PARAM_LIKES = "likes";

    public static final String PARAM_CATEGORY_ID = "category_id";
    public static final String PARAM_URL = "url";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_ABOUT = "about";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_STREET_NUMBER = "street_number";
    public static final String PARAM_PHONE_NUMBER = "phone_number";

    public static final String PARAM_RATE = "rate";
    public static final String PARAM_RATE_NUMBER = "rate_number";
    public static final String PARAM_FIRST_NAME = "first_name";
    public static final String PARAM_LAST_NAME = "last_name";
    public static final String PARAM_GENDER = "gender";
    public static final String PARAM_AVATAR_URL = "avatar_url";
    public static final String PARAM_TIME_OPEN = "time_open";
    public static final String URL_CREATE_TIME_OPEN = URL_BASE + "timeopen/create";
    public static final String PARAM_USER_ID = "user_id";
    public static final String PARAM_PHOTO = "photo";
}
