package bk.vinhdo.taxiads.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import bk.vinhdo.taxiads.activitis.MapsActivity;
import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.SlideMenuActivity;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.utils.ToastUtil;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.SAutoBgImageButton;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vinh on 2/4/15.
 */
public class MainFragment extends Fragment {

    // view header
    private View mNavigateView;
    private CustomTextView mLeftText;
    private CustomTextView mRightText;
    private CustomTextView mTitleText;
    private SAutoBgImageButton mLeftImage;
    private SAutoBgImageButton mRightImage;
    private RelativeLayout mLeftBarLayout;
    private RelativeLayout mRightBarLayout;
    private ImageView mTitleImage;

    //Content
//    SAutoBgImageButton addr_nearby, addr_shop, addr_restaurant, addr_cafe, addr_repair, addr_heath;
    private LinearLayout mLayoutWeather;
//    public LinearLayout mLLBG;

    public interface MainClickListener {
        public void MenuClick();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, null, false);
        initHeaderView(view);
        ButterKnife.inject(this,view);
        return view;
    }

    protected void initHeaderView(View view) {
        mNavigateView = view.findViewById(R.id.navigate_bar);
//        mLLBG = (LinearLayout)view.findViewById(R.id.bg_ll);
//        Bitmap mBitmapBg = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.hn_bg);
//        BitmapDrawable drawableBg = new BitmapDrawable(BitmapUtil.fastblur(getActivity(),mBitmapBg,16));
//        mLLBG.setBackgroundDrawable(drawableBg);
        final Drawable cd = getResources().getDrawable(R.drawable.ab_background_light);
        mNavigateView.setBackgroundDrawable(cd);
        cd.setAlpha(0);
        mLeftText = (CustomTextView) view.findViewById(R.id.left_text);
        mLeftText.setVisibility(View.INVISIBLE);

        mLeftImage = (SAutoBgImageButton) view.findViewById(R.id.left_image);
        mLeftImage.setVisibility(View.VISIBLE);
        mLeftImage.setBackgroundResource(R.drawable.pink_ic_navigation_drawer);
        mRightText = (CustomTextView) view.findViewById(R.id.right_text);
        mRightText.setVisibility(View.INVISIBLE);

        mRightImage = (SAutoBgImageButton) view.findViewById(R.id.right_image);
        mRightImage.setVisibility(View.VISIBLE);
        mRightImage.setBackgroundResource(R.drawable.topic_map_ic);

        mTitleText = (CustomTextView) view.findViewById(R.id.title_text);
        mTitleText.setTextSize(getActivity().getResources().getDimension(R.dimen.text_size_very_small));
        mTitleText.setText("Search e.g Cafe..");
        mTitleText.setVisibility(View.VISIBLE);
        mTitleText.setBackgroundResource(R.drawable.bg_search_selector);

        mTitleImage = (ImageView) view.findViewById(R.id.logo_image);
        mTitleImage.setVisibility(View.GONE);

        mLeftBarLayout = (RelativeLayout) view.findViewById(R.id.left_layout);

        mRightBarLayout = (RelativeLayout) view.findViewById(R.id.right_layout);
        mLayoutWeather = (LinearLayout) view.findViewById(R.id.weather_main);
//        addr_nearby = (SAutoBgImageButton) view.findViewById(R.id.main_addr_nearby);
//        addr_shop = (SAutoBgImageButton) view.findViewById(R.id.main_addr_shop);
//        addr_restaurant = (SAutoBgImageButton) view.findViewById(R.id.main_addr_restaurant);
//        addr_cafe = (SAutoBgImageButton) view.findViewById(R.id.main_addr_cafe);
//        addr_repair = (SAutoBgImageButton) view.findViewById(R.id.main_addr_residence);
//        addr_heath = (SAutoBgImageButton) view.findViewById(R.id.main_addr_heath);
    }

    @OnClick(R.id.main_addr_nearby)
    public void nearby(){
        Intent i = new Intent(getActivity(), MapsActivity.class);
        i.putExtra(Key.EXTRA_ACTION,Key.KEY_NEARBY);
        startActivity(i);
    }

    @OnClick(R.id.main_addr_cafe)
    public void cafe(){
        Intent i = new Intent(getActivity(), MapsActivity.class);
        i.putExtra(Key.EXTRA_ACTION,Key.KEY_CAFE);
        startActivity(i);
    }

    @OnClick(R.id.main_addr_heath)
    public void heath(){
//        Intent i = new Intent(getActivity(), MapsActivity.class);
//        i.putExtra(Key.EXTRA_ACTION,Key.KEY_HEATH);
//        startActivity(i);
        ToastUtil.show("Active in next version");
    }

    @OnClick(R.id.main_addr_residence)
    public void residence(){
        Intent i = new Intent(getActivity(), MapsActivity.class);
        i.putExtra(Key.EXTRA_ACTION,Key.KEY_RESIDENCE);
        startActivity(i);
    }

    @OnClick(R.id.main_addr_restaurant)
    public void restaurant(){
        Intent i = new Intent(getActivity(), MapsActivity.class);
        i.putExtra(Key.EXTRA_ACTION,Key.KEY_RESTAURANT);
        startActivity(i);
    }

    @OnClick(R.id.main_addr_shop)
    public void shop(){
        Intent i = new Intent(getActivity(), MapsActivity.class);
        i.putExtra(Key.EXTRA_ACTION,Key.KEY_SHOP);
        startActivity(i);
    }

    @OnClick(R.id.left_image)
    public void leftImage(){
        ((SlideMenuActivity) getActivity()).openMenu();
    }

    @OnClick(R.id.right_image)
    public void rightImage(){
        Intent i = new Intent(getActivity(), MapsActivity.class);
        i.putExtra(Key.EXTRA_ACTION,Key.KEY_NEARBY);
        startActivity(i);
    }

    @OnClick(R.id.title_text)
    public void search(){
        /*
        TODO search Address here
         */
    }
}
