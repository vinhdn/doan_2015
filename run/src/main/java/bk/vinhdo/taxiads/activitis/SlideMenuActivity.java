package bk.vinhdo.taxiads.activitis;

import android.content.Intent;
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
import bk.vinhdo.taxiads.config.ApiConfig;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.fragments.FragmentIndex;
import bk.vinhdo.taxiads.fragments.MainFragment;
import bk.vinhdo.taxiads.fragments.RegisterFragment;
import bk.vinhdo.taxiads.fragments.SettingFragment;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.view.CircleImage;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.MySlidingLayer;
import bk.vinhdo.taxiads.utils.view.SlidingLayer;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by Vinh on 2/4/15.
 */
public class SlideMenuActivity extends MaterialNavigationDrawer implements View.OnClickListener{

    protected RegisterFragment mRegisterFragment;
    private UserModel mCurrentUser;

    @Override
    public void init(Bundle bundle) {
        // create sections
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        this.addSection(newSection(getString(R.string.home_menu), new MainFragment()));

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
            this.addSection(newSection(getString(R.string.setting_menu), new SettingFragment()));
            this.addSection(newSection(getString(R.string.activity_menu), new FragmentIndex("Menu")));
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
                ImageLoader.getInstance().displayImage(ApiConfig.URL_GET_IMAGE_MYSERVER + avatarUrl, avatar);
            }
            CustomTextView mNameTv = (CustomTextView) view.findViewById(R.id.user_name);
            mNameTv.setText((getCurrentUser().getFisrtName() == null ? "" : getCurrentUser().getFisrtName()) + " " + (getCurrentUser().getLastName() == null ? "" : getCurrentUser().getLastName()));
        }
    }

    private void setupSignUp() {
        mRegisterFragment = new RegisterFragment(this){
            @Override
            protected void onClosed() {
            }
        };
        RelativeLayout root = (RelativeLayout)
                findViewById(R.id.content);
        try {
            root.addView(mRegisterFragment);
        } catch (ClassCastException ce) {
            throw new RuntimeException(
                    "root layout of activity isn't relative layout");
        }
    }

    @Override
    public void onBackPressed() {
        if (mRegisterFragment != null && mRegisterFragment.isOpened()) {
            mRegisterFragment.closeLayer(true);
            return;
        }
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
                if (mRegisterFragment != null && !mRegisterFragment.isOpened()) {
                    closeMenu();
                    mRegisterFragment.openLayer(true);
                }
            break;
            case R.id.btn_signup_facebook:
                Intent intent = new Intent(SlideMenuActivity.this, LoginActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Key.EXTRA_ACTION, Key.ACTION_LOGIN);
                intent.putExtras(intent);
                startActivityForResult(intent, Key.REQUEST_CODE_LOGIN);
                break;
        }
    }

    public UserModel getCurrentUser(){
        if(mCurrentUser == null){
            mCurrentUser = UserModel.getCurrentUser();
        }
        return mCurrentUser;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Key.REQUEST_CODE_LOGIN){
            if(resultCode == RESULT_OK){
                Intent ii = getIntent();
                finish();
                startActivity(ii);
            }
        }else
            if(mRegisterFragment != null){
            mRegisterFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
