package com.linsr.linlibrary.gui.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;

import com.linsr.linlibrary.R;


/**
 * Created by Linsr on 2015/8/25.
 * 对话框工厂，所有对话框都在此创建
 *
 * @author Linsr
 */
public class DialogFactory {

    private static class TransparentProgressDialog extends Dialog {
        TransparentProgressDialog(Context context) {
            super(context, R.style.ProgressDialog);
            setContentView(R.layout.dialog_progress);
        }
    }

    private static volatile DialogFactory mInstance;

    private DialogFactory() {

    }

    public static DialogFactory getInstance() {

        if (mInstance == null) {
            synchronized (DialogFactory.class) {
                if (mInstance == null) {
                    mInstance = new DialogFactory();
                }
            }
        }
        return mInstance;

    }

    public void dismissDialog(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialog(Dialog dialog) {
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDialogFragment(Activity activity, DialogFragment dialog) {
        try {
            dialog.show(activity.getFragmentManager(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissDialogFragment(DialogFragment dialog) {
        try {
            dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AlertDialog createSimpleDialog(Activity activity,
                                          String message,
                                          DialogInterface.OnClickListener confirmClick,
                                          DialogInterface.OnClickListener cancelClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setNegativeButton(R.string.cancel, cancelClick)
                .setPositiveButton(R.string.confirm, confirmClick)
                .setCancelable(false);
        return builder.create();
    }

    public Dialog createTransparentProgressDialog(Activity activity) {
        return new TransparentProgressDialog(activity);
    }

    public TakePhotoDialog createTakePhotoDialog() {
        return new TakePhotoDialog();
    }

}