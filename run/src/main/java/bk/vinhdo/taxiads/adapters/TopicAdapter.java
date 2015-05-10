package bk.vinhdo.taxiads.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.Tip;
import bk.vinhdo.taxiads.utils.view.CustomTextView;

/**
 * Created by Vinh on 2/24/15.
 */
public class TopicAdapter extends BaseAdapter {

    private Context context;
    private boolean isPost;
    private Address mAddress;

    public TopicAdapter(Context context, boolean isTrue,Address address){
        this.context = context;
        isPost = isTrue;
        this.mAddress = address;
    }

    @Override
    public int getCount() {
        return 3 + mAddress.getListTips().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position == 0){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_header, null);
            return convertView;
        }
        if(position <= 2){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_info_add, null);
            return convertView;
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_post_of_address, null);
        if(isPost){
            CustomTextView contentTV = (CustomTextView) convertView.findViewById(R.id.content_tip);
            TextView tvWho = (TextView) convertView.findViewById(R.id.username);
            contentTV.setText(mAddress.getListTips().get(position - 3).getContent());
            tvWho.setText(mAddress.getListTips().get(position - 3).getUser().getFirst_name());
        }

        return convertView;
    }
}
