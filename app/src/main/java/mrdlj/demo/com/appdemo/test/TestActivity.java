package mrdlj.demo.com.appdemo.test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.base.OnItemClickListener;
import mrdlj.demo.com.appdemo.mvp.main.MainModel;
import mrdlj.demo.com.appdemo.mvp.main.MainPresenter;
import mrdlj.demo.com.appdemo.mvp.main.MainView;
import mrdlj.demo.com.appdemo.mvp.other.MvpActivity;
import mrdlj.demo.com.appdemo.ui.activity.SplashActivity;
import mrdlj.demo.com.appdemo.ui.dialogs.ProgressDialogs;
import mrdlj.demo.com.appdemo.ui.widget.TipView;
import mrdlj.demo.com.appdemo.ui.widget.banner.Banner;
import mrdlj.demo.com.appdemo.ui.widget.banner.BannerConfig;
import mrdlj.demo.com.appdemo.ui.widget.banner.Transformer;
import mrdlj.demo.com.appdemo.ui.widget.banner.listener.OnBannerListener;
import mrdlj.demo.com.appdemo.ui.widget.banner.loader.GlideImageLoader;
import mrdlj.demo.com.appdemo.ui.widget.irecyclerview.IRecyclerView;
import mrdlj.demo.com.appdemo.ui.widget.irecyclerview.OnLoadMoreListener;
import mrdlj.demo.com.appdemo.ui.widget.irecyclerview.OnRefreshListener;
import mrdlj.demo.com.appdemo.ui.widget.irecyclerview.footer.LoadMoreFooterView;
import mrdlj.demo.com.appdemo.ui.widget.skeleton.SkeletonScreen;
import mrdlj.demo.com.appdemo.ui.widget.stateview.StateView;
import mrdlj.demo.com.appdemo.utils.LogUtil;
import mrdlj.demo.com.appdemo.utils.RxBus;


public class TestActivity extends MvpActivity<MainPresenter> implements MainView, OnRefreshListener, OnLoadMoreListener, OnItemClickListener<String> {

    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout app_bar;
    ButtonBarLayout playButton;
    TabLayout tabs;


    private IRecyclerView iRecyclerView;
    //    private BannerView bannerView;
    private LoadMoreFooterView loadMoreFooterView;

    //    private TestAdapter mAdapter;
    private TestAdapter2 mAdapter;

    private int mPage;

    ArrayList<String> datas;
    TipView mTipView;
    SkeletonScreen skeletonScreen;

    Banner banner;
    List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("onActivityResult onCreate");
        mTipView = (TipView) findViewById(R.id.tip_view);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        toolbar.setTitle(R.string.app_name);
//        toolbar.setNavigationIcon(R.drawable.ic_back_1);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//
//        app_bar = (AppBarLayout) findViewById(R.id.appbar);
//        playButton = (ButtonBarLayout) findViewById(R.id.playButton);
//        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                reqYoutubeTest();
//            }
//        });
//        tabs = (TabLayout) findViewById(R.id.tabs);
//        tabs.setTabMode(TabLayout.MODE_FIXED);
//        tabs.addTab(tabs.newTab().setText("first"));
//        tabs.addTab(tabs.newTab().setText("second"));
//        tabs.addTab(tabs.newTab().setText("thrid"));
//        tabs.addTab(tabs.newTab().setText("four"));


//        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(TestFragment.newInstance("1", "1"));
//        fragmentList.add(TestFragment.newInstance("2", "2"));
//        fragmentList.add(TestFragment.newInstance("3", "3"));
//        fragmentList.add(TestFragment.newInstance("4", "4"));
//        TestAdapter viewPagerAdapter = new TestAdapter(getSupportFragmentManager(), fragmentList);
//        mViewPager.setAdapter(viewPagerAdapter);
//        tabs.setupWithViewPager(mViewPager,true);
//        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (verticalOffset == 0) {
//                    if (state != CollapsingToolbarLayoutState.EXPANDED) {//展开状态
//                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
//                        collapsingToolbarLayout.setTitle("");//设置title为EXPANDED
//                    }
//                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {//收起状态
//                        collapsingToolbarLayout.setTitle("");//设置title不显示
//                        playButton.setVisibility(View.VISIBLE);//隐藏播放按钮
//                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
//                    }
//                } else {
//                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {//中间状态
//                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
//                            playButton.setVisibility(View.VISIBLE);//由折叠变为中间状态时隐藏播放按钮
//                        }
//                        collapsingToolbarLayout.setTitle("");//设置title为INTERNEDIATE
//                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
//                    }
//                }
//            }
//        });


