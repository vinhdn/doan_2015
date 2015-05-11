package me.vinhdo.getvenues.api.loopj;

import android.text.TextUtils;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import me.vinhdo.getvenues.Models.ResponseModel;
import me.vinhdo.getvenues.api.parse.JSONConvert;
import me.vinhdo.getvenues.utils.Log;

/**
 * Created by vietthangif on 1/19/2015.
 * <p/>
 * Parse error message from server to show alert
 */
public class HandleErrorMessage implements IStrategyHandleError {
    @Override
    public boolean handleError(int statusCode, Header[] headers, String responseString, Throwable throwable, TextHttpResponseHandler originHandler) {
        Log.d("ERROR ================================");
        Log.d("status: " + statusCode);
        if (responseString != null) {
            Log.d(responseString);
        }
        Log.d("ERROR ================================");
        if ((JSONConvert.getResponseFS(responseString) == null) || (originHandler != null)) {
            originHandler.onFailure(statusCode, headers, responseString, throwable);
        }
        return true;
    }
}
