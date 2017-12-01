package mrdlj.demo.com.appdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import mrdlj.demo.com.appdemo.base.BaseApp;
import mrdlj.demo.com.appdemo.data.source.db.GreenDaoManager;
import mrdlj.demo.com.appdemo.utils.CrashHandlerUtil;

/**
 * Created by du on 2017/9/12 15:10.
 */

public class AppReader extends BaseApp {

    private static AppReader sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        sApp = this;

        //初始化greendao
        GreenDaoManager.getInstance();
        //崩溃处理
        CrashHandlerUtil crashHandlerUtil = CrashHandlerUtil.getInstance();
        crashHandlerUtil.init(this);
        crashHandlerUtil.setCrashTip("很抱歉，程序出现异常，即将退出！");
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static AppReader getInstance() {
        return sApp;
    }
}
