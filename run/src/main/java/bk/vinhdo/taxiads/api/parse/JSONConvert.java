package bk.vinhdo.taxiads.api.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.TaxiApplication;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.PostModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.text.StringUtil;

/**
 * Created by vinhdo on 4/30/15.
 */
public class JSONConvert {
    public static Gson mGson = new Gson();

    public static ResponseModel getResponse(String responseString) {
        if (responseString == null) return new ResponseModel(0, TaxiApplication.getAppContext().getString(R.string.generic_error));

        ResponseModel responseModel = new ResponseModel();
        try {
            JSONObject jResponse = new JSONObject(responseString);
            int success = jResponse.getInt("success");
            responseModel.setSuccess(success);
            boolean hasData = jResponse.has("data");
            boolean hasMessage = jResponse.has("message");
            boolean hasErrorDetails = jResponse.has("error_details");

            if (hasData) {
                String data = jResponse.getString("data");
                responseModel.setData(data);
            }

            if (hasMessage) {
                String message = jResponse.getString("message");
                responseModel.setMessage(message);
            }

            if (hasErrorDetails) {
                String errorDetail = jResponse.getString("error_details");
                responseModel.setErrorDetails(errorDetail);
            }
            return responseModel;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ResponseModel(0, TaxiApplication.getAppContext().getString(R.string.generic_error));
    }

    public static UserModel getUser(String responseString){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(responseString, UserModel.class);
    }

    public static AddressModel getAddress(String responseString){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(responseString, AddressModel.class);
    }

    public static PostModel getPost(String responseString){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(responseString, PostModel.class);
    }

    public static List<AddressModel> getAddresses(String responseString) {
        Type type = new TypeToken<List<AddressModel>>() {
        }.getType();
        return (List<AddressModel>) mGson.fromJson(responseString, type);
    }
}
