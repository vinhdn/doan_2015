package me.vinhdo.getvenues;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.vinhdo.getvenues.config.Key;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.cafe_btn)
    public void cafe(){
        startSearch(1);
    }

    @OnClick(R.id.res_btn)
    public void res(){
        startSearch(2);
    }

    @OnClick(R.id.shop_btn)
    public void shop(){
        startSearch(3);
    }

    @OnClick(R.id.hotel_btn)
    public void hotel(){
        startSearch(4);
    }

    private void startSearch(int i){
        Bundle bundle = new Bundle();
        bundle.putInt(Key.KEY_CATE, i);
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
