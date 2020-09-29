package com.nitjsr.tapcell.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private static final String SHARED_PREF = "SharedPref";
    private static final String IS_FIRST_OPEN = "isFirstOpen";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String IS_POPUP_SHOWN="IsPopupShown";

    public SharedPrefManager(Context context){
        sharedPref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    public boolean isFirstOpen(){
        return sharedPref.getBoolean(IS_FIRST_OPEN, true);
    }

    public void setIsFirstOpen(boolean isFirstOpen){
        editor.putBoolean(IS_FIRST_OPEN, isFirstOpen).apply();
    }

    public boolean isLoggedIn() {
        return sharedPref.getBoolean(IS_LOGGED_IN, false);
    }

    public void setIsLoggedIn(boolean isLoggedIn){
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn).apply();
    }

    public boolean isPopupDialogShown(){
        return sharedPref.getBoolean(IS_POPUP_SHOWN, false);
    }

    public void setPopupShown(boolean isFirstTime){
        editor.putBoolean(IS_POPUP_SHOWN,isFirstTime);
        editor.commit();
    }
}