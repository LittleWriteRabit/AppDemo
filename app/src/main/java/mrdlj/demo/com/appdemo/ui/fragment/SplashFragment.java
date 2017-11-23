package mrdlj.demo.com.appdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.base.BaseFragment;
import mrdlj.demo.com.appdemo.data.Constants;
import mrdlj.demo.com.appdemo.mvp.presenters.SplashFragPresenter;
import mrdlj.demo.com.appdemo.mvp.views.ISplashFragView;
import mrdlj.demo.com.appdemo.test.TestActivity;
import mrdlj.demo.com.appdemo.ui.activity.LauncherActivity;
import mrdlj.demo.com.appdemo.utils.SharedPreferencesUtil;
import mrdlj.demo.com.appdemo.utils.glide.GlideUtil;

/**
 * Created by du on 2017/10/13 10:06.
 * 用fragment使用
 */

public class SplashFragment extends BaseFragment<SplashFragPresenter> implements ISplashFragView {

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_tag)
    TextView mTvTag;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public SplashFragment() {
        // Required empty public constructor
    }

    public static SplashFragment newInstance(String param1, String param2) {
        SplashFragment fragment = new SplashFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected SplashFragPresenter createPresenter() {
        return new SplashFragPresenter(this, mParam1);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.layout_frag_splash;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        GlideUtil.setImage(this, mIvSplash, "");
//        mTvTag.setText(mParam1 + "");
        mPresenter.getImages();
//        mStateView.showLoading();
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showImage(String url) {
        GlideUtil.setImage(this, mIvSplash, url);
    }

    @Override
    public void showText(String text) {
        mTvTag.setText(text + "");
    }

    @OnClick(R.id.tv_tag)
    public void onClickTag() {
        if (isAdded()) {//保存是否第一次进入 进入之后就不是第一次进入
            SharedPreferencesUtil.setBoolean(getActivity(), Constants.FIRST_OPEN_APP, false);
            getActivity().startActivity(new Intent(getActivity(), TestActivity.class));
            getActivity().finish();
        }
    }
}
