package bk.vinhdo.taxiads.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import bk.vinhdo.taxiads.R;
import bk.vinhdo.taxiads.activitis.ActivityAddress;
import bk.vinhdo.taxiads.activitis.SlideMenuActivity;
import bk.vinhdo.taxiads.adapters.AddressInListAdapter;
import bk.vinhdo.taxiads.models.Address;
import bk.vinhdo.taxiads.models.AddressModel;
import bk.vinhdo.taxiads.utils.view.CustomTextView;
import bk.vinhdo.taxiads.utils.view.SAutoBgImageButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by vinhdo on 5/27/15.
 */
public class SavedAddressFragment extends BaseFragment{
    @InjectView(R.id.list_address_lv)
    ListView mAddressLv;

    private View mNavigateView;
    private CustomTextView mLeftText;
    private CustomTextView mRightText;
    private CustomTextView mTitleText;
    private SAutoBgImageButton mLeftImage;
    private SAutoBgImageButton mRightImage;
    private RelativeLayout mLeftBarLayout;
    private RelativeLayout mRightBarLayout;
    private ImageView mTitleImage;

    private List<AddressModel> mData = new ArrayList<>();
    private AddressInListAdapter mAdapter;

    public SavedAddressFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_address, container, false);
        initHeaderView(view);
        ButterKnife.inject(this, view);
        return view;
    }

    protected void initHeaderView(View view) {
        mNavigateView = view.findViewById(R.id.navigate_bar);
//        mLLBG = (LinearLayout)view.findViewById(R.id.bg_ll);
//        Bitmap mBitmapBg = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.hn_bg);
//        BitmapDrawable drawableBg = new BitmapDrawable(BitmapUtil.fastblur(getActivity(),mBitmapBg,16));
//        mLLBG.setBackgroundDrawable(drawableBg);
//        final Drawable cd = getResources().getDrawable(R.drawable.ab_background_light);
//        mNavigateView.setBackgroundDrawable(cd);
//        cd.setAlpha(100);
        mLeftText = (CustomTextView) view.findViewById(R.id.left_text);
        mLeftText.setVisibility(View.GONE);

        mLeftImage = (SAutoBgImageButton) view.findViewById(R.id.left_image);
        mLeftImage.setVisibility(View.VISIBLE);
        mLeftImage.setBackgroundResource(R.drawable.ic_white_menu_nar);
        mRightText = (CustomTextView) view.findViewById(R.id.right_text);
        mRightText.setVisibility(View.GONE);
        mRightText.setText("EDIT");
        mRightImage = (SAutoBgImageButton) view.findViewById(R.id.right_image);
        mRightImage.setVisibility(View.GONE);

        mTitleText = (CustomTextView) view.findViewById(R.id.title_text);
        mTitleText.setText("Saved Addresses");
        mTitleText.setVisibility(View.VISIBLE);
        mTitleText.setBackgroundResource(android.R.color.transparent);

        mTitleImage = (ImageView) view.findViewById(R.id.logo_image);
        mTitleImage.setVisibility(View.GONE);

        mLeftBarLayout = (RelativeLayout) view.findViewById(R.id.left_layout);

        mRightBarLayout = (RelativeLayout) view.findViewById(R.id.right_layout);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getSavedAddress();
    }

    @OnClick(R.id.left_image)
    public void leftImage(){
        ((SlideMenuActivity) getActivity()).openMenu();
    }

    @OnClick(R.id.right_image)
    public void rightImage(){
        //TODO Show screen edit profile
//        Intent i = new Intent(getActivity(), MapsActivity.class);
//        i.putExtra(Key.EXTRA_ACTION,Key.KEY_NEARBY);
//        startActivity(i);
    }

    public void getSavedAddress() {
        mData = (List<AddressModel>)AddressModel.getAll(AddressModel.class);
        if(mData == null){
            return;
        }
        mAdapter = new AddressInListAdapter(getActivity(), mData);
        mAddressLv.setAdapter(mAdapter);
        mAddressLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ActivityAddress.class);
                i.putExtra("id_address",mData.get(position).getId());
                i.putExtra("distance", mData.get(position).getDistance());
                getActivity().startActivity(i);
            }
        });
    }
}
