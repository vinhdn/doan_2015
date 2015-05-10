package bk.vinhdo.taxiads.api.parse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bk.vinhdo.taxiads.utils.Log;
import bk.vinhdo.taxiads.utils.text.StringUtil;

public class JSONUtil {

    /*
     * value null for all data integer, double, float
     */
    public final static int IGNORE_VALUE = 0;

    /**
     * @param jsonObject
     * @param key
     * @return
     */
    public static boolean hasKey(JSONObject jsonObject, String key) {
        return jsonObject.has(key);
    }

    /**
     * @param jsonObject
     * @param key
     * @return
     */
    public static boolean isNull(JSONObject jsonObject, String key) {
        return jsonObject.isNull(key);
    }

    /**
     * @param jsonObject
     * @param key
     * @return
     */
    public static boolean getBoolean(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            Log.e(e.toString());
        }
        return false;
    }

    /**
     * @param jsonObject
     * @param key
     * @return
     */
    public static String getString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            Log.e(e.toString());
        }
        return "";
    }

    /**
     * get value integer from jsonObject if value in json is string "", return
     * value IGNORE_VALUE
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static int getInt(JSONObject jsonObject, String key) {
        String value;
        try {
            value = jsonObject.getString(key);
            if (value != null && !value.equals("") && !value.equals("null")) {
                try {
                    return Integer.valueOf(value);
                } catch (NumberFormatException e) {
                    Log.e(e.toString());
                }
            }
        } catch (JSONException e) {
            Log.e(e.toString());
        }
        return IGNORE_VALUE;
    }

    /**
     * get value float from jsonObject if value in json is string "", return
     * value IGNORE_VALUE
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static float getFloat(JSONObject jsonObject, String key) {
        String value;
        try {
            value = jsonObject.getString(key);
            if (value != null && !value.equals("")) {
                try {
                    return Float.valueOf(value);
                } catch (NumberFormatException e) {
                    Log.e(e.toString());
                }
            }
        } catch (JSONException e) {
            Log.e(e.toString());
        }
        return IGNORE_VALUE;
    }

    /**
     * get value integer from double if value in json is string "", return value
     * IGNORE_VALUE
     *
     * @param jsonObject
     * @param key
     * @return
     */
    public static Double getDouble(JSONObject jsonObject, String key) {
        String value;
        try {
            value = jsonObject.getString(key);
            if (value != null && !value.equals("")) {
                try {
                    return Double.valueOf(value);
                } catch (NumberFormatException e) {
                    Log.e(e.toString());
                }
            }
        } catch (Exception e) {
            Log.e(e.toString());
        }
        return IGNORE_VALUE * 1.0;
    }

    /**
     * @param jsonObject
     * @param key
     * @return
     */
    public static Long getLong(JSONObject jsonObject, String key) {
        String value;
        try {
            value = jsonObject.getString(key);
            if (value != null && !value.equals("")) {
                try {
                    return Long.valueOf(value);
                } catch (NumberFormatException e) {
                    Log.e(e.toString());
                }
            }
        } catch (Exception e) {
            Log.e(e.toString());
        }
        return 0L;
    }

    /**
     * convert list to json string to save in db
     *
     * @param list
     * @return
     */
    @SuppressWarnings("serial")
    public static String convertListToJson(ArrayList<String> list) {
        Gson gson = new Gson();
        ArrayList<String> listSaveToDatabase = new ArrayList<String>();

        for (String str : list) {
            listSaveToDatabase.add(StringUtil.sqlQuote(str));
        }

        Type type = new TypeToken<List<String>>() {
        }.getType();
        String json = gson.toJson(list, type);
        gson = null;
        return json;
    }

    /**
     * Convert from string json to list
     *
     * @param json
     * @return
     */
    @SuppressWarnings("serial")
    public static ArrayList<String> convertJsonToList(String json) {
        Gson gson = new Gson();

        // This is how you tell gson about the generic type you want to get
        // back:
        Type type = new TypeToken<List<String>>() {
        }.getType();

        ArrayList<String> list = gson.fromJson(json, type);

        return list;
    }

}
