package bk.vinhdo.taxiads.utils.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DynamicMapFragment extends SupportMapFragment {

	private LatLng mPosFija;
	private OnMapClickListener mMapClickListener;
    private int resource;
    private boolean isShowInfo;

	public DynamicMapFragment() {
		super();
	}

	public static DynamicMapFragment newInstance(LatLng posicion,int resource, boolean isShowInfo) {
		DynamicMapFragment frag = new DynamicMapFragment();
		frag.mPosFija = posicion;
        frag.resource = resource;
        frag.isShowInfo = isShowInfo;
		return frag;
	}

	public DynamicMapFragment setOnMapClickListener(
			OnMapClickListener mapClickListener) {
		this.mMapClickListener = mapClickListener;
		return this;
	}

	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		View v = super.onCreateView(arg0, arg1, arg2);
		initMap();
		return v;
	}

	private void initMap() {
		GoogleMap map = getMap();
		if(map == null) {
			return;
		}
		UiSettings settings = map.getUiSettings();
		settings.setAllGesturesEnabled(false);
		settings.setMyLocationButtonEnabled(false);
		settings.setZoomControlsEnabled(false);
		getMap().setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				if(mMapClickListener != null) {
					mMapClickListener.onMapClick(null);
				}
			}
		});
		if (mPosFija != null) {
			getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(mPosFija, 16));
            if(!isShowInfo) {
			getMap().addMarker(
					new MarkerOptions().position(mPosFija).icon(
							BitmapDescriptorFactory
									.fromResource(resource)));
            }else
			getAddress = new getAddresAsyns().execute(mPosFija);
		}
	}
	private boolean isGetFinish = true;
	private AsyncTask<LatLng, Void, String> getAddress;
	
	private class getAddresAsyns extends AsyncTask<LatLng, Void, String>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isGetFinish = false;
		}
		
		@Override
		protected String doInBackground(LatLng... arg0) {
			LatLng paramLatLng = arg0[0];
			Geocoder localGeocoder = new Geocoder(getActivity(), Locale.getDefault());
			try {
				List localList = localGeocoder.getFromLocation(
						paramLatLng.latitude, paramLatLng.longitude, 1);
				String str = ((Address) localList.get(0)).getAddressLine(0);
				((Address) localList.get(0)).getAddressLine(1);
				((Address) localList.get(0)).getAddressLine(2);
				Log.d("ds", str);
				return str;
			} catch (IOException localIOException) {
				localIOException.printStackTrace();
			}
			return "";
		}
		@Override
		protected void onPostExecute(String str) {
			super.onPostExecute(str);
			isGetFinish = true;
			if(getMap() != null)
			getMap().addMarker(
					new MarkerOptions().position(mPosFija).icon(
							BitmapDescriptorFactory
									.fromResource(resource))
									.title(str)
					).showInfoWindow();
		}
		
	}

	public void clear() {
		if(getAddress != null && !isGetFinish){
			getAddress.cancel(true);
		}
		if(getMap() != null)
			getMap().clear();
	}
}
