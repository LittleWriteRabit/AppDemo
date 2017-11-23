package mrdlj.demo.com.appdemo.ui.widget.skeleton;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import mrdlj.demo.com.appdemo.R;

/**
 * Created by ethanhua on 2017/7/29.
 */

public class ShimmerViewHolder extends RecyclerView.ViewHolder {

    public ShimmerViewHolder(LayoutInflater inflater, ViewGroup parent, int innerViewResId) {
        super(inflater.inflate(R.layout.layout_shimmer, parent, false));
        ViewGroup layout = (ViewGroup) itemView;
        inflater.inflate(innerViewResId, layout, true);
    }
}
