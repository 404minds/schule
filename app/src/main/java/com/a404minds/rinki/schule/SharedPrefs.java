package com.a404minds.rinki.schule;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by rinki on 16/11/16.
 */
public class SharedPrefs {
    private Context context;

    public SharedPrefs(Context context) {
        this.context = context;
    }

    public String getPrefs(String PREFS_NAME, String key ) {

        SharedPreferences sharedPref = this.context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        String silent = sharedPref.getString(key, "");

        return String.valueOf(silent);
    }

    public void putPrefs(String PREFS_NAME, String key, String value) {
        SharedPreferences sharedPref = this.context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);

        // Commit the edits!
        editor.commit();
    }
}
