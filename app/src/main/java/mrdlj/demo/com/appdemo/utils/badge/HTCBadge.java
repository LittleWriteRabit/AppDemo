package mrdlj.demo.com.appdemo.utils.badge;

import android.content.ComponentName;
import android.content.Intent;

public class HTCBadge extends BaseBadge {
    private final static String NAME = "com.htc.launcher";

    public static boolean isSelf(String name) {
        return NAME.equalsIgnoreCase(name);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected void setBadge(int count) {
        Intent setNotificationIntent = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
        ComponentName localComponentName = new ComponentName(getPackageName(), getLancherClassName());
        setNotificationIntent.putExtra("com.htc.launcher.extra.COMPONENT", localComponentName.flattenToShortString());
        setNotificationIntent.putExtra("com.htc.launcher.extra.COUNT", count);
        sendBroadcast(setNotificationIntent);

        // byte[] arrayOfByte = BadgeImageUtil.a(this.a, paramInt);
        // String str =
        // context.getResources().getText(context.getResources().getIdentifier("app_name",
        // "string", context.getPackageName())).toString();
        // Uri localUri =
        // Uri.parse("content://com.htc.launcher.settings/favorites?notify=true");
        // ContentResolver localContentResolver = context.getContentResolver();
        // ContentValues localContentValues = new ContentValues();
        // localContentValues.put("iconType", Integer.valueOf(1));
        // localContentValues.put("itemType", Integer.valueOf(1));
        // localContentValues.put("icon", arrayOfByte);
        // localContentResolver.update(localUri, localContentValues, "title=?",
        // new String[] { str });
    }

}
