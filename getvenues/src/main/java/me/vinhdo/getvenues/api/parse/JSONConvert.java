package me.vinhdo.getvenues.api.parse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import me.vinhdo.getvenues.Models.AddressFSModel;
import me.vinhdo.getvenues.Models.LikesFSModel;
import me.vinhdo.getvenues.Models.ResponseFSModel;
import me.vinhdo.getvenues.Models.ResponseModel;
import me.vinhdo.getvenues.Models.TimeOpenFSModel;
import me.vinhdo.getvenues.Models.TipFSModel;
import me.vinhdo.getvenues.TaxiApplication;

/**
 * Created by vinhdo on 4/30/15.
 */
public class JSONConvert {
    public static Gson mGson = new Gson();

    public static AddressFSModel getAddress(String paramString)
            throws JSONException {
        String str = JSONUtil.getString(new JSONObject(paramString), "venue");
        return new GsonBuilder().create().fromJson(str, AddressFSModel.class);
    }

    public static LikesFSModel getLikes(String paramString)
            throws JSONException {
        String str = JSONUtil.getString(new JSONObject(paramString), "likes");
        return new GsonBuilder().create().fromJson(str, LikesFSModel.class);
    }

    public static List<AddressFSModel> getListAddresses(String paramString)
            throws JSONException {
        String str = JSONUtil.getString(new JSONObject(paramString), "venues");
        Type localType = new TypeToken<List<AddressFSModel>>() {
        }.getType();
        return (List<AddressFSModel>) mGson.fromJson(str, localType);
    }

    public static List<TimeOpenFSModel> getListTimeOpen(String paramString)
            throws JSONException {
        JSONObject localJSONObject1 = new JSONObject(paramString);
        if (localJSONObject1.has("popular")) {
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("popular");
            if (localJSONObject2.has("timeframes")) {
                String str = JSONUtil.getString(localJSONObject2, "timeframes");
                Type localType = new TypeToken<List<TimeOpenFSModel>>() {
                }.getType();
                return (List<TimeOpenFSModel>) mGson.fromJson(str, localType);
            }
        }
        return new ArrayList<>();
    }

    public static List<TipFSModel> getListTip(String paramString)
            throws JSONException {
        String str = JSONUtil.getString(new JSONObject(paramString).getJSONObject("tips"), "items");
        Type localType = new TypeToken<List<TipFSModel>>() {
        }.getType();
        return (List<TipFSModel>) mGson.fromJson(str, localType);
    }

    public static ResponseModel getResponse(String paramString) {
        ResponseModel localResponseModel;
        if (paramString == null) {
            return new ResponseModel(0, "Error");
        }
        localResponseModel = new ResponseModel();
        try {
            JSONObject localJSONObject = new JSONObject(paramString);
            localResponseModel.setSuccess(localJSONObject.getInt("success"));
            if (localJSONObject.has("message")) {
                localResponseModel.setMessage(localJSONObject.getString("message"));
            }
            if (localJSONObject.has("data")) {
                localResponseModel.setData(localJSONObject.getString("data"));
            }
            return localResponseModel;
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
        }
        return new ResponseModel(0, "Error");
    }

    public static ResponseFSModel getResponseFS(String paramString) {
        ResponseFSModel localResponseFSModel;
        if (paramString == null) {
            return new ResponseFSModel(400, "Error");
        }
        localResponseFSModel = new ResponseFSModel();
        try {
            JSONObject localJSONObject1 = new JSONObject(paramString);
            JSONObject localJSONObject2 = localJSONObject1.getJSONObject("meta");
            int i = localJSONObject2.getInt("code");
            localResponseFSModel.setCode(i);
            if (i != 200) {
                boolean bool1 = localJSONObject2.has("errorType");
                boolean bool2 = localJSONObject2.has("errorDetail");
                if (bool1) {
                    localResponseFSModel.setErrorType(localJSONObject2.getString("errorType"));
                }
                if (bool2) {
                    localResponseFSModel.setErrorType(localJSONObject2.getString("errorDetail"));
                }
            }
            if (localJSONObject1.has("response")) {
                localResponseFSModel.setResponse(localJSONObject1.getString("response"));
            }
            return  localResponseFSModel;
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
        }
        return new ResponseFSModel(400, "error");
    }
}
