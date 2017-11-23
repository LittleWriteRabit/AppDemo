package mrdlj.demo.com.appdemo.utils.badge;

import android.content.ComponentName;
import android.content.Intent;

public class XiaomiBadge extends BaseBadge {
    private final static String NAME = "com.miui.home";

    public static boolean isSelf(String name) {
        return NAME.equalsIgnoreCase(name);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void setBadge(int count) {
        ComponentName localComponentName = new ComponentName(getPackageName(), getLancherClassName());
        Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
        localIntent.putExtra("android.intent.extra.update_application_component_name", localComponentName.flattenToShortString());
        String str = "";
        if (count > 0) {
            if (count > 99) {
                str = "99";
            } else {
                str = String.valueOf(count);
            }
        }
        localIntent.putExtra("android.intent.extra.update_application_message_text", str);
        sendBroadcast(localIntent);
    }

}
