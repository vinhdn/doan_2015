/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bk.vinhdo.taxiads.fragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Space;

import java.lang.ref.WeakReference;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.ActivityAddress;
import bk.vinhdo.taxiads.adapters.TopicAdapter;
import bk.vinhdo.taxiads.models.Address;

/**
 * Created by Vinh on 3/10/15.
 */
public class AddressListViewFragment extends Fragment {

    private ListView mListView;
    private Address mAddress;
    public AddressListViewFragment(Address address){
        this.mAddress = address;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mListView = (ListView) inflater.inflate(R.layout.activity_address_listview,container,false);
        mListView.setAdapter(new TopicAdapter(getActivity(),true,this.mAddress));
        return mListView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
