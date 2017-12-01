package mrdlj.demo.com.appdemo.test;

import android.content.Context;
import android.view.View;

import java.util.List;

import mrdlj.demo.com.appdemo.R;
import mrdlj.demo.com.appdemo.base.BaseAdapter;
import mrdlj.demo.com.appdemo.base.OnItemClickListener;
import mrdlj.demo.com.appdemo.base.ViewHolder;


/**
 * Created by du on 2017/9/5 17:27.
 */

public class TestAdapter2 extends BaseAdapter<String> {


    public TestAdapter2(Context context, List<String> datas) {
        super(context, R.layout.item_test_layout, datas);
    }

    @Override
    public void convert(ViewHolder holder, final String s, final int position) {
        holder.setText(R.id.tv_text,s+"");
        holder.setOnclickListener(holder.itemView.getId(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!= null){
                    listener.onItemClick(position,s,view);
                }
            }
        });
    }

    private OnItemClickListener<String> listener;

    public void setOnItemClickListener(OnItemClickListener<String> listener) {
        this.listener = listener;
    }
}
