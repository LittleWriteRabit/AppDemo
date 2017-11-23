package mrdlj.demo.com.appdemo.retrofit;


import java.util.Map;

import mrdlj.demo.com.appdemo.mvp.main.MainModel;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 访问接口
 */
public interface ApiStores {
    //debug baseUrl
    String API_SERVER_URL_DEBUG = "http://www.weather.com.cn/";
    //release baseUrl
    String API_SERVER_URL = "http://www.weather.com.cn/";

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Call<MainModel> loadDataByRetrofit(@Path("cityId") String cityId);

    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<MainModel> loadDataByRetrofitRxjava(@Path("cityId") String cityId);

    @FormUrlEncoded
    @POST("/gologin/")
    Observable<String> onLoginStatus(@FieldMap Map<String, String> map);
}
