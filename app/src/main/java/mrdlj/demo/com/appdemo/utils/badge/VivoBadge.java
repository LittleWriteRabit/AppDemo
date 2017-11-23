package mrdlj.demo.com.appdemo.utils.badge;

import android.content.Intent;

public class VivoBadge extends BaseBadge {
    private final static String NAME = "com.bbk.launcher2";

    public static boolean isSelf(String name) {
        return NAME.equalsIgnoreCase(name);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void setBadge(int count) {
        Intent localIntent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
        localIntent.putExtra("packageName", getPackageName());
        localIntent.putExtra("className", getLancherClassName());
        localIntent.putExtra("notificationNum", count);
        sendBroadcast(localIntent);
    }

}
