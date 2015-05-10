package bk.vinhdo.taxiads.utils.getimage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.utils.view.SAutoBgImageButton;

/**
 * Created by Vinh on 2/9/15.
 */
public class RateUtil {
    /**
     * Returns pixel accurate dimensions for a metric in pixel density units
     *
     * @param dimensionDp
     * @param context
     * @return
     */
    public static int GetDimensionPx(int dimensionDp, Context context) {

        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dimensionDp * density + 0.5f);

    }

    /**
     * Utility function initalizes and sets up a view to display user rating 'stars' via provided parameters
     *
     * @param rating
     * @param container
     */
    public static void SetRatingView(float rating, View container, int height) {
        try {

            if (container != null) {
                LinearLayout layout = (LinearLayout) container;
                Context context = container.getContext();

                //Clear the target container/view
                layout.removeAllViews();

                //Initialize the target container/view and add star widget/view icons if requreid
                if (rating > 0) {
                    for (int i = 0; i < 5; i++) {

                        SAutoBgImageButton star = new SAutoBgImageButton(context);
                        star.setLayoutParams(new ViewGroup.LayoutParams(GetDimensionPx(height, context), GetDimensionPx(height, context)));
                        //star.setImageResource(i + 1 > scaledRating ? R.drawable.star_empty : R.drawable.star_full);
                        if (i < rating) {
                            if (i + 1 > rating) {
                                star.setBackgroundResource(R.drawable.star_half);
                            } else
                                star.setBackgroundResource(R.drawable.star_full);
                        } else
                            star.setBackgroundResource(R.drawable.star_empty);

                        layout.addView(star);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
