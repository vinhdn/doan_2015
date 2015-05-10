package bk.vinhdo.taxiads.utils.text;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.util.Hashtable;

public class FontUtil {
    public static final String HELVETICA_CYRILLIC_BOLD = "Helvetica_CY_Bold.ttf";
    public static final String HELVETICA_NEUE_REGULAR = "HelveticaNeue.ttf";
    private static final String TAG = "Font";
    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(),
                            "fonts/" + assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e(TAG, "Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }

}
