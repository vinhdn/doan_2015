package bk.vinhdo.taxiads.social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public interface FacebookState {

    public void onCreate(Bundle savedInstanceState);

    public void onStart();

    public void onStop();

    public void onSaveInstanceState(Bundle outState);

    public void onActivityResult(Activity activity, int requestCode,
                                 int resultCode, Intent data);

    public void login(Activity activity);

    public void logout();

}
