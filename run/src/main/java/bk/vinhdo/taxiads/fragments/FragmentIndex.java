package bk.vinhdo.taxiads.fragments;

/**
 * Created by Vinh on 2/4/15.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by neokree on 24/11/14.
 */
public class FragmentIndex extends Fragment {

    String title= "Section";
    public FragmentIndex(String title){
        title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView text = new TextView(this.getActivity());
        text.setText(title);
        text.setGravity(Gravity.CENTER);
        return text;
    }

}
