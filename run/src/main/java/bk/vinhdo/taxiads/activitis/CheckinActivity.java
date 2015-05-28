package bk.vinhdo.taxiads.activitis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.config.ApiConfig;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.models.PostModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.utils.Log;
import bk.vinhdo.taxiads.utils.ToastUtil;
import bk.vinhdo.taxiads.utils.getimage.Action;
import bk.vinhdo.taxiads.utils.view.CustomEditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import simplecropimage.InternalStorageContentProvider;

public class CheckinActivity extends BaseActivity{

    @InjectView(R.id.image_checkin)
    ImageView mImageIv;

    @InjectView(R.id.content_edt)
    CustomEditText mContentEdt;

    private String mAddressId, mContent, mTitle;
    private File mImage;
    private boolean isHasFile = false;
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            mAddressId = bundle.getString(ApiConfig.PARAM_ADDRESS_ID);
            mTitle = bundle.getString(ApiConfig.PARAM_NAME);
        }
        setContentView(R.layout.layout_checkin, false);
        ButterKnife.inject(this);
    }

    @Override
    public void setActionView() {
        setVisibleRightImage(false);
        setVisibleLeftImage(true);
        setBackgroundLeftImage(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setVisibleRightText(true);
        setBackgroundRightText("POST", 0);
        setVisibleTitleImage(false);
        setVisibleTitleText(true);
        setBackgroundTitleText(mTitle, 0);
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            ToastUtil.show(getString(R.string.error));
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = getContentResolver().openInputStream(
                            data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            mImage);
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
//                String path = data.getStringExtra(CropImage.IMAGE_PATH);
//                if (path == null) {
//                    return;
//                }
//                bitmap = BitmapFactory.decodeFile(mAvatarFile.getPath());
//                mAvatarIv.setImageBitmap(bitmap);
                isHasFile = true;
                Bitmap thePic = data.getExtras().getParcelable("data");
                mImageIv.setImageBitmap(thePic);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void customHeaderView() {

    }

    @Override
    public void onLeftHeaderClick() {
        finish();
    }

    @Override
    public void onRightHeaderClick() {
        if(getCurrentUser() == null){
            return;
        }
        mContent = mContentEdt.getText().toString().trim();
        mContentEdt.setError(null);
        if(mContent.equals("")) {
            mContentEdt.setError("Enter comment here");
            return;
        }
//        if(mCurrentUser!= null)
        RestClient.createPost(getCurrentUser().getAccessToken(), mAddressId, mContent, isHasFile ? mImage : null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if (response.isSuccess()) {
                    ToastUtil.show("created");
                    Intent intent = new Intent();
                    PostModel post = JSONConvert.getPost(response.getData());
                    intent.putExtra("post", response.getData());
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    ToastUtil.show(response.getMessage());
                }
            }
        });
    }

    @Override
    protected void initModels(Bundle savedInstanceState) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mImage = new File(Key.FILE_TEMP_PATH);
        } else {
            mImage = new File(getFilesDir(), Key.FILE_TEMP_NAME);
        }
    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.image_checkin)
    public void imageCheckin(){
        showUpdateImageDialog();
    }

    /**
     * show update image dialog
     */
    private void showUpdateImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
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

    private void startCropImage() {

        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            Uri uri = Uri.fromFile(mImage);
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
            startActivityForResult(cropIntent, REQUEST_CODE_CROP_IMAGE);
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            ToastUtil.show(errorMessage);
        }


//        Intent intent = new Intent(this, CropImage.class);
//        intent.putExtra(CropImage.IMAGE_PATH, mAvatarFile.getPath());
//        intent.putExtra(CropImage.SCALE, true);
//
//        intent.putExtra(CropImage.ASPECT_X, 1);
//        intent.putExtra(CropImage.ASPECT_Y, 1);
//
//        //indicate output X and Y
//        intent.putExtra("outputX", 512);
//        intent.putExtra("outputY", 512);
//
//        //retrieve data on return
//        intent.putExtra("return-data", true);
//        //start the activity - we handle returnin
//
//        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    private void takePhoto() {
        // take photo
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mImage);
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
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Log.d("cannot take picture", e);
        }
    }

    private void getGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }
}
