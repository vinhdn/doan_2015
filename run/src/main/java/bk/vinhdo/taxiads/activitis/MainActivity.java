package bk.vinhdo.taxiads.activitis;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main, true);
        ButterKnife.inject(this);
    }

    @Override
    public void setActionView() {
        setVisibleRightImage(true);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.pink_ic_navigation_drawer);
        setBackgroundRightImage(R.drawable.topic_map_ic);
        setBackgroundTitleText("Search e.g Hanoi", android.R.color.transparent);
        final Drawable cd = getResources().getDrawable(R.drawable.ab_background_light);
        layout_actionbar.setBackgroundDrawable(cd);
        cd.setAlpha(0);
    }

    @Override
    protected void customHeaderView() {

    }

    @Override
    public void onLeftHeaderClick() {
        Intent i = new Intent(MainActivity.this, SlideMenuActivity.class);
        startActivity(i);
    }

    @Override
    public void onRightHeaderClick() {
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(i);
    }

    @Override
    protected void initModels(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.right_layout)
    public void right(){
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.left_layout)
    public void left(){
        Intent i = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.main_addr_nearby)
    public void nearby(){

    }

    @OnClick(R.id.main_addr_cafe)
    public void cafe(){

    }

    @OnClick(R.id.main_addr_heath)
    public void heath(){

    }

    @OnClick(R.id.main_addr_repair)
    public void repair(){

    }

    @OnClick(R.id.main_addr_restaurant)
    public void restaurant(){

    }

    @OnClick(R.id.main_addr_shop)
    public void shop(){

    }

}
