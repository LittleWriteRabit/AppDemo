package mrdlj.demo.com.appdemo.mvp.presenters;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.mvp.other.BasePresenter;
import mrdlj.demo.com.appdemo.mvp.views.ILauncherView;
import mrdlj.demo.com.appdemo.utils.glide.GlideUtil;

/**
 * Created by du on 2017/10/17 10:23.
 * 加载页 广告倒计时等首页
 */

public class LauncherPresenter extends BasePresenter<ILauncherView> {
    /**
     * 倒计时
     */
    private int count = 5;

    public LauncherPresenter(ILauncherView view) {
        super(view);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (count != 0) {
                    if (mvpView != null && mvpView.getCountTextView() != null) {
                        mvpView.getCountTextView().setText(String.format(mvpView.getContext().getString(R.string.count_down), count-- + ""));
                    }
                    handler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    mvpView.goMainActivity();
                }
            }
        }
    };


    public void loadAdImage() {
        GlideUtil.setImage(mvpView.getContext(), mvpView.getAdsImageView(), "http://www.baidu.com/img/baidu_jgylogo3.gif");
    }

    public void startCountDown() {
        handler.sendEmptyMessageDelayed(0, 0);
    }

    @Override
    public void onUnsubscribe() {
        super.onUnsubscribe();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
