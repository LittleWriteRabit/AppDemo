package mrdlj.demo.com.appdemo.ui.activity;

import android.os.Bundle;

import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.mvp.main.MainModel;
import mrdlj.demo.com.appdemo.mvp.main.MainPresenter;
import mrdlj.demo.com.appdemo.mvp.main.MainView;
import mrdlj.demo.com.appdemo.mvp.other.MvpActivity;

public class MainActivity extends MvpActivity<MainPresenter> implements MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvpPresenter.loadDataByRetrofitRxjava("hh");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void getDataSuccess(MainModel model) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public boolean enableSlideClose() {
        return false;
    }
}
