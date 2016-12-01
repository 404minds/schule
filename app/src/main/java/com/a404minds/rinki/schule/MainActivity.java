package com.a404minds.rinki.schule;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        first_time_check();
    }

    private void first_time_check() {
        SharedPrefs sharedPrefs = new SharedPrefs(MainActivity.this);
        String token = sharedPrefs.getPrefs("Auth_file", "token");
        Intent i;
        if((token == null)){
            i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }
        i = new Intent(MainActivity.this, ClassListActivity.class);
        startActivity(i);
    }
}