        iRecyclerView = (IRecyclerView) findViewById(R.id.iRecyclerView);
//        iRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        iRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        loadMoreFooterView = (LoadMoreFooterView) iRecyclerView.getLoadMoreFooterView();

        datas = new ArrayList<>();
        mAdapter = new TestAdapter2(this, datas);
        iRecyclerView.setIAdapter(mAdapter);
        TextView textView = new TextView(this);
        textView.setText("add_head");
        iRecyclerView.addHeaderView(textView);
        iRecyclerView.setOnRefreshListener(this);
        iRecyclerView.setOnLoadMoreListener(this);
        mAdapter.setOnItemClickListener(this);

      /*  iRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(true);
            }
        });*/
        initBanner();
        mvpPresenter.loadDataByRetrofitRxjava("111");
//        setStatusBarColor(0xffff0000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initBanner() {
        images = new ArrayList<>();
        View v = LayoutInflater.from(this).inflate(R.layout.banner_layout, null, false);
        banner = v.findViewById(R.id.banner_view);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                toastShow("Clicked [" + position + "]");
            }
        });
        if (v != null) {
            iRecyclerView.addHeaderView(v);
        }

        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //设置图片集合
//        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(images);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
//        banner.start();
    }

    @Override
    protected void initStateView() {
        super.initStateView();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    public boolean transStatusBar() {
        return false;
    }

    @Override
    public boolean fullScreen() {
        return false;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public boolean enableSlideClose() {
        return true;
    }

    private CollapsingToolbarLayoutState state;

    @Override
    public void onRefresh() {
//        loadBanner();datas.add("111111111111");
        LogUtil.d("onActivityResult 4");
        if(mStateView!= null) {
            mStateView.showLoading();
        }

        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
//        refresh();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(false);
                mTipView.show("网络不可用");
                datas.clear();
                datas.add("111111111111");
                datas.add("2111111111111");
                datas.add("3111111111111");
                datas.add("4111111111111");
                datas.add("5111111111111");
                datas.add("6111111111111");
                datas.add("7111111111111");
                datas.add("8111111111111");
                datas.add("9111111111111");
                datas.add("10111111111111");
                datas.add("11-111111111111");
                datas.add("12-111111111111");
                datas.add("13-111111111111");
                datas.add("14-111111111111");
                datas.add("15-111111111111");
                datas.add("16-111111111111");
                datas.add("17-111111111111");
                mAdapter.notifyDataSetChanged();
                images.clear();
                images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2701408155,2184514200&fm=27&gp=0.jpg");
                images.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3773529027,1138739684&fm=200&gp=0.jpg");
                images.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2584270420,477562920&fm=27&gp=0.jpg");
                images.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2613456534,2675973410&fm=27&gp=0.jpg");
                images.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3331867874,2565784416&fm=27&gp=0.jpg");
                banner.setImages(images);
                //banner设置方法全部调用完毕时最后调用
                banner.start();
                if(mStateView!= null) {
                    mStateView.showRetry();
                }
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        if (loadMoreFooterView.canLoadMore() && mAdapter.getItemCount() > 0) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
            loadMore();
        }
    }

    private void refresh() {
        mPage = 1;
//        NetworkAPI.requestImages(mPage, new NetworkAPI.Callback<List<Image>>() {
//            @Override
//            public void onSuccess(List<Image> images) {
//                iRecyclerView.setRefreshing(false);
//                if (ListUtils.isEmpty(images)) {
////                    mAdapter.clear();
//                    mAdapter.clearDatas();
//                } else {
//                    mPage = 2;
//                    List<String> data=new ArrayList<String>();
//                    for(int i=0;i<images.size();i++){
//                        data.add(images.get(i).title+"");
//                    }
////                    mAdapter.setList(data);
//                    mAdapter.setDatas(data);
//                }
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                e.printStackTrace();
//                iRecyclerView.setRefreshing(false);
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    int page = 1;

    private void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas.add("111111111111===>" + page);
                datas.add("111111111111===>" + page);
                datas.add("111111111111===>" + page);
                datas.add("111111111111===>" + page);
                mAdapter.notifyDataSetChanged();
                //还可以加载
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                mTipView.show("获取4条新数据");
                page++;
                //没有更多数据
//                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.THE_END);
                //失败，出错
//                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR);
            }
        }, 2000);

