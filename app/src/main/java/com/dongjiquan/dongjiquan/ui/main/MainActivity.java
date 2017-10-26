package com.dongjiquan.dongjiquan.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dongjiquan.dongjiquan.R;
import com.dongjiquan.dongjiquan.ui.home.HomeFragment;
import com.dongjiquan.dongjiquan.utils.ScaleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.dongjiquan.dongjiquan.R.id.tv_num;


/*隐藏状态栏和ActionBar的方式在4.1系统之上和4.1系统之下还是不一样的，
        这里我就不准备考虑4.1系统之下的兼容性了，因为过于老的系统根本就没有提供沉浸式体验的支持。*/
public class MainActivity extends AppCompatActivity {

    Timer timer = new Timer();
    @BindView(R.id.main_view_pager)
    ViewPager mainViewPager;
    List<Fragment> list = new ArrayList<>();
    @BindView(R.id.scale_view)
    ScaleView scaleView;
    @BindView(tv_num)
    TextView tvNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {

            //这里先调用getWindow().getDecorView()方法获取到了当前界面的DecorView
            View decorView = getWindow().getDecorView();
            //SYSTEM_UI_FLAG_FULLSCREEN表示全屏的意思，也就是会将状态栏隐藏。
            int uiFlagFullscreen = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            //然后调用它的setSystemUiVisibility()方法来设置系统UI元素的可见性
            decorView.setSystemUiVisibility(uiFlagFullscreen);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

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

    @OnClick()
    public void onClick(View view) {

    }





}
