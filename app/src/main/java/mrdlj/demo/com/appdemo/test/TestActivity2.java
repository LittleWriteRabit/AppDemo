package mrdlj.demo.com.appdemo.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.base.BaseActivity;
import mrdlj.demo.com.appdemo.mvp.other.BasePresenter;

/**
 * Created by du on 2017/10/23 10:45.
 */

public class TestActivity2 extends BaseActivity {
    AppBarLayout app_bar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    Toolbar toolbar;
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        app_bar = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {//展开状态
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        collapsingToolbarLayout.setTitle("");//设置title为EXPANDED
//                        title.setText("");
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {//收起状态
                        collapsingToolbarLayout.setTitle("");//设置title不显示
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
//                        title.setText(R.string.app_name);
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {//中间状态
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            //由折叠变为中间状态时
//                            title.setText("");
                        }
                        collapsingToolbarLayout.setTitle("");//设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
                float alpha = (float) Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange();
                toolbar.setAlpha(alpha);
                Log.d("alpha====",">>"+alpha);
            }
        });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.layout_test2;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


}
