package bk.vinhdo.taxiads.api.loopj;

import android.content.Intent;
import android.text.TextUtils;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.utils.Log;
import bk.vinhdo.taxiads.utils.ToastUtil;

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
        if (responseString != null)
            Log.d(responseString);
        Log.d("ERROR ================================");
        ResponseModel responseModel = JSONConvert.getResponse(responseString);
        if (responseModel != null) {
            // TODO handle error code here
            if (responseModel.getRideErrorCode() != null && !TextUtils.isEmpty(responseModel.getRideErrorCode())) {

            }
        }

        if (originHandler != null) {
            originHandler.onFailure(statusCode, headers, responseString, throwable);
        }

        return true;
    }
}
