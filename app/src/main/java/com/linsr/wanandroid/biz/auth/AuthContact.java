package com.linsr.wanandroid.biz.auth;

import java.io.File;
import java.util.Map;

/**
 * Description
 @author Linsr
 */

public interface AuthContact {

    interface View {
        void hideDrivingLayout();
        void hideVehicleLayout();
        void hideIdcardLayout();
        void hideSafeFormLayout();

        void enableNextButtonStatus();
        void checkStatus();
    }

    interface Presenter {
        void sendToNext(Map<String,String> map);
        void driving(String path);
        void idcard(String path);
        void vehicle(String path);
        void safeform(String path);
    }
}
