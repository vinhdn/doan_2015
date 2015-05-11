package bk.vinhdo.taxiads.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;
import org.json.JSONObject;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.models.AddressModel;

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
        View v = context.getLayoutInflater().inflate(R.layout.item_info_maps, null);
        TextView tvRate = (TextView)v.findViewById(R.id.info_maps_rate);
        TextView tvTitle = (TextView)v.findViewById(R.id.info_maps_title);
        TextView tvContent = (TextView)v.findViewById(R.id.info_maps_content);
        tvTitle.setText(marker.getTitle());
        Log.d("Marker ID", marker.getId());
        AddressModel address = JSONConvert.getAddress(marker.getSnippet());
        if(address.getRate() > 0){
            tvRate.setText(String.valueOf(address.getRate()));
        }
        tvTitle.setText(address.getName());
        tvContent.setText(address.getCategory().getName());
        infoClickListener.onClick(marker);
        return v;
    }
}
