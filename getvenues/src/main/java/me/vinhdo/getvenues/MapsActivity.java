package me.vinhdo.getvenues;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import me.vinhdo.getvenues.Models.AddressFSModel;
import me.vinhdo.getvenues.Models.LikesFSModel;
import me.vinhdo.getvenues.Models.ResponseFSModel;
import me.vinhdo.getvenues.Models.TimeOpenFSModel;
import me.vinhdo.getvenues.Models.TipFSModel;
import me.vinhdo.getvenues.api.loopj.RestClient;
import me.vinhdo.getvenues.api.parse.JSONConvert;
import me.vinhdo.getvenues.config.Key;
import me.vinhdo.getvenues.utils.Log;

public class MapsActivity extends FragmentActivity {

    private AddressFSModel mAddress;
    private List<AddressFSModel> mListAddress;
    private List<AddressFSModel> mListAddressLoaded = new ArrayList();
    private GoogleMap mMap;
    private int mNumberAddressLoaded = 0;
    private String mKeySearch;
    private String mKeyCate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int id = bundle.getInt(Key.KEY_CATE);
            switch (id){
                case 1:
                    mKeySearch = Key.KEY_CAFE_ID;
                    mKeyCate = Key.KEY_CA;
                    break;
                case 2:
                    mKeySearch = Key.KEY_RESTAURANT_ID;
                    mKeyCate = Key.KEY_RES;
                    break;
                case 3:
                    mKeySearch = Key.KEY_SH;
                    mKeyCate = Key.KEY_SH;
                    break;
                case 4:
                    mKeySearch = Key.KEY_HOTEL;
                    mKeyCate = Key.KEY_HOTEL;
                    break;
                default:
                    mKeySearch = Key.KEY_HOTEL;
                    mKeyCate = Key.KEY_HOTEL;
                    break;
            }
        }
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        this.mMap.setMyLocationEnabled(true);
        this.mMap.getUiSettings().setMyLocationButtonEnabled(true);
        this.mMap.getUiSettings().setMapToolbarEnabled(true);
        this.mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            public void onMapLongClick(LatLng paramAnonymousLatLng) {
                RestClient.searchAddressFS(paramAnonymousLatLng.latitude, paramAnonymousLatLng.longitude, mKeySearch, 50, new TextHttpResponseHandler() {
                    public void onFailure(int paramAnonymous2Int, Header[] paramAnonymous2ArrayOfHeader, String paramAnonymous2String, Throwable paramAnonymous2Throwable) {
                    }

                    public void onSuccess(int paramAnonymous2Int, Header[] paramAnonymous2ArrayOfHeader, String paramAnonymous2String) {
                        ResponseFSModel localResponseFSModel = JSONConvert.getResponseFS(paramAnonymous2String);
                        if (localResponseFSModel.isSuccess()) {

                            try {
                                mListAddress = JSONConvert.getListAddresses(localResponseFSModel.getResponse());
                                if ((mListAddress != null) && (mListAddress.size() > 0)) {
                                    Log.d("SIZE============= : " + mListAddress.size());
                                    mAddress = mListAddress.get(mNumberAddressLoaded);
                                    getData();
                                }
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
    }

    private void getData() {
        Log.d("ID==========================================: " + this.mNumberAddressLoaded);
        RestClient.getAddressFS(this.mAddress.getId(), new TextHttpResponseHandler() {
            public void onFailure(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, String paramAnonymousString, Throwable paramAnonymousThrowable) {
                nextAddress();
            }

            public void onSuccess(int paramAnonymousInt, Header[] paramAnonymousArrayOfHeader, String paramAnonymousString) {
                ResponseFSModel localResponseFSModel = JSONConvert.getResponseFS(paramAnonymousString);
                if (localResponseFSModel.isSuccess()) {
                    try {
                        mAddress = JSONConvert.getAddress(localResponseFSModel.getResponse());
                        RestClient.createAddressFS(mAddress, mKeyCate, new TextHttpResponseHandler() {
                            public void onFailure(int paramAnonymous2Int, Header[] paramAnonymous2ArrayOfHeader, String paramAnonymous2String, Throwable paramAnonymous2Throwable) {
                                nextAddress();
                            }

                            public void onSuccess(int paramAnonymous2Int, Header[] paramAnonymous2ArrayOfHeader, String paramAnonymous2String) {
                                if (JSONConvert.getResponse(paramAnonymous2String).isSuccess()) {
                                    RestClient.getTimeOpenOfAddressFS(MapsActivity.this.mAddress.getId(), new TextHttpResponseHandler() {
                                        public void onFailure(int paramAnonymous3Int, Header[] paramAnonymous3ArrayOfHeader, String paramAnonymous3String, Throwable paramAnonymous3Throwable) {
                                            nextAddress();
                                        }

                                        public void onSuccess(int paramAnonymous3Int, Header[] paramAnonymous3ArrayOfHeader, String paramAnonymous3String) {
                                            ResponseFSModel localResponseFSModel = JSONConvert.getResponseFS(paramAnonymous3String);
                                            if (localResponseFSModel.isSuccess()) {
                                                try {
                                                    List<TimeOpenFSModel> localList = JSONConvert.getListTimeOpen(localResponseFSModel.getResponse());
                                                    mAddress.setListTimeOpens(localList);
                                                    String str = "";
                                                    if (localList.size() > 0) {
                                                        for (TimeOpenFSModel timeOpen : localList) {
                                                            str = str + timeOpen.getTime() + ";";
                                                        }
                                                    }
                                                    if ((str.length() > 0) && (str.charAt(-1 + str.length()) == ';')) {
                                                        str = str.substring(0, -1 + str.length());
                                                    }

                                                    if (!TextUtils.isEmpty(str)) {
                                                        RestClient.createTimeOpenFS(str, mAddress.getId(), new TextHttpResponseHandler() {
                                                            public void onFailure(int paramAnonymous4Int, Header[] paramAnonymous4ArrayOfHeader, String paramAnonymous4String, Throwable paramAnonymous4Throwable) {
                                                            }

                                                            public void onSuccess(int paramAnonymous4Int, Header[] paramAnonymous4ArrayOfHeader, String paramAnonymous4String) {
                                                            }
                                                        });
                                                    }
                                                    RestClient.getTipsOfAddressFS(mAddress.getId(), 100, 0, new TextHttpResponseHandler() {
                                                        public void onFailure(int paramAnonymous4Int, Header[] paramAnonymous4ArrayOfHeader, String paramAnonymous4String, Throwable paramAnonymous4Throwable) {
                                                            nextAddress();
                                                        }

                                                        public void onSuccess(int paramAnonymous4Int, Header[] paramAnonymous4ArrayOfHeader, String paramAnonymous4String) {
                                                            ResponseFSModel localResponseFSModel = JSONConvert.getResponseFS(paramAnonymous4String);
                                                            if (localResponseFSModel.isSuccess()) {
                                                                try {
                                                                    List<TipFSModel> listTips = JSONConvert.getListTip(localResponseFSModel.getResponse());
                                                                    mAddress.setListTips(listTips);
                                                                    if ((listTips != null) && (listTips.size() > 0)) {
                                                                        RestClient.createListPostFS(listTips, MapsActivity.this.mAddress.getId(), new TextHttpResponseHandler() {
                                                                            public void onFailure(int paramAnonymous5Int, Header[] paramAnonymous5ArrayOfHeader, String paramAnonymous5String, Throwable paramAnonymous5Throwable) {
                                                                            }

                                                                            public void onSuccess(int paramAnonymous5Int, Header[] paramAnonymous5ArrayOfHeader, String paramAnonymous5String) {
                                                                            }
                                                                        });
                                                                    }
                                                                    RestClient.getLikesOfAddressFS(MapsActivity.this.mAddress.getId(), 200, 0, new TextHttpResponseHandler() {
                                                                        public void onFailure(int paramAnonymous5Int, Header[] paramAnonymous5ArrayOfHeader, String paramAnonymous5String, Throwable paramAnonymous5Throwable) {
                                                                            nextAddress();
                                                                        }

                                                                        public void onSuccess(int paramAnonymous5Int, Header[] paramAnonymous5ArrayOfHeader, String paramAnonymous5String) {
                                                                            ResponseFSModel localResponseFSModel = JSONConvert.getResponseFS(paramAnonymous5String);
                                                                            if (localResponseFSModel.isSuccess()) {
                                                                                try {
                                                                                    LikesFSModel localLikesFSModel = JSONConvert.getLikes(localResponseFSModel.getResponse());
                                                                                    mAddress.setListLikes(localLikesFSModel);
                                                                                    if ((localLikesFSModel.getListUsers() != null) && (localLikesFSModel.getListUsers().size() > 0)) {
                                                                                        RestClient.createListLikeFS(localLikesFSModel.getListUsers(), MapsActivity.this.mAddress.getId(), new TextHttpResponseHandler() {
                                                                                            public void onFailure(int paramAnonymous6Int, Header[] paramAnonymous6ArrayOfHeader, String paramAnonymous6String, Throwable paramAnonymous6Throwable) {
                                                                                            }

                                                                                            public void onSuccess(int paramAnonymous6Int, Header[] paramAnonymous6ArrayOfHeader, String paramAnonymous6String) {
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                    mListAddressLoaded.add(mAddress);
                                                                                    Log.d(mAddress.getId());
                                                                                    nextAddress();
                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                    nextAddress();
                                                                                }
                                                                            } else {
                                                                                nextAddress();
                                                                            }
                                                                        }
                                                                    });
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                    nextAddress();
                                                                }
                                                            }else{
                                                                nextAddress();
                                                            }
                                                        }
                                                    });
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    nextAddress();
                                                }
                                            } else {
                                                nextAddress();
                                            }
                                        }
                                    });
                                } else {
                                    nextAddress();
                                }
                            }
                        });
                    } catch (JSONException e) {
                        nextAddress();
                    }
                } else {
                    nextAddress();
                }
            }
        });
    }

    private void nextAddress() {
        mNumberAddressLoaded++;
        if (mNumberAddressLoaded < mListAddress.size()) {
            mAddress = mListAddress.get(mNumberAddressLoaded);
            getData();
        } else {
            mNumberAddressLoaded = 0;
        }
    }
}
