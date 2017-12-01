package mrdlj.demo.com.appdemo.data.source;

import mrdlj.demo.com.appdemo.AppReader;
import rx.Observable;

/**
 * @author du 2017/12/1 13:14
 * @Email: 2857692313@qq.com
 * @deccription:TODO 耗时操作
 */
public class AppRepository implements AppContract {

    private static volatile AppRepository sInstance;

    private AppLocalDataSource mLocalDataSource;


    private AppRepository() {
        mLocalDataSource = new AppLocalDataSource(AppReader.getInstance());
    }

    public static AppRepository getInstance() {
        if (sInstance == null) {
            synchronized (AppRepository.class) {
                if (sInstance == null) {
                    sInstance = new AppRepository();
                }
            }
        }
        return sInstance;
    }


    @Override
    public Observable<String> update(String text) {
        return mLocalDataSource.update(text);
    }
}
