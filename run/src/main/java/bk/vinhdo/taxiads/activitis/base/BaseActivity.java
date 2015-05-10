package bk.vinhdo.taxiads.activitis.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.TaxiApplication;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.SAutoBgImageButton;

/**
 * Created by Vinh on 2/3/15.
 */
public abstract class BaseActivity extends FragmentActivity {
    protected BaseActivity mSelf;
    protected TaxiApplication mApp;
    protected static LayoutInflater mInflater;

    protected Handler mHandler;

    private RelativeLayout rootLayout;

    // view header
    public View mNavigateView;
    public CustomTextView mLeftText;
    public CustomTextView mRightText;
    public CustomTextView mTitleText;
    public SAutoBgImageButton mLeftImage;
    public SAutoBgImageButton mRightImage;
    public RelativeLayout mLeftBarLayout;
    public RelativeLayout mRightBarLayout;
    public ImageView mTitleImage;
    public LinearLayout layout_actionbar;

    protected UserModel mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mSelf = BaseActivity.this;
        mApp = (TaxiApplication) getApplication();

        mInflater = LayoutInflater.from(getApplicationContext());

        mHandler = new Handler();

        super.setContentView(R.layout.layout_base);
        rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        initModels(savedInstanceState);
    }

    public void setContentView(int id, boolean isActionBar) {
        LinearLayout contentView = (LinearLayout) findViewById(R.id.content_view);
        if (isActionBar) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT);
            params.addRule(RelativeLayout.BELOW, 0);
            contentView.setLayoutParams(params);
        }
        contentView.removeAllViews();
        getLayoutInflater().inflate(id, contentView);
        super.setContentView(rootLayout);
        initHeaderView();
        layout_actionbar = (LinearLayout) findViewById(R.id.navigate_bar);
        setActionView();
    }

    public void setBackgroundActionBar(int id){
        layout_actionbar.setBackgroundResource(id);
    }

    public void setBackgroundActionBarDrawable(Drawable drawable){
        layout_actionbar.setBackgroundDrawable(drawable);
    }

    public abstract void setActionView();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Init all header view
     */
    protected void initHeaderView() {
        mNavigateView = findViewById(R.id.navigate_bar);
        mLeftText = (CustomTextView) findViewById(R.id.left_text);
        mLeftText.setOnClickListener(onLeftHeaderClickListener);
        mLeftText.setVisibility(View.INVISIBLE);

        mLeftImage = (SAutoBgImageButton) findViewById(R.id.left_image);
        mLeftImage.setOnClickListener(onLeftHeaderClickListener);
        mLeftImage.setVisibility(View.GONE);

        mRightText = (CustomTextView) findViewById(R.id.right_text);
        mRightText.setOnClickListener(onRightHeaderClickListener);
        mRightText.setVisibility(View.INVISIBLE);

        mRightImage = (SAutoBgImageButton) findViewById(R.id.right_image);
        mRightImage.setOnClickListener(onRightHeaderClickListener);
        mRightImage.setVisibility(View.GONE);

        mTitleText = (CustomTextView) findViewById(R.id.title_text);
        mTitleText.setVisibility(View.INVISIBLE);

        mTitleImage = (ImageView) findViewById(R.id.logo_image);
        mTitleImage.setVisibility(View.GONE);

        mLeftBarLayout = (RelativeLayout) findViewById(R.id.left_layout);
        mLeftBarLayout.setOnClickListener(onLeftHeaderClickListener);

        mRightBarLayout = (RelativeLayout) findViewById(R.id.right_layout);
        mRightBarLayout.setOnClickListener(onRightHeaderClickListener);

        customHeaderView();
    }

    protected boolean isNetwork() {
        ConnectivityManager connectivity = (ConnectivityManager) this
                .getApplicationContext().getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        Toast.makeText(this, "Not network",
                Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * Implement show, hide.. for header view
     */
    protected abstract void customHeaderView();

    protected View.OnClickListener onLeftHeaderClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            onLeftHeaderClick();
        }
    };

    protected View.OnClickListener onRightHeaderClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            onRightHeaderClick();
        }
    };

    public abstract void onLeftHeaderClick();

    public abstract void onRightHeaderClick();

    /**
     * Init all model when onCreate activity here
     */
    protected abstract void initModels(Bundle savedInstanceState);

    /**
     * Init all views when onCreate activity here
     */
    protected abstract void initViews();

    /**
     * set visible button
     *
     * @param view
     * @param showOrHide
     */
    private void setVisibleView(View view, boolean showOrHide) {
        view.setVisibility(showOrHide ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * set visible left button
     *
     * @param showOrHide : true if show, else hide is false
     */
    public void setVisibleLeftText(boolean showOrHide) {
        setVisibleView(mLeftText, showOrHide);
    }

    /**
     * set visible right button
     *
     * @param showOrHide : true if show, else hide is false
     */
    public void setVisibleRightText(boolean showOrHide) {
        setVisibleView(mRightText, showOrHide);
    }

    /**
     * @param showOrHide
     */
    public void setVisibleLeftImage(boolean showOrHide) {
        setVisibleView(mLeftImage, showOrHide);
    }

    /**
     * @param showOrHide
     */
    public void setVisibleRightImage(boolean showOrHide) {
        setVisibleView(mRightImage, showOrHide);
    }

    /**
     * @param showOrHide
     */
    public void setVisibleTitleImage(boolean showOrHide) {
        setVisibleView(mTitleImage, showOrHide);
    }

    /**
     * @param showOrHide
     */
    public void setVisibleTitleText(boolean showOrHide) {
        setVisibleView(mTitleText, showOrHide);
    }

    /**
     * @param text
     * @param drawable
     */
    private void setBackgroundText(CustomTextView view, String text,
                                   int drawable) {
        view.setVisibility(View.VISIBLE);
        if (drawable > 0) {
            view.setBackgroundResource(drawable);
        }
        if (!TextUtils.isEmpty(text)) {
            view.setText(text);
        }
    }

    /**
     * @param imageView
     * @param drawable
     */
    private void setBackgroundImage(View imageView, int drawable) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setBackgroundResource(drawable);
    }

    /**
     * @param text
     * @param drawable
     */
    public void setBackgroundLeftText(String text, int drawable) {
        setBackgroundText(mLeftText, text, drawable);
    }

    /**
     * @param text
     * @param drawable
     */
    public void setBackgroundRightText(String text, int drawable) {
        setBackgroundText(mRightText, text, drawable);
    }

    /**
     * @param drawable
     */
    public void setBackgroundLeftImage(int drawable) {
        setBackgroundImage(mLeftImage, drawable);
    }

    /**
     * @param drawable
     */
    public void setBackgroundRightImage(int drawable) {
        setBackgroundImage(mRightImage, drawable);
    }

    /**
     * @param drawable
     */
    public void setBackgroundTitleImage(int drawable) {
        setBackgroundImage(mTitleImage, drawable);
    }

    /**
     * @param drawable
     */
    public void setBackgroundTitleText(String text, int drawable) {
        setBackgroundText(mTitleText, text, drawable);
    }

    public UserModel getCurrentUser(){
        return mCurrentUser;
    }
}
