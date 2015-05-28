package bk.vinhdo.taxiads.service;

import android.app.IntentService;
import android.content.Intent;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;

import bk.vinhdo.taxiads.TaxiApplication;
import bk.vinhdo.taxiads.api.loopj.RestClient;
import bk.vinhdo.taxiads.api.parse.JSONConvert;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.models.ResponseModel;
import bk.vinhdo.taxiads.models.UserModel;
import bk.vinhdo.taxiads.utils.Log;

/**
 * Created by vinhdo on 5/29/15.
 */
public class AppIntentService extends IntentService{

    public static final String ACTION_GET_ADDRESS_SAVED = "ACTION_GET_ADDRESS_SAVED";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public AppIntentService() {
        super("AppIntentService");
    }

    public static void startActionGetListSaved() {
        Intent intent = new Intent(TaxiApplication.getAppContext(), AppIntentService.class);
        intent.setAction(ACTION_GET_ADDRESS_SAVED);
        TaxiApplication.getAppContext().startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.d("action: " + action);

            if (action.equals(ACTION_GET_ADDRESS_SAVED)) {
                handleActionGetListSaved();
            }
        }
    }

    private void handleActionGetListSaved() {
        UserModel user = UserModel.getCurrentUser();
        List<AddressModel> addesss = (List<AddressModel>)AddressModel.getAll(AddressModel.class);
        if(addesss != null && addesss.size() > 0) return;
        if(user == null) return;
        RestClient.synchronize().getListSaved(user.getAccessToken(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ResponseModel response = JSONConvert.getResponse(responseString);
                if(response.isSuccess()){
                    List<AddressModel> listsSave = JSONConvert.getAddresses(response.getData());
                    for (AddressModel address : listsSave){
                        try {
                            address.create();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
