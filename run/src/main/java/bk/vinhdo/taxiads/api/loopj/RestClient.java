package bk.vinhdo.taxiads.api.loopj;

import android.text.TextUtils;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import bk.vinhdo.taxiads.config.ApiConfig;


public class RestClient {

    static {
        LoopjRestClient.setHandleError(new HandleErrorMessage());
    }

    public static RestClient synchronize() {
        LoopjRestClient.synchronize();
        return new RestClient();
    }

    public static void downloadFile(String url, FileAsyncHttpResponseHandler responseHandler) {
        LoopjRestClient.download(url, responseHandler);
    }

    public static void login(String email, String password, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put(ApiConfig.PARAM_EMAIL, email);
        params.put(ApiConfig.PARAM_PASSWORD, password);
        LoopjRestClient.post(ApiConfig.URL_LOGIN, params, responseHandler);
    }

    public static void loginFacebook(String facebookToken, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put(ApiConfig.PARAM_ACCESS_TOKEN, facebookToken);
        LoopjRestClient.post(ApiConfig.URL_LOGIN_WITH_FACEBOOK, params, responseHandler);
    }

    public static void getListAddress(double lat, double lng,int distance, String categoryId, String query, int limit, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put(ApiConfig.PARAM_LAT, lat);
        params.put(ApiConfig.PARAM_LNG, lng);
        params.put(ApiConfig.PARAM_DIST, distance);
        params.put(ApiConfig.PARAM_LIMIT, limit);
        if (categoryId != null)
            params.put(ApiConfig.PARAM_CATEGORY_ID, categoryId);
        if (query != null)
            params.put("q", query);

        LoopjRestClient.post(ApiConfig.URL_GET_LIST_ADDRESS, params, responseHandler);
    }

    public static void getInfoAddress(String accessToken, String addressId, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        if (accessToken != null)
            params.put(ApiConfig.PARAM_ACCESS_TOKEN, accessToken);
        params.put(ApiConfig.PARAM_ID, addressId);

        LoopjRestClient.post(ApiConfig.URL_GET_INFO_ADDRESS, params, responseHandler);
    }

    public static void createPost(String accessToken, String addressId, String content, File image, TextHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put(ApiConfig.PARAM_ACCESS_TOKEN, accessToken);
        params.put(ApiConfig.PARAM_ADDRESS_ID, addressId);
        params.put(ApiConfig.PARAM_CONTENT, content);
        try {
            if (image != null && image.isFile() && image.canRead())
                params.put(ApiConfig.PARAM_IMAGE, image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        LoopjRestClient.post(ApiConfig.URL_CREATE_POST, params, responseHandler);
    }

    public static void likeAddress(String accessToken, String addressId, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put(ApiConfig.PARAM_ACCESS_TOKEN, accessToken);
        params.put(ApiConfig.PARAM_ADDRESS_ID, addressId);

        LoopjRestClient.post(ApiConfig.URL_LIKE_ADDRESS, params, responseHandler);
    }

    public static void rateAddress(String accessToken, String addressId, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put(ApiConfig.PARAM_ACCESS_TOKEN, accessToken);
        params.put(ApiConfig.PARAM_ADDRESS_ID, addressId);

        LoopjRestClient.post(ApiConfig.URL_LIKE_ADDRESS, params, responseHandler);
    }

    public static void register(String email, String firstName, String lastName,long birthday, String password,File avatar, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("email", email);
        localRequestParams.put("first_name", firstName);
        localRequestParams.put("last_name", lastName);
        localRequestParams.put("password", password);
        localRequestParams.put("birthday",birthday);
        try {
            localRequestParams.put("avatar", avatar);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        LoopjRestClient.post(ApiConfig.URL_REGISTER, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void getListSaved(String accessToken, TextHttpResponseHandler responseHandler){
        RequestParams params = new RequestParams();
        params.put(ApiConfig.PARAM_ACCESS_TOKEN, accessToken);

        LoopjRestClient.post(ApiConfig.URL_GET_LIST_SAVED, params, responseHandler);
    }
}
