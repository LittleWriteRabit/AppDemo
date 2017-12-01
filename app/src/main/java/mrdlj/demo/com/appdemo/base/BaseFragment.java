package mrdlj.demo.com.appdemo.base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.mvp.other.BasePresenter;
import mrdlj.demo.com.appdemo.ui.widget.stateview.StateView;

/**
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    protected T mPresenter;
    private View rootView;
    protected StateView mStateView;//用于显示加载中、网络异常，空布局、内容布局
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            ButterKnife.bind(this, rootView);
            mStateView = StateView.inject(getStateViewRoot());
            initStateView();
//            if (mStateView != null){
//                mStateView.setLoadingResource(R.layout.page_loading);
//                mStateView.setRetryResource(R.layout.page_net_error);
//            }
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        rootView = null;
    }

    /**
     * StateView的根布局，默认是整个界面，如果需要变换可以重写此方法
     */
    public View getStateViewRoot() {
        return rootView;
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    //加载数据
    protected abstract void loadData();

    public Toolbar initToolBar(View view, String title) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
        return toolbar;
    }

    /**
     * 初始化StateView 加载中等等 注意ProgressBar的样式 及颜色
     */
    protected void initStateView() {
        if (mStateView != null) {
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
    }

    /**
     * 点击了重试
     */
    protected void onRetryClickListener() {
    }


//    private CompositeSubscription mCompositeSubscription;
//
//    public void onUnsubscribe() {
//        //取消注册，以避免内存泄露
//        if (mCompositeSubscription != null) {
//            mCompositeSubscription.unsubscribe();
//        }
//    }
//
//    public void addSubscription(Subscription subscription) {
////        if (mCompositeSubscription == null) {
//        mCompositeSubscription = new CompositeSubscription();
////        }
//        mCompositeSubscription.add(subscription);
//    }
}
