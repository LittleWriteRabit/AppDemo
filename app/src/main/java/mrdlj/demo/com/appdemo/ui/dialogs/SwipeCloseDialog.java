package mrdlj.demo.com.appdemo.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.utils.DisplayMetricsUtil;
import mrdlj.demo.com.appdemo.utils.UIUtils;

/**
 * @author: du on 2017/11/29 10:56.
 * @Email: 2857692313@qq.com
 * @description: TODO 滑动关闭Dialog 在外层布局内部添加关闭
 */

public class SwipeCloseDialog extends Dialog {

    private Context context;
    float startY;
    float moveY = 0;
    protected View rootView;

    public SwipeCloseDialog(Context context) {
        super(context);
        this.context = context;
    }

    public SwipeCloseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = getLayoutInflater().inflate(R.layout.dialog_swipe_layout, null);
        setContentView(rootView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.setWindowAnimations(R.style.bottomShow);
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        params.height = DisplayMetricsUtil.dip2px(context, 300);
        params.width = DisplayMetricsUtil.getScreenWidth(context);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(params);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getY() - startY;
                rootView.scrollBy(0, -(int) moveY);
                startY = ev.getY();
                if (rootView.getScrollY() > 0) {
                    rootView.scrollTo(0, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (rootView.getScrollY() < -this.getWindow().getAttributes().height / 4 && moveY > 0) {
                    this.dismiss();
                }
                rootView.scrollTo(0, 0);
                break;
        }
        return super.onTouchEvent(ev);
    }

}
