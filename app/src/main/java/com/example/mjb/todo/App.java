package com.example.mjb.todo;

import android.app.Application;

import com.example.mjb.todo.database.MyDevOpenHelper;
import com.example.mjb.todo.models.DaoMaster;
import com.example.mjb.todo.models.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private static App app;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this, "mytasks.db");
        Database db = myDevOpenHelper.getWritableDb();

        daoSession = new DaoMaster(db).newSession();

        app = this;
    }

    public static App getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
