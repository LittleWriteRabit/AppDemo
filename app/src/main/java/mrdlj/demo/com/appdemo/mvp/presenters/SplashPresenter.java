package mrdlj.demo.com.appdemo.mvp.presenters;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import mrdlj.demo.com.appdemo.mvp.other.BasePresenter;
import mrdlj.demo.com.appdemo.mvp.views.ISplashView;
import mrdlj.demo.com.appdemo.ui.fragment.SplashFragment;
import mrdlj.demo.com.appdemo.utils.RxBus;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by du on 2017/10/12 16:01.
 * 安装引导Presenter
 */

public class SplashPresenter extends BasePresenter<ISplashView> {

    public SplashPresenter(ISplashView view) {
        super(view);
    }

    public void getViews() {
        Fragment f1 = SplashFragment.newInstance("1", "1");
        Fragment f2 = SplashFragment.newInstance("2", "2");
        Fragment f3 = SplashFragment.newInstance("3", "3");
        List<Fragment> a = new ArrayList<>(5);
        a.add(f1);
        a.add(f2);
        a.add(f3);
        if (mvpView != null) {
            mvpView.setFrags(a);
        }
    }
}
