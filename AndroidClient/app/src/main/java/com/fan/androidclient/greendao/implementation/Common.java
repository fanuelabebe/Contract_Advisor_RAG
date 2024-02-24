package com.fan.androidclient.greendao.implementation;

import android.content.Context;

import com.fan.androidclient.Singleton;
import com.fan.androidclient.greendao.models.DaoSession;
import com.fan.androidclient.greendao.models.HistoryDao;

public class Common {
    public static HistoryDao getHistoryDao(Context context){
        try{
            DaoSession daoSession = Singleton.getInstance(context).getDaoSession();
            if(daoSession != null) {
                HistoryDao historyDao = daoSession.getHistoryDao();
                if (historyDao != null) {
                    return historyDao;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
