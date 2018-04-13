package com.linsr.linlibrary.net;

import android.text.TextUtils;

import com.linsr.linlibrary.utils.JLog;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Description
 @author Linsr
 */

public class NetReqManager {

    private static volatile NetReqManager mInstance;
    private Retrofit mRetrofit;
    private ConcurrentHashMap<String, Object> mServiceCache;
    private boolean mIsInitialized;

    public static NetReqManager getInstance() {
        if (mInstance == null) {
            synchronized (NetReqManager.class) {
                if (mInstance == null) {
                    mInstance = new NetReqManager();
                }
            }
        }
        return mInstance;
    }

    private NetReqManager() {
    }

    public void init(String host, boolean isDebug) {
        if (!mIsInitialized) {
            mServiceCache = new ConcurrentHashMap<>();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //设置缓存
//            File cacheFile = new File(DemoApplication.getContext().getExternalCacheDir(), "NetRequestCache");
//            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
//            Interceptor cacheInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request();
//                    if (!AppUtils.networkIsAvailable(DemoApplication.getContext())) {
//                        request = request.newBuilder()
//                                .cacheControl(CacheControl.FORCE_CACHE)
//                                .build();
//                    }
//                    Response response = chain.proceed(request);
//                    if (AppUtils.networkIsAvailable(DemoApplication.getContext())) {
//                        int maxAge = 0;
//                        // 有网络时 设置缓存超时时间0个小时
//                        response.newBuilder()
//                                .header("Cache-Control", "public, max-age=" + maxAge)
//                                .removeHeader("WuXiaolong")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                                .build();
//                    } else {
//                        // 无网络时，设置超时为4周
//                        int maxStale = 60 * 60 * 24 * 28;
//                        response.newBuilder()
//                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                .removeHeader("nyn")
//                                .build();
//                    }
//                    return response;
//                }
//            };
//            builder.cache(cache).addInterceptor(cacheInterceptor);
//            //公共参数
//            Interceptor addQueryParameterInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//                    Request request;
//                    String method = originalRequest.method();
//                    Headers headers = originalRequest.headers();
//                    HttpUrl modifiedUrl = originalRequest.url().newBuilder()
//                            // Provide your custom parameter here
//                            .addQueryParameter("platform", "android")
//                            .addQueryParameter("version", "1.0.0")
//                            .build();
//                    request = originalRequest.newBuilder().url(modifiedUrl).build();
//                    return chain.proceed(request);
//                }
//            };
//            builder.addInterceptor(addQueryParameterInterceptor);
            if (isDebug) {
//                HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
//                        new HttpLoggingInterceptor.Logger() {
//                            @Override
//                            public void log(String message) {
//                                if (TextUtils.isEmpty(message)) return;
//                                String s = message.substring(0, 1);
//                                //如果收到响应是json才打印
//                                if ("{".equals(s) || "[".equals(s)) {
//                                    JLog.d("收到响应: " + message);
//                                }
//                            }
//                        });
//                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(new LogInterceptor());

//                // Log信息拦截器
//                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                //设置 Debug Log 模式
//                builder.addInterceptor(loggingInterceptor);
            }
            //设置超时
            builder.connectTimeout(15, TimeUnit.SECONDS);
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);

            //以上设置结束，才能build(),不然设置白搭
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(host)
                    .client(okHttpClient)
                    //设置 Json 转换器
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    //RxJava 适配器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mIsInitialized = true;
        }
    }

    public <S> S create(@NotNull Class<S> service) {
        if (mRetrofit == null || mServiceCache == null) {
            throw new RuntimeException("NetReqManager have not be initialized");
        }
        String key = service.getName();
        Object s = mServiceCache.get(key);
        if (s != null) {
            return (S) s;
        }
        s = mRetrofit.create(service);
        mServiceCache.put(key, s);
        return (S) s;
    }

    class LogInterceptor implements Interceptor {

        private String TAG = "interceptor";

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            okhttp3.Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            JLog.d(TAG, "\n");
            JLog.d(TAG, "----------Start----------------");
            JLog.d(TAG, "| " + request.toString());
            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    JLog.d(TAG, "| RequestParams:{" + sb.toString() + "}");
                }
            }
            JLog.d(TAG, "| Response:" + content);
            JLog.d(TAG, "----------End:" + duration + "毫秒----------");
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }
}
