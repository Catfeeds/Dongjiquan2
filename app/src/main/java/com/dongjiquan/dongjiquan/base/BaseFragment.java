package com.dongjiquan.dongjiquan.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wlx on 2017/9/27.
 */

public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;
    private View mView;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(getLayoutId(),container,false);
        bind = ButterKnife.bind(this,mView);
        mPresenter=initPresenter();
        if (mPresenter!=null)mPresenter.attach((V) this);
        initView();
        return mView;
    }

    protected abstract void initView();

    protected abstract T initPresenter();

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter!=null){
            mPresenter.detach();
        }
        bind.unbind();
    }

    //    @Override
//    public void onDestroyView() {
//        super.onDestroy();
//        if(mPresenter!=null){
//            mPresenter.detach();
//        }
//        bind.unbind();
//    }
}
