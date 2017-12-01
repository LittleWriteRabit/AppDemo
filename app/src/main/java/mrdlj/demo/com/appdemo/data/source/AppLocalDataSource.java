package mrdlj.demo.com.appdemo.data.source;

import android.content.Context;

import mrdlj.demo.com.appdemo.data.source.db.GreenDaoManager;
import mrdlj.demo.com.appdemo.greendao.gen.DaoSession;
import rx.Observable;
import rx.Subscriber;

/**
 * @author du 2017/12/1 13:14
 * @Email: 2857692313@qq.com
 * @deccription:TODO
 */
/* package */ class AppLocalDataSource implements AppContract {

    private static final String TAG = "AppLocalDataSource";

    private Context mContext;
    private DaoSession mDaoSession;

    /**
     * UserDao userDao = GreenDaoManager.getInstance().getNewSession().getUserDao();
     *
     * @param context
     */

    public AppLocalDataSource(Context context) {
        mContext = context;
        mDaoSession = GreenDaoManager.getInstance().getSession();
    }


    @Override
    public Observable<String> update(String text) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
//                int result = mLiteOrm.update(song);
//                if (result > 0) {
//                    subscriber.onNext(song);
//                } else {
//                    subscriber.onError(new Exception("Update song failed"));
//                }
//                subscriber.onCompleted();
            }
        });
    }

}
