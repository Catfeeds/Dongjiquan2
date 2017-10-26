package com.dongjiquan.dongjiquan.view;

import com.dongjiquan.dongjiquan.bean.BannerItemBean;

/**
 * Created by wlx on 2017/10/9.
 */

public interface IHomeView {
    void requestBannerSuccess(BannerItemBean itemBean);
    void requestMarathonSuccess();
}
