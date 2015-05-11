package me.vinhdo.getvenues.api.loopj;

import android.content.Intent;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import me.vinhdo.getvenues.TaxiApplication;

public class HandleAuthTokenExpired implements IStrategyHandleError {

    @Override
    public boolean handleError(int statusCode, Header[] headers,
                               String responseString, Throwable throwable, TextHttpResponseHandler originHandler) {
        if (statusCode == 403) {
            // token is expired, back to login activity
            return true;
        }
        return false;
    }

}
