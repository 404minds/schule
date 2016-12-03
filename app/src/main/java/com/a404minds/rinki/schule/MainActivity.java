package com.a404minds.rinki.schule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        Intent i = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(i);

        Intent mServiceIntent = new Intent(MainActivity.this, AttendanceService.class);
        startService(mServiceIntent);
//        first_time_check();
    }
//
//    private void first_time_check() {
//        System.out.println("First time check");
//        SharedPrefs sharedPrefs = new SharedPrefs(MainActivity.this);
//        //sharedPrefs.putPrefs("Auth_file", "token", "");
//        String token = sharedPrefs.getPrefs("Auth_file", "token");
//        System.out.println(token.toString());
//        Intent i;
//        if(token.equals("")){
//            i = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(i);
//        } else {
//            i = new Intent(MainActivity.this, ClassListActivity.class);
//            startActivity(i);
//        }
//    }
}
