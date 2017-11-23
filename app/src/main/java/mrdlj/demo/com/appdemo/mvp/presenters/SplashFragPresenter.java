package mrdlj.demo.com.appdemo.mvp.presenters;

import mrdlj.demo.com.appdemo.mvp.other.BasePresenter;
import mrdlj.demo.com.appdemo.mvp.views.ISplashFragView;
import mrdlj.demo.com.appdemo.mvp.views.ISplashView;

/**
 * Created by du on 2017/10/13 10:07.
 * SplashFragment Presenter
 */

public class SplashFragPresenter extends BasePresenter<ISplashFragView> {

    private String tag;

    public SplashFragPresenter(ISplashFragView view, String tag) {
        super(view);
        this.tag = tag;
    }

    public void getImages() {
        String url = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2619688132,1951136045&fm=27&gp=0.jpg";
        mvpView.showImage(url);
        mvpView.showText(tag + "");
    }
}
