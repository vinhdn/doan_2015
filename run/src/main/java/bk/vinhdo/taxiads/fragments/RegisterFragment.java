package bk.vinhdo.taxiads.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.Log;
import bk.vinhdo.taxiads.utils.ToastUtil;
import bk.vinhdo.taxiads.utils.text.StringUtil;
import bk.vinhdo.taxiads.utils.text.ValidateUtil;
import bk.vinhdo.taxiads.utils.view.CircleImage;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.MySlidingLayer;
import bk.vinhdo.taxiads.utils.view.SlidingLayer;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import simplecropimage.InternalStorageContentProvider;

/**
 * Created by vinhdo on 5/26/15.
 */
public class RegisterFragment extends MySlidingLayer {

    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;

    @InjectView(R.id.image_avatar)
    CircleImage mAvatar;

    @InjectView(R.id.firstname_edt)
    EditText mFirstNameEdt;

    @InjectView(R.id.lastname_edt)
    EditText mLastNameEdt;

    @InjectView(R.id.email_edt)
    EditText mEmailEdt;

    @InjectView(R.id.password_edt)
    EditText mPasswordEdt;

    @InjectView(R.id.repassword_edt)
    EditText mRePasswordEdt;

    @InjectView(R.id.signup_checkbox)
    ImageView mCheckAgree;

    @InjectView(R.id.birthday_tv)
    TextView mBirthDayTv;

    String mDob;
    File mAvatarFile;
    String mFirstName, mLastName, mEmail, mPassword, mRePassword;
    boolean isAvatar;
    Activity mActivity;
    long mBirthday;
    boolean isTick;
    Calendar mBirthCalendar = Calendar.getInstance();

    public RegisterFragment(Activity context) {
        super(context);
        mActivity = context;
        View signupView = LayoutInflater.from(context).inflate(R.layout.layout_register, null, false);
        setStickTo(SlidingLayer.STICK_TO_TOP);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.navigate_bar);
        addView(signupView);
        setCloseOnTapEnabled(false);
        setLayoutParams(lp);
        setSlidingEnabled(false);
        ButterKnife.inject(this, signupView);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mAvatarFile = new File(Key.FILE_TEMP_PATH);
        } else {
            mAvatarFile = new File(mActivity.getFilesDir(), Key.FILE_TEMP_NAME);
        }
    }

    @Override
    protected void onClose() {
        super.onClose();
    }

    @OnClick(R.id.signup_btn)
    public void signup(){
        if(!isValidate()) return;
        RestClient.register(mEmail, mFirstName, mLastName,mBirthday, mPassword, mAvatarFile, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.show("Have a error in create account. Please try again later.");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if (response.isSuccess()) {
                    UserModel user = JSONConvert.getUser(response.getData());
                    user.create();
                    ToastUtil.show("Create account success");
                    Intent intent = mActivity.getIntent();
                    mActivity.finish();
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @OnClick(R.id.image_avatar)
    public void avatar(){
        showUpdateImageDialog();
    }

    @OnClick(R.id.siggup_checkLl)
    public void checkAgree(){
        if(isTick) {
            mCheckAgree.setBackgroundResource(R.drawable.checkmark_gray);
        }else{
            mCheckAgree.setBackgroundResource(R.drawable.checkmark_pink);
        }
        isTick = !isTick;
    }

    @OnClick(R.id.birthday_tv)
    public void selectBirthday(){
//            DialogFragment newFragment = new DatePickerFragment();
//            newFragment.show(mActivity.getSupportFragmentManager(), "datePicker");
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                mBirthCalendar.set(year, month, day);
                mBirthday = mBirthCalendar.getTimeInMillis()/1000;
                mDob = StringUtil.convertCalendarToString(mBirthCalendar, StringUtil.DATE_FORMAT_11);
                mBirthDayTv.setText(mDob);
            }
        },year, month, day);

        datePicker.show();
    }

    /**
     * show update image dialog
     */
    private void showUpdateImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                mActivity);
        builder.setTitle(R.string.choose_a_picture).setItems(
                R.array.type_image, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                dialog.dismiss();
                                takePhoto();
                                break;
                            case 1:
                                dialog.dismiss();
                                getGallery();
                                break;
                            case 2:
                                // cancel
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takePhoto() {
        // take photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mAvatarFile);
            } else {
                /*
                 * The solution is taken from here:
				 * http://stackoverflow.com/questions
				 * /10042695/how-to-get-camera-result-as-a-uri-in-data-folder
				 */
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            intent.putExtra("return-data", true);
            mActivity.startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Log.d("cannot take picture", e);
        }
    }

    private void getGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        mActivity.startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }

    private void startCropImage() {

        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            Uri uri = Uri.fromFile(mAvatarFile);
            cropIntent.setDataAndType(uri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 512);
            cropIntent.putExtra("outputY", 512);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            mActivity.startActivityForResult(cropIntent, REQUEST_CODE_CROP_IMAGE);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            ToastUtil.show(errorMessage);
        }
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = mActivity.getContentResolver().openInputStream(
                            data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            mAvatarFile);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    startCropImage();
                } catch (Exception e) {
                    Log.e("Error while creating temp file", e);
                }
                break;
            case REQUEST_CODE_TAKE_PICTURE:
                startCropImage();
                break;
            case REQUEST_CODE_CROP_IMAGE:
                Bitmap thePic = data.getExtras().getParcelable("data");
                mAvatar.setImageBitmap(thePic);
                isAvatar = true;
                break;
        }
    }

    private boolean isValidate(){
        mFirstName = mFirstNameEdt.getText().toString().trim();
        mLastName = mLastNameEdt.getText().toString().trim();
        mEmail = mEmailEdt.getText().toString().trim();
        mPassword = mPasswordEdt.getText().toString().trim();
        mRePassword = mRePasswordEdt.getText().toString().trim();

        mFirstNameEdt.setError(null);
        mLastNameEdt.setError(null);
        mEmailEdt.setError(null);
        mPasswordEdt.setError(null);
        mRePasswordEdt.setError(null);

        if(TextUtils.isEmpty(mFirstName)){
            mFirstNameEdt.setError("Please enter your first name.");
            return false;
        }

        if(TextUtils.isEmpty(mLastName)){
            mLastNameEdt.setError("Please enter your last name.");
            return false;
        }

        if(TextUtils.isEmpty(mEmail) || !ValidateUtil.getInstance().validateEmail(mEmail)){
            mEmailEdt.setError("Please enter your email.");
            return false;
        }

        if(TextUtils.isEmpty(mDob)){
            ToastUtil.show("Please select your birthday.");
            return false;
        }

        if(TextUtils.isEmpty(mPassword) || mPassword.length() < 6){
            mPasswordEdt.setError("Please enter your password with >= 6 character.");
            return false;
        }

        if(TextUtils.isEmpty(mRePassword) || !mPassword.equals(mRePassword)){
            mFirstNameEdt.setError("Your password do not match. Please try again.");
            return false;
        }

        if (!isAvatar || !mAvatarFile.isFile() || !mAvatarFile.exists()) {
            ToastUtil.show("This avatar is error, try again.");
            return false;
        }

        if(!isTick){
            ToastUtil.show("Your must agree with your profile.");
            return false;
        }

        return true;
    }

}
