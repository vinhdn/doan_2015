package bk.vinhdo.taxiads.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import bk.vinhdo.taxiads.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by vinhdo on 5/12/15.
 */
public class FeedbackItem extends LinearLayout {

    @InjectViews({R.id.fb_dk, R.id.fb_00, R.id.fb_01, R.id.fb_02, R.id.fb_03, R.id.fb_04, R.id.fb_05})
    CustomTextView[] mItemsTv;
    @InjectView(R.id.low_tv)
    CustomTextView mLowTv;
    @InjectView(R.id.high_tv)
    CustomTextView mHighTv;
    @InjectView(R.id.fb_title_tv)
    CustomTextView mTitleTv;
    int mFeedback = -1;
    int mColor;
    String mTitle;
    ItemFeedbackListener mListener;

    /**
     * Constructor
     *
     * @param context
     * @param attrs
     */
    public FeedbackItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FeedbackItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public FeedbackItem(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.item_layout_feedback, null);
        ButterKnife.inject(this, view);
        addView(view);
    }

    public void setListener(ItemFeedbackListener listener) {
        this.mListener = listener;
    }

    public void setTitle(String title) {
        mTitle = title;
        if (mTitle != null) {
            mTitleTv.setText(mTitle);
            mTitleTv.setVisibility(View.VISIBLE);
        } else {
            mTitleTv.setVisibility(View.GONE);
        }
    }

    public int getFeedbackSelected() {
        return mFeedback;
    }

    @OnClick(R.id.fb_dk)
    public void fbDK() {
        mFeedback = -1;
        selectFeedback();
    }

    @OnClick(R.id.fb_00)
    public void fb00() {
        mFeedback = 0;
        selectFeedback();
    }

    @OnClick(R.id.fb_01)
    public void fb01() {
        mFeedback = 1;
        selectFeedback();
    }

    @OnClick(R.id.fb_02)
    public void fb02() {
        mFeedback = 2;
        selectFeedback();
    }

    @OnClick(R.id.fb_03)
    public void fb03() {
        mFeedback = 3;
        selectFeedback();
    }

    @OnClick(R.id.fb_04)
    public void fb04() {
        mFeedback = 4;
        selectFeedback();
    }

    @OnClick(R.id.fb_05)
    public void fb05() {
        mFeedback = 5;
        selectFeedback();
    }

    private void selectFeedback() {
        if (mListener != null) {
            mListener.onClick(mFeedback);
        }
        switch (mFeedback) {
            case -1:
            case 0:
                mColor = R.color.feedback_red;
                break;
            case 1:
            case 2:
            case 3:
                mColor = R.color.feedback_orange;
                break;
            case 4:
            case 5:
                mColor = R.color.feedback_green;
                break;
        }
        for (int i = mFeedback + 1; i >= 0; i--) {
            mItemsTv[i].setBackgroundColor(getResources().getColor(mColor));
            mItemsTv[i].setTextColor(getResources().getColor(R.color.white));
        }
        for (int i = mFeedback + 2; i < 7; i++) {
            mItemsTv[i].setBackgroundColor(getResources().getColor(R.color.feedback_gray));
            mItemsTv[i].setTextColor(getResources().getColor(R.color.feedback_text_gray));
        }
        if (mFeedback > 3) {
            mLowTv.setTextColor(getResources().getColor(R.color.feedback_text_gray));
            mHighTv.setTextColor(getResources().getColor(R.color.feedback_green));
        } else if (mFeedback < 1) {
            mLowTv.setTextColor(getResources().getColor(R.color.feedback_red));
            mHighTv.setTextColor(getResources().getColor(R.color.feedback_text_gray));
        } else {
            mHighTv.setTextColor(getResources().getColor(R.color.feedback_text_gray));
            mLowTv.setTextColor(getResources().getColor(R.color.feedback_text_gray));
        }
    }

    public interface ItemFeedbackListener {
        void onClick(int fb);
    }
}
