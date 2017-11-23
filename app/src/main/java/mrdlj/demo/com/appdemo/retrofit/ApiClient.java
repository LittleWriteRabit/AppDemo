package mrdlj.demo.com.appdemo.retrofit;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mrdlj.demo.com.appdemo.BuildConfig;
import mrdlj.demo.com.appdemo.utils.LogUtil;
import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit 初始化
 */
public class ApiClient {
    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES));
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request.Builder builder = chain.request().newBuilder();
                    Request build = builder.build();
                    if (BuildConfig.DEBUG) {
                        LogUtil.d("=== Current url ==>>" + build.url().toString());
                        //如果要添加所有请求都带有的参数 则可以用下面的方式
//                        Request original = chain.request();
//                        HttpUrl originalHttpUrl = original.url();
//
//                        HttpUrl url = originalHttpUrl.newBuilder()
//                                .addQueryParameter("apikey", "your-actual-api-key")
//                                .build();
//
//                        // Request customization: add request headers
//                        Request.Builder requestBuilder = original.newBuilder()
//                                .url(url);
//
//                        Request request = requestBuilder.build();
//                        return chain.proceed(request);
                    }
                    return chain.proceed(build);
                }
            });
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
                //通过拦截器添加公共参数
//                builder.addInterceptor(new CommonInterceptor("a","b"));
            }
            //连接池
            OkHttpClient okHttpClient = builder.build();
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            if (BuildConfig.DEBUG) {
                retrofitBuilder.baseUrl(ApiStores.API_SERVER_URL_DEBUG);
            } else {
                retrofitBuilder.baseUrl(ApiStores.API_SERVER_URL);
            }
            mRetrofit = retrofitBuilder
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return mRetrofit;
    }

}
