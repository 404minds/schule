package com.a404minds.rinki.schule;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by rinki on 22/11/16.
 */
public class LoginActivity extends AppCompatActivity{
    Button b1;
    EditText ed1, ed2;
    TextView forgotPassword;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        getSupportActionBar().hide();
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Window window = LoginActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.colorStatusBar));
        }
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.text1);
        ed2 = (EditText) findViewById(R.id.text2);
        forgotPassword = (TextView) findViewById(R.id.link_forgot_password);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Redirecting...", Toast.LENGTH_SHORT).show();
                JSONObject creds = new JSONObject();
                try {
                    creds.put("username", ed1.getText().toString());
                    creds.put("password", ed2.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    String responseStr = new NetworkingPost().execute("/token", creds.toString()).get();

                    JSONObject responseData = new JSONObject(responseStr);

                    if (responseData.getString("code").equals("401")) {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else if (responseData.getString("code").equals("200")) {
                        SharedPrefs sharedActivity = new SharedPrefs(LoginActivity.this);
                        sharedActivity.putPrefs("Auth_file", "token", responseData.getString("data"));

                        Intent i=new Intent(
                                LoginActivity.this,
                                ClassListActivity.class);
                        startActivity(i);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i=new Intent(
                        LoginActivity.this,
                        ForgotPasswordActivity.class);
                startActivity(i);
            }
        });
    }

}
