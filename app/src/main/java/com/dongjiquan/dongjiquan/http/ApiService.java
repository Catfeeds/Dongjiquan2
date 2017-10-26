package com.dongjiquan.dongjiquan.http;

import com.dongjiquan.dongjiquan.base.BaseModel;
import com.dongjiquan.dongjiquan.bean.BannerItemBean;
import com.dongjiquan.dongjiquan.utils.UrlUtil;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wlx on 2017/9/28.
 */

public interface ApiService {



    /**
     * 首页轮播图
     */
    @GET(UrlUtil.BANNER_LIST)
    Observable<BaseModel<BannerItemBean> > bannerList(@Query("imgCastingTitle") String imgCastingTitle ,
                                                      @Query("imgCastingType") String imgCastingType);

}
