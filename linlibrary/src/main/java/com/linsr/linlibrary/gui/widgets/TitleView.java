package com.linsr.linlibrary.gui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.linsr.linlibrary.R;
import com.linsr.linlibrary.utils.DisplayUtil;


/**
 * title view
 * <p>
 * Created by Linsr on 15-8-5.
 */
public class TitleView extends FrameLayout {

    private static final float TITLE_VIEW_HEIGHT = 48;

    private TextView mTitleTextView;
    private TextView mLeftTextView;
    private ImageView mLeftImageView;
    private TextView mRightTextView;
    private ImageView mRightImageView;

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                DisplayUtil.dipConversionPx(context, TITLE_VIEW_HEIGHT));
        setLayoutParams(params);

        View view = LayoutInflater.from(context).inflate(R.layout.widget_title_view, null);

        mTitleTextView = (TextView) view.findViewById(R.id.title_view_tv_title);
        mLeftTextView = (TextView) view.findViewById(R.id.title_view_tv_left);
        mRightTextView = (TextView) view.findViewById(R.id.title_view_tv_right);
        mLeftImageView = (ImageView) view.findViewById(R.id.title_view_iv_left);
        mRightImageView = (ImageView) view.findViewById(R.id.title_view_iv_right);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleView);

        String titleString = a.getString(R.styleable.TitleView_titleString);
        if (!TextUtils.isEmpty(titleString)) {
            mTitleTextView.setText(titleString);
        }
        boolean showLeft = a.getBoolean(R.styleable.TitleView_showLeft, false);
        if (showLeft) {
            showLeftImage();
        }
        a.recycle();

        addView(view);
    }

    /**
     * set Title Text
     */
    public void setTitleText(CharSequence text) {
        mTitleTextView.setText(text);
    }

    /**
     * set Text of Left TextView
     */
    public void setLeftText(CharSequence text) {
        showLeftText();
        mLeftTextView.setText(text);
    }

    /**
     * set Text of Right TextView
     */
    public void setRightText(CharSequence text) {
        showRightText();
        mRightTextView.setText(text);
    }

    public void hideRightText() {
        mRightTextView.setVisibility(View.GONE);
    }

    /**
     * set Image of Left ImageVIew
     */
    public void setLeftImage(int resId) {
        showLeftImage();
        mLeftImageView.setImageResource(resId);
    }

    /**
     * set Image of Right ImageView
     */
    public void setRightImage(int resId) {
        showRightImage();
        mRightImageView.setImageResource(resId);
    }

    /**
     * set onClickListener of LeftArea
     */
    public void setOnLeftClickListener(OnClickListener listener) {
        mLeftImageView.setOnClickListener(listener);
        mLeftTextView.setOnClickListener(listener);
    }

    /**
     * set onClickListener of RightArea
     */
    public void setOnRightClickListener(OnClickListener listener) {
        mRightImageView.setOnClickListener(listener);
        mRightTextView.setOnClickListener(listener);
    }

    private void showLeftText() {
        mLeftTextView.setVisibility(View.VISIBLE);
        mLeftImageView.setVisibility(View.GONE);
    }

    private void showLeftImage() {
        mLeftImageView.setVisibility(View.VISIBLE);
        mLeftTextView.setVisibility(View.GONE);
    }

    private void showRightText() {
        mRightImageView.setVisibility(View.GONE);
        mRightTextView.setVisibility(View.VISIBLE);
    }

    private void showRightImage() {
        mRightImageView.setVisibility(View.VISIBLE);
        hideRightText();
    }


    public TitleView(Context context) {
        this(context, null, 0);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
}
