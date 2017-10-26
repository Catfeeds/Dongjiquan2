package com.dongjiquan.dongjiquan.ui.home;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.dongjiquan.dongjiquan.R;
import com.dongjiquan.dongjiquan.base.BaseFragment;
import com.dongjiquan.dongjiquan.bean.BannerItemBean;
import com.dongjiquan.dongjiquan.presneter.HomePresenter;
import com.dongjiquan.dongjiquan.utils.GlideImageLoader;
import com.dongjiquan.dongjiquan.view.IHomeView;
import com.vondear.rxtools.view.RxToast;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment<IHomeView, HomePresenter> implements IHomeView {


    @BindView(R.id.banner_first)
    Banner banner;
    private ArrayList<String> imgeList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView() {
        mPresenter.getBannerImage();
        imgeList=new ArrayList<>();
    }

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void requestBannerSuccess(BannerItemBean bannerItemBean) {
        RxToast.info("........................");
        List<BannerItemBean.ImgCastinglistBean> imgCastinglist = bannerItemBean.getImgCastinglist();
        for (BannerItemBean.ImgCastinglistBean b:imgCastinglist) {
            String imgUrl = b.getImgCastingImgUrl();
            imgeList.add(imgUrl);
            Log.e("wlx22", "requestBannerSuccess: "+imgUrl );
        }
        initBanner(imgCastinglist);
    }

    private void initBanner(final List<BannerItemBean.ImgCastinglistBean> imgCastinglist) {

        //图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imgeList);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerItemBean.ImgCastinglistBean bean = imgCastinglist.get(position);


            }
        });
    }

    @Override
    public void requestMarathonSuccess() {

    }


}
