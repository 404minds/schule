package com.a404minds.rinki.schule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
public class ForgotPasswordActivity extends AppCompatActivity {
    Button b1;
    EditText ed1, ed2;
    TextView signin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.forgot_password_layout);

        b1 = (Button) findViewById(R.id.button1);
        ed1 = (EditText) findViewById(R.id.username_email1);
        ed2 = (EditText) findViewById(R.id.text2);
        signin = (TextView) findViewById(R.id.link_signin);


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
                        Log.e("Response Code",responseData.getString("code"));
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else if (responseData.getString("code").equals("200")) {
                        SharedPrefs sharedActivity = new SharedPrefs(ForgotPasswordActivity.this);
                        sharedActivity.putPrefs("Auth_file", "token", responseData.getString("data"));

                        Intent i=new Intent(
                                ForgotPasswordActivity.this,
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

        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i=new Intent(
                        ForgotPasswordActivity.this,
                        LoginActivity.class);
                startActivity(i);
            }
        });
    }

}
