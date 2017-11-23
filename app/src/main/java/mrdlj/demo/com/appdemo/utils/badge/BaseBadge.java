package mrdlj.demo.com.appdemo.utils.badge;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import mrdlj.demo.com.appdemo.AppReader;


public abstract class BaseBadge {
    private Context context;

    public BaseBadge() {
        context = AppReader.getInstance().getApplicationContext();
    }

    public abstract String getName();

    protected abstract void setBadge(int count);

    protected String getPackageName() {
        return context.getPackageName();
    }

    protected String getLancherClassName() {
        String str = this.context.getPackageManager().getLaunchIntentForPackage(getPackageName()).getComponent().getClassName();
        if (TextUtils.isEmpty(str)) {
            return "SplashActivity";
        }
        return str;
    }

    protected void sendBroadcast(Intent intent) {
        context.sendBroadcast(intent);
    }
}
