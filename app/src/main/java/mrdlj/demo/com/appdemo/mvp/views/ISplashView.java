package mrdlj.demo.com.appdemo.mvp.views;

import android.support.v4.app.Fragment;

import java.util.List;

import mrdlj.demo.com.appdemo.mvp.main.BaseView;

/**
 * Created by du on 2017/10/12 16:02.
 * 安装引导View
 */

public interface ISplashView extends BaseView {
    void setFrags(List<Fragment> a);
}
