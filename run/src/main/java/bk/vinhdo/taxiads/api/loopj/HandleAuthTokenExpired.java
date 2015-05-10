package bk.vinhdo.taxiads.api.loopj;

import android.content.Intent;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import bk.vinhdo.taxiads.TaxiApplication;
import bk.vinhdo.taxiads.activitis.MainActivity;

public class HandleAuthTokenExpired implements IStrategyHandleError {

    @Override
    public boolean handleError(int statusCode, Header[] headers,
                               String responseString, Throwable throwable, TextHttpResponseHandler originHandler) {
        if (statusCode == 403) {
            // token is expired, back to login activity
            Intent intent = new Intent(TaxiApplication.getAppContext(),
                    MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            TaxiApplication.getAppContext().startActivity(intent);
            return true;
        }
        return false;
    }

}
