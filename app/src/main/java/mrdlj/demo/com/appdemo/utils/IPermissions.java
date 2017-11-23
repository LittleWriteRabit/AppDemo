package mrdlj.demo.com.appdemo.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import java.util.List;

/**
 * Created by du on 2017/9/20 10:18.
 * >=M运行时权限请求
 */

public interface IPermissions {

    public List<String> mshouldShowRequestPermissionRationale(Activity activity, List<String> permissionsList);

    public void checkRequiredPermission(Activity activity, String... permissions);

    public void onMRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    public abstract void onRequestPermissionGranted(List<String> grantedPermissions);

    public abstract void onRequestPermissionDenied(List<String> deniedPermissions);

    public AlertDialog onShowRationaleDialog(Activity activity,List<String> permissions);


}
