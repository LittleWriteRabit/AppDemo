package mrdlj.demo.com.appdemo.mvp.views;

import mrdlj.demo.com.appdemo.mvp.main.BaseView;

/**
 * Created by du on 2017/10/13 10:08.
 * 启动页面fragment view
 */

public interface ISplashFragView extends BaseView {
    void showImage(String url);
    void showText(String text);
}
