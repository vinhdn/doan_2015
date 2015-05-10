package bk.vinhdo.taxiads.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import bk.vinhdo.taxiads.R;

public class CustomEditText extends EditText {

	public CustomEditText(Context context) {
		super(context);
	}
	
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setCustomFont(context, attrs);
	}
	
	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setCustomFont(context, attrs);
	}
	
	/**
	 * 
	 * @param ctx
	 * @param attrs
	 */
	private void setCustomFont(Context ctx, AttributeSet attrs) {

		if (!isInEditMode()) {
			TypedArray a = ctx.obtainStyledAttributes(attrs,
					R.styleable.CustomView);

			// get font name
			String nameOfFont = a.getString(R.styleable.CustomView_customFont);

			// set default name of font
			if (nameOfFont == null) {
				nameOfFont = "Roboto-Regular.ttf";
			}

			setCustomFont(ctx, nameOfFont);

			a.recycle();
		}
	}

	/**
	 * set custom font for text view
	 * 
	 * @param ctx
	 * @param nameOfFont
	 * @return
	 */
	public boolean setCustomFont(Context ctx, String nameOfFont) {

		Typeface typeface = loadFont(ctx, "fonts/" + nameOfFont);

		if (typeface == null) {
			return false;
		}

		setTypeface(typeface);

		return true;
	}

	/**
	 * Load font
	 * 
	 * @param context
	 * @param pathOfFont
	 * @return
	 */
	private Typeface loadFont(Context context, String pathOfFont) {
		Typeface tf = null;
		try {
			tf = Typeface.createFromAsset(context.getAssets(), pathOfFont);
		} catch (Exception e) {
			Log.e("Custom Edit Text","Could not get typeface: " + e.getMessage());
		}
		return tf;
	}

}
