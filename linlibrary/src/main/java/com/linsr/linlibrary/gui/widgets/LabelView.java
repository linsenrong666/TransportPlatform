package com.linsr.linlibrary.gui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.linsr.linlibrary.R;


/**
 * Description
 *
 * @author linsenrong on 2016/10/12 17:22
 */

public class LabelView extends FrameLayout {

    private TextView mTitleTextView;
    private TextView mContentTextView;
    private ImageView mContentImageView;
    private EditText mContentEditText;

    private boolean mIsShowEdit;

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.widget_label_view,
                (ViewGroup) getParent(), false);
        mTitleTextView = (TextView) view.findViewById(R.id.label_title_tv);
        mContentTextView = (TextView) view.findViewById(R.id.label_content_tv);
        mContentImageView = (ImageView) view.findViewById(R.id.label_content_iv);
        mContentEditText = (EditText) view.findViewById(R.id.label_content_et);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelView);

        String titleString = a.getString(R.styleable.LabelView_title_text);
        String contentString = a.getString(R.styleable.LabelView_content_text);
        Drawable drawable = a.getDrawable(R.styleable.LabelView_content_image);
        mIsShowEdit = a.getBoolean(R.styleable.LabelView_show_edit_text, false);
        String hintText = a.getString(R.styleable.LabelView_hint_text);
        float titleTextSize = a.getDimension(R.styleable.LabelView_title_text_size,
                context.getResources().getDimension(R.dimen.font_middle));
        float contentEditSize = a.getDimension(R.styleable.LabelView_content_edit_size,
                context.getResources().getDimension(R.dimen.font_middle));
        float contentTextSize = a.getDimension(R.styleable.LabelView_content_text_size,
                context.getResources().getDimension(R.dimen.font_middle));
        int inputType = a.getInt(R.styleable.LabelView_edit_input_type, InputType.TYPE_CLASS_TEXT);

        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        mContentEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentEditSize);
        mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentTextSize);
        mContentEditText.setInputType(inputType);

        if (!TextUtils.isEmpty(titleString)) {
            mTitleTextView.setText(titleString);
        }
        if (!TextUtils.isEmpty(contentString)) {
            mContentTextView.setVisibility(VISIBLE);
            mContentTextView.setText(contentString);
        }
        if (drawable != null) {
            mContentImageView.setVisibility(VISIBLE);
            mContentImageView.setImageDrawable(drawable);
        }
        if (mIsShowEdit) {
            mContentTextView.setVisibility(GONE);
            mContentEditText.setVisibility(VISIBLE);
            mContentEditText.setHint(hintText);
        } else {
            mContentEditText.setVisibility(GONE);
        }

        a.recycle();

        addView(view);
    }

    public void setLabelTitle(String str) {
        mTitleTextView.setText(str);
    }

    public void setInputType(int type) {
        mContentEditText.setInputType(type);
    }

    public void setContentText(String str) {
        if (mContentTextView.getVisibility() != VISIBLE) {
            mContentTextView.setVisibility(VISIBLE);
        }
        mContentTextView.setText(str);
    }

    public void setEditContentText(String str) {
        if (mContentEditText.getVisibility() != VISIBLE) {
            mContentEditText.setVisibility(VISIBLE);
        }
        mContentEditText.setText(str);
    }

    public void setContentImage(Drawable d) {
        if (mContentImageView.getVisibility() != VISIBLE) {
            mContentImageView.setVisibility(VISIBLE);
        }
        mContentImageView.setImageDrawable(d);
    }

    public void showEdit(boolean isShowEdit) {
        mIsShowEdit = isShowEdit;
        if (mIsShowEdit) {
            mContentTextView.setVisibility(GONE);
            mContentEditText.setVisibility(VISIBLE);
        } else {
            mContentEditText.setVisibility(GONE);
        }
    }

    public String getEditContentText() {
        return mContentEditText.getText().toString().trim();
    }

    public EditText getContentEditText() {
        return mContentEditText;
    }

    public void setContentImageClickListener(OnClickListener listener) {
        mContentImageView.setOnClickListener(listener);
    }

    public LabelView(Context context) {
        this(context, null, 0);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

}
