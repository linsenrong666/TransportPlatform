package com.linsr.linlibrary.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author Linsr
 */
public class DisplayUtil {

    /**
     * get phone screen width
     *
     * @param context context
     * @return screen width
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * get phone screen height
     *
     * @param context context
     * @return screen height
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * @param context 上下文
     * @param pxValue 像素值
     * @return dip值
     */
    public static int pxConversionDip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param context  上下文
     * @param dipValue dip值
     * @return 像素
     */
    public static int dipConversionPx(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * @param context 上下文
     * @param pxValue 像素
     * @return sp值
     */
    public static int pxConversionSp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * @param context 上下文
     * @param spValue sp值
     * @return 像素
     */
    public static int spConversionPx(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getExpectedGridItemWidth(Context context, int columnCount) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / columnCount;
    }

    public static int getExpectedGridBitmapWidth(Context context, int columnCount) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / (displayMetrics.scaledDensity * columnCount));
    }
}