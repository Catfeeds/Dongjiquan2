package com.dongjiquan.dongjiquan.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by wlx on 2017/9/27.
 */

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    public T mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);
        if (getLayoutId()!=0)setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter=initPresenter();
        EventBus.getDefault().register(this);
        if (mPresenter != null) mPresenter.attach((V) this);
        initView();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    protected abstract void initView();

    protected abstract T initPresenter();


    /**
     * 对应的布局
     * @return
     */
    @LayoutRes
    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)mPresenter.detach();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThread(String s){}

}
