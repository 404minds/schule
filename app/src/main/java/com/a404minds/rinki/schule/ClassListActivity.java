package com.a404minds.rinki.schule;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.widget.Toolbar;
import java.util.concurrent.ExecutionException;
import android.view.View;
import android.view.MenuInflater;
import android.view.Menu;
import android.widget.Toast;
/**
 * Created by rinki on 14/11/16.
 */
public class ClassListActivity extends AppCompatActivity {

    Context context = this;
    public static RecyclerView.Adapter adapter;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = ClassListActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(ClassListActivity.this, R.color.colorStatusBar));
        }

        setContentView(R.layout.class_list_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.actionbar_space_between_icon_and_title);


        String responseStr = null;
        try {
            responseStr = new NetworkingGet(ClassListActivity.this).execute("/classes").get();
            JSONObject responseData = new JSONObject(responseStr);
            JSONArray classes = new JSONArray(responseData.getString("data"));

            RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.class_recycler_view);

            recyclerView1.setHasFixedSize(true);
            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView1.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            adapter = new ClassAdapter(classes);
            recyclerView1.setAdapter(adapter);
//            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
//            itemAnimator.setAddDuration(1000);
//            itemAnimator.setRemoveDuration(1000);
//            recyclerView1.setItemAnimator(itemAnimator);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
