package bk.vinhdo.taxiads.activitis;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.fragments.FragmentIndex;
import bk.vinhdo.taxiads.fragments.MainFragment;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.view.CircleImage;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.MySlidingLayer;
import bk.vinhdo.taxiads.utils.view.SlidingLayer;
import bk.vinhdo.taxiads.utils.view.SquareImage;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by Vinh on 2/4/15.
 */
public class SlideMenuActivity extends MaterialNavigationDrawer implements View.OnClickListener{

    protected MySlidingLayer mSlidingMenu;
    private UserModel mCurrentUser;

    @Override
    public void init(Bundle bundle) {
        // create sections
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.addSection(newSection(getString(R.string.home_menu), new MainFragment()));
        this.addSection(newSection(getString(R.string.activity_menu), new FragmentIndex("Menu")));
        this.addSection(newSection(getString(R.string.notifi_menu), new FragmentIndex("Notifi")));
        this.addSection(newSection(getString(R.string.setting_menu), new FragmentIndex("Setting")));
        this.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.d("State", newState + "");
            }
        });
        if(getCurrentUser() == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_menu_header_signup, null, false);
            setDrawerHeaderCustom(view);
            changeHeightCustomHeader(12 / 16);
            //setActionButtomMenu(R.layout.layout_action_menu);
            findViewById(R.id.btn_signup_email).setOnClickListener(this);
            findViewById(R.id.btn_signup_facebook).setOnClickListener(this);
            setupSignUp();
        }else{
            View view = LayoutInflater.from(this).inflate(R.layout.layout_menu_header_info, null, false);
            setDrawerHeaderCustom(view);
            changeHeightCustomHeader(12 / 16);
            setActionButtomMenu(R.layout.layout_action_menu);
            final CircleImage avatar = (CircleImage)view.findViewById(R.id.image_avatar);
            ImageSize size = new ImageSize(100, 100);
            String avatarUrl = getCurrentUser().getAvatarUrl().trim();
            if(avatarUrl != null && avatarUrl.startsWith("https://")){
                ImageLoader.getInstance().loadImage(avatarUrl, size, new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        avatar.setImageBitmap(loadedImage);
                    }
                });
            }else{
                // Get Image my server
            }
            CustomTextView mNameTv = (CustomTextView) view.findViewById(R.id.user_name);
            mNameTv.setText((getCurrentUser().getFisrtName() == null ? "" : getCurrentUser().getFisrtName()) + " " + (getCurrentUser().getLastName() == null ? "" : getCurrentUser().getLastName()));
        }
    }

    private void setupSignUp() {
        View signupView = LayoutInflater.from(this).inflate(R.layout.layout_register, null, false);
        mSlidingMenu = new MySlidingLayer(this){
            @Override
            protected void onClosed() {
            }
        };
        mSlidingMenu.setStickTo(SlidingLayer.STICK_TO_TOP);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.BELOW, R.id.navigate_bar);
        mSlidingMenu.addView(signupView);
        mSlidingMenu.setCloseOnTapEnabled(false);
        mSlidingMenu.setLayoutParams(lp);
        mSlidingMenu.setSlidingEnabled(false);
        RelativeLayout root = (RelativeLayout)
                findViewById(R.id.content);
        try {
            root.addView(mSlidingMenu);
        } catch (ClassCastException ce) {
            throw new RuntimeException(
                    "root layout of activity isn't relative layout");
        }
    }

    @Override
    public void onBackPressed() {
        switch (getCurrentSection().getPosition()){
            case 0:
                finish();
                super.onBackPressed();
                break;
            case 1:
                setSection(getSectionAtCurrentPosition(0));
                break;
            case 2:
                setSection(getSectionAtCurrentPosition(0));
                break;
            case 3:
                if (mSlidingMenu.isOpened())
                    mSlidingMenu.closeLayer(true);
                break;
            case 4:
                setSection(getSectionAtCurrentPosition(0));
                break;
            default:
                finish();
                super.onBackPressed();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup_email:
                if (mSlidingMenu.isOpened()) {
                    mSlidingMenu.closeLayer(true);
                } else {
                    mSlidingMenu.openLayer(true);
                }
            break;
            case R.id.btn_signup_facebook:
                break;
        }
    }

    public UserModel getCurrentUser(){
        if(mCurrentUser == null){
            mCurrentUser = UserModel.getCurrentUser();
        }
        return mCurrentUser;
    }
}
