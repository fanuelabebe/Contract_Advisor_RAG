package com.fan.androidclient.implementation;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fan.androidclient.Singleton;
import com.fan.androidclient.models.RequestPackage;

import org.json.JSONObject;

public class Volley {
    Context context;
    RequestQueue requestQueue;
    RequestPackage requestPackage;
    public int timeOut;

    public Volley(Context context, RequestPackage requestPackage, int timeOut) {
        this.context = context;
        this.requestPackage = requestPackage;
        this.timeOut = timeOut;
        requestQueue = Singleton.getInstance(context).getRequestQueue();
    }

    public OnCallDone onCallDone;

    public interface OnCallDone{
        void onJsonObjectResponse(JSONObject response);
        void onStringResponse(String response);
        void onErrorResponse(VolleyError error);
    }
    public void getJsonObject(){

        try
        {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestPackage.getMethod(), requestPackage.getUri(), null, response -> onCallDone.onJsonObjectResponse(response), error -> {
                onCallDone.onErrorResponse(error);

                error.printStackTrace();
            }
            );

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getString(RequestPackage requestPackage){
        try{
            StringRequest request = new StringRequest(requestPackage.getMethod(), requestPackage.getUri(), response -> {
                Log.v("response", response);
                onCallDone.onStringResponse(response);
            }, error -> Log.v("error", error.toString()));
            requestQueue.add(request);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
