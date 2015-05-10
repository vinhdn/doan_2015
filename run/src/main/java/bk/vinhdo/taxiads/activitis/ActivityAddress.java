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

import org.json.JSONException;
import org.json.JSONObject;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.fragments.AddressListViewFragment;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.utils.Keys;
import bk.vinhdo.taxiads.volley.VolleySingleton;

public class ActivityAddress extends BaseActivity {

    private static String LINK_DATA = "https://api.foursquare.com/v2/venues/";

    private Address mAddress;
    private ListView mDataLv;
    private RelativeLayout mRequestProgress;
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddress = new Address();
        mAddress.setId(getIntent().getExtras().getString("id_address"));
        setContentView(R.layout.address_activity, false);
    }

    @Override
    public void setActionView() {
        setVisibleRightImage(true);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setBackgroundTitleText("Address",android.R.color.transparent);

        final Drawable cd = getResources().getDrawable(R.drawable.ab_background_light);
        layout_actionbar.setBackgroundDrawable(cd);
        cd.setAlpha(100);

        mRequestProgress = (RelativeLayout) findViewById(R.id.request_progress);
        getData(LINK_DATA + mAddress.getId() + "/?" + Keys.KEY);
    }

    private void getData(String url) {
        new VolleySingleton().getInstance().getRequestQueue().add(
                new JsonObjectRequest(Request.Method.GET, url.equals("") ? LINK_DATA : url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            processData(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }));
    }

    private void processData(JSONObject jo) throws JSONException{
        if(jo.getJSONObject("meta").getInt("code") != 200)
            return;
        JSONObject joVenue = jo.getJSONObject("response").getJSONObject("venue");
        mAddress = new Address(joVenue);
//        tvName.setText(address.getTitle());
//        if(mAddress.getRate() > 0)
//            RateUtil.SetRatingView(address.getRate() / 2, findViewById(R.id.item_rating_frame), 28);
//        if(mAddress.getLocation() !=null) {
//            tvAdrress1.setText(mAddress.getLocation().getAddress());
//            tvAddress2.setText(mAddress.getLocation().getCrossStreet());
//            loadMap(mAddress.getLocation().getLat(), mAddress.getLocation().getLng());
//        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new AddressListViewFragment(mAddress)).commit();
        mRequestProgress.setVisibility(View.GONE);
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
