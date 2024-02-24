package com.fan.androidclient.greendao.implementation;

import android.content.Context;

import com.fan.androidclient.greendao.models.History;
import com.fan.androidclient.greendao.models.HistoryDao;

import java.util.List;

public class Select {
    public static History fetchHistoryWithId(Context context, long id){
        HistoryDao historyDao = Common.getHistoryDao(context);
        if (historyDao != null) {
            try {
                History history = historyDao.queryBuilder().where(HistoryDao.Properties.Id.eq(id)).unique();
                if(history != null){
                    return history;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
    public static List<History> fetchStoryList(Context context){
        HistoryDao historyDao = Common.getHistoryDao(context);
        if (historyDao != null) {
            try {
                List<History> historyList = historyDao.loadAll();
                if(historyList != null){
                    return historyList;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

}
