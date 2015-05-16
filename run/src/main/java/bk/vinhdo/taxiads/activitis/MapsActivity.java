package bk.vinhdo.taxiads.activitis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.adapters.InfoMapsAdapter;
import bk.vinhdo.taxiads.adapters.ItemAddressListViewAdapter;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.volley.VolleySingleton;

public class MapsActivity extends BaseActivity implements
        GoogleMap.OnMapLongClickListener, InfoMapsAdapter.OnInfoMapsClick,
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static String TAG = MapsActivity.class.getName();
    private ArrayList<AddressModel> listAddress;
    private String LINK_API = "https://api.foursquare.com/v2/venues/search?ll=";
    private String KEY = "&client_id=JCB1YGORS2P4HU5NM3TONSJGENFED54JS4F21CC1KVNFPMEZ&client_secret=N5QCN4MYPBEV0JDQ2TFQYJCYSXZZ04ZCTGDZVNLHLQYJH2C4&v=20150120";
    private String LOC_TEST = "21.0070681,105.8429626";
    private String LINK_TEST = LINK_API + LOC_TEST + KEY;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private boolean isChangePosition = true;
    private LocationManager locationManager;
    private ConnectivityManager cm;
    private Location lastKnownLocation;
    private HashMap<Integer, Marker> listAddressMarker = new HashMap<>();

    private HashMap<Marker, Integer> listMarkerAdd = new HashMap<>();

    private HashMap<String, Marker> addressMarker = new HashMap<>();
    private boolean isProcessing = false;
    private final long SECOND = 04;
    private String mAction;
    private String mCategoryId;

    ViewPager lvAddress;
    ItemAddressListViewAdapter adapter;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    protected LocationRequest mLocationRequest;

    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;

    LatLngBounds.Builder builderBounds;

    /**
     * UI
     */
    //private TextView tvTitleMarkerClick;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps, false);
        String action = getIntent().getStringExtra(Key.EXTRA_ACTION);
        if (action != null) {
            mAction = action;
            if (action.equals(Key.KEY_NEARBY)) {
                mCategoryId = null;
            } else if (action.equals(Key.KEY_CAFE)) {
                mCategoryId = Key.KEY_CATE_ID_CAFE;
            } else if (action.equals(Key.KEY_SHOP)) {
                mCategoryId = Key.KEY_CATE_ID_SHOP_AND_SERVICE;
            } else if (action.equals(Key.KEY_RESIDENCE)) {
                mCategoryId = Key.KEY_CATE_ID_RESIDENCE;
            } else if (action.equals(Key.KEY_RESTAURANT)) {
                mCategoryId = Key.KEY_CATE_ID_RESTAURANT;
            } else if (action.equals(Key.KEY_HEATH)) {

            }
        }
        mRequestingLocationUpdates = true;
        mLastUpdateTime = "";
        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
        // API.
        buildGoogleApiClient();
        //tvTitleMarkerClick = (TextView)findViewById(R.id.title_marker);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        setUpMapIfNeeded();
        listAddress = new ArrayList<>();
    }

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
                //setButtonsEnabledState();
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
            }
            //updateUI();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(50f);
    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void setActionView() {

        setVisibleRightImage(true);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setBackgroundRightImage(R.drawable.audience_location_list);
        setBackgroundTitleText("Maps", android.R.color.transparent);
        lvAddress = (ViewPager) findViewById(R.id.lv_address_in_maps);
        listAddress = new ArrayList<>();
        adapter = new ItemAddressListViewAdapter(getSupportFragmentManager(), this, listAddress);
        lvAddress.setAdapter(adapter);
        lvAddress.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected(int)", position + "");
                try {
                    listAddressMarker.get(position).showInfoWindow();
//                    listAddressMarker.get(position).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin_large_selected));
//                    if (mMap != null)
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(listAddressMarker.get(position).getPosition(), 16));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.d("onPageScrollStateChanged(int state)", state + "");
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

    }

    @Override
    protected void initModels(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Within {@code onPause()}, we pause location updates, but leave the
        // connection to GoogleApiClient intact.  Here, we resume receiving
        // location updates if the user has requested them.

        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setOnMapLongClickListener(this);
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setMapToolbarEnabled(false);
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.showInfoWindow();
                        return true;
                    }
                });
