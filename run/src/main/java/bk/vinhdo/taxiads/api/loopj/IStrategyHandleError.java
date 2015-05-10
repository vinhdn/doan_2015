package bk.vinhdo.taxiads.api.loopj;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

public interface IStrategyHandleError {
    public boolean handleError(int statusCode, Header[] headers,
                               String responseString, Throwable throwable, TextHttpResponseHandler originHandler);
}
