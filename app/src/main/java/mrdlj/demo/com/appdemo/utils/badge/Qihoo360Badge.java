package mrdlj.demo.com.appdemo.utils.badge;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import mrdlj.demo.com.appdemo.AppReader;


public class Qihoo360Badge extends BaseBadge {
    private final static String NAME = "com.qihoo360.launcher";

    public static boolean isSelf(String name) {
        return NAME.equalsIgnoreCase(name) || (("android.process.acore".equalsIgnoreCase(name)) &&
                (isLancher("com.qihoo360.launcher")));
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void setBadge(int count) {
        Intent localIntent = new Intent("com.qihoo360.launcher.action.APP_ICON_NOTIFICATION_COUNT");
        localIntent.putExtra("package_name", getPackageName());
        localIntent.putExtra("class_name", getLancherClassName());
        localIntent.putExtra("notification_count", count);
        sendBroadcast(localIntent);
    }


    public static boolean isLancher(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            return false;
        }
        PackageManager localPackageManager = AppReader.getInstance().getPackageManager();
        try {
            localPackageManager.getPackageInfo(paramString, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
        }
        return false;
    }

}
