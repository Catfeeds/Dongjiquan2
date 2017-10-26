package com.dongjiquan.dongjiquan.http;


import android.util.Log;

import com.dongjiquan.dongjiquan.base.BaseModel;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wlx on 2017/9/28.
 */

public class RxHelper {
    /**
     * 处理网络请求的结果
     */
    public static <T> Observable.Transformer<BaseModel<T>,T> handlerResult(){
        return new Observable.Transformer<BaseModel<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseModel<T>> baseModelObservable) {

                return  baseModelObservable.flatMap(new Func1<BaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseModel<T> tBaseModel) {
                        if (tBaseModel.success()) {
                            return createData(tBaseModel.data);
                        } else {
                            Log.e("wlx", "call: " + tBaseModel.code);
                            return Observable.error(new Exception(tBaseModel.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

        }
    /**
     * 创建数据
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data){


        return  Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
               try {
                   subscriber.onNext(data);
                   subscriber.onCompleted();
               }catch (Exception e){
                   subscriber.onError(e);
                   e.printStackTrace();
               }
            }
        });

    }
}
