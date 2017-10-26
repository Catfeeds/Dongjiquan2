package com.dongjiquan.dongjiquan.base;

import android.content.Context;
import android.view.View;

import java.lang.ref.WeakReference;


/**
 * Created by wlx on 2017/9/27.
 */

public class BasePresenter<V> {
    protected Context mContext;
    //弱引用
    protected WeakReference<V> mWeakReference;

    /**
     * 注入view
     */
    public void attach(V mView){
        mWeakReference=new WeakReference<V>(mView);
        getContext();
    }

    /**
     * 清除操作
     */
    public void detach(){
        if (mWeakReference!=null){
            mWeakReference.clear();
        }
    }

    protected V getView(){
        return mWeakReference.get();
    }
    /**
     * 获取context
     */
    public void getContext(){

        if (getView() instanceof BaseActivity){
            mContext= (Context) getView();
        }else if (getView() instanceof BaseFragment){
            mContext=((BaseFragment) getView()).getActivity();
        }else if (getView() instanceof View){
            mContext=((View) getView()).getContext();
        }else
            throw new IllegalArgumentException("view must instanceof activity or fragment");
    }
}
