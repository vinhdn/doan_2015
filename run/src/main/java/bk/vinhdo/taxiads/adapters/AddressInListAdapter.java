package bk.vinhdo.taxiads.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;
import java.util.zip.Inflater;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.config.ApiConfig;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by vinhdo on 5/27/15.
 */
public class AddressInListAdapter extends BaseAdapter {

    Context mContext;
    List<AddressModel> mData;

    public AddressInListAdapter(Context context, List<AddressModel> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ItemHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_address_in_list, parent, false);
            holder = new ItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ItemHolder) convertView.getTag();
        }
        AddressModel address = mData.get(position);
        holder.mAddressNameTv.setText(address.getName());
        if (address.getAddress() != null) {
            holder.mCateAddressTv.setText(address.getAddress());
        } else if (address.getStreetNumber() != null) {
            holder.mCateAddressTv.setText(address.getStreetNumber());
        }{
            holder.mCateAddressTv.setText("");
        }
        float rate = address.getRate();
        if (rate < 5) {
            holder.mRateTv.setText("---");
            holder.mRateTv.setBackgroundResource(R.drawable.bg_rate_gray);
        } else if (rate < 6.5) {
            holder.mRateTv.setText(String.format("%.1f", rate));
            holder.mRateTv.setBackgroundResource(R.drawable.bg_rate_orange);
        } else if (rate < 8) {
            holder.mRateTv.setText(String.format("%.1f", rate));
            holder.mRateTv.setBackgroundResource(R.drawable.bg_rate_yellow);
        } else {
            holder.mRateTv.setText(String.format("%.1f", rate));
            holder.mRateTv.setBackgroundResource(R.drawable.bg_rate_green);
        }
        if (!TextUtils.isEmpty(address.getCover())) {
            String bestPhoto = address.getCover();
            ImageSize size = new ImageSize(300, 120);
            if (bestPhoto.startsWith("https://")) {
                ImageLoader.getInstance().loadImage(bestPhoto, size, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        holder.mCoverIv.setImageBitmap(loadedImage);
                    }
                });
            } else {
                // Get Image my server
                ImageLoader.getInstance().loadImage(ApiConfig.URL_GET_IMAGE_MYSERVER + bestPhoto, size, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        holder.mCoverIv.setImageBitmap(loadedImage);
                    }
                });
            }
        }

        return convertView;
    }

    public class ItemHolder {

        @InjectView(R.id.bg_cover_iv)
        ImageView mCoverIv;

        @InjectView(R.id.rate_tv)
        CustomTextView mRateTv;

        @InjectView(R.id.address_name_tv)
        CustomTextView mAddressNameTv;

        @InjectView(R.id.cate_address_tv)
        CustomTextView mCateAddressTv;

        public ItemHolder(View itemView) {
            ButterKnife.inject(this, itemView);
        }
    }
}
