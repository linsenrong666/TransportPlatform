package com.linsr.wanandroid.biz.auth;

import android.app.Activity;
import android.content.Intent;

import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.net.NetObserver;
import com.linsr.linlibrary.net.RxHelper;
import com.linsr.wanandroid.data.local.WanPreferences;
import com.linsr.wanandroid.data.model.CommonVo;
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description
 @author Linsr
 */

public class AuthPresenter implements AuthContact.Presenter {

    private WanPreferences mWanPreferences;
    private AuthContact.View mView;
    private Activity mActivity;

    private String mPhone;
    private String mUserCode;

    private String mPlateNum;

    public AuthPresenter(AuthContact.View view, Activity activity) {
        mView = view;
        mActivity = activity;
        mWanPreferences = WanPreferences.getInstance();
        mPhone = mWanPreferences.getUserPhone();
        mUserCode = mWanPreferences.getUserCode();

    }

    @Override
    public void sendToNext(Map<String, String> map) {
        mActivity.startActivity(new Intent(mActivity, Auth2Activity.class));
    }

    @Override
    public void driving(String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.toast(mActivity, "请上传驾驶证");
            return;
        }
        try {
            file = new Compressor(mActivity).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Api.request().driving(mPhone, mUserCode, body)
                .compose(RxHelper.<ResponsePojo<CommonVo>>ioMain())
                .compose(RxHelper.<CommonVo>handleResponse())
                .subscribe(new NetObserver<CommonVo>(mActivity, true) {
                    @Override
                    public void onSucceed(CommonVo data) {
                        mView.hideDrivingLayout();
                        mWanPreferences.setUserDriving(data.getParam1());
                        ToastUtils.toast(mActivity, "驾驶证验证成功");
                        mView.checkStatus();
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                    }
                });
    }

    @Override
    public void vehicle(String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.toast(mActivity, "请上传行驶证");
            return;
        }
        try {
            file = new Compressor(mActivity).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Api.request().vehicle(mPhone, mUserCode, body)
                .compose(RxHelper.<ResponsePojo<CommonVo>>ioMain())
                .compose(RxHelper.<CommonVo>handleResponse())
                .subscribe(new NetObserver<CommonVo>(mActivity, true) {
                    @Override
                    public void onSucceed(CommonVo data) {
                        mView.hideVehicleLayout();
                        mPlateNum = data.getParam2();
                        String license = data.getParam1();
                        mWanPreferences.setUserVehicle(license);
                        ToastUtils.toast(mActivity, "行驶证验证成功");
                        mView.checkStatus();
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                    }
                });
    }

    @Override
    public void safeform(String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.toast(mActivity, "请上传保单");
            return;
        }
        try {
            file = new Compressor(mActivity).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Api.request().safeform(mPhone, mUserCode, body)
                .compose(RxHelper.<ResponsePojo>ioMain())
                .compose(RxHelper.handleResponse1())
                .subscribe(new NetObserver<ResponsePojo>(mActivity, true) {
                    @Override
                    public void onSucceed(ResponsePojo data) {
                        mView.hideSafeFormLayout();
                        mWanPreferences.setUserSafeForm(true);
                        ToastUtils.toast(mActivity, "保单上传成功");
                        mView.checkStatus();
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                    }
                });
    }

    @Override
    public void idcard(String path) {
        File file = new File(path);
        if (!file.exists()) {
            ToastUtils.toast(mActivity, "请上身份证");
            return;
        }
        try {
            file = new Compressor(mActivity).compressToFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Api.request().idcard(mPhone, mUserCode, body)
                .compose(RxHelper.<ResponsePojo<CommonVo>>ioMain())
                .compose(RxHelper.<CommonVo>handleResponse())
                .subscribe(new NetObserver<CommonVo>(mActivity, true) {
                    @Override
                    public void onSucceed(CommonVo data) {
                        mView.hideIdcardLayout();
                        mWanPreferences.setUserIdCard(data.getParam1());
                        ToastUtils.toast(mActivity, "身份证验证成功");
                        mView.checkStatus();
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                    }
                });
    }

}
