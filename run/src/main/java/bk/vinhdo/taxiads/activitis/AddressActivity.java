package bk.vinhdo.taxiads.activitis;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.adapters.TopicAdapter;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.utils.Helper;
import bk.vinhdo.taxiads.utils.Keys;
import bk.vinhdo.taxiads.utils.Log;
import bk.vinhdo.taxiads.utils.getimage.RateUtil;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.DynamicMapFragment;
import bk.vinhdo.taxiads.utils.view.ScrollViewX;
import bk.vinhdo.taxiads.volley.VolleySingleton;

public class AddressActivity extends BaseActivity implements View.OnClickListener {

    private static String LINK_DATA = "https://api.foursquare.com/v2/venues/";

    RelativeLayout mMapLayout;
    ScrollViewX scroll;
    LinearLayout header;
    private DynamicMapFragment mMapFragment;
    private Address address;

    private ListView lvTopics, lvTips;

    private CustomTextView tvName,tvType;

    // info maps
    private CustomTextView tvAdrress1,tvAddress2,tvInfoPhone;
    private LinearLayout llInfoPhone,llInfoAddr;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        RestClient.getInfoAddress(mCurrentUser != null ? mCurrentUser.getAccessToken() : null, "#7732641271", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if(response.isSuccess()){
                    AddressModel address = JSONConvert.getAddress(response.getData());
                    if(address != null){
                        Log.d("Break line");
                    }
                }
            }
        });
        address = new Address();
        address.setId(getIntent().getExtras().getString("id_address"));
        setContentView(R.layout.activity_address,true);
//        FadingActionBarHelper helper = new FadingActionBarHelper()
//                .actionBarBackground(R.drawable.ab_background)
//               .headerLayout(R.layout.layout_header)
//                .contentLayout(R.layout.activity_address);
//        setContentView(helper.createView(this));
//        helper.initActionBar(this);
        findViewById(R.id.btn_more).setOnClickListener(this);
        mMapLayout = (RelativeLayout) findViewById(R.id.map_layout);
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
        address = new Address(joVenue);
        tvName.setText(address.getTitle());
        if(address.getRate() > 0)
        RateUtil.SetRatingView(address.getRate() / 2,findViewById(R.id.item_rating_frame),28);
        if(address.getLocation() !=null) {
            tvAdrress1.setText(address.getLocation().getAddress());
            tvAddress2.setText(address.getLocation().getCrossStreet());
            loadMap(address.getLocation().getLat(), address.getLocation().getLng());
        }

    }

    @Override
    public void setActionView() {
        setVisibleRightImage(true);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setBackgroundTitleText("Address",android.R.color.transparent);
        scroll = (ScrollViewX)findViewById(R.id.scroll);
        //final ColorDrawable cd = new ColorDrawable(R.color.header_background);
        final Drawable cd = getResources().getDrawable(R.drawable.ab_background_light);
        layout_actionbar.setBackgroundDrawable(cd);
        cd.setAlpha(0);
        scroll.setOnScrollViewListener(new ScrollViewX.OnScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollViewX v, int l, int t, int oldl, int oldt) {

                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0,maxDist = 650;
                if(scrollY>maxDist){
                    return 255;
                }
                else if(scrollY<minDist){
                    return 0;
                }
                else {
                    int alpha = 0;
                    alpha = (int)  ((255.0/maxDist)*scrollY);
                    return alpha;
                }
            }
        });

        tvName = (CustomTextView) findViewById(R.id.address_name);
        tvType = (CustomTextView) findViewById(R.id.address_type);

        tvAdrress1 = (CustomTextView) findViewById(R.id.info_map_title);
        tvAddress2 = (CustomTextView) findViewById(R.id.info_map_title2);
        llInfoPhone = (LinearLayout) findViewById(R.id.ll_info_phone);
        tvInfoPhone = (CustomTextView) findViewById(R.id.info_phone);

        lvTopics = (ListView) findViewById(R.id.list_topic_of_addr);
        lvTopics.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    lvTopics.scrollBy(0, 1);
                }
                return false;
            }
        });
        lvTopics.setAdapter(new TopicAdapter(this,true,null));
        Helper.setListViewHeightBasedOnChildren(lvTopics);
//        actionData();
        getData(LINK_DATA + address.getId() + "/?" + Keys.KEY);
        scroll.scrollTo(0,0);
    }

    private void actionData(){
        RateUtil.SetRatingView(3.5f, findViewById(R.id.item_rating_frame), 28);
    }

    @Override
    protected void customHeaderView() {

    }

    @Override
    public void onLeftHeaderClick() {

    }

    @Override
    public void onRightHeaderClick() {
        Intent i = new Intent(AddressActivity.this,CheckinActivity.class);
        startActivity(i);
    }

    @Override
    protected void initModels(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_address, menu);
        return true;
    }

    private void loadMap(double lat, double lng){
        AsyncTask<Double, Void, Void> loadMapTask = new AsyncTask<Double, Void, Void>() {

            @Override
            public Void doInBackground(Double... params) {
                double lat = params[0];
                double lng = params[1];
                mMapFragment = DynamicMapFragment.newInstance(new LatLng(lat,lng),R.drawable.eyewitness_icon,false);
                return null;
            }

            @Override
            public void onPostExecute(Void results) {
                switchContent(R.id.map_frame, mMapFragment);
            }
        };
        loadMapTask.execute(lat,lng);
    }

    /**
     * Switch content tab
     *
     * @param fragment
     */
    public void switchContent(int contentId, Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(contentId, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_more:
                Intent i = new Intent(AddressActivity.this,CheckinActivity.class);
                startActivity(i);
                break;
        }
    }

}
