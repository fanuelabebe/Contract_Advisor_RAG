package com.fan.androidclient.implementation;

import com.fan.androidclient.models.RagResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonParser {
    public static RagResponse parseRagResponse(String content){
        try{
            Gson gson = new GsonBuilder().create();
            RagResponse ragResponse =  gson.fromJson(content, RagResponse.class);
            if(ragResponse != null){
                return ragResponse;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
