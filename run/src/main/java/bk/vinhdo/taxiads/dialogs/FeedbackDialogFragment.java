package bk.vinhdo.taxiads.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.listeners.FeedbackFragmentListener;
import bk.vinhdo.taxiads.utils.ToastUtil;
import bk.vinhdo.taxiads.utils.view.CustomEditText;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.FeedbackItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by vinhdo on 5/12/15.
 */
public class FeedbackDialogFragment extends DialogFragment {

    @InjectView(R.id.title_fb)
    CustomTextView mTitleTv;

    @InjectView(R.id.recommended_item)
    FeedbackItem mRecommendedItem;
    @InjectView(R.id.friendliness_item)
    FeedbackItem mFriendlinessItem;
    @InjectView(R.id.punctuality_item)
    FeedbackItem mPunctualityItem;
    @InjectView(R.id.cleanliness_item)
    FeedbackItem mCleanlinessItem;

    @InjectView(R.id.fb_comment_edt)
    CustomEditText mCommentEdt;
    String mAddressId;
    int r, f, p, c, s;
    String mComment;
    String mTitle;
    String mAccessToken;
    FeedbackFragmentListener mListener;

    public static FeedbackDialogFragment newInstance(String addressId, String accessToken, FeedbackFragmentListener listener) {
        FeedbackDialogFragment fragment = new FeedbackDialogFragment();
        fragment.mListener = listener;
        Bundle bundle = new Bundle();
        bundle.putString(Key.EXTRA_ID, addressId);
        bundle.putString(Key.EXTRA_ACCESSTOKE, accessToken);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static FeedbackDialogFragment newInstance(FeedbackFragmentListener listener) {
        FeedbackDialogFragment fragment = new FeedbackDialogFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAddressId = getArguments().getString(Key.EXTRA_ID);
            mAccessToken = getArguments().getString(Key.EXTRA_ACCESSTOKE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_feedback, null, false);
        ButterKnife.inject(this, view);
        if (TextUtils.isEmpty(mTitle)) {
            mTitleTv.setVisibility(View.GONE);
        } else {
            mTitleTv.setVisibility(View.VISIBLE);
            mTitleTv.setText(mTitle);
        }
        //TODO set String for rate
        mRecommendedItem.setTitle(getString(R.string.space));
        mPunctualityItem.setTitle(getString(R.string.service));
        mFriendlinessItem.setTitle(getString(R.string.quality));
        mCleanlinessItem.setTitle(getString(R.string.like));
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.cancel_btn)
    public void cancelCl() {
        if (mListener != null) {
            mListener.onCancel(this);
        }
    }

    @OnClick(R.id.submit_btn)
    public void submit() {

        if (!isValidate()) return;
            ToastUtil.show("Thanks for rate.");
        dismiss();
    }

    private boolean isValidate() {
        if (TextUtils.isEmpty(mAddressId))
            return false;
        r = mRecommendedItem.getFeedbackSelected();
        f = mFriendlinessItem.getFeedbackSelected();
        p = mPunctualityItem.getFeedbackSelected();
        c = mCleanlinessItem.getFeedbackSelected();
        mComment = mCommentEdt.getText().toString().trim();

        mCommentEdt.setError(null);
        if (TextUtils.isEmpty(mComment)) {
            mCommentEdt.setError("You must enter your comment");
            mCommentEdt.requestFocus();
            return false;
        }
        return true;
    }
}
