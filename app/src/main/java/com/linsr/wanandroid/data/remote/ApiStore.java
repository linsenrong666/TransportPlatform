package com.linsr.wanandroid.data.remote;

import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.wanandroid.data.model.ArticleDTO;
import com.linsr.wanandroid.data.model.ArticlePojo;
import com.linsr.wanandroid.data.model.BannerPojo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Description
 @author Linsr
 */

public interface ApiStore {

    String HOST = "http://www.wanandroid.com/";

    @GET("article/list/{page}/json")
    Observable<ResponsePojo<ArticleDTO>> getHomeArticleList(@Path("page") int page);

    @GET("banner/json")
    Observable<ResponsePojo<List<BannerPojo>>> getHomeBanner();

}
