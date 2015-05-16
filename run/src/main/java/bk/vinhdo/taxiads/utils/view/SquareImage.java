package bk.vinhdo.taxiads.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SquareImage extends ImageView {

    private boolean isAnimal = false;

    public SquareImage(Context context) {
        super(context);
        isAnimal = false;
    }

    public SquareImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        isAnimal = false;
    }

    public SquareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        isAnimal = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAnimal) {
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_HOVER_ENTER
                    || event.getAction() == MotionEvent.ACTION_MOVE) {
                setAlpha(130);
            } else {
                setAlpha(255);
            }
        }
        return super.onTouchEvent(event);
    }

    public boolean isMyAnimal() {
        return isAnimal;
    }

    public void setMyAnimal(boolean isAnimal) {
        this.isAnimal = isAnimal;
    }

}
