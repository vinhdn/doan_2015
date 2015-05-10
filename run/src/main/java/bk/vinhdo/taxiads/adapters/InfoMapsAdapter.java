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
        try {
            JSONObject jo = new JSONObject(marker.getSnippet());
            if(!jo.isNull("rate"))
                tvRate.setText(jo.getString("rate"));
            if(!jo.isNull("cate"))
                tvRate.setText(jo.getString("cate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Marker ID",marker.getId());
        infoClickListener.onClick(marker);
        return v;
    }
}
