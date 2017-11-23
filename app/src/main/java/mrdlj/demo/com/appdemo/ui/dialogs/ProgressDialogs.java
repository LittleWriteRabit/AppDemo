package mrdlj.demo.com.appdemo.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import mrdlj.demo.com.appdemo.R;

/**
 * @author du 2017-11-23 23:36:01
 * Email:dulijie2162@163.com
 * @description TODO 加载中dialog
 */
public class ProgressDialogs extends Dialog {

    Unbinder unbinder;
    @BindView(R.id.tv_progress)
    TextView tvTips;

    public ProgressDialogs(@NonNull Context context) {
        super(context, R.style.progress_dialog_no_frame);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.progress_dialog);
        unbinder = ButterKnife.bind(this, this);
    }

    /**
     * 设置进度内容
     * @param text
     */
    public void setText(String text) {
        if (!TextUtils.isEmpty(text) && tvTips != null) {
            tvTips.setText(text);
            tvTips.setVisibility(View.VISIBLE);
        }else{
            tvTips.setVisibility(View.GONE);
        }
    }

    /**
     * 设置进度内容
     * @param text
     */
    public void setText(CharSequence text) {
        if (!TextUtils.isEmpty(text) && tvTips != null) {
            tvTips.setText(text);
            tvTips.setVisibility(View.VISIBLE);
        }else{
            tvTips.setVisibility(View.GONE);
        }
    }

    /**
     * @param resId string resId
     */
    public void setText(@StringRes int resId) {
        if (tvTips != null) {
            tvTips.setText(resId);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
