package com.linsr.wanandroid.biz.register;

/**
 * Description
 @author Linsr
 */

public interface RegisterContact {
    interface View {
        void setCode(String phone);
        void setCodeBtn(boolean canClick,String text);
    }

    interface Presenter {
        boolean paramsValid(String phone, String pwd1, String pwd2);

        void doRegister(String phone, String password, String code);

        void  identifyingCode(String phone);
    }

}
