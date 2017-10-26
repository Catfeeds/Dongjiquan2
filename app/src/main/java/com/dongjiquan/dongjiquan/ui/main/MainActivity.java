package com.dongjiquan.dongjiquan.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.dongjiquan.dongjiquan.R;
import com.dongjiquan.dongjiquan.base.BaseActivity;
import com.dongjiquan.dongjiquan.base.BasePresenter;
import com.dongjiquan.dongjiquan.ui.home.HomeFragment;
import com.dongjiquan.dongjiquan.utils.ScaleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/*隐藏状态栏和ActionBar的方式在4.1系统之上和4.1系统之下还是不一样的，
        这里我就不准备考虑4.1系统之下的兼容性了，因为过于老的系统根本就没有提供沉浸式体验的支持。*/
public class MainActivity extends BaseActivity {

    Timer timer = new Timer();
    @BindView(R.id.main_view_pager)
    ViewPager mainViewPager;
    List<Fragment> list = new ArrayList<>();
    @BindView(R.id.scale_view)
    ScaleView scaleView;
    @BindView(R.id.tv_num)
    TextView tvNum;


    @Override
    protected void initView() {
        list.add(new HomeFragment());
        list.add(new HomeFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_view_pager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        /**
         * 刻度显示的位置
         */
        scaleView.setOnSelectItem(new ScaleView.SelectItem() {
            @Override
            public int setSelectItem() {
                return 50;
            }
        });
        /**
         * 滑动时刻度值
         */
        scaleView.setOnRulerValueChangeListener(new ScaleView.RulerValue() {
            @Override
            public void value(int var1) {
                tvNum.setText(var1+"");
            }
        });
    }


    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick()
    public void onClick(View view) {

    }





}
