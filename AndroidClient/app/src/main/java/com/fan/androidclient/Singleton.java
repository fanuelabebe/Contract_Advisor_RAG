package com.fan.androidclient;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton {
    private final String TAG = Singleton.this.getClass().getSimpleName();
    private RequestQueue mRequestQueue;
    Context context;
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
}
