package edu.temple.materialdesigntest.network;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by rafaellima on 10/22/15.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){

        this.mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());

    }

    public static VolleySingleton getsInstance(){
        if(sInstance == null){
            sInstance=new VolleySingleton();

        }

        return sInstance;
    }

    public RequestQueue getmRequestQueue(){
        return mRequestQueue;
    }
}
