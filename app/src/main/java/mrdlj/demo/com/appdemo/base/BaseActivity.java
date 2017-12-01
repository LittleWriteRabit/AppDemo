package mrdlj.demo.com.appdemo.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import butterknife.ButterKnife;
import mrdlj.demo.com.appdemo.AppReader;
import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.mvp.other.BasePresenter;
import mrdlj.demo.com.appdemo.retrofit.ApiClient;
import mrdlj.demo.com.appdemo.retrofit.ApiStores;
import mrdlj.demo.com.appdemo.test.TestActivity;
import mrdlj.demo.com.appdemo.ui.dialogs.ProgressDialogs;
import mrdlj.demo.com.appdemo.ui.widget.slidecloselib.SlidingLayout;
import mrdlj.demo.com.appdemo.ui.widget.stateview.StateView;
import mrdlj.demo.com.appdemo.utils.IPermissions;
import mrdlj.demo.com.appdemo.utils.LogUtil;
import mrdlj.demo.com.appdemo.utils.UIUtils;
import mrdlj.demo.com.appdemo.utils.news.StatusBarHelper;
import mrdlj.demo.com.appdemo.utils.statusbar.Eyes;
import retrofit2.Call;
import rx.subscriptions.CompositeSubscription;


/**
 * @author du 2017/11/29 10:23
 * @Email: 2857692313@qq.com
 * @deccription:TODO
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IPermissions {
    /**
     * 申请权限后的返回码
     */
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 0x1001;

    /**
     * 跳转到Setting界面 请求码2 startActivityForResult
     */
    private static final int REQUEST_SETTING_PERMISSION = 0x1002;

    public Activity mActivity;
    //    private CompositeSubscription mCompositeSubscription;
    //    private List<Call> calls;
    //    protected T mPresenter;
    private static long mPreTime;
    private static Activity mCurrentActivity;
    /**
     * 对所有activity进行管理
    */
    public static List<Activity> mActivities = new LinkedList<Activity>();
    protected Bundle savedInstanceState;
    protected StateView mStateView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (transStatusBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
        if (fullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        if (enableSlideClose()) {
            SlidingLayout rootView = new SlidingLayout(this);
            rootView.bindActivity(this);
        }

        /**
         *初始化的时候将其添加到集合中
         */
        synchronized (mActivities) {
            mActivities.add(this);
        }
        /**
         * 子类不再需要设置布局ID，也不再需要使用ButterKnife.bind()
         */
        setContentView(provideContentViewId());
        ButterKnife.bind(this);
        initStateView();
        loadColor();
    }

    /**
     * 初始化StateView 加载中等等
     */
    protected void initStateView() {
        mStateView = StateView.inject(this);
        mStateView.setEmptyResource(R.layout.base_empty);
        mStateView.setRetryResource(R.layout.base_retry);
        mStateView.setLoadingResource(R.layout.base_loading);
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                onRetryClickListener();
            }
        });
    }

    /**
     * 点击了重试
     */
    protected void onRetryClickListener() {
    }

    /**
     * 得到当前界面的布局文件id(由子类实现)
     */
    protected abstract int provideContentViewId();

    /**
     * 用于创建Presenter和判断是否使用MVP模式(由子类实现)
     *
     * @return 返回Presenter
     */
    protected abstract T createPresenter();

    public boolean enableSlideClose() {
        return true;
    }

    public boolean transStatusBar() {
        return false;
    }

    public boolean fullScreen() {
        return false;
    }

    public void setStatusBarColor(int statusBarColor) {
        Eyes.setStatusBarColor(this, statusBarColor);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d("onActivityResult onDestroy");
        /**
         * 销毁的时候从集合中移除
         */
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    /**
     * 退出应用的方法
     */
    public static void exitApp() {
        ListIterator<Activity> iterator = mActivities.listIterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            next.finish();
        }
    }

    /**
     * 接口调用
     *
     * @return
     */
    public ApiStores apiStores() {
        return ApiClient.retrofit().create(ApiStores.class);
    }

    public Toolbar initToolBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolBarAsHome(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public ProgressDialogs progressDialog;

    public ProgressDialogs showProgressDialog() {
        progressDialog = new ProgressDialogs(mActivity);
        progressDialog.setCancelable(cancelAble());
        progressDialog.setCanceledOnTouchOutside(cancelAbleOnTouchOutside());
        progressDialog.setText(R.string.text_loading);
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialogs showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialogs(mActivity);
        progressDialog.setCancelable(cancelAble());
        progressDialog.setCanceledOnTouchOutside(cancelAbleOnTouchOutside());
        progressDialog.setText(message);
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialogs showProgressDialog(@StringRes int resId) {
        progressDialog = new ProgressDialogs(mActivity);
        progressDialog.setCancelable(cancelAble());
        progressDialog.setCanceledOnTouchOutside(cancelAbleOnTouchOutside());
        progressDialog.setText(resId);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 点击返回可以取消掉ProgressDialog
     *
     * @return true 可以 false 不可以
     */
    public boolean cancelAble() {
        return true;
    }

    /**
     * 点击外部可以取消掉ProgressDialog
     *
     * @return true 可以 false 不可以
     */
    public boolean cancelAbleOnTouchOutside() {
        return true;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof TestActivity) {
            /**
             * 如果是主页面
             */
            if (System.currentTimeMillis() - mPreTime > getResources().getInteger(R.integer.time_second_exit)) {// 两次点击间隔大于2秒
                UIUtils.showToast(R.string.two_second_exit);
                mPreTime = System.currentTimeMillis();
                return;
            }
            exitApp();
        }
        super.onBackPressed();
    }

/**
 * retrofit 请求接口回调及取消掉回调
 */
//    public void addCalls(Call call) {
//        if (calls == null) {
//            calls = new ArrayList<>();
//        }
//        calls.add(call);
//    }
//
//    private void callCancel() {
//        if (calls != null && calls.size() > 0) {
//            for (Call call : calls) {
//                if (!call.isCanceled())
//                    call.cancel();
//            }
//            calls.clear();
//        }
//    }

    /**
     * 添加Rxjava监听和取消注册
     */
//    public void addSubscription(Observable observable, Subscriber subscriber) {
//        if (mCompositeSubscription == null) {
//            mCompositeSubscription = new CompositeSubscription();
//        }
//        mCompositeSubscription.add(observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber));
//    }

    /**
     * 添加Rxjava监听和取消注册
     */
 /*   public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {
        LogUtil.d("onUnsubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }*/


    private List<String> mPermissions = new ArrayList<>();
    String[] permissions;
    boolean requestFlag = false;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void checkRequiredPermission(Activity activity, String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onRequestPermissionGranted(Arrays.asList(permissions));
            return;
        }
        LogUtil.d("onActivityResult 3");
        this.permissions = permissions;
        if (permissions == null || permissions.length == 0) {
            onRequestPermissionGranted(null);
            return;
        }
        List<String> permissionsList = new ArrayList<String>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
            }
        }

        List<String> deniedAndNoRequest = mshouldShowRequestPermissionRationale(activity, permissionsList);
        if (deniedAndNoRequest == null) {
            if (permissionsList != null && permissionsList.size() > 0) {
                requestFlag = true;
                ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                onRequestPermissionGranted(Arrays.asList(permissions));
            }
        } else {
            AlertDialog dialog = onShowRationaleDialog(activity, deniedAndNoRequest);
            if (dialog != null) {
                dialog.show();
            } else {
                onRequestPermissionDenied(permissionsList);
            }
        }
    }

    /**
     * 重写该方法弹出dialog询问
     * @param activity
     * @param permissions
     * @return
     */
    @Override
    public AlertDialog onShowRationaleDialog(Activity activity, List<String> permissions) {
        AlertDialog dialog = null;
        if (activity != null && !activity.isFinishing()) {
            dialog = new AlertDialog.Builder(activity)
                    .setTitle("请求权限")
                    .setMessage("xxxx需要。。" + permissions.toString() + "。权限才可以。。。")
                    .setNegativeButton("CANCEL", null)
                    .setPositiveButton("GO SETTING", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startAppSetting();
                        }
                    })
                    .setNeutralButton("Netural", null)
                    .create();
        }
        return dialog;
    }

    /**
     * 使用startActivityForResult的方式启动应用设置界面
     */
    public void startAppSetting() {
        Intent in = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        in.setData(uri);
        startActivityForResult(in, REQUEST_SETTING_PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("onActivityResult 1");
        switch (requestCode) {
            case REQUEST_SETTING_PERMISSION:
                //在这里再次检查权限进行拍照
                LogUtil.d("onActivityResult 2");
                checkRequiredPermission(this, permissions);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults.length > i && grantResults[i] == PackageManager.PERMISSION_GRANTED) {//请求成功的权限
                        granted.add(permissions[i]);
                        onRequestPermissionGranted(granted);
                    } else {//请求失败的权限
                        denied.add(permissions[i]);
                        onRequestPermissionDenied(denied);
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onMRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onRequestPermissionDenied(List<String> deniedPermissions) {
    }

    @Override
    public void onRequestPermissionGranted(List<String> grantedPermissions) {
    }

    @Override
    public List<String> mshouldShowRequestPermissionRationale(Activity activity, List<String> permissionsList) {
        List<String> deniedCheck = new ArrayList<>();
        deniedCheck.clear();
        //拒绝且不再询问的存起来搞
        for (int i = 0; i < permissionsList.size(); i++) {
            if (requestFlag && !ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permissionsList.get(i))) {//设置为勾选不再显示的时候调用
                deniedCheck.add(permissionsList.get(i));
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
        }
        if (deniedCheck == null || deniedCheck.size() == 0) {
            return null;
        } else {
            return deniedCheck;
        }
    }


    private int initialColor = 0xffff0040;
    private int initialColors[] = new int[2];
    private static final String PREF_BACKGROUND_COLOR = "BACKGROUND_COLOR";
    private static final String PREF_TEXT_COLOR = "TEXT_COLOR";
    public static final String PREF_ACCOUNT_NAME = "accountName";

    /**
     * Loads app theme color saved in preferences
     */
    private void loadColor() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int backgroundColor = sp.getInt(PREF_BACKGROUND_COLOR, -1);
        int textColor = sp.getInt(PREF_TEXT_COLOR, -1);

        if (backgroundColor != -1 && textColor != -1) {
            setColors(backgroundColor, textColor);
        } else {
            initialColors = new int[]{
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    ContextCompat.getColor(this, R.color.textColorPrimary)};
        }
    }

    protected void showColorPicker() {
        //在设置前调用 主题颜色设置为白色
        ColorPickerDialogBuilder
                .with(this)
                .setTitle(getString(R.string.choose_colors))
                .initialColor(initialColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .setPickerCount(2)
                .initialColors(initialColors)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton(getString(R.string.ok), new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        //changeBackgroundColor(selectedColor);
                        if (allColors != null) {
                            setColors(allColors[0], allColors[1]);
                        }
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .showColorEdit(true)
                .build()
                .show();
    }

    /**
     * Save app theme color in preferences
     */
    private void setColors(int backgroundColor, int textColor) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setBackgroundColor(backgroundColor);
            toolbar.setTitleTextColor(textColor);
        }
//        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
//        tabs.setBackgroundColor(backgroundColor);
//        tabs.setTabTextColors(textColor, textColor);
        setStatusBarColor(backgroundColor);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt(PREF_BACKGROUND_COLOR, backgroundColor).apply();
        sp.edit().putInt(PREF_TEXT_COLOR, textColor).apply();

        initialColors[0] = backgroundColor;
        initialColors[1] = textColor;
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public void setStatusBarColor(int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(color);
//        }
//    }

//    private void doIt() {
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//        int backgroundColor = sp.getInt(PREF_BACKGROUND_COLOR, -1);
//        int textColor = sp.getInt(PREF_TEXT_COLOR, -1);
//        setTheme(R.style.customTheme);
//    }

}
