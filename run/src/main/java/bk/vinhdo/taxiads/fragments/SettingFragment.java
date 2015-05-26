package bk.vinhdo.taxiads.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.MapsActivity;
import bk.vinhdo.taxiads.activitis.SlideMenuActivity;
import bk.vinhdo.taxiads.config.ApiConfig;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.listeners.AlertListener;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.view.CircleImage;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.SAutoBgImageButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by vinhdo on 5/26/15.
 */
public class SettingFragment extends BaseFragment{

    private View mNavigateView;
    private CustomTextView mLeftText;
    private CustomTextView mRightText;
    private CustomTextView mTitleText;
    private SAutoBgImageButton mLeftImage;
    private SAutoBgImageButton mRightImage;
    private RelativeLayout mLeftBarLayout;
    private RelativeLayout mRightBarLayout;
    private ImageView mTitleImage;

    @InjectView(R.id.image_avatar)
    CircleImage mAvatar;

    @InjectView(R.id.user_name)
    CustomTextView mUserNameTv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null, false);
        initHeaderView(view);
        ButterKnife.inject(this, view);
        return view;
    }

    protected void initHeaderView(View view) {
        mNavigateView = view.findViewById(R.id.navigate_bar);
//        mLLBG = (LinearLayout)view.findViewById(R.id.bg_ll);
//        Bitmap mBitmapBg = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.hn_bg);
//        BitmapDrawable drawableBg = new BitmapDrawable(BitmapUtil.fastblur(getActivity(),mBitmapBg,16));
//        mLLBG.setBackgroundDrawable(drawableBg);
//        final Drawable cd = getResources().getDrawable(R.drawable.ab_background_light);
//        mNavigateView.setBackgroundDrawable(cd);
//        cd.setAlpha(100);
        mLeftText = (CustomTextView) view.findViewById(R.id.left_text);
        mLeftText.setVisibility(View.GONE);

        mLeftImage = (SAutoBgImageButton) view.findViewById(R.id.left_image);
        mLeftImage.setVisibility(View.VISIBLE);
        mLeftImage.setBackgroundResource(R.drawable.ic_white_menu_nar);
        mRightText = (CustomTextView) view.findViewById(R.id.right_text);
        mRightText.setVisibility(View.VISIBLE);
        mRightText.setText("EDIT");
        mRightImage = (SAutoBgImageButton) view.findViewById(R.id.right_image);
        mRightImage.setVisibility(View.GONE);

        mTitleText = (CustomTextView) view.findViewById(R.id.title_text);
        mTitleText.setText("Setting");
        mTitleText.setVisibility(View.VISIBLE);
        mTitleText.setBackgroundResource(android.R.color.transparent);

        mTitleImage = (ImageView) view.findViewById(R.id.logo_image);
        mTitleImage.setVisibility(View.GONE);

        mLeftBarLayout = (RelativeLayout) view.findViewById(R.id.left_layout);

        mRightBarLayout = (RelativeLayout) view.findViewById(R.id.right_layout);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String avatarUrl = getCurrentUser().getAvatarUrl().trim();
        ImageSize size = new ImageSize(100, 100);
        if(avatarUrl != null && avatarUrl.startsWith("https://")){
            ImageLoader.getInstance().loadImage(avatarUrl, size, new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    mAvatar.setImageBitmap(loadedImage);
                }
            });
        }else{
            // Get Image my server
            ImageLoader.getInstance().loadImage(ApiConfig.URL_GET_IMAGE_MYSERVER + avatarUrl, size, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    mAvatar.setImageBitmap(loadedImage);
                }
            });
        }
        mUserNameTv.setText((getCurrentUser().getFisrtName() == null ? "" : getCurrentUser().getFisrtName()) + " " + (getCurrentUser().getLastName() == null ? "" : getCurrentUser().getLastName()));
    }

    @OnClick(R.id.left_image)
    public void leftImage(){
        ((SlideMenuActivity) getActivity()).openMenu();
    }

    @OnClick(R.id.right_image)
    public void rightImage(){
        //TODO Show screen edit profile
//        Intent i = new Intent(getActivity(), MapsActivity.class);
//        i.putExtra(Key.EXTRA_ACTION,Key.KEY_NEARBY);
//        startActivity(i);
    }

    @OnClick(R.id.logout_btn)
    public void logout(){
        showConfirmDialog(getActivity(), "Logout", "You want to logout? Your data will be delete after logout.", new AlertListener() {
            @Override
            public void onPositiveButton(DialogInterface dialog) {
                UserModel.clear(UserModel.class);
                AddressModel.clear(AddressModel.class);
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                getActivity().startActivity(intent);
            }

            @Override
            public void onNegativeButton(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }
}
