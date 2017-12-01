package mrdlj.demo.com.appdemo.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import jp.wasabeef.glide.transformations.BlurTransformation;
import mrdlj.demo.com.appdemo.R;

/**
 * Created by Administrator on 2017/5/25.
 */

public class GlideUtil {

    /**
     * DiskCacheStrategy.NONE :不缓存图片
     * DiskCacheStrategy.SOURCE :缓存图片源文件
     * DiskCacheStrategy.RESULT:缓存修改过的图片
     * DiskCacheStrategy.ALL:缓存所有的图片，默认
     */

    public static void setImage(Activity activity, ImageView imageview, String url) {
        GlideApp.with(activity)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(imageview);
    }

    public static void setImage(Fragment fragment, ImageView imageview, String url) {
        GlideApp.with(fragment)
                .asBitmap()//.asGif()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()//是否显示动画
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
//                .priority( Priority.HIGH )//设置加载优先级
//                .dontAnimate() //不显示动画效果
                .into(imageview);

    }

    public static void setImage(final Context context, final ImageView view, final String url) {
        if(context instanceof Activity){
            if(((Activity) context).isFinishing()){
                return;
            }else{
                GlideApp.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(view);
            }
        }else if(context!= null) {
            GlideApp.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(view);
        }
    }

    /**
     * 是否有加载动画 banner有动画会出现问题，这里隐藏掉
     * @param context
     * @param view
     * @param url
     * @param hasAnim
     */
    public static void setImage(final Context context, final ImageView view, final String url, boolean hasAnim) {
        if(hasAnim) {
            GlideApp.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(view);
        }else{
            GlideApp.with(context).load(url).dontAnimate().placeholder(R.mipmap.ic_launcher).into(view);
        }
    }

    public static void setImageBanner(final Context context, final ImageView view, final String url) {
        GlideApp.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                view.setImageBitmap(resource);
            }
        });
    }

    public static void setCircleImage(Context context, final ImageView view, final String url, int resId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(resId)
                .error(resId)
                .transform(new GlideCircleTransform())
                .into(view);
    }

    public static void setImagePlayLists(Context context, final ImageView view, final String url, int resId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(resId)
                .error(resId)
                .into(view);
    }

    public static void setImagePlaylist(final Context context, final ImageView view, final String
            url) {
        GlideApp.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(view);
    }

    public static void setImage(final Context context, final ImageView view, final int resid) {
        Glide.with(context).load(resid).into(view);
    }

//    public static void setImageBlur(final Context context, final ImageView view, final String url) {
//        GlideApp.with(context).load(url).transition(new BlurTransformation(context,100))
//                .into(view);
//    }
//
//    public static void setImageBlur(final Context context, final ImageView view, final int resid) {
//        Glide.with(context).load(resid).bitmapTransform(new BlurTransformation(context, 100))
//                .into(view);
//    }

    public static void setThemeColor(final Context context, String url, final CollapsingToolbarLayout toolbarLayout) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                Drawable drawable =new BitmapDrawable(resource);
                toolbarLayout.setContentScrim(drawable);
            }
        });
    }
}
