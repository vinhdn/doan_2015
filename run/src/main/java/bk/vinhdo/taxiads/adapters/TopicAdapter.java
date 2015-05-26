package bk.vinhdo.taxiads.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.Header;

import java.util.ArrayList;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.config.ApiConfig;
import bk.vinhdo.taxiads.dialogs.FeedbackDialogFragment;
import bk.vinhdo.taxiads.listeners.FeedbackFragmentListener;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.PostModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.models.Tip;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.getimage.RateUtil;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.SAutoBgImageButton;

/**
 * Created by Vinh on 2/24/15.
 */
public class TopicAdapter extends BaseAdapter implements View.OnClickListener{

    private BaseActivity context;
    private boolean isPost;
    private AddressModel mAddress;
    private UserModel mCurrentUser;
    public TopicAdapter(BaseActivity context, boolean isTrue,AddressModel address){
        this.context = context;
        isPost = isTrue;
        this.mAddress = address;
        mCurrentUser = UserModel.getCurrentUser();
    }

    @Override
    public int getCount() {
        return 2 + mAddress.getListPosts().size();
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
            if(!TextUtils.isEmpty(mAddress.getCover())){
                String bestPhoto = mAddress.getCover();
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
                    ImageLoader.getInstance().displayImage(ApiConfig.URL_GET_IMAGE_MYSERVER + bestPhoto, coverIv);
                }
            }
            if(mAddress.getRate() < 5){

            }
            convertView.findViewById(R.id.rate_btn).setOnClickListener(this);
            SAutoBgImageButton saveBtn =  (SAutoBgImageButton)convertView.findViewById(R.id.save_btn);
            saveBtn.setOnClickListener(this);
            if(mAddress.isLike()){
                saveBtn.setBackgroundResource(R.drawable.actionbar_save_pressed);
            }
            convertView.findViewById(R.id.share_btn).setOnClickListener(this);
            nameTv.setText(mAddress.getName());
            return convertView;
        }
        if(position == 1){
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_info_add, null);
            return convertView;
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_post_of_address, null);
        if(isPost){
            PostModel post = mAddress.getListPosts().get(position - 2);
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
                    ImageLoader.getInstance().displayImage(ApiConfig.URL_GET_IMAGE_MYSERVER + post.getImage(), imageIv);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rate_btn:
                if(mCurrentUser != null){
                    final FeedbackDialogFragment dialogFragment = FeedbackDialogFragment.newInstance(mAddress.getId(), mCurrentUser.getAccessToken(), new FeedbackFragmentListener() {
                        @Override
                        public void onCancel(DialogFragment dialog) {
                            dialog.dismiss();
                        }

                        @Override
                        public void onSubmit(DialogFragment dialog) {
                            dialog.dismiss();
                        }
                    });
                    dialogFragment.show(context.getSupportFragmentManager(), "dialog");
                }
                break;
            case R.id.save_btn:
                mAddress.create();
                if(mCurrentUser != null){
                    RestClient.likeAddress(mCurrentUser.getAccessToken(), mAddress.getId(), new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            ResponseModel response = JSONConvert.getResponse(responseString);
                            if(response.isSuccess()){

                            }
                        }
                    });
                }
                break;
            case R.id.share_btn:
                break;
        }
    }
}
