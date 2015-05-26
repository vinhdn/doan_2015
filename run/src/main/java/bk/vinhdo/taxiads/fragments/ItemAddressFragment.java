package bk.vinhdo.taxiads.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.ActivityAddress;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;

/**
 * Created by Vinh on 1/29/15.
 */
public class ItemAddressFragment extends Fragment {

    private Activity mActivity;
    private AddressModel address;

    public static ItemAddressFragment getInstance(AddressModel address){
        ItemAddressFragment fragment = new ItemAddressFragment();
        fragment.address = address;
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.item_info_maps,container,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, ActivityAddress.class);
                i.putExtra("id_address",address.getId());
                mActivity.startActivity(i);
            }
        });
        if(address == null)
            return  view;
        TextView title = (TextView)view.findViewById(R.id.info_maps_title);
        TextView tvRate = (TextView)view.findViewById(R.id.info_maps_rate);
        TextView tvContent = (TextView)view.findViewById(R.id.info_maps_content);
        TextView tvDistance = (TextView)view.findViewById(R.id.distance_tv);
        title.setText(address.getName());
        if(address.getRate() > 0){
            tvRate.setText(String.format("%.1f", address.getRate()));
        }
        tvContent.setText(address.getCategory().getName());
        tvDistance.setText(String.format("%.1f",((float)address.getDistance() / 1000f)));
        return view;
    }
}
