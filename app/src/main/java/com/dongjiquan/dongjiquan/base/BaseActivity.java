package com.dongjiquan.dongjiquan.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by wlx on 2017/9/27.
 */

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    public T mPresenter;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE);
        if (getLayoutId()!=0)setContentView(getLayoutId());
        ButterKnife.bind(this);
        initImm();
        mPresenter=initPresenter();
        EventBus.getDefault().register(this);
        if (mPresenter != null) mPresenter.attach((V) this);
        initView();

        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT);
        }
    }

    private void initImm() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarDarkFont(true);
//        mImmersionBar.flymeOSStatusBarFontColor(R.color.red_btn_bg_pressed_color);
//        mImmersionBar.statusBarColorTransform(R.color.orange);
        mImmersionBar.fitsSystemWindows(true) ;
//        mImmersionBar .statusBarColor(R.color.red);
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
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
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainThread(String s){}

}
