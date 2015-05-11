package me.vinhdo.getvenues;

import android.app.Application;
import android.content.Context;

/**
 * Created by Vinh on 1/21/15.
 */
public class TaxiApplication extends Application {

    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return mContext;
    }

}
