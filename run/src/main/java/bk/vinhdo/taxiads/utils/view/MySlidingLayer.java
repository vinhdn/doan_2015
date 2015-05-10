package bk.vinhdo.taxiads.utils.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import bk.vinhdo.taxiads.utils.hardware.ConvertUtil;
import bk.vinhdo.taxiads.utils.hardware.DeviceUtil;

/**
 * Custom sliding layer allow slide when layer hide
 * 
 */
public class MySlidingLayer extends SlidingLayer {

	public static int OFFSET_WIDTH_ALLOW_SLIDING = 20; // dp
	public static float MAX_WIDTH_TO_STOP_RATIO = 0.5f;
	public static int ORIENTATION_CUSTOM_SUPPORT = SlidingLayer.STICK_TO_RIGHT;

	private int mOffsetWidthAllowSliding;
	private int mWidthStop;
	private int mWidthDevice;
	private boolean isReset = false;
	private boolean isResize = false;
	RelativeLayout.LayoutParams stopParams;
	RelativeLayout.LayoutParams fillParams;

	public MySlidingLayer(Context context) {
		super(context);
		mOffsetWidthAllowSliding = (int) ConvertUtil.convertDpToPixel(context,
                OFFSET_WIDTH_ALLOW_SLIDING);
		mWidthDevice = DeviceUtil.getInstance(
                getContext().getApplicationContext()).getDeviceInfo().mWidth;
		mWidthStop = (int) (mWidthDevice * MAX_WIDTH_TO_STOP_RATIO);
		stopParams = new RelativeLayout.LayoutParams(mWidthStop,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		stopParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		fillParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		setOnInteractListener(mDefaultInteractListener);
	}

	@Override
	protected boolean allowSlidingFromHereX(final MotionEvent ev,
			final float initialX) {
		switch (mScreenSide) {
		case STICK_TO_LEFT:
		case STICK_TO_RIGHT:
		case STICK_TO_MIDDLE:
			if (mIsOpen) {
				return true;
			}
			if (!mIsOpen) {
				switch (mScreenSide) {
				case STICK_TO_LEFT:
					return initialX <= mOffsetWidthAllowSliding;
				case STICK_TO_RIGHT:
					return initialX >= getWidth() - mOffsetWidthAllowSliding;
				}
			}
		default:
			return false;
		}
	}

	/**
	 * Default interact listener
	 */
	private OnInteractListener mDefaultInteractListener = new OnInteractListener() {

		@Override
		public void onScrollTo(int x, int y) {
			if (mScreenSide != ORIENTATION_CUSTOM_SUPPORT) {
				return;
			}
			MySlidingLayer.this.onScrollTo(x, y);
			if (isReset) {
				setVisibility(View.VISIBLE);
				isReset = false;
				mIsDragging = false;
			}
		}

		@Override
		public void onOpened() {
			MySlidingLayer.this.onOpened();
		}

		@Override
		public void onOpen() {
			MySlidingLayer.this.onOpen();
		}

		@Override
		public void onClosed() {
			if (mScreenSide != ORIENTATION_CUSTOM_SUPPORT) {
				return;
			}
			MySlidingLayer.this.onClosed();
			setLayoutParams(stopParams);
			isResize = false;
		}

		@Override
		public void onClose() {
			MySlidingLayer.this.onClose();
		}

		@Override
		public void onActionMove(float x, float y) {
			if (mScreenSide != ORIENTATION_CUSTOM_SUPPORT) {
				return;
			}
			if (x < 0) {
				setLayoutParams(fillParams);
				setVisibility(View.INVISIBLE);
				openLayer(false);
				isReset = true;
				isResize = true;
				mIsDragging = true;
			}
		}

		@Override
		public boolean onInterceptActionMove(float x, float y) {
			if (mScreenSide != ORIENTATION_CUSTOM_SUPPORT) {
				return false;
			}
			if (x < 0) {
				mIsDragging = true;
				mLastX = x;
				setDrawingCacheEnabled(true);
				return true;
			}
			return false;
		}
	};

	@Override
	public void setStickTo(int screenSide) {
		super.setStickTo(screenSide);
		if (screenSide == ORIENTATION_CUSTOM_SUPPORT) {
			setLayoutParams(stopParams);
		}
	}

	public void setStickTo(int screenSide, int widthStop) {
		super.setStickTo(screenSide);
		if (screenSide == ORIENTATION_CUSTOM_SUPPORT) {
			mWidthStop = widthStop;
			stopParams = new RelativeLayout.LayoutParams(mWidthStop,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			stopParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			setLayoutParams(stopParams);
		}

	}

	public boolean isResize() {
		return isResize;
	}

	public int getWidthStop() {
		return mWidthStop;
	}

	protected void onScrollTo(int x, int y) {
	}

	protected void onOpened() {
	}

	protected void onOpen() {
	}

	protected void onClosed() {
	}

	protected void onClose() {
	}

}
