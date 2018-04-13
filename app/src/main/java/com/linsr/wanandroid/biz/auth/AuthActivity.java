package com.linsr.wanandroid.biz.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linsr.linlibrary.gui.dialogs.DialogFactory;
import com.linsr.linlibrary.gui.dialogs.TakePhotoDialog;
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
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.utils.DateUtils;
import com.linsr.wanandroid.utils.FileUtils;
import com.linsr.wanandroid.utils.ToastUtils;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 @author Linsr
 */

public class AuthActivity extends BaseActivity implements
        TakePhoto.TakeResultListener, InvokeListener, AuthContact.View {

    private TakePhoto mTakePhoto;
    private DialogFactory mDialogFactory;
    private int mClickId;
    private TakePhoto takePhoto;
    private Map<String, String> mImageList;
    private AuthPresenter mAuthPresenter;

    @BindView(R.id.auth_title_View)
    TitleView mTitleView;
    @BindView(R.id.auth_vehicle_license_iv)
    ImageView mVehicleImageView;
    @BindView(R.id.auth_driving_license_iv)
    ImageView mDrivingImageView;
    @BindView(R.id.auth_id_card_iv)
    ImageView mIdCardImageView;
    @BindView(R.id.auth_safe_form_iv)
    ImageView mSafeFormImageView;
    @BindView(R.id.auth_title_driving)
    TextView mTitleDriving;
    @BindView(R.id.auth_driving_layout)
    View mDrivingLayout;
    @BindView(R.id.auth_title_vehicle)
    TextView mTitleVehicle;
    @BindView(R.id.auth_vehicle_layout)
    View mVehicleLayout;
    @BindView(R.id.auth_title_idcard)
    TextView mTitleIdcard;
    @BindView(R.id.auth_idcard_layout)
    View mIdcardLayout;
    @BindView(R.id.auth_title_safeform)
    TextView mTitleSafeform;
    @BindView(R.id.auth_safeform_layout)
    View mSafefromLayout;
    @BindView(R.id.auth_next)
    View mNextButton;

    @Override
    protected void init(Bundle savedInstanceState) {
        mTitleView.setRightText("注销");
        mTitleView.setOnRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api.request().logout(mUserCode)
                        .compose(RxHelper.<ResponsePojo<Object>>ioMain())
                        .compose(RxHelper.handleResponse())
                        .subscribe(new NetObserver<Object>() {
                            @Override
                            public void onSucceed(Object data) {
                                ToastUtils.toast(AuthActivity.this, "注销成功");
                                WanPreferences.getInstance().logout();
                                Intent intent = new Intent(AuthActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailed(String msg) {
                                ToastUtils.toast(AuthActivity.this, "登出失败");
                            }
                        });
            }
        });
        Api.request().getUserInfo(mUserCode)
                .compose(RxHelper.<ResponsePojo<UserPojo>>ioMain())
                .compose(RxHelper.<UserPojo>handleResponse())
                .subscribe(new NetObserver<UserPojo>(this, true) {
                    @Override
                    public void onSucceed(UserPojo data) {
                        WanPreferences wanPreferences = WanPreferences.getInstance();
                        wanPreferences.setUserDriving(data.getDriving_license());
                        wanPreferences.setUserIdCard(data.getId_card());
                        wanPreferences.setUserVehicle(data.getVehicle_license());
                        checkStatus();
                    }

                    @Override
                    public void onFailed(String msg) {
                        JLog.e(msg);
                    }
                });

        mImageList = new HashMap<>();
        mDialogFactory = DialogFactory.getInstance();
        mTakePhoto = getTakePhoto();
        mAuthPresenter = new AuthPresenter(this, this);
        mTakePhoto.onCreate(savedInstanceState);
    }

    @Override
    public void checkStatus() {
        int i = 0;
        WanPreferences wanPreferences = WanPreferences.getInstance();
        if (!TextUtils.isEmpty(wanPreferences.getUserDriving())) {
            hideDrivingLayout();
            i++;
        }
        if (!TextUtils.isEmpty(wanPreferences.getUserIdCard())) {
            hideIdcardLayout();
            i++;
        }
        if (!TextUtils.isEmpty(wanPreferences.getUserVehicle())) {
            hideVehicleLayout();
            i++;
        }
        if (wanPreferences.getUserSafeForm()) {
            hideSafeFormLayout();
        }
        if (i == 3) {
            enableNextButtonStatus();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_auth;
    }

    @OnClick(R.id.auth_driving_license_rl)
    void onDriving() {
        mClickId = 1;
        showTakePhoto();
    }

    @OnClick(R.id.auth_vehicle_license_rl)
    void onVehicle() {
        mClickId = 2;
        showTakePhoto();
    }

    @OnClick(R.id.auth_id_card_rl)
    void onID() {
        mClickId = 3;
        showTakePhoto();
    }

    @OnClick(R.id.auth_safe_form_rl)
    void onSafe() {
        mClickId = 4;
        showTakePhoto();
    }

    @OnClick(R.id.auth_next)
    void onNext() {

        mAuthPresenter.sendToNext(mImageList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mTakePhoto.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JLog.i("onActivityResult resultCode:" + resultCode, "data:" + data);
        mTakePhoto.onActivityResult(requestCode, resultCode, data);
    }

    private void showTakePhoto() {
        TakePhotoDialog dialog = mDialogFactory.createTakePhotoDialog();
        dialog.setOnTakePhotoListener(new TakePhotoDialog.OnTakePhotoListener() {
            @Override
            public void onCamera() {
                String name = DateUtils.getCurrentTimeString() + ".jpg";
                String dir = FileUtils.getImageSavePath(AuthActivity.this);
                File mPhotoFile = new File(dir, name);
                Uri fileUri = Uri.fromFile(mPhotoFile);
                mTakePhoto.onPickFromCapture(fileUri);
            }

            @Override
            public void onAlbum() {
                mTakePhoto.onPickFromGallery();
            }
        });
        mDialogFactory.showDialogFragment(this, dialog);
    }

    private InvokeParam invokeParam;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        String path = result.getImage().getOriginalPath();
        if (TextUtils.isEmpty(path)) {
            ToastUtils.toast(this, "选择图片失败");
            return;
        }
        switch (mClickId) {
            case 1:
                mDrivingImageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(path).into(mDrivingImageView);
                mAuthPresenter.driving(path);
                break;
            case 2:
                mVehicleImageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(path).into(mVehicleImageView);
                mAuthPresenter.vehicle(path);
                break;
            case 3:
                mIdCardImageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(path).into(mIdCardImageView);
                mAuthPresenter.idcard(path);
                break;
            case 4:
                mSafeFormImageView.setVisibility(View.VISIBLE);
                Glide.with(this).load(path).into(mSafeFormImageView);
                mAuthPresenter.safeform(path);
                break;
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {
        String path = result.getImage().getOriginalPath();
        JLog.e(path, msg);
    }

    @Override
    public void takeCancel() {
        JLog.e("cancel");
    }

    @Override
    public void hideDrivingLayout() {
        mTitleDriving.setText("驾驶证      已上传√");
        mDrivingLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideVehicleLayout() {
        mTitleVehicle.setText("行驶证     已上传√");
        mVehicleLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideIdcardLayout() {
        mTitleIdcard.setText("身份证     已上传√");
        mIdcardLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideSafeFormLayout() {
        mTitleSafeform.setText("交强保单     已上传√");
        mSafefromLayout.setVisibility(View.GONE);
    }

    @Override
    public void enableNextButtonStatus() {
        mNextButton.setClickable(true);
        mNextButton.setEnabled(true);
    }

}
