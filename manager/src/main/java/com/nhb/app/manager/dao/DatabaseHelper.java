package com.nhb.app.manager.dao;

import android.content.Context;
import com.fast.library.database.BaseOrmLiteDatabaseHelper;
import com.nhb.app.manager.bean.OrderMessageBean;

/**
 * 说明：DatabaseHelper
 * <p/>
 * 作者：fanly
 * <p/>
 * 类型：Class
 * <p/>
 * 时间：2016/8/19 9:26
 * <p/>
 * 版本：verson 1.0
 */
public class DatabaseHelper extends BaseOrmLiteDatabaseHelper{

    private static DatabaseHelper sHelper;
    private static String NAME = "nbhb";
    private static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
        addTable();
    }

    @Override
    public void addTable() {
        //添加消息表
        registerTable(OrderMessageBean.class);
    }

    public static DatabaseHelper getInstance(Context context){
        if (sHelper == null){
            synchronized (DatabaseHelper.class){
                if (sHelper == null){
                    sHelper = new DatabaseHelper(context);
                }
            }
        }
        return sHelper;
    }
}
