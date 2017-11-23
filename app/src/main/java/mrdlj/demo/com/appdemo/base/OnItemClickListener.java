package mrdlj.demo.com.appdemo.base;

import android.view.View;

/**
 * Created by aspsine on 16/4/6.
 */
public interface OnItemClickListener<T> {
    void onItemClick(int position, T t, View v);
}
