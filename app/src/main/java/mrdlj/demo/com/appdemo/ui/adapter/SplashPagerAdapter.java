package mrdlj.demo.com.appdemo.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import java.util.List;

/**
 * Created by du on 2017/10/13 10:02.
 * 开屏页ViewPager显示
 */

public class SplashPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> frags;

    public SplashPagerAdapter(FragmentManager fm, List<Fragment> frags) {
        super(fm);
        this.frags = frags;
    }

    @Override
    public Fragment getItem(int position) {
        return frags == null ? null : (position < frags.size() ? frags.get(position) : null);
    }

    @Override
    public int getCount() {
        return frags == null ? 0 : frags.size();
    }
}
