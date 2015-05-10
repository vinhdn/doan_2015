package bk.vinhdo.taxiads.activitis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.apache.http.Header;

import java.io.File;
import java.net.URI;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.utils.ToastUtil;
import bk.vinhdo.taxiads.utils.getimage.Action;
import bk.vinhdo.taxiads.utils.view.CustomEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CheckinActivity extends BaseActivity{

    @InjectView(R.id.image_checkin)
    ImageView iv;

    @InjectView(R.id.content_edt)
    CustomEditText mContentEdt;

    private String mAddressId = "111111", mContent;
    private File mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_checkin, false);
        ButterKnife.inject(this);
    }

    @Override
    public void setActionView() {
        setVisibleRightImage(false);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setVisibleRightText(true);
        setBackgroundRightText("POST", 0);
        setVisibleTitleImage(false);
        setVisibleTitleText(true);
        setBackgroundTitleText("Karaoke BMW", 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            String single_path = data.getStringExtra("single_path");
            ImageLoader.getInstance().displayImage("file://" + single_path, iv);
            mImage = new File(URI.create("file://" + single_path));
        }
    }

    @Override
    protected void customHeaderView() {

    }

    @Override
    public void onLeftHeaderClick() {
        finish();
    }

    @Override
    public void onRightHeaderClick() {
        mContent = mContentEdt.getText().toString().trim();
        mContentEdt.setError(null);
        if(mContent.equals("")) {
            mContentEdt.setError("Phần này không được bỏ trống.");
            return;
        }
//        if(mCurrentUser!= null)
        RestClient.createPost("NmVEi0ybw3LnPSgzBYIRsmQVZsLoMK", mAddressId, mContent, mImage, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ToastUtil.show("Tạo post thành công");
                finish();
            }
        });
    }

    @Override
    protected void initModels(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.image_checkin)
    public void imageCheckin(){
        Intent i = new Intent(Action.ACTION_PICK);
        startActivityForResult(i, 100);
    }
}
