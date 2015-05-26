package bk.vinhdo.taxiads.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import bk.vinhdo.taxiads.TaxiApplication;
import bk.vinhdo.taxiads.listeners.AlertListener;
import bk.vinhdo.taxiads.models.UserModel;

/**
 * Created by vinhdo on 5/10/15.
 */
public abstract class BaseFragment extends Fragment{

    protected UserModel mRidesUser;
    protected TaxiApplication mApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = (TaxiApplication) getActivity().getApplication();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Update details user
        mRidesUser = UserModel.getCurrentUser();
    }

    public UserModel getCurrentUser() {
        if(mRidesUser == null){
            mRidesUser = UserModel.getCurrentUser();
        }
        return mRidesUser;
    }

    public void showConfirmDialog(Context context, String title, String message, final AlertListener alertListener) {
        new AlertDialog.Builder(context).setTitle(title)
                .setCancelable(false).setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        alertListener.onPositiveButton(dialog);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                alertListener.onNegativeButton(dialog);
            }
        }).show();
    }
}
