package mrdlj.demo.com.appdemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.mvp.other.MvpActivity;
import mrdlj.demo.com.appdemo.mvp.presenters.LauncherPresenter;
import mrdlj.demo.com.appdemo.mvp.views.ILauncherView;
import mrdlj.demo.com.appdemo.test.TestActivity;

/**
 * 加载页广告等
 */
public class LauncherActivity extends MvpActivity<LauncherPresenter> implements ILauncherView {

    @BindView(R.id.iv_ads)
    ImageView ivLaunchAds;
    @BindView(R.id.tv_seconds)
    TextView tvSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.loadAdImage();
        mvpPresenter.startCountDown();
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
        return R.layout.activity_launcher;
    }

    @Override
    protected LauncherPresenter createPresenter() {
        return new LauncherPresenter(this);
    }

    @Override
    public ImageView getAdsImageView() {
        return ivLaunchAds;
    }

    @Override
    public TextView getCountTextView() {
        return tvSeconds;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void goMainActivity() {
        startActivity(new Intent(this, TestActivity.class));
        finish();
    }

    @OnClick(R.id.tv_seconds)
    public void goSkip(){
        goMainActivity();
    }
}
