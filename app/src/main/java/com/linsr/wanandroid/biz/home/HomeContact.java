package com.linsr.wanandroid.biz.home;

import com.linsr.wanandroid.data.model.ArticlePojo;
import com.linsr.wanandroid.data.model.BannerPojo;

import java.util.List;

/**
 * Description
 @author Linsr
 */

public interface HomeContact {
    interface View {
        void showArticles(List<ArticlePojo> data, boolean isRefresh);
        void showBanners(List<BannerPojo> data);
        void onLoadFailed(boolean isRefresh);
    }

    interface Presenter {
        void getArticles(boolean isRefresh);

        void getBanner();

        void refreshList();

        void loadMore();
    }
}
