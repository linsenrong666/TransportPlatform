package com.linsr.linlibrary.net;

import android.app.Activity;
import android.app.Dialog;


import com.linsr.linlibrary.gui.dialogs.DialogFactory;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Description
 @author Linsr
 */

public abstract class NetObserver<T> implements Observer<T> {

    public abstract void onSucceed(T data);

    public abstract void onFailed(String msg);


    private Dialog mTransparentDialog;
    private DialogFactory mDialogFactory;
    private boolean mShowLoading = false;
    private Activity mActivity;

    public NetObserver() {
    }

    public NetObserver(Activity activity, boolean showLoading) {
        mActivity = activity;
        mDialogFactory = DialogFactory.getInstance();
        mShowLoading = showLoading;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (mShowLoading) {
            displayTransparentProgressDialog();
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        onSucceed(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailed(e.getMessage());
        if (mShowLoading) {
            dismissTransparentProgressDialog();
        }
    }

    @Override
    public void onComplete() {
        if (mShowLoading) {
            dismissTransparentProgressDialog();
        }
    }

    /**
     * Display a transparent progress dialog
     */
    private void displayTransparentProgressDialog() {
        mTransparentDialog = mDialogFactory.createTransparentProgressDialog(mActivity);
        mDialogFactory.showDialog(mTransparentDialog);
    }

    /**
     * dismiss the transparent progress dialog if displayed before
     */
    private void dismissTransparentProgressDialog() {
        if (mTransparentDialog != null) {
            mDialogFactory.dismissDialog(mTransparentDialog);
            mTransparentDialog = null;
        }
    }
}
