package com.linsr.wanandroid.biz.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import com.linsr.linlibrary.gui.widgets.LabelView;
import com.linsr.linlibrary.gui.widgets.TitleView;
import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.net.NetObserver;
import com.linsr.linlibrary.net.RxHelper;
import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseActivity;
import com.linsr.wanandroid.data.model.UserPojo;
import com.linsr.wanandroid.data.model.to.AuthVehicleTo;
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.utils.ToastUtils;
import com.linsr.wanandroid.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 @author Linsr
 */

public class Auth2Activity extends BaseActivity {

    @BindView(R.id.auth2_title_view)
    TitleView mTitleView;

    @BindView(R.id.auth2_next)
    View mNextButton;
    @BindView(R.id.auth2_vehicle_num)
    LabelView mVehicleNum;
    @BindView(R.id.auth2_vehicle_mode)
    LabelView mVehicleMode;
    @BindView(R.id.auth2_vehicle_length)
    LabelView mVehicleLength;
    @BindView(R.id.auth2_vehicle_load)
    LabelView mVehicleLoad;
    @BindView(R.id.auth2_vehicle_brand)
    LabelView mVehicleBrand;
    @BindView(R.id.auth2_vehicle_date)
    LabelView mVehicleDate;

    private String mUserName, mIdcard;
    private Integer mGender;

    @Override
    protected void init(Bundle savedInstanceState) {
        mTitleView.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mVehicleLoad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mVehicleLength.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        startRequest();
    }

    private void startRequest() {
        Api.request().getUserInfo(mUserCode)
                .compose(RxHelper.<ResponsePojo<UserPojo>>ioMain())
                .compose(RxHelper.<UserPojo>handleResponse())
                .subscribe(new NetObserver<UserPojo>() {
                    @Override
                    public void onSucceed(UserPojo data) {
                        mUserName = data.getUser_name();
                        mGender = data.getGender();
                        mIdcard = data.getId_card();
                        mVehicleBrand.setEditContentText(data.getVehicle_brand());
                        mVehicleNum.setEditContentText(data.getVehicle_number());
                    }

                    @Override
                    public void onFailed(String msg) {
                        JLog.e(msg);
                    }
                });
    }

    @OnClick(R.id.auth2_next)
    void onFinish() {
        String num = mVehicleNum.getEditContentText();
        String mode = mVehicleMode.getEditContentText();
        String length = mVehicleLength.getEditContentText();
        String load = mVehicleLoad.getEditContentText();
        String brand = mVehicleBrand.getEditContentText();
        String date = mVehicleDate.getEditContentText();
        if (Utils.isNotEmpty(num, mode, length, load, brand, date)) {
            AuthVehicleTo to = new AuthVehicleTo(num, mode, length, load, brand, date);
            to.setGender(mGender);
            to.setIdcard(mIdcard);
            to.setUserName(mUserName);
            Intent intent = new Intent(this, Auth3Activity.class);
            intent.putExtra("auth", to);
            startActivity(intent);
        } else {
            ToastUtils.toast(this, "请完善信息");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auth_2;
    }

}
