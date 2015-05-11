package me.vinhdo.getvenues.api.loopj;

import android.text.TextUtils;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import me.vinhdo.getvenues.Models.AddressFSModel;
import me.vinhdo.getvenues.Models.TipFSModel;
import me.vinhdo.getvenues.Models.UserFSModel;
import me.vinhdo.getvenues.config.ApiConfig;


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

    public static String ACCESS_TOKEN = "imC2m63i55D7Y34k00s0VS9H84wsTS";
    public static String USER_ID = "222222";


    public static void createAddress() {
    }

    public static void createAddressFS(AddressFSModel paramAddressFSModel, String paramString, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("access_token", ACCESS_TOKEN);
        localRequestParams.put("user_id", USER_ID);
        localRequestParams.put("lat", paramAddressFSModel.getLocation().getLat());
        localRequestParams.put("lng", paramAddressFSModel.getLocation().getLng());
        if (paramAddressFSModel.getLocation().getAddress() == null) {
            localRequestParams.put("street_number", paramAddressFSModel.getLocation().getAddress());
            localRequestParams.put("address", toStringFromList(paramAddressFSModel.getLocation().getFormattedAddress()));
        }
        localRequestParams.put("category_id", paramString);
        localRequestParams.put("name", paramAddressFSModel.getName());
        localRequestParams.put("id", paramAddressFSModel.getId());
        if (paramAddressFSModel.getContact() != null && paramAddressFSModel.getContact().getPhone() != null) {
            localRequestParams.put("phone_number", paramAddressFSModel.getContact().getPhone());
        }
        localRequestParams.put("url", paramAddressFSModel.getUrl());
        localRequestParams.put("rate", paramAddressFSModel.getRating());
        localRequestParams.put("rate_number", paramAddressFSModel.getRatingSignals());
        LoopjRestClient.post(ApiConfig.URL_CREATE_ADDRESS, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void createListLikeFS(List<UserFSModel> paramList, String paramString, TextHttpResponseHandler paramTextHttpResponseHandler) {
        if ((paramList == null) || (paramList.size() <= 0)) {
            return;
        }
        for (UserFSModel user : paramList) {
            likeAddressFS(user.getId(), paramString, paramTextHttpResponseHandler);
        }
    }

    public static void createListPostFS(List<TipFSModel> paramList, String paramString, TextHttpResponseHandler paramTextHttpResponseHandler) {
        if (paramList == null) return;
        for(TipFSModel tip : paramList) {
            createPostFS(tip, paramString, paramTextHttpResponseHandler);
        }
    }

    public static void createPostFS(TipFSModel paramTipFSModel, String paramString, TextHttpResponseHandler paramTextHttpResponseHandler) {
        createUserFS(paramTipFSModel.getUser(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

            }
        });
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("user_id", paramTipFSModel.getUser().getId());
        localRequestParams.put("address_id", paramString);
        localRequestParams.put("id", paramTipFSModel.getId());
        localRequestParams.put("content", paramTipFSModel.getText());
        if (!TextUtils.isEmpty(paramTipFSModel.getPhotoUrl())) {
            localRequestParams.put("photo", paramTipFSModel.getPhotoUrl());
        }
        LoopjRestClient.post(ApiConfig.URL_CREATE_POST, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void createTimeOpenFS(String paramString1, String paramString2, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("time_open", paramString1);
        localRequestParams.put("address_id", paramString2);
        LoopjRestClient.post(ApiConfig.URL_CREATE_TIME_OPEN, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void createUser() {
    }

    public static void createUserFS(UserFSModel paramUserFSModel, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("id", paramUserFSModel.getId());
        localRequestParams.put("first_name", paramUserFSModel.getFirstName());
        localRequestParams.put("last_name", paramUserFSModel.getLastName());
        localRequestParams.put("gender", paramUserFSModel.getGender());
        localRequestParams.put("avatar_url", paramUserFSModel.getPhoto().getURL(200));
        if (!TextUtils.isEmpty(paramUserFSModel.getHomeCity())) {
            localRequestParams.put("address", paramUserFSModel.getHomeCity());
        }
        LoopjRestClient.post(ApiConfig.URL_REGISTER, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void getAddressFS(String paramString, TextHttpResponseHandler paramTextHttpResponseHandler) {
        LoopjRestClient.get(ApiConfig.FS_URL_VENUES + paramString, ApiConfig.FS_KEY, paramTextHttpResponseHandler);
    }

    public static void getInfoAddress(String paramString1, String paramString2, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        if (paramString1 != null) {
            localRequestParams.put("access_token", paramString1);
        }
        localRequestParams.put("id", paramString2);
        LoopjRestClient.post(ApiConfig.URL_GET_INFO_ADDRESS, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void getLikesOfAddressFS(String paramString, int paramInt1, int paramInt2, TextHttpResponseHandler paramTextHttpResponseHandler) {
        String str = ApiConfig.FS_URL_VENUES + paramString + "/" + "likes";
        RequestParams localRequestParams = ApiConfig.FS_KEY;
        if (paramInt1 > 0) {
            localRequestParams.put("limit", paramInt1);
        }
        if (paramInt2 > 0) {
            localRequestParams.put("limit", paramInt2);
        }
        LoopjRestClient.get(str, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void getListAddress(double paramDouble1, double paramDouble2, int paramInt, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("lat", Double.valueOf(paramDouble1));
        localRequestParams.put("lng", Double.valueOf(paramDouble2));
        localRequestParams.put("limit", paramInt);
        LoopjRestClient.post(ApiConfig.URL_GET_LIST_ADDRESS, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void getTimeOpenOfAddressFS(String paramString, TextHttpResponseHandler paramTextHttpResponseHandler) {
        LoopjRestClient.get("https://api.foursquare.com/v2/venues/" + paramString + "/" + "hours", ApiConfig.FS_KEY, paramTextHttpResponseHandler);
    }

    public static void getTipsOfAddressFS(String paramString, int paramInt1, int paramInt2, TextHttpResponseHandler paramTextHttpResponseHandler) {
        String str = "https://api.foursquare.com/v2/venues/" + paramString + "/" + "tips";
        RequestParams localRequestParams = ApiConfig.FS_KEY;
        if (paramInt1 > 0) {
            localRequestParams.put("limit", paramInt1);
        }
        if (paramInt2 > 0) {
            localRequestParams.put("limit", paramInt2);
        }
        LoopjRestClient.get(str, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void likeAddressFS(String paramString1, String paramString2, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("user_id", paramString1);
        localRequestParams.put("address_id", paramString2);
        LoopjRestClient.post(ApiConfig.URL_LIKE_ADDRESS, localRequestParams, paramTextHttpResponseHandler);
    }

    public static void login(String paramString1, String paramString2, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("email", paramString1);
        localRequestParams.put("password", paramString2);
        LoopjRestClient.post("http://192.168.0.119/doan/user/login", localRequestParams, null, paramTextHttpResponseHandler);
    }

    public static void loginFacebook(String paramString, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = new RequestParams();
        localRequestParams.put("access_token", paramString);
        LoopjRestClient.post("http://192.168.0.119/doan/user/loginWithFacebook", localRequestParams, null, paramTextHttpResponseHandler);
    }

    public static void searchAddressFS(double paramDouble1, double paramDouble2, String paramString, int paramInt, TextHttpResponseHandler paramTextHttpResponseHandler) {
        RequestParams localRequestParams = ApiConfig.FS_KEY;
        localRequestParams.put("ll", String.valueOf(paramDouble1) + "," + String.valueOf(paramDouble2));
        if (paramInt > 0) {
            localRequestParams.put("limit", paramInt);
        }
        if (!TextUtils.isEmpty(paramString)) {
            localRequestParams.put("categoryId", paramString);
        }
        localRequestParams.put("radius", 5000);
        LoopjRestClient.get(ApiConfig.FS_URL_SEARCH_ADDRESS, localRequestParams, paramTextHttpResponseHandler);
    }


    private static String toStringFromList(List<String> data) {
        String str1 = "";
        if (data.size() > 0) {
            for (String str : data)
                str1 = str1 + str + ",";
        }
        return str1.trim();
    }

}
