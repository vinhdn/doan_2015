package bk.vinhdo.taxiads.social;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookGraphResponseException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;

import bk.vinhdo.taxiads.R;

/**
 * Created by khanhnguyen on 4/11/15.
 */
public class FacebookManager {

    public static final List<String> READ_PERMISSIONS_FULL = Arrays.asList("public_profile", "email", "user_friends");
    public static final List<String> READ_PERMISSIONS = Arrays.asList("public_profile", "email");

    public static final List<String> PUBLISH_PERMISSIONS = Arrays.asList("publish_actions");
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;
    public static FacebookManager instance;
    LoginManager loginManager;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    Activity context;
    private boolean canPresentShareDialog;
    private boolean canPresentShareDialogWithPhotos;
    private ShareDialog shareDialog;
    private FacebookCallback<Sharer.Result> shareCallback =
            new FacebookCallback<Sharer.Result>() {
                @Override
                public void onCancel() {
//                    processDialogResults(null, true);
                }

                @Override
                public void onError(FacebookException error) {
                    if (error instanceof FacebookGraphResponseException) {
                        FacebookGraphResponseException graphError =
                                (FacebookGraphResponseException) error;
                        if (graphError.getGraphResponse() != null) {
                            handleError(graphError.getGraphResponse());
                            return;
                        }
                    }
                    processDialogError(error);
                }

                @Override
                public void onSuccess(Sharer.Result result) {
                    processDialogResults(result.getPostId(), false);
                }
            };

    public FacebookManager(Activity context) {
        this.context = context;

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();


//        accessTokenTracker = new AccessTokenTracker() {
//            @Override
//            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            }
//        };
//
//        profileTracker = new ProfileTracker() {
//            @Override
//            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
//            }
//        };

        shareDialog = new ShareDialog(context);
        shareDialog.registerCallback(callbackManager, shareCallback);

        // Can we present the share dialog for regular links?
        canPresentShareDialog = ShareDialog.canShow(
                ShareLinkContent.class);

        // Can we present the share dialog for photos?
        canPresentShareDialogWithPhotos = ShareDialog.canShow(
                SharePhotoContent.class);
    }

    public static FacebookManager getInstance(Activity context) {
        if (instance == null) instance = new FacebookManager(context);
        return instance;
    }

    public AccessToken getAccessToken() {
        return AccessToken.getCurrentAccessToken();
    }

    /**
     * You have to register callback first when you call login facebook
     *
     * @param callback
     */
    public void registerCallBack(FacebookCallback<LoginResult> callback) {
        loginManager.registerCallback(callbackManager, callback);
    }

    /**
     * You have to register callback first when you call login facebook
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
//        accessTokenTracker.stopTracking();
//        profileTracker.stopTracking();
    }

    public void logInWithReadPermissions(Activity activity) {
        loginManager.logInWithReadPermissions(activity, READ_PERMISSIONS);
    }

    public void logInWithReadPermissions(Fragment fragment) {
        loginManager.logInWithReadPermissions(fragment, READ_PERMISSIONS);
    }

    public void logInWithPublishPermissions(Activity activity) {
        loginManager.logInWithPublishPermissions(activity, PUBLISH_PERMISSIONS);
    }

    public void logInWithPublishPermissions(Fragment fragment) {
        loginManager.logInWithPublishPermissions(fragment, PUBLISH_PERMISSIONS);
    }

    public void logout() {
        loginManager.logOut();
    }

    private void processDialogResults(String postId, boolean isCanceled) {
        boolean resetSelections = true;
        if (isCanceled) {
            // Leave selections alone if user canceled.
            resetSelections = false;
            showCancelResponse();
        } else {
            showSuccessResponse(postId);
        }

//        if (resetSelections) {
//            init(null);
//        }
    }

    private void showSuccessResponse(String postId) {
        String dialogBody;
        if (postId != null) {
            dialogBody = String.format(context.getString(R.string.result_dialog_text_with_id), postId);
        } else {
            dialogBody = context.getString(R.string.result_dialog_text_default);
        }
        showResultDialog(dialogBody);
    }

    private void showCancelResponse() {
        showResultDialog(context.getString(R.string.result_dialog_text_canceled));
    }

    private void showResultDialog(String dialogBody) {
        new AlertDialog.Builder(context)
                .setPositiveButton(R.string.result_dialog_button_text, null)
                .setTitle(R.string.result_dialog_title)
                .setMessage(dialogBody)
                .show();
    }

    private void handleError(GraphResponse response) {
        FacebookRequestError error = response.getError();
        DialogInterface.OnClickListener listener = null;
        String dialogBody = null;

        if (error == null) {
            dialogBody = context.getString(R.string.error_dialog_default_text);
        } else {
            switch (error.getCategory()) {
                case LOGIN_RECOVERABLE:
                    // There is a login issue that can be resolved by the LoginManager.
                    // LoginManager.getInstance().resolveError(context, response);
                    return;

                case TRANSIENT:
                    dialogBody = context.getString(R.string.error_transient);
                    break;

                case OTHER:
                default:
                    // an unknown issue occurred, this could be a code error, or
                    // a server side issue, log the issue, and either ask the
                    // user to retry, or file a bug
                    dialogBody = context.getString(R.string.error_unknown, error.getErrorMessage());
                    break;
            }
        }

        String title = error.getErrorUserTitle();
        String message = error.getErrorUserMessage();
        if (message == null) {
            message = dialogBody;
        }
        if (title == null) {
            title = context.getResources().getString(R.string.error_dialog_title);
        }

        new AlertDialog.Builder(context)
                .setPositiveButton(R.string.error_dialog_button_text, listener)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    private void processDialogError(FacebookException error) {
        if (error != null) {
            new AlertDialog.Builder(context)
                    .setPositiveButton(R.string.error_dialog_button_text, null)
                    .setTitle(R.string.error_dialog_title)
                    .setMessage(error.getLocalizedMessage())
                    .show();
        }
    }

    private boolean hasPublishPermission() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null && accessToken.getPermissions().contains("publish_actions");
    }

    public void sharePhoto(Bitmap bitmap, FacebookCallback<Sharer.Result> callback) {
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap).build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .setContentUrl(Uri.parse("http://android.com"))
                .addPhoto(photo)
                .build();
        if (hasPublishPermission()) {
            ShareApi.share(content, callback);
        }else{
            logInWithPublishPermissions(this.context);
        }
    }

    public void postStatus(String title, String content, FacebookCallback<Sharer.Result> callback) {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(content)
                .setContentUrl(Uri.parse("http://128.199.69.38/doan/App/Android/"))
                .build();
        if (hasPublishPermission()) {
            ShareApi.share(linkContent, callback);
        } else {
            logInWithPublishPermissions(this.context);
        }
    }

    public void shareStatus(Activity activty, String title, String content, FacebookCallback<Sharer.Result> callback){
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentDescription(content)
                .setContentUrl(Uri.parse("http://128.199.69.38/doan/App/Android/"))
                .build();
        shareDialog.show(activty, linkContent);
    }

    public void sendMessageWithImage(Uri uri, Activity activity) {
        // The URI can reference a file://, content://, or android.resource. Here we use
        // android.resource for sample purposes.

        // Create the parameters for what we want to send to Messenger.
        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams.newBuilder(uri, "image/jpeg")
                        .setMetaData("{ \"image\" : \"tree\" }")
                        .build();

        MessengerUtils.shareToMessenger(
                activity,
                REQUEST_CODE_SHARE_TO_MESSENGER,
                shareToMessengerParams);
    }
}
