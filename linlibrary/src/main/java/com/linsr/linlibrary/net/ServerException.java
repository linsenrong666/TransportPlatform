package com.linsr.linlibrary.net;

/**
 * Description
 @author Linsr
 */

public class ServerException extends RuntimeException {
    private String mErrorMsg;

    public ServerException(String msg) {
        mErrorMsg = msg;
    }

    @Override
    public String getMessage() {
        return mErrorMsg;
    }

}