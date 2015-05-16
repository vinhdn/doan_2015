package bk.vinhdo.taxiads.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import bk.vinhdo.taxiads.R;

public class CircleImage extends ImageView {

    private int borderWidth = 3;
    private int shadowWidth = 3;
    private int viewWidth;
    private int viewHeight;
    private Bitmap image;
    private Paint paint;
    private Paint paintBorder;
    private Paint paintShadow;
    private BitmapShader shader;

    public CircleImage(Context context) {
        super(context);
        setup();
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs);
    }

    private void setup() {
        // init paint
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        paintBorder = new Paint();
        setBorderColor(Color.WHITE);
        paintBorder.setAntiAlias(true);

        paintShadow = new Paint();
        paintShadow.setColor(Color.parseColor("#AA000000"));
        paintShadow.setAntiAlias(true);
    }

    private void setup(Context context, AttributeSet attrs) {
        // init paint
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        paintBorder = new Paint();
        setBorderColor(Color.WHITE);
        paintBorder.setAntiAlias(true);

        paintShadow = new Paint();
        paintShadow.setColor(Color.parseColor("#AA000000"));
        paintShadow.setAntiAlias(true);

        if (!isInEditMode()) {
            TypedArray a;
            a = context.obtainStyledAttributes(attrs,
                    R.styleable.CircleImage);
            borderWidth = a.getInt(R.styleable.CircleImage_borderWidth, 3);
            shadowWidth = a.getInt(R.styleable.CircleImage_shadowWidth, 3);
            a.recycle();
        }

    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        this.invalidate();
    }

    public void setBorderColor(int borderColor) {
        if (paintBorder != null)
            paintBorder.setColor(borderColor);
        this.invalidate();
    }

    private void loadBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();

        if (bitmapDrawable != null) {
            image = bitmapDrawable.getBitmap();
        }
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        // load the bitmap
        loadBitmap();

        // init shader
        if (image != null) {
            image = scaleCenterCrop(image, viewHeight, viewWidth);
            int circleCenter = viewWidth / 2;
            // Create a shader with a scaled bitmap to match the view dimensions
            shader = new BitmapShader(image, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
            paint.setShader(shader);
            RadialGradient radialGradientShader = new RadialGradient(
                    circleCenter + borderWidth + shadowWidth, circleCenter
                    + borderWidth + shadowWidth, circleCenter
                    + borderWidth + shadowWidth, Color.BLACK,
                    Color.TRANSPARENT, Shader.TileMode.MIRROR
            );
            paintShadow.setShader(radialGradientShader);

            canvas.drawCircle(circleCenter + borderWidth + shadowWidth,
                    circleCenter + borderWidth + shadowWidth, circleCenter
                            + borderWidth + shadowWidth, paintShadow
            );

            // Draw the outer border
            canvas.drawCircle(circleCenter + borderWidth + shadowWidth,
                    circleCenter + borderWidth + shadowWidth, circleCenter
                            + borderWidth, paintBorder
            );
            // circleCenter is the x or y of the view's center
            // radius is the radius in pixels of the cirle to be drawn
            // paint contains the shader that will texture the shape
            canvas.drawCircle(circleCenter + borderWidth + shadowWidth,
                    circleCenter + borderWidth + shadowWidth, circleCenter,
                    paint);
        }
    }

    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();

        // Compute the scaling factors to fit the new height and width,
        // respectively.
        // To cover the final image, the final scaling will be the bigger
        // of these two.
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.max(xScale, yScale);

        // Now get the size of the source bitmap when scaled
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        // Let's find out the upper left coordinates if the scaled bitmap
        // should be centered in the new size give by the parameters
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        // The target rectangle for the new, scaled version of the source bitmap
        // will now
        // be
        RectF targetRect = new RectF(left, top, left + scaledWidth, top
                + scaledHeight);

        // Finally, we create a new bitmap of the specified size and draw our
        // new,
        // scaled bitmap onto it.
        if (source.getConfig() == null) {
            return Bitmap.createBitmap(image);
        }
        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight,
                source.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(source, null, targetRect, null);

        return dest;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec, widthMeasureSpec);

        Log.d("Circle Image", width + " " + height);

        viewWidth = width - (borderWidth * 2 + shadowWidth * 2);
        viewHeight = height - (borderWidth * 2 + shadowWidth * 2);

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = viewWidth;

        }

        return result;
    }

    private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            result = viewHeight;
        }
        return result;
    }
}
