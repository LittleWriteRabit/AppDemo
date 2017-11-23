package mrdlj.demo.com.appdemo.retrofit;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by du on 2017/9/14 14:38.
 */

public class DataSources {

    public static Map<String, String> common;
    private static ApiStores sApiStores;

    static {
        sApiStores = ApiClient.retrofit().create(ApiStores.class);
        common = new HashMap<>();
        common.put("device", "android");
    }

    public static Map<String, String> getCommons() {
        common = new HashMap<>();
        common.put("device", "android");
        return common;
    }

    public static Observable<String> loadDataByRetrofitRxjava() {
        return sApiStores.onLoginStatus(common);
    }
}
