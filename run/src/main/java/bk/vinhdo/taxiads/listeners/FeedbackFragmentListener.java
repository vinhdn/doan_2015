package bk.vinhdo.taxiads.listeners;

import android.support.v4.app.DialogFragment;

/**
 * Created by vinhdo on 5/12/15.
 */
public interface FeedbackFragmentListener {
    void onCancel(DialogFragment dialog);

    void onSubmit(DialogFragment dialog);
}
