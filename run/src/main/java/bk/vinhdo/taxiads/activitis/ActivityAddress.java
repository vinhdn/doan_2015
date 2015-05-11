/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bk.vinhdo.taxiads.activitis;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.fragments.AddressListViewFragment;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.utils.Keys;
import bk.vinhdo.taxiads.volley.VolleySingleton;

public class ActivityAddress extends BaseActivity {

    private static String LINK_DATA = "https://api.foursquare.com/v2/venues/";

    private AddressModel mAddress;
    private ListView mDataLv;
    private RelativeLayout mRequestProgress;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddress = new AddressModel();
        mAddress.setId(getIntent().getExtras().getString("id_address"));
        setContentView(R.layout.address_activity, false);
    }

    @Override
    public void setActionView() {
        setVisibleRightImage(true);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setBackgroundTitleText("Address",android.R.color.transparent);

//        final Drawable cd = getResources().getDrawable(R.drawable.ab_background_light);
//        layout_actionbar.setBackgroundDrawable(cd);
//        cd.setAlpha(100);

        mRequestProgress = (RelativeLayout) findViewById(R.id.request_progress);
        getData(LINK_DATA + mAddress.getId() + "/?" + Keys.KEY);
    }

    private void getData(String url) {
        RestClient.getInfoAddress(getCurrentUser() == null ? null : getCurrentUser().getAccessToken(), mAddress.getId(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if(response.isSuccess()){
                    mAddress = JSONConvert.getAddress(response.getData());
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.container, new AddressListViewFragment(mAddress)).commit();
                    mRequestProgress.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void customHeaderView() {

    }

    @Override
    public void onLeftHeaderClick() {
        finish();
    }

    @Override
    public void onRightHeaderClick() {
        Intent i = new Intent(ActivityAddress.this,CheckinActivity.class);
        startActivity(i);
    }

    @Override
    protected void initModels(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }

}
