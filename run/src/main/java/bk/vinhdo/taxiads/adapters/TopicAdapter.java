package bk.vinhdo.taxiads.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.Header;

import java.util.ArrayList;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.AddressInMapActivity;
import bk.vinhdo.taxiads.activitis.MapsActivity;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.config.ApiConfig;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.dialogs.FeedbackDialogFragment;
import bk.vinhdo.taxiads.listeners.FeedbackFragmentListener;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.PostModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.models.Tip;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.ToastUtil;
import bk.vinhdo.taxiads.utils.getimage.RateUtil;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.DynamicMapFragment;
import bk.vinhdo.taxiads.utils.view.SAutoBgImageButton;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Vinh on 2/24/15.
 */
public class TopicAdapter extends BaseAdapter implements View.OnClickListener {

    private BaseActivity context;
    private boolean isPost;
    private AddressModel mAddress;
    private UserModel mCurrentUser;

    public TopicAdapter(BaseActivity context, boolean isTrue, AddressModel address) {
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
        if (position == 0) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_header, null);
            final HeaderHolder holder = new HeaderHolder(convertView);
            if (!TextUtils.isEmpty(mAddress.getCover())) {
                String bestPhoto = mAddress.getCover();
                ImageSize size = new ImageSize(300, 300);
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
                    if (bestPhoto.startsWith("https://")) {
                        ImageLoader.getInstance().loadImage(ApiConfig.URL_GET_IMAGE_MYSERVER + bestPhoto, size, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                holder.mCoverIv.setImageBitmap(loadedImage);
                            }
                        });
                    }
                }
            }
            float rate = mAddress.getRate();
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
            convertView.findViewById(R.id.rate_btn).setOnClickListener(this);
            SAutoBgImageButton saveBtn = (SAutoBgImageButton) convertView.findViewById(R.id.save_btn);
            saveBtn.setOnClickListener(this);
            if (mAddress.isLike()) {
                saveBtn.setBackgroundResource(R.drawable.actionbar_save_pressed);
            }
            convertView.findViewById(R.id.share_btn).setOnClickListener(this);
            holder.mNameTv.setText(mAddress.getName());
            return convertView;
        }
        if (position == 1) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_info_add, null);
            InfoHeaderHolder holder = new InfoHeaderHolder(convertView);
            if (mAddress.getAddress() != null) {
                holder.mAddressTv.setText(mAddress.getAddress());
            } else if (mAddress.getStreetNumber() != null) {
                holder.mAddressTv.setText(mAddress.getStreetNumber());
            } else {
                holder.mAddressTv.setText("");
            }
            if (!TextUtils.isEmpty(mAddress.getPhoneNumber())) {
                holder.mPhoneTv.setText(mAddress.getPhoneNumber());
            }
            holder.mDistanceTv.setText(String.format("About %.1f km", ((float) mAddress.getDistance() / 1000f)));
            holder.mMoreDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.mDistanceTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, AddressInMapActivity.class);
                    i.putExtra(Key.EXTRA_NAME,mAddress.getName());
                    i.putExtra(Key.EXTRA_LAT,mAddress.getLat());
                    i.putExtra(Key.EXTRA_LNG,mAddress.getLng());
                    context.startActivity(i);
                }
            });
//            holder.mMapFrame.initMap(new LatLng(mAddress.getLat(), mAddress.getLng()),R.drawable.map_pin_large_selected, false);
            return convertView;
        }
        convertView = LayoutInflater.from(context).inflate(R.layout.item_post_of_address, null);
        if (isPost) {
            PostModel post = mAddress.getListPosts().get(position - 2);
            final ImageView imageIv = (ImageView) convertView.findViewById(R.id.image_tip);
            if (post.getImage() != null) {
                imageIv.setVisibility(View.VISIBLE);
                ImageSize size = new ImageSize(300, 300);
                if (post.getImage().startsWith("https://")) {
                    ImageLoader.getInstance().loadImage(post.getImage(), size, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            imageIv.setImageBitmap(loadedImage);
                        }
                    });
                } else {
                    // Get Image my server
                    if (post.getImage().startsWith("https://")) {
                        ImageLoader.getInstance().loadImage(ApiConfig.URL_GET_IMAGE_MYSERVER + post.getImage(), size, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                super.onLoadingComplete(imageUri, view, loadedImage);
                                imageIv.setImageBitmap(loadedImage);
                            }
                        });
                    }
                }
            } else {
                imageIv.setVisibility(View.GONE);
            }
            CustomTextView contentTV = (CustomTextView) convertView.findViewById(R.id.content_tip);
            TextView tvWho = (TextView) convertView.findViewById(R.id.username);
            final ImageView userAvatarIv = (ImageView) convertView.findViewById(R.id.user_avatar);
            contentTV.setText(post.getContent());
            tvWho.setText((post.getUser().getFisrtName() == null ? "" : post.getUser().getFisrtName()) + " " + (post.getUser().getLastName() == null ? "" : post.getUser().getLastName()));
            if (post.getUser().getAvatarUrl() != null) {
                String avatarU = post.getUser().getAvatarUrl();
                ImageSize size = new ImageSize(20, 20);
                if (avatarU.startsWith("https://")) {
                    ImageLoader.getInstance().loadImage(avatarU, size, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            userAvatarIv.setImageBitmap(loadedImage);
                        }
                    });
                } else {
                    // Get Image my server
                    ImageLoader.getInstance().loadImage(ApiConfig.URL_GET_IMAGE_MYSERVER + avatarU, size, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            super.onLoadingComplete(imageUri, view, loadedImage);
                            userAvatarIv.setImageBitmap(loadedImage);
                        }
                    });
                }
            }
        }

        return convertView;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.rate_btn:
                if (mCurrentUser != null) {
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
                if (mCurrentUser != null) {
                    RestClient.likeAddress(mCurrentUser.getAccessToken(), mAddress.getId(), new TextHttpResponseHandler() {
                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            ResponseModel response = JSONConvert.getResponse(responseString);
                            if (response.isSuccess()) {
                                v.setBackgroundResource(R.drawable.actionbar_save_pressed);
                            }
                        }
                    });
                }
                break;
            case R.id.share_btn:
                context.mFacebookManager.shareStatus(context, mAddress.getName(), mAddress.getAddress() == null ? "" : mAddress.getAddress(), new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        ToastUtil.show(result.getPostId());
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException e) {

                    }
                });
                break;
        }
    }

    public class InfoHeaderHolder {

        @InjectView(R.id.address_tv)
        CustomTextView mAddressTv;

        @InjectView(R.id.info_phone)
        CustomTextView mPhoneTv;

        @InjectView(R.id.distance_tv)
        CustomTextView mDistanceTv;

        @InjectView(R.id.ll_info_more_detail)
        LinearLayout mMoreDetail;

        DynamicMapFragment mMapFrame;

        public InfoHeaderHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public class HeaderHolder {

        @InjectView(R.id.cover_iv)
        ImageView mCoverIv;

        @InjectView(R.id.rate_tv)
        TextView mRateTv;

        @InjectView(R.id.address_name)
        TextView mNameTv;

        public HeaderHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