//                Criteria criteria = new Criteria();
//
//                lastKnownLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
//                if (lastKnownLocation != null) {
//                    Log.d("Current Location", lastKnownLocation.getLatitude() + "  " + lastKnownLocation.getLongitude());
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                            new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), 13));
//
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()))      // Sets the center of the map to location user
//                            .zoom(17)                   // Sets the zoom
//                            .bearing(90)                // Sets the orientation of the camera to east
//                            .tilt(40)                   // Sets the tilt of the camera to 30 degrees
//                            .build();                   // Creates a CameraPosition from the builder
//                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                    getData(LINK_API + lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude() + KEY);
//                } else
//                    getData("");
                GoogleMap.InfoWindowAdapter infoWindowAdapter = new InfoMapsAdapter(this, this);
                mMap.setInfoWindowAdapter(infoWindowAdapter);
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker").icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon_blue_background_white_car_smiley)));
    }

    private void addAddToMap(AddressModel add, int p, boolean isMoveCamera) {
        int IDIm = new Random().nextInt(6);
        int imageRe;
        switch (IDIm) {
            case 0:
                imageRe = R.drawable.bank;
                break;
            case 2:
                imageRe = R.drawable.cafe;
                break;
            case 3:
                imageRe = R.drawable.edu;
                break;
            case 4:
                imageRe = R.drawable.shop;
                break;
            case 5:
                imageRe = R.drawable.y_te;
                break;
            default:
                imageRe = R.drawable.mapicon_blue_background_white_car_smiley;
        }
//        if(addressMarker.get(add.getId()) == null) {
//            Marker marker = mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(add.getLat(), add.getLng()))
//                    .title(add.getTitle())
//                    .snippet(add.getJson().toString())
//                    .icon(BitmapDescriptorFactory.fromResource(imageRe)));
//            add.setIDMarker(marker.getId());
//            addressMarker.put(add.getId(), marker);
//        }
        if (listAddressMarker.get(p) != null) {
            listAddressMarker.get(p).remove();
            listAddressMarker.remove(p);
        }
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(add.getLat(), add.getLng()))
                .snippet(add.toJSON())
                .icon(BitmapDescriptorFactory.fromResource(imageRe)));
        add.setIdMarker(marker.getId());
        builderBounds.include(marker.getPosition());
        listAddressMarker.put(p, marker);
    }

    private void getAddress(double lat, double lng) {
        RestClient.getListAddress(lat, lng,10000, mCategoryId, null, 20, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if (response.isSuccess()) {
                    List<AddressModel> listAddrs = JSONConvert.getAddresses(response.getData());
                    if (listAddrs != null && listAddrs.size() > 0) {
                        Log.d("Size Addrs", listAddrs.size() + "");
                        ArrayList<AddressModel> list = new ArrayList<>();
                        list.addAll(listAddress);
                        listAddress.clear();
                        listAddress.addAll(listAddrs);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = null;
                                adapter = new ItemAddressListViewAdapter(MapsActivity.this.getSupportFragmentManager(), MapsActivity.this, listAddress);
                                try {
                                    lvAddress.setAdapter(adapter);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                        builderBounds = new LatLngBounds.Builder();
                        for (int i = 0; i < listAddress.size(); i++) {
                            AddressModel add = listAddress.get(i);
                            add.setIdInList(i);
//            add.setJson(add.getJson().put("listID", i));
                            addAddToMap(add, i, false);
                        }
                        LatLngBounds bounds = builderBounds.build();
                        int padding = 0; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        mMap.animateCamera(cu);
                    } else {
                        Log.d("Size Addrs", "NULL 0r 0");
                    }
                }
            }
        });
    }

    private void getData(String url) {
        isProcessing = true;
        new VolleySingleton().getInstance().getRequestQueue().add(
                new JsonObjectRequest(Request.Method.GET, url.equals("") ? LINK_TEST : url, null, new Response.Listener<JSONObject>() {
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

    private void processData(JSONObject object) throws JSONException {
        ArrayList<AddressModel> listNew = new ArrayList<>();
        List<String> listID = new ArrayList<>();
        if (object.getJSONObject("meta").getInt("code") != 200)
            return;
        JSONObject response = object.getJSONObject("response");
        JSONArray arrayAdd = response.getJSONArray("venues");
        //mMap.clear();
        for (int i = 0; i < arrayAdd.length(); i++) {
            JSONObject jo = arrayAdd.getJSONObject(i);
            AddressModel add = new AddressModel();
//            add.setJson(jo);
//            add.setId(jo.getString("id"));
//            add.setTitle(jo.getString("name"));
            JSONObject joLoc = jo.getJSONObject("location");
            add.setLat(joLoc.getDouble("lat"));
            add.setLng(joLoc.getDouble("lng"));
//            if (i < arrayAdd.length() - 1)
//                addAddToMap(add, false);
//            else
//                addAddToMap(add, false);
            listNew.add(add);
            listID.add(add.getId());
        }
        ArrayList<AddressModel> list = new ArrayList<>();
        list.addAll(listAddress);
        //new RemoveMaker().execute(listID,list);
        listAddress.clear();
        listAddress.addAll(listNew);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = null;
                adapter = new ItemAddressListViewAdapter(MapsActivity.this.getSupportFragmentManager(), MapsActivity.this, listAddress);
                try {
                    lvAddress.setAdapter(adapter);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        builderBounds = new LatLngBounds.Builder();
        for (int i = 0; i < listAddress.size(); i++) {
            AddressModel add = listAddress.get(i);
//            add.setJson(add.getJson().put("listID", i));
            addAddToMap(add, i, false);
        }
        LatLngBounds bounds = builderBounds.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (isChangePosition) {
            getAddress(latLng.latitude, latLng.longitude);
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        Toast.makeText(this, "UpdateLocation",
                Toast.LENGTH_SHORT).show();
        getAddress(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 16f));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Connected to GoogleApiClient");

        // If the initial location was never previously requested, we use
        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
        // its value in the Bundle and check for it in onCreate(). We
        // do not request it again unless the user specifically requests location updates by pressing
        // the Start Updates button.
        //
        // Because we cache the value of the initial location in the Bundle, it means that if the
        // user launches the activity,
        // moves to a new location, and then changes the device orientation, the original location
        // is displayed as the activity is re-created.
        //if (mCurrentLocation == null) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        if (mCurrentLocation != null && mMap != null) {
            Log.d("Current Location", mCurrentLocation.getLatitude() + "  " + mCurrentLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 16));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            getAddress(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }
        //}

        // If the user presses the Start Updates button before GoogleApiClient connects, we set
        // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
        // the value of mRequestingLocationUpdates and if it is true, we start location updates.
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    /**
     * Stores activity data in the Bundle.
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    class RemoveMaker extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            ArrayList<Address> old = (ArrayList<Address>) params[1];
            List<String> newd = (List<String>) params[0];
            int i = 0;
            while (i < old.size()) {
                if (newd.remove(old.get(i).getId())) {
                    old.remove(i);
                } else
                    i++;
            }
            for (final Address add : old) {
                Log.d("Remove", add.getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addressMarker.get(add.getId()).remove();
                        addressMarker.remove(add.getId());
                    }
                });

            }
            return null;
        }
    }

    private BroadcastReceiver connectivityBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                if (noConnectivity) {
                    // Do nothing for the time being.
                    Log.i(TAG,
                            "connectivityBroadcastReceiver.onReceive() :: ALL CONNECTIVITY LOST");
                } else {
                    // Resume all uploader and downloader threads:
                    Log.i(TAG,
                            "connectivityBroadcastReceiver.onReceive() :: CONNECTIVITY ACQUIRED");
                }
            }
        }
    };

    public Location getLastKnownLocation() {
        if (lastKnownLocation == null) {
            return locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER) == null ? locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    : locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            return lastKnownLocation;
        }
    }

    @Override
    public void onClick(Marker marker) {
        AddressModel address = JSONConvert.getAddress(marker.getSnippet());
        if (address.getIdInList() >= 0) {
            lvAddress.setCurrentItem(address.getIdInList());
        }
        //tvTitleMarkerClick.setText(marker.getTitle());
    }
}