//        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
//        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.ERROR);
    }

    @Override
    public void onItemClick(int position, String s, View v) {
//        ArrayList<Class<? extends ViewPager.PageTransformer>> t = new ArrayList<>();
//        t.add(Transformer.Default);
//        t.add(Transformer.Accordion);
//        t.add(Transformer.BackgroundToForeground);
//        t.add(Transformer.ForegroundToBackground);
//        t.add(Transformer.CubeIn);
//        t.add(Transformer.CubeOut);
//        t.add(Transformer.DepthPage);
//        t.add(Transformer.FlipHorizontal);
//        t.add(Transformer.FlipVertical);
//        t.add(Transformer.RotateDown);
//        t.add(Transformer.RotateUp);
//        t.add(Transformer.ScaleInOut);
//        t.add(Transformer.Stack);
//        t.add(Transformer.Tablet);
//        t.add(Transformer.ZoomIn);
//        t.add(Transformer.ZoomOut);
//        t.add(Transformer.ZoomOutSlide);
//        int size = t.size();
//        int me = position % size;
//        banner.setBannerAnimation(t.get(me));
//        LogUtil.d("banner--->" + t.get(me).getSimpleName());

//        startActivity(new Intent(this, TestActivity.class));
//        checkRequiredPermission(this, permissionsArray);
//        DownloadUtil downloadUtil = new DownloadUtil(this, "http://app.mi.com/download/25323");
//        downloadUtil.setDownloadFileName("apkName" + System.currentTimeMillis() + ".apk");
//        downloadUtil.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        downloadUtil.start();

        showColorPicker();

//        final ProgressDialogs dialogs = new ProgressDialogs(this);
//        dialogs.setText(R.string.text_loading);
//        dialogs.show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (dialogs != null && dialogs.isShowing()) {
//                    dialogs.dismiss();
//                }
//                startActivity(new Intent(TestActivity.this, TestActivity.class));
//            }
//        }, 200);
    }

    @Override
    public void onRequestPermissionGranted(List grantedPermissions) {
        super.onRequestPermissionGranted(grantedPermissions);
        LogUtil.d("permissions=======>>" + (grantedPermissions == null ? " all null" : grantedPermissions.toString()));
    }

    @Override
    public void onRequestPermissionDenied(List deniedPermissions) {
        super.onRequestPermissionDenied(deniedPermissions);
        LogUtil.d("permissions=======>>" + (deniedPermissions == null ? "denine null" : deniedPermissions.toString()));
    }

    //所需要申请的权限数组
    private static final String[] permissionsArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS};

    @Override
    public void getDataSuccess(MainModel model) {
        iRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                iRecyclerView.setRefreshing(true);
            }
        });
    }

    @Override
    public void getDataFail(String msg) {
        datas.add("111111111111=failed==>" + page);
        datas.add("111111111111==failed=>" + page);
        datas.add("111111111111=failed==>" + page);
        datas.add("111111111111==failed=>" + page);
        mAdapter.notifyDataSetChanged();
//        iRecyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                iRecyclerView.setRefreshing(true);
////                images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2701408155,2184514200&fm=27&gp=0.jpg");
////                images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2701408155,2184514200&fm=27&gp=0.jpg");
////                images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2701408155,2184514200&fm=27&gp=0.jpg");
////                images.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2701408155,2184514200&fm=27&gp=0.jpg");
////                banner.update(images,images);
//            }
//        });
    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

}
