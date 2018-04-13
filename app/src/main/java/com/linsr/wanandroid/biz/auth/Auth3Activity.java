package com.linsr.wanandroid.biz.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.linsr.linlibrary.gui.widgets.LabelView;
import com.linsr.linlibrary.gui.widgets.TitleView;
import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.net.NetObserver;
import com.linsr.linlibrary.net.RxHelper;
import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseActivity;
import com.linsr.wanandroid.biz.login.LoginActivity;
import com.linsr.wanandroid.biz.main.MainActivity;
import com.linsr.wanandroid.data.local.WanPreferences;
import com.linsr.wanandroid.data.model.UserPojo;
import com.linsr.wanandroid.data.model.to.AuthVehicleTo;
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.utils.ToastUtils;
import com.linsr.wanandroid.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Description
 @author Linsr
 */

public class Auth3Activity extends BaseActivity {

    private AuthVehicleTo mVehicleTo;
    private int mGenderInt;

    @BindView(R.id.auth3_title_view)
    TitleView mTitleView;
    @BindView(R.id.auth3_user_name)
    LabelView mUserName;
    @BindView(R.id.auth3_gender)
    LabelView mGender;
    @BindView(R.id.auth3_idcard)
    LabelView mIdcard;
    @BindView(R.id.auth3_vehicle_num)
    LabelView mVehicleNum;
    @BindView(R.id.auth3_vehicle_mode)
    LabelView mVehicleMode;
    @BindView(R.id.auth3_vehicle_len)
    LabelView mVehicleLen;
    @BindView(R.id.auth3_vehicle_load)
    LabelView mVehicleLoad;
    @BindView(R.id.auth3_vehicle_brand)
    LabelView mVehicleBrand;
    @BindView(R.id.auth3_vehicle_date)
    LabelView mVehicleDate;

    @Override
    protected void init(Bundle savedInstanceState) {
        mTitleView.setOnLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mVehicleTo = (AuthVehicleTo) getIntent().getSerializableExtra("auth");
        mUserName.setEditContentText(mVehicleTo.getUserName());
        if (mVehicleTo.getGender() != null) {
            mGenderInt = mVehicleTo.getGender();
            mGender.setContentText(mVehicleTo.getGender() == 1 ? "男" : "女");
        }
        mIdcard.setEditContentText(mVehicleTo.getIdcard());
        mVehicleNum.setEditContentText(mVehicleTo.getVehicleNum());
        mVehicleMode.setEditContentText(mVehicleTo.getVehicleMode());
        mVehicleLen.setEditContentText(mVehicleTo.getVehicleLength());
        mVehicleLoad.setEditContentText(mVehicleTo.getVehicleLoad());
        mVehicleBrand.setEditContentText(mVehicleTo.getVehicleBrand());
        mVehicleDate.setEditContentText(mVehicleTo.getVehicleDate());

        mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_sex();
            }
        });

        mVehicleLoad.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mVehicleLen.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @OnClick(R.id.auth3_next)
    void onNext() {
        String name = mUserName.getEditContentText();
        String idcard = mIdcard.getEditContentText();
        String num = mVehicleNum.getEditContentText();
        String mode = mVehicleMode.getEditContentText();
        String length = mVehicleLen.getEditContentText();
        String load = mVehicleLoad.getEditContentText();
        String brand = mVehicleBrand.getEditContentText();
        String date = mVehicleDate.getEditContentText();
        if (!Utils.isNotEmpty(name, idcard, num, mode, length, load, brand, date)
                || mGenderInt == 0) {
            ToastUtils.toast(this, "请完善信息");
            return;
        }

        UserPojo userPojo = new UserPojo();
        userPojo.setUser_name(name);
        userPojo.setGender(mGenderInt);
        userPojo.setId_card(idcard);
        userPojo.setVehicle_number(num);
        userPojo.setVehicle_mode(mode);
        userPojo.setVehicle_length(Double.valueOf(length));
        userPojo.setVehicle_load(Double.valueOf(load));
        userPojo.setVehicle_brand(brand);
        userPojo.setVehicle_production_date(date);
        userPojo.setUser_code(mUserCode);

        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json"),
                gson.toJson(userPojo));
        Api.request().vehicleinfo(body)
                .compose(RxHelper.<ResponsePojo>ioMain())
                .compose(RxHelper.handleResponse1())
                .subscribe(new NetObserver<ResponsePojo>() {
                    @Override
                    public void onSucceed(ResponsePojo data) {
                        JLog.i("success");
                        WanPreferences.getInstance().setUserAuthStatus("b");
                        Intent intent = new Intent(Auth3Activity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(String msg) {
                        JLog.e(msg);
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auth_3;
    }

    public void change_sex() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //定义一个AlertDialog
        String[] strarr = {"男", "女"};
        builder.setItems(strarr, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                String sex = "2";
                // 自动生成的方法存根
                String gender;
                if (arg1 == 0) {//男
                    sex = "1";
                    gender = "男";
                } else {//女
                    sex = "2";
                    gender = "女";
                }
                mGenderInt = Integer.parseInt(sex);
                mGender.setContentText(gender);
            }
        });
        builder.show();
    }
}
