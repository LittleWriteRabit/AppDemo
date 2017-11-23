package mrdlj.demo.com.appdemo.utils.badge;

import android.content.Intent;

public class SamsungBadge extends BaseBadge {
    private final static String NAME = "com.sec.android.app.launcher";

    public static boolean isSelf(String name) {
        return NAME.equalsIgnoreCase(name) || ("com.sec.android.app.twlauncher".equalsIgnoreCase(name));
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void setBadge(int count) {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", getPackageName());
        intent.putExtra("badge_count_class_name", getLancherClassName());
        sendBroadcast(intent);
    }

}
