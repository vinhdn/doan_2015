package bk.vinhdo.taxiads.activitis;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.fragments.BaseFragment;

public class AddressInMapActivity extends BaseActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private String mTitle = "Maps";
    private double mLat;
    private double mLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getIntent().getStringExtra(Key.EXTRA_NAME);
        if(mTitle == null)
            mTitle = "Map";
        mLat = getIntent().getDoubleExtra(Key.EXTRA_LAT, 0);
        mLng = getIntent().getDoubleExtra(Key.EXTRA_LNG, 0);
        setContentView(R.layout.activity_address_in_map, false);
        setUpMapIfNeeded();
    }

    @Override
    public void setActionView() {
        setVisibleRightImage(false);
        setVisibleRightText(false);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setBackgroundTitleText(mTitle, android.R.color.transparent);
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
        setUpMapIfNeeded();
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
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(mLat, mLng)).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin_large_selected)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLat, mLng), 13f));
    }
}
