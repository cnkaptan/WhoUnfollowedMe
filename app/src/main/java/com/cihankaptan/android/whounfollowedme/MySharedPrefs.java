package com.cihankaptan.android.whounfollowedme;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by cihan on 13.07.2015.
 */
public class MySharedPrefs {
    private static SharedPreferences sharedPreferences;
    private static Gson gson;

    public MySharedPrefs(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new GsonBuilder().create();
    }

    public static void saveString(String key,String string){
        sharedPreferences.edit().putString(key,string).apply();
    }

    public static void saveBoolean(String key,boolean bool){
        sharedPreferences.edit().putBoolean(key, bool).apply();
    }

    public static void saveObject(String key,Object object){
        String json = gson.toJson(object);
        MySharedPrefs.saveString(key, json);
    }

    public static String loadString(String key){
        return  sharedPreferences.getString(key,null);
    }

    public static String loadString(String key,String defValue){
        return sharedPreferences.getString(key,defValue);
    }

    public static boolean loadBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public static boolean loadBoolean(String key,boolean defValue){
        return sharedPreferences.getBoolean(key,defValue);
    }

    public static <T> T loadObject(String key,Class<T> type){
        String object = sharedPreferences.getString(key, null);
        if (object != null){
            return gson.fromJson(object, type);
        }else{
            return null;
        }
    }

    public static <T> void saveList(String key ,List<T> list){
        sharedPreferences.edit().putString(key,gson.toJson(list)).apply();
    }

    public static <T> List<T>  loadList(String key,Class<T> type){
        String json = sharedPreferences.getString(key,null);
        if (json != null){
            List<T> list = gson.fromJson(json, new TypeToken<List<T>>(){}.getType());
            return list;
        }else{
            return null;
        }
    }
}
