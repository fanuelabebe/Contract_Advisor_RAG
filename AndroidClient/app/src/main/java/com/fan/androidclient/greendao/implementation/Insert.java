package com.fan.androidclient.greendao.implementation;

import android.content.Context;

import com.fan.androidclient.greendao.models.History;
import com.fan.androidclient.greendao.models.HistoryDao;

public class Insert {
    public static boolean insertHistoryData(Context context, History history){
        HistoryDao historyDao = Common.getHistoryDao(context);
        if(historyDao != null){
            try {
                historyDao.insertOrReplace(history);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }


        }
        return false;
    }
}
