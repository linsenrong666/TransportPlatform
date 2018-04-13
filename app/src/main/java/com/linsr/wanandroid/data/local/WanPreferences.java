package com.linsr.wanandroid.data.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Linsr on 2015/9/29.
 * @author linsr
 */
public class WanPreferences {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String SELF_ID = "self_id";
    private static final String SELF_PHONE = "self_phone";

    private static final String SELF_VEHICLE_LICENSE = "self_vehicle_license";
    private static final String SELF_DRIVING_LICENSE = "self_driving_license";
    private static final String SELF_ID_CARD = "self_id_card";
    private static final String SELF_SAFE_FORM = "self_safe_form";

    private static final String AUTH_STATUS = "auth_status";


    private SharedPreferences mSharedPreferences;

    private static volatile WanPreferences mInstance;

    public static WanPreferences getInstance() {

        if (mInstance == null) {
            synchronized (WanPreferences.class) {
                if (mInstance == null) {
                    mInstance = new WanPreferences();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        mSharedPreferences = context.getSharedPreferences(
                context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
    }

    /**
     * save access token
     */
    public boolean setAccessToken(String token) {
        return mSharedPreferences.edit().putString(ACCESS_TOKEN, token).commit();
    }

    public String getAccessToken() {
        return mSharedPreferences.getString(ACCESS_TOKEN, "");
    }

    /**
     * save user code
     */
    public boolean setUserCode(String id) {
        return mSharedPreferences.edit().putString(SELF_ID, id).commit();
    }

    public String getUserCode() {
        return mSharedPreferences.getString(SELF_ID, "");
    }

    /**
     * save user phone
     */
    public boolean setUserPhone(String phone) {
        return mSharedPreferences.edit().putString(SELF_PHONE, phone).commit();
    }

    public String getUserPhone() {
        return mSharedPreferences.getString(SELF_PHONE, "");
    }

    public boolean setUserDriving(String driving) {
        return mSharedPreferences.edit().putString(SELF_DRIVING_LICENSE, driving).commit();
    }

    public String getUserDriving() {
        return mSharedPreferences.getString(SELF_DRIVING_LICENSE, "");
    }

    public boolean setUserVehicle(String vehicle) {
        return mSharedPreferences.edit().putString(SELF_VEHICLE_LICENSE, vehicle).commit();
    }

    public String getUserVehicle() {
        return mSharedPreferences.getString(SELF_VEHICLE_LICENSE, "");
    }

    public boolean setUserIdCard(String idCard) {
        return mSharedPreferences.edit().putString(SELF_ID_CARD, idCard).commit();
    }

    public String getUserIdCard() {
        return mSharedPreferences.getString(SELF_ID_CARD, "");
    }

    public boolean setUserSafeForm(boolean isUploaded) {
        return mSharedPreferences.edit().putBoolean(SELF_SAFE_FORM, isUploaded).commit();
    }

    public boolean getUserSafeForm() {
        return mSharedPreferences.getBoolean(SELF_SAFE_FORM, false);
    }

    public boolean setUserAuthStatus(String status) {
        return mSharedPreferences.edit().putString(AUTH_STATUS, status).commit();
    }

    public String getUserAuthStatus() {
        return mSharedPreferences.getString(AUTH_STATUS, "");
    }

    public void logout() {
        setAccessToken("");
        setUserCode("");
        setUserPhone("");
        setUserAuthStatus("");
    }
}
