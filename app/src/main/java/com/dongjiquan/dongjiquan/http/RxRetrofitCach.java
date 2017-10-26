package com.dongjiquan.dongjiquan.http;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wlx on 2017/9/29.
 * RxJava+Retrofit 的缓存机制
 */

public class RxRetrofitCach {


    /**
     *
     * @param context
     * @param cacheKey 缓存key
     * @param isCache
     * @param fromNetWork 从网络获取的Observable
     * @param isForceRefresh 是否强制刷新
     * @param <T>
     * @return
     */
    public static <T> Observable<T> load(Context context, final String cacheKey, boolean isCache, Observable<T> fromNetWork, boolean isForceRefresh) {
        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T t = (T) Hawk.get(cacheKey);
                if (t != null) {
                    subscriber.onNext(t);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        //是否缓存
            if (isCache){
                fromNetWork=fromNetWork.map(new Func1<T, T>() {
                    @Override
                    public T call(T t) {
                        Hawk.put(cacheKey, t);
                        return t;
                    }
                });
            }

            if (isForceRefresh){
                return fromNetWork;
            }else {
                return Observable.concat(fromCache,fromNetWork).first();
            }
    }

}