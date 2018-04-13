package com.linsr.wanandroid.biz.login;

/**
 * Description
 @author Linsr
 */

public interface LoginContact {

    interface View {

    }

    interface Presenter {
        void login(String phoneNumber, String password);
        void logout(String userCode);
    }

}
