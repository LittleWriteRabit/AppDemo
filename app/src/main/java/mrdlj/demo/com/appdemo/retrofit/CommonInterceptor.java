package mrdlj.demo.com.appdemo.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 封装公共参数 例如（Key和密码）
 * 在 ApiClient  添加到OkHttpClient
 * CommonInterceptor commonInterceptor = new CommonInterceptor( "key", "Secret");
 * OkHttpClient client = new OkHttpClient.Builder()
 * .addInterceptor(commonInterceptor)
 * .build();
 * <p>
 */
public class CommonInterceptor implements Interceptor {
    private final String mApiKey;
    private final String mApiSecret;

    public CommonInterceptor(String apiKey, String apiSecret) {
        mApiKey = apiKey;
        mApiSecret = apiSecret;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        String marvelHash = "ApiUtils.generateMarvelHash(mApiKey, mApiSecret)";
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("MarvelService.PARAM_API_KEY", "mApiKey")
                .addQueryParameter("MarvelService.PARAM_TIMESTAMP", "ApiUtils.getUnixTimeStamp()")
                .addQueryParameter("MarvelService.PARAM_HASH", "marvelHash");

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}