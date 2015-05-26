package bk.vinhdo.taxiads.activitis;

import android.os.ResultReceiver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.base.BaseActivity;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.config.ApiConfig;
import bk.vinhdo.taxiads.config.Key;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.ToastUtil;
import bk.vinhdo.taxiads.utils.text.ValidateUtil;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.email_edt)
    EditText mEmailEdt;

    @InjectView(R.id.password_edt)
    EditText mPasswordEdt;

    private String mEmail;
    private String mPassword;

    private String mAction;
    private ResultReceiver mResultReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        mResultReceiver = getIntent().getParcelableExtra(Key.EXTRA_RECEIVER);
        if(bundle != null){
            mAction = bundle.getString(ApiConfig.PARAM_ACTION);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
    }

    @Override
    public void setActionView() {

    }

    @Override
    protected void customHeaderView() {

    }

    @Override
    public void onLeftHeaderClick() {

    }

    @Override
    public void onRightHeaderClick() {
        finish();
    }

    @Override
    protected void initModels(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {

    }

    @OnClick(R.id.signin_btn)
    public void signin(){
        if(!isValidate()) return;
        RestClient.login(mEmail, mPassword, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                ToastUtil.show(response.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if(response.isSuccess()){
                    UserModel user = JSONConvert.getUser(response.getData());
                    user.create();
                    setResult(RESULT_OK);
                    finish();
                }else {
                    ToastUtil.show(response.getMessage());
                }
            }
        });
    }

    private boolean isValidate(){
        mEmail = mEmailEdt.getText().toString().trim();
        mPassword = mPasswordEdt.getText().toString().trim();

        mEmailEdt.setError(null);
        mPasswordEdt.setError(null);

        if (TextUtils.isEmpty(mEmail)) {
            mEmailEdt.setError(getString(R.string.error_enter_email));
            mEmailEdt.requestFocus();
            return false;
        }

        if (!ValidateUtil.getInstance().validateEmail(mEmail)) {
            mEmailEdt.setError(getString(R.string.error_enter_email_correct));
            mEmailEdt.requestFocus();
            return false;
        }


        if (TextUtils.isEmpty(mPassword)) {
            mPasswordEdt.setError(getString(R.string.error_enter_password));
            mPasswordEdt.requestFocus();
            return false;
        }
        return true;
    }
}
