package bk.vinhdo.taxiads.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import bk.vinhdo.taxiads.fragments.ItemAddressFragment;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;

/**
 * Created by Vinh on 1/29/15.
 */
public class ItemAddressListViewAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private ArrayList<AddressModel> data;

    public ItemAddressListViewAdapter(FragmentManager fm,Context context, ArrayList<AddressModel> data){
        super(fm);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Fragment getItem(int position) {
        return ItemAddressFragment.getInstance(data.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return data.indexOf(object);
    }
}
