package bk.vinhdo.taxiads.models.utils;

public class DeviceInfoModel {

	public int mWidth;
	public int mHeight;
	public int mOrientation;
	public boolean mIsTablet;

	public DeviceInfoModel() {
		super();
	}

	public DeviceInfoModel(int mWidth, int mHeight, int mOrientation) {
		super();
		this.mWidth = mWidth;
		this.mHeight = mHeight;
		this.mOrientation = mOrientation;
	}

	public DeviceInfoModel(int mWidth, int mHeight, int mOrientation,
			boolean mIsTablet) {
		super();
		this.mWidth = mWidth;
		this.mHeight = mHeight;
		this.mOrientation = mOrientation;
		this.mIsTablet = mIsTablet;
	}

	@Override
	public String toString() {
		return "DeviceInfo [mWidth=" + mWidth + ", mHeight=" + mHeight
				+ ", mOrientation=" + mOrientation + ", mIsTablet=" + mIsTablet
				+ "]";
	}

}
