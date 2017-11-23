package mrdlj.demo.com.appdemo.utils.badge;

import android.content.Intent;

public class OtherBadge extends BaseBadge {

    @Override
    public String getName() {
        return "OtherBadge";
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
