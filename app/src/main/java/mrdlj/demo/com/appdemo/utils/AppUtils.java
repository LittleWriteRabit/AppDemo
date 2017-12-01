package mrdlj.demo.com.appdemo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

import mrdlj.demo.com.appdemo.BuildConfig;

/**
 * app软键盘 小数
 * 把Bitmap转Byte
 * * MD5加密
 * installAPK
 * * 跳到拨号盘-拨打电话
 * * 直接拨号，需要增加CALL_PHONE权限
 */
public class AppUtils {
    /**
     * 关闭键盘
     *
     * @param activity Activity
     */
    public static void hideSoftInput(Activity activity) {
        if (activity.getCurrentFocus() != null)
            ((InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(activity.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 指定小数输出
     * 0：
     * 比实际数字的位数多，不足的地方用0补上。
     * new DecimalFormat("00.00").format(3.14)  //结果：03.14
     * new DecimalFormat("0.000").format(3.14)  //结果： 3.140
     * new DecimalFormat("00.000").format(3.14)  //结果：03.140
     * 比实际数字的位数少：整数部分不改动，小数部分，四舍五入
     * new DecimalFormat("0.000").format(13.146)  //结果：13.146
     * new DecimalFormat("00.00").format(13.146)  //结果：13.15
     * new DecimalFormat("0.00").format(13.146)  //结果：13.15
     * #：
     * 比实际数字的位数多，不变。
     * new DecimalFormat("##.##").format(3.14)  //结果：3.14
     * new DecimalFormat("#.###").format(3.14)  //结果： 3.14
     * new DecimalFormat("##.###").format(3.14)  //结果：3.14
     * 比实际数字的位数少：整数部分不改动，小数部分，四舍五入
     * new DecimalFormat("#.###").format(13.146)  //结果：13.146
     * new DecimalFormat("##.##").format(13.146)  //结果：13.15
     * new DecimalFormat("#.##").format(13.146)  //结果：13.15
     *
     * @param s      输入
     * @param format 小数格式，比如保留两位0.00
     * @return 输出结果
     */
    public static String decimalFormat(double s, String format) {
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(s);
    }


    /**
     * 把Bitmap转Byte
     *
     * @param bitmap bitmap对象
     * @return Bytes
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    /**
     * MD5加密
     *
     * @param plainText 需要加密的字符串
     * @return 加密后字符串
     */
    public static String md5(String plainText) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString().toLowerCase();// 32位的加密（转成小写）

            buf.toString().substring(8, 24);// 16位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 安装apk
     *
     * @param context 上下文
     * @param path    文件路劲
     */
    public static void installAPK(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        File apkFile = new File(path);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 直接拨号，需要增加CALL_PHONE权限
     *
     * @param context 上下文
     * @param phone   手机号码
     */
    public static void actionCall(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.setAction(Intent.ACTION_CALL);// 直接拨号
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

    /**
     * 跳到拨号盘-拨打电话
     *
     * @param context 上下文
     * @param phone   手机号码
     */
    public static void actionDial(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        intent.setAction(Intent.ACTION_DIAL);// 拨号盘
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(intent);
    }

}
