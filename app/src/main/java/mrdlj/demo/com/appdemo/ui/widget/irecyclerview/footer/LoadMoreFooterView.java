package mrdlj.demo.com.appdemo.ui.widget.irecyclerview.footer;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import mrdlj.demo.com.appdemo.R;


/**
 * Created by aspsine on 16/3/14.
 */
public class LoadMoreFooterView extends FrameLayout {

    private Status mStatus;

    private View mLoadingView;

    private View mErrorView;

    private View mTheEndView;

    private OnRetryListener mOnRetryListener;

    private TextView tvLoading;

    private TextView tvError;

    private TextView tvTheEnd;

    public LoadMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_irecyclerview_load_more_footer_view, this, true);

        mLoadingView = findViewById(R.id.loadingView);
        mErrorView = findViewById(R.id.errorView);
        mTheEndView = findViewById(R.id.theEndView);

        tvLoading = (TextView) mLoadingView.findViewById(R.id.tv_loading);
        tvError = (TextView) mErrorView.findViewById(R.id.tvError);
        tvTheEnd = (TextView) mTheEndView.findViewById(R.id.tvTheEnd);


        mErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRetryListener != null) {
                    mOnRetryListener.onRetry(LoadMoreFooterView.this);
                }
            }
        });

        setStatus(Status.GONE);
    }

    public void setOnRetryListener(OnRetryListener listener) {
        this.mOnRetryListener = listener;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
        change();
    }

    public boolean canLoadMore() {
        return mStatus == Status.GONE || mStatus == Status.ERROR;
    }

    private void change() {
        switch (mStatus) {
            case GONE:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mTheEndView.setVisibility(GONE);
                break;

            case LOADING:
                mLoadingView.setVisibility(VISIBLE);
                mErrorView.setVisibility(GONE);
                mTheEndView.setVisibility(GONE);
                break;

            case ERROR:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(VISIBLE);
                mTheEndView.setVisibility(GONE);
                break;

            case THE_END:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mTheEndView.setVisibility(VISIBLE);
                break;
        }
    }

    public enum Status {
        GONE, LOADING, ERROR, THE_END
    }

    public interface OnRetryListener {
        void onRetry(LoadMoreFooterView view);
    }

    /**
     * 设置加载更多Text
     *
     * @param text
     */
    public void setLoadingText(String text) {
        if (!TextUtils.isEmpty(text) && tvLoading != null) {
            tvLoading.setText(text);
        }
    }

    /**
     * 设置加载更多Text
     *
     * @param resId string资源id
     */
    public void setLoadingText(@StringRes int resId) {
        if (tvLoading != null) {
            tvLoading.setText(resId);
        }
    }

    /**
     * 设置出错Text
     *
     * @param text
     */
    public void setErrorText(String text) {
        if (!TextUtils.isEmpty(text) && tvError != null) {
            tvError.setText(text);
        }
    }

    /**
     * 设置出错Text
     *
     * @param resId string资源id
     */
    public void setErrorText(@StringRes int resId) {
        if (tvError != null) {
            tvError.setText(resId);
        }
    }

    /**
     * 设置结束页Text
     *
     * @param text
     */
    public void setTheEndText(String text) {
        if (!TextUtils.isEmpty(text) && tvTheEnd != null) {
            tvTheEnd.setText(text);
        }
    }

    /**
     * * 设置结束页Text
     *
     * @param resId string资源id
     */
    public void setTheEndText(@StringRes int resId) {
        if (tvTheEnd != null) {
            tvTheEnd.setText(resId);
        }
    }
}
