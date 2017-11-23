package mrdlj.demo.com.appdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;

import butterknife.BindView;
import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.data.Constants;
import mrdlj.demo.com.appdemo.mvp.other.MvpActivity;
import mrdlj.demo.com.appdemo.mvp.presenters.SplashPresenter;
import mrdlj.demo.com.appdemo.mvp.views.ISplashView;
import mrdlj.demo.com.appdemo.ui.adapter.SplashPagerAdapter;
import mrdlj.demo.com.appdemo.ui.widget.indicator.PageIndicatorView;
import mrdlj.demo.com.appdemo.utils.SharedPreferencesUtil;

/**
 * 引导页面
 */
public class SplashActivity extends MvpActivity<SplashPresenter> implements ISplashView {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    PageIndicatorView indicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //是否是第一次打开App 是责进入引导否则进入LauncherActivity
        boolean firstOpenApp = SharedPreferencesUtil.getBoolean(this, Constants.FIRST_OPEN_APP, true);
        if (!firstOpenApp) {
//            SharedPreferencesUtil.setBoolean(this, Constants.FIRST_OPEN_APP, false);
            startActivity(new Intent(this, LauncherActivity.class));
            finish();
        }
        if (mvpPresenter != null) {
            mvpPresenter.getViews();
        }
    }

    @Override
    public boolean fullScreen() {
        return true;
    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public void setFrags(List<Fragment> a) {
        SplashPagerAdapter adapter = new SplashPagerAdapter(getSupportFragmentManager(), a);
        viewPager.setAdapter(adapter);
        indicatorView.setViewPager(viewPager);
    }
}
