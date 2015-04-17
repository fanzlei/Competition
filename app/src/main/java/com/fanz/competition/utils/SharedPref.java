package com.fanz.competition.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Fanz on 3/16/15.
 */
public class SharedPref {

    private static SharedPref sharedPref;
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    //使用单例
    private SharedPref(){}

    public static SharedPref getInstance(Context context){
        if( sharedPref == null){
            sharedPref = new SharedPref();
            sp = context.getSharedPreferences("CompetitionSharedPref",Context.MODE_PRIVATE);
            editor = sp.edit();
        }
        return sharedPref;
    }
}
