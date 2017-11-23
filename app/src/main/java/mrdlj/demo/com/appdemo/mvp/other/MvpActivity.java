package mrdlj.demo.com.appdemo.mvp.other;

import android.os.Bundle;

import mrdlj.demo.com.appdemo.base.BaseActivity;
import mrdlj.demo.com.appdemo.mvp.main.BaseView;


/**
 * baseActivity
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView{
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 用于创建Presenter和判断是否使用MVP模式(由子类实现)
     * @return
     */
    @Override
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog(null);
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }

}
