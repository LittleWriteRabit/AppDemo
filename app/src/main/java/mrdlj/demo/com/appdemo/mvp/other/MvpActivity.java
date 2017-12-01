package mrdlj.demo.com.appdemo.mvp.other;

import android.os.Bundle;

import mrdlj.demo.com.appdemo.base.BaseActivity;


/**
 * @author: du on 2017/11/27 12:16.
 * Email: 2857692313@qq.com
 * description: TODO: baseActivity
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    public void showLoading() {
        showProgressDialog();
    }

    public void hideLoading() {
        dismissProgressDialog();
    }

}
