package bk.vinhdo.taxiads.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.utils.view.CustomTextView;

/**
 * Created by Vinh on 1/21/15.
 */
public class InfoMapsAdapter implements GoogleMap.InfoWindowAdapter {

    public interface OnInfoMapsClick{
        public void onClick(Marker marker);
    }

    private OnInfoMapsClick infoClickListener;
    private Activity context;
    public InfoMapsAdapter(Activity add, OnInfoMapsClick infoClickListener){
        this.context = add;
        this.infoClickListener = infoClickListener;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        marker.hideInfoWindow();
        View v = context.getLayoutInflater().inflate(R.layout.item_title_marker, null);
        CustomTextView titleTv = (CustomTextView)v.findViewById(R.id.title_tv);
        AddressModel address = JSONConvert.getAddress(marker.getSnippet());
        titleTv.setText(address.getName());
        infoClickListener.onClick(marker);
        return v;
    }
}
