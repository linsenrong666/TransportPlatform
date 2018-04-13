package com.linsr.wanandroid.biz.home;

import android.app.Activity;

import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.net.NetObserver;
import com.linsr.linlibrary.net.RxHelper;
import com.linsr.wanandroid.data.model.ArticleDTO;
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.data.model.BannerPojo;

import java.util.List;


/**
 * Description
 @author Linsr
 */

public class HomePresenter implements HomeContact.Presenter {

    private HomeContact.View mView;
    private Activity mActivity;
    private int mPage = 0;

    public HomePresenter(Activity activity, HomeContact.View view) {
        mActivity = activity;
        mView = view;
    }

    @Override
    public void getArticles(final boolean isRefresh) {
        Api.service()
                .getHomeArticleList(mPage)
                .compose(RxHelper.<ResponsePojo<ArticleDTO>>ioMain())
                .compose(RxHelper.<ArticleDTO>handleResponse())
                .subscribe(new NetObserver<ArticleDTO>() {
                    @Override
                    public void onSucceed(ArticleDTO data) {
                        mView.showArticles(data.getDatas(), isRefresh);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onLoadFailed(isRefresh);
                    }
                });
    }

    @Override
    public void getBanner() {
        Api.service().getHomeBanner()
                .compose(RxHelper.<ResponsePojo<List<BannerPojo>>>ioMain())
                .compose(RxHelper.<List<BannerPojo>>handleResponse())
                .subscribe(new NetObserver<List<BannerPojo>>() {
                    @Override
                    public void onSucceed(List<BannerPojo> data) {
                        mView.showBanners(data);
                    }

                    @Override
                    public void onFailed(String msg) {

                    }
                });
    }

    @Override
    public void refreshList() {
        mPage = 0;
        getArticles(true);

    }

    @Override
    public void loadMore() {
        mPage++;
        getArticles(false);
    }
}
