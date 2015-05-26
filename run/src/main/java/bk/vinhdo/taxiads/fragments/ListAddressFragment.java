package bk.vinhdo.taxiads.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.ActivityAddress;
import bk.vinhdo.taxiads.adapters.AddressInListAdapter;
import bk.vinhdo.taxiads.models.AddressModel;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by vinhdo on 5/27/15.
 */
public class ListAddressFragment extends BaseFragment {

    @InjectView(R.id.list_address_lv)
    ListView mAddressLv;

    private List<AddressModel> mData = new ArrayList<>();
    private AddressInListAdapter mAdapter;

    public ListAddressFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_address, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new AddressInListAdapter(getActivity(), mData);
        mAddressLv.setAdapter(mAdapter);
        mAddressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ActivityAddress.class);
                i.putExtra("id_address",mData.get(position).getId());
                getActivity().startActivity(i);
            }
        });
    }

    public void setDataAddress(List<AddressModel> data) {
        mData = data;
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }
}
