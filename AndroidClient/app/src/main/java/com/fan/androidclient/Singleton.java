package com.fan.androidclient;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fan.androidclient.greendao.models.DaoMaster;
import com.fan.androidclient.greendao.models.DaoSession;

public class Singleton {
    private final String TAG = Singleton.this.getClass().getSimpleName();
    private RequestQueue mRequestQueue;
    Context context;
    private DaoSession daoSession;
    private static Singleton mSingleton;
    public static synchronized Singleton getInstance(Context context){
        if(mSingleton == null){
            mSingleton = new Singleton(context);
        }
        return mSingleton;
    }
    private Singleton(Context context){
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }
    public void initGreenDao(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "contract_db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
    public DaoSession getDaoSession(){
        return daoSession;
    }
}
