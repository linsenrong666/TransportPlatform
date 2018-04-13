package com.linsr.wanandroid.data.remote;

import com.linsr.linlibrary.net.NetReqManager;

/**
 * Description
 @author Linsr
 */

public class Api {

    public static ApiStore service() {
        return NetReqManager.getInstance().create(ApiStore.class);
    }

    public static TransportStore request() {
        return NetReqManager.getInstance().create(TransportStore.class);
    }
}
