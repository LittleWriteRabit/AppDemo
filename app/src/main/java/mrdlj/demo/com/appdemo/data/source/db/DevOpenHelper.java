package mrdlj.demo.com.appdemo.data.source.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import mrdlj.demo.com.appdemo.greendao.gen.DaoMaster;
import mrdlj.demo.com.appdemo.greendao.gen.DaoSession;
import mrdlj.demo.com.appdemo.greendao.gen.UserDao;
import mrdlj.demo.com.appdemo.utils.LogUtil;

/**
 * @author: du on 2017/12/1 14:01.
 * @Email: 2857692313@qq.com
 * @description: TODO 数据库帮助类
 */

public class DevOpenHelper extends DaoMaster.OpenHelper {

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static final String DBNAME = "greendao.db";

    public DevOpenHelper(Context context) {
        super(context, DBNAME, null);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        LogUtil.d("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion < newVersion) {
            LogUtil.d("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    DaoMaster.createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    DaoMaster.dropAllTables(db, ifExists);
                }
            }, UserDao.class);
//            MigrationHelper.getInstance().migrate(db., UserDao.class);
            //更改过的实体类(新增的不用加)   更新UserDao文件 可以添加多个  XXDao.class 文件
//             MigrationHelper.getInstance().migrate(db, UserDao.class,XXDao.class);
        }
    }

//    /**
//     * 取得DaoMaster
//     *
//     * @param context
//     * @return
//     */
//    public static DaoMaster getDaoMaster(Context context) {
//        if (daoMaster == null) {
//            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
//                    DBNAME, null);
//            daoMaster = new DaoMaster(helper.getWritableDatabase());
//        }
//        return daoMaster;
//    }
//
//    /**
//     * 取得DaoSession
//     *
//     * @param context
//     * @return
//     */
//    public static DaoSession getDaoSession(Context context) {
//        if (daoSession == null) {
//            if (daoMaster == null) {
//                daoMaster = getDaoMaster(context);
//            }
//            daoSession = daoMaster.newSession();
//        }
//        return daoSession;
//    }
}