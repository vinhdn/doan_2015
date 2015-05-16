package bk.vinhdo.taxiads.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.PostModel;
import bk.vinhdo.taxiads.models.Tip;
import bk.vinhdo.taxiads.utils.getimage.RateUtil;
import bk.vinhdo.taxiads.utils.view.CustomTextView;

/**
 * Created by Vinh on 2/24/15.
 */
public class TopicAdapter extends BaseAdapter {

    private Context context;
    private boolean isPost;
    private AddressModel mAddress;

    public TopicAdapter(Context context, boolean isTrue,AddressModel address){
        this.context = context;
        isPost = isTrue;
        this.mAddress = address;
    }

    @Override
    public int getCount() {
        return 3 + mAddress.getListPosts().size();
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
            final ImageView coverIv = (ImageView)convertView.findViewById(R.id.cover_iv);
            ImageView avatarIv = (ImageView) convertView.findViewById(R.id.image_avatar);
            CustomTextView nameTv = (CustomTextView) convertView.findViewById(R.id.address_name);
            if(!TextUtils.isEmpty(mAddress.getBestPhoto())){
                String bestPhoto = mAddress.getBestPhoto();
                ImageSize size = new ImageSize(300, 300);
                if(bestPhoto.startsWith("https://")){
                    ImageLoader.getInstance().loadImage(bestPhoto, size, new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            coverIv.setImageBitmap(loadedImage);
                        }
                    });
                }else{
                    // Get Image my server
                }
            }
            nameTv.setText(mAddress.getName());
            if(mAddress.getRate() > 0){
                RateUtil.SetRatingView(mAddress.getRate()/2, convertView.findViewById(R.id.item_rating_frame), 28);
            }else{
                convertView.findViewById(R.id.item_rating_frame).setVisibility(View.GONE);
            }
            return convertView;
        }
        if(position <= 2){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_info_add, null);
            return convertView;
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_post_of_address, null);
        if(isPost){
            PostModel post = mAddress.getListPosts().get(position - 3);
            final ImageView imageIv = (ImageView) convertView.findViewById(R.id.image_tip);
            if(post.getImage() != null){
                imageIv.setVisibility(View.VISIBLE);
                ImageSize size = new ImageSize(300, 300);
                if(post.getImage().startsWith("https://")){
                    ImageLoader.getInstance().loadImage(post.getImage(), size, new SimpleImageLoadingListener(){
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            imageIv.setImageBitmap(loadedImage);
                        }
                    });
                }else{
                    // Get Image my server
                }
            }else {
                imageIv.setVisibility(View.GONE);
            }
            CustomTextView contentTV = (CustomTextView) convertView.findViewById(R.id.content_tip);
            TextView tvWho = (TextView) convertView.findViewById(R.id.username);
            contentTV.setText(post.getContent());
            tvWho.setText(post.getUser().getFisrtName());
        }

        return convertView;
    }
}
