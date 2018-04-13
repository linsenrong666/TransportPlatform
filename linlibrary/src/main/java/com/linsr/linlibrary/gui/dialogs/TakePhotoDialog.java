package com.linsr.linlibrary.gui.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.linsr.linlibrary.R;


/**
 * Description
 @author Linsr
 */

public class TakePhotoDialog extends DialogFragment implements View.OnClickListener {

    public interface OnTakePhotoListener {
        void onCamera();

        void onAlbum();
    }

    private OnTakePhotoListener mOnTakePhotoListener;

    public void setOnTakePhotoListener(OnTakePhotoListener onTakePhotoListener) {
        mOnTakePhotoListener = onTakePhotoListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_take_photo, container);
        view.findViewById(R.id.dialog_take_photo_cancel).setOnClickListener(this);
        view.findViewById(R.id.dialog_album).setOnClickListener(this);
        view.findViewById(R.id.dialog_camera).setOnClickListener(this);

        Window window = getDialog().getWindow();
        window.setGravity(Gravity.BOTTOM); //可设置dialog的位置
        window.getDecorView().setPadding(0, 0, 0, 0); //消除边距

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return view;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_album) {
            if (mOnTakePhotoListener != null) {
                mOnTakePhotoListener.onAlbum();
                dismiss();
            }
        } else if (v.getId() == R.id.dialog_camera) {
            if (mOnTakePhotoListener != null) {
                mOnTakePhotoListener.onCamera();
                dismiss();
            }
        } else if (v.getId() == R.id.dialog_take_photo_cancel) {
            dismiss();
        }
    }

}
