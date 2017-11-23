package mrdlj.demo.com.appdemo.mvp.views;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import mrdlj.demo.com.appdemo.mvp.main.BaseView;

/**
 * Created by du on 2017/10/17 10:23.
 * View Launcher
 */

public interface ILauncherView extends BaseView {
    ImageView getAdsImageView();

    TextView getCountTextView();

    Context getContext();

    void goMainActivity();
}
