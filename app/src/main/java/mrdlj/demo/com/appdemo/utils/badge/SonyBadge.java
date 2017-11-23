package mrdlj.demo.com.appdemo.utils.badge;

import android.content.Intent;

public class SonyBadge extends BaseBadge {
    private final static String NAME = "com.sonyericsson.home";

    public static boolean isSelf(String name) {
        return NAME.equalsIgnoreCase(name);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void setBadge(int count) {
        Intent intent = new Intent();
        boolean isShow = true;
        if (count <= 0) {
            count = 0;
            isShow = false;
        }
        intent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", getLancherClassName());
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count));
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", getPackageName());

        sendBroadcast(intent);
    }

}
