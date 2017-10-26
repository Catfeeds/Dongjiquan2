package com.dongjiquan.dongjiquan.presneter;

import android.util.Log;
import android.widget.Toast;

import com.dongjiquan.dongjiquan.base.BasePresenter;
import com.dongjiquan.dongjiquan.bean.BannerItemBean;
import com.dongjiquan.dongjiquan.http.Api;
import com.dongjiquan.dongjiquan.http.RxHelper;
import com.dongjiquan.dongjiquan.http.RxRetrofitCach;
import com.dongjiquan.dongjiquan.http.RxSubscribe;
import com.dongjiquan.dongjiquan.view.IHomeView;
import com.vondear.rxtools.view.RxToast;

import rx.Observable;


/**
 * Created by wlx on 2017/10/9.
 */

public class HomePresenter extends BasePresenter<IHomeView> {

    public void getBannerImage(){
        if (getView()!=null){
            Observable<BannerItemBean> compose= Api.getService().bannerList(",","1").compose(RxHelper.<BannerItemBean>handlerResult());
            Observable<BannerItemBean> bannerList = RxRetrofitCach.load(mContext, "BannerList", false, compose, false);
            bannerList.subscribe(new RxSubscribe<BannerItemBean>(mContext) {
                @Override
                protected void _onNext(BannerItemBean bannerItemBean) {
                 RxToast.info(bannerItemBean.getImgCastinglist().get(0).getImgCastingImgUrl() );
                    getView().requestBannerSuccess(bannerItemBean);
                }

                @Override
                protected void _onError(String e) {
                    Toast.makeText(mContext,e,Toast.LENGTH_SHORT).show();
                    Log.e("lyf", e);
                }
            });
       }
    }

}
