package mrdlj.demo.com.appdemo.data.source.db;


import mrdlj.demo.com.appdemo.AppReader;
import mrdlj.demo.com.appdemo.greendao.gen.DaoMaster;
import mrdlj.demo.com.appdemo.greendao.gen.DaoSession;

/**
 * @author du 2017/12/1 13:35
 * @Email: 2857692313@qq.com
 * @deccription:TODO 数据库操作管理类
 */
public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static volatile GreenDaoManager mInstance = null;

    private GreenDaoManager() {
        if (mInstance == null) {
            /**
             * 第一个是没有数据库升级的，下面第二个使用了数据库升级
             */
//            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(AppReader.getInstance(), "user.db");
            DevOpenHelper devOpenHelper = new DevOpenHelper(AppReader.getInstance());
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}