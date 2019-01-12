package com.example.mjb.todo;

import android.util.Log;

public class MyLogger {

    private  static boolean isDebugMode=false;


    public static void d(String tag,String text){
        if (isDebugMode){
            Log.d(tag,text);

        }
    }



}
