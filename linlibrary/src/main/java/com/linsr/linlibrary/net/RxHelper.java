package com.linsr.linlibrary.net;

import android.content.Context;
import android.text.TextUtils;

import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.utils.JLog;

import org.jetbrains.annotations.Contract;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Observable.error;
import static io.reactivex.Observable.just;

/**
 * Description
 @author Linsr
 */

public class RxHelper implements NetConstants {

    public static <T> ObservableTransformer<T, T> ioMain() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<ResponsePojo<T>, T> handleResponse() {
        return new ObservableTransformer<ResponsePojo<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<ResponsePojo<T>> upstream) {
                return upstream.map(new Function<ResponsePojo<T>, T>() {
                    @Override
                    public T apply(@NonNull ResponsePojo<T> result) throws Exception {
                        if (result.getCode() == NetConstants.CODE_SUCCESS) {
                            if (result.getData() != null) {
                                return result.getData();
                            } else {
                                return (T) new Object();
                            }
                        } else {
                            throw new ServerException(result.getMsg());
                        }
                    }
                });
            }
        };
    }

    public static ObservableTransformer<ResponsePojo, ResponsePojo> handleResponse1() {
        return new ObservableTransformer<ResponsePojo, ResponsePojo>() {
            @Override
            public ObservableSource<ResponsePojo> apply(@NonNull Observable<ResponsePojo> upstream) {
                return upstream.map(new Function<ResponsePojo, ResponsePojo>() {
                    @Override
                    public ResponsePojo apply(@NonNull ResponsePojo result) throws Exception {
                        if (result.getCode() == NetConstants.CODE_SUCCESS) {
                            return result;
                        } else {
                            throw new ServerException(result.getMsg());
                        }
                    }
                });
            }
        };
    }

}
