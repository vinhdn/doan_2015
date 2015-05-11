package me.vinhdo.getvenues.utils;

import android.widget.Toast;

import me.vinhdo.getvenues.TaxiApplication;


public class ToastUtil {

    public static void show(String text) {
        Toast.makeText(TaxiApplication.getAppContext(), text, Toast.LENGTH_LONG).show();
    }

    public static void show(int textId) {
        Toast.makeText(TaxiApplication.getAppContext(), TaxiApplication.getAppContext().getString(textId), Toast.LENGTH_LONG).show();
    }

}
