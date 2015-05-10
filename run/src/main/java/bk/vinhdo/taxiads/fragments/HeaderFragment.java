/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package bk.vinhdo.taxiads.fragments;

/**
 * Created by Vinh on 3/10/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Space;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.utils.view.NotifyingScrollView;

/**
 * Little header fragment.
 * <p>
 * <b>Important</b>: Use {@link android.R.id#background} to specify background view and
 * {@link android.R.id#title} to specify view on top of the header
 * (for example: a shadow for {@code ActionBar}).
 * <p>
 * Created by AChep@xda <artemchep@gmail.com>
 */
// let's \'/
// remember -= * =-
// happy {.}
// 2013 {.-'}
// year! {`_.-'}
// It was {-` _.-'}
// amazing! `":=:"`
// `---`
public abstract class HeaderFragment extends Fragment {

    private static final String TAG = "HeaderFragment";

    public static final int HEADER_BACKGROUND_SCROLL_NORMAL = 0;
    public static final int HEADER_BACKGROUND_SCROLL_PARALLAX = 1;
    public static final int HEADER_BACKGROUND_SCROLL_STATIC = 2;

    private FrameLayout mFrameLayout;
    private View mContentOverlay;

    // header
    private View mHeader;
    private View mHeaderHeader;
    private View mHeaderBackground;
    private int mHeaderHeight;
    private int mHeaderScroll;

    private int mHeaderBackgroundScrollMode = HEADER_BACKGROUND_SCROLL_NORMAL;

    private Space mFakeHeader;
    private boolean isListViewEmpty;

    // listeners
    private AbsListView.OnScrollListener mOnScrollListener;
    private OnHeaderScrollChangedListener mOnHeaderScrollChangedListener;

    public interface OnHeaderScrollChangedListener {
        public void onHeaderScrollChanged(float progress, int height, int scroll);
    }

    public void setOnHeaderScrollChangedListener(OnHeaderScrollChangedListener listener) {
        mOnHeaderScrollChangedListener = listener;
    }

    public void setHeaderBackgroundScrollMode(int scrollMode) {
        mHeaderBackgroundScrollMode = scrollMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Activity activity = getActivity();
        assert activity != null;
        mFrameLayout = (FrameLayout) activity.findViewById(R.id.container);
        mFrameLayout.removeAllViews();

        mHeader = onCreateHeaderView(inflater, mFrameLayout);
        mHeaderHeader = mHeader.findViewById(android.R.id.title);
        mHeaderBackground = mHeader.findViewById(android.R.id.background);
        assert mHeader.getLayoutParams() != null;
        mHeaderHeight = mHeader.getLayoutParams().height;

        mFakeHeader = new Space(activity);
        mFakeHeader.setLayoutParams(
                new ListView.LayoutParams(0, mHeaderHeight));

        View content = onCreateContentView(inflater, mFrameLayout);
        if (content instanceof ListView) {
            isListViewEmpty = true;

            final ListView listView = (ListView) content;
            listView.addHeaderView(mFakeHeader);
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onScrollStateChanged(absListView, scrollState);
                    }
                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    if (mOnScrollListener != null) {
                        mOnScrollListener.onScroll(
                                absListView, firstVisibleItem,
                                visibleItemCount, totalItemCount);
                    }

                    if (isListViewEmpty) {
                        scrollHeaderTo(0);
                    } else {
                        final View child = absListView.getChildAt(0);
                        assert child != null;
                        scrollHeaderTo(child == mFakeHeader ? child.getTop() : -mHeaderHeight);
                    }
                }
            });
        } else {

            // Merge fake header view and content view.
            final LinearLayout view = new LinearLayout(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOrientation(LinearLayout.VERTICAL);
            view.addView(mFakeHeader);
            view.addView(content);

            // Put merged content to ScrollView
            final NotifyingScrollView scrollView = new NotifyingScrollView(activity);
            scrollView.addView(view);
            scrollView.setOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {
                @Override
                public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                    scrollHeaderTo(-t);
                }
            });
            content = scrollView;
        }

        mFrameLayout.addView(content);
        mFrameLayout.addView(mHeader);

        // Content overlay view always shows at the top of content.
        if ((mContentOverlay = onCreateContentOverlayView(inflater, mFrameLayout)) != null) {
            mFrameLayout.addView(mContentOverlay, new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }

        // Post initial scroll
        mFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                scrollHeaderTo(0, true);
            }
        });
        onCreateAdapterAfterCreateHeader();
        return mFrameLayout;
    }

    private void scrollHeaderTo(int scrollTo) {
        scrollHeaderTo(scrollTo, false);
    }

    private void scrollHeaderTo(int scrollTo, boolean forceChange) {
        scrollTo = Math.min(Math.max(scrollTo, -mHeaderHeight), 0);
        if (mHeaderScroll == (mHeaderScroll = scrollTo) & !forceChange) return;

        setViewTranslationY(mHeader, scrollTo);
        setViewTranslationY(mHeaderHeader, -scrollTo);

        switch (mHeaderBackgroundScrollMode) {
            case HEADER_BACKGROUND_SCROLL_NORMAL:
                setViewTranslationY(mHeaderBackground, 0);
                break;
            case HEADER_BACKGROUND_SCROLL_PARALLAX:
                setViewTranslationY(mHeaderBackground, -scrollTo / 1.6f);
                break;
            case HEADER_BACKGROUND_SCROLL_STATIC:
                setViewTranslationY(mHeaderBackground, -scrollTo);
                break;
        }

        if (mContentOverlay != null) {
            final ViewGroup.LayoutParams lp = mContentOverlay.getLayoutParams();
            final int delta = mHeaderHeight + scrollTo;
            lp.height = mFrameLayout.getHeight() - delta;
            mContentOverlay.setLayoutParams(lp);
            mContentOverlay.setTranslationY(delta);
        }

        notifyOnHeaderScrollChangeListener(
                (float) -scrollTo / mHeaderHeight,
                mHeaderHeight,
                -scrollTo);
    }

    private void setViewTranslationY(View view, float translationY) {
        if (view != null) view.setTranslationY(translationY);
    }

    private void notifyOnHeaderScrollChangeListener(float progress, int height, int scroll) {
        if (mOnHeaderScrollChangedListener != null) {
            mOnHeaderScrollChangedListener.onHeaderScrollChanged(progress, height, scroll);
        }
    }

    public abstract View onCreateHeaderView(LayoutInflater inflater, ViewGroup container);

    public abstract void onCreateAdapterAfterCreateHeader();

    public abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container);

    public abstract View onCreateContentOverlayView(LayoutInflater inflater, ViewGroup container);

    public void setListViewAdapter(ListView listView, ListAdapter adapter) {
        isListViewEmpty = adapter == null;
        listView.setAdapter(null);
        listView.removeHeaderView(mFakeHeader);
        listView.addHeaderView(mFakeHeader);
        listView.setAdapter(adapter);
    }

    /**
     * {@inheritDoc AbsListView#setOnScrollChangedListener}
     */
    public void setListViewOnScrollChangedListener(AbsListView.OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    // //////////////////////////////////////////
    // //////////// -- GETTERS -- ///////////////
    // //////////////////////////////////////////

    public View getHeaderView() {
        return mHeader;
    }

    public View getHeaderHeaderView() {
        return mHeaderHeader;
    }

    public View getHeaderBackgroundView() {
        return mHeaderBackground;
    }

    public int getHeaderBackgroundScrollMode() {
        return mHeaderBackgroundScrollMode;
    }

}
