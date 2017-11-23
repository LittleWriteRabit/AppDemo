package mrdlj.demo.com.appdemo.utils.badge;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mrdlj.demo.com.appdemo.AppReader;


public class BadgeUtils {
    static Map<String, BaseBadge> map = new HashMap<String, BaseBadge>();

    static {
        init();
    }

    private static void init() {
        //
        String phoneLaucher = getLauncherName(AppReader.getInstance().getApplicationContext());
        if (phoneLaucher != null) {
            BaseBadge badge = null;
            if (HTCBadge.isSelf(phoneLaucher)) {
                badge = new HTCBadge();
            } else if (Qihoo360Badge.isSelf(phoneLaucher)) {
                badge = new Qihoo360Badge();
            } else if (SamsungBadge.isSelf(phoneLaucher)) {
                badge = new SamsungBadge();
            } else if (SonyBadge.isSelf(phoneLaucher)) {
                badge = new SonyBadge();
            } else if (VivoBadge.isSelf(phoneLaucher)) {
                badge = new VivoBadge();
            } else if (XiaomiBadge.isSelf(phoneLaucher)) {
                badge = new XiaomiBadge();
            } else {
                badge = new OtherBadge();
            }
            if (badge != null) {
                map.put(badge.getName(), badge);
            }
        }
    }

    public static void setBadge(Context context, int count) {
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            BaseBadge badge = map.get(iterator.next());
            badge.setBadge(count);
        }
    }

    public static void clearBadge() {
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            BaseBadge badge = map.get(iterator.next());
            badge.setBadge(0);
        }
    }

    /**
     * 获取手机的launcher name
     *
     * @param context
     * @return
     */
    private static String getLauncherName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null)
            return resolveInfo.activityInfo.packageName;
        else
            return "";
    }
}
