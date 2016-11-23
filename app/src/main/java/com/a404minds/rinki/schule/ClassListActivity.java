package com.a404minds.rinki.schule;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * Created by rinki on 14/11/16.
 */
public class ClassListActivity extends AppCompatActivity {

    Context context = this;
    public static RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        String responseStr = null;
        try {
            responseStr = new NetworkingGet(ClassListActivity.this).execute("/classes").get();
            System.out.print(responseStr);
            JSONObject responseData = new JSONObject(responseStr);

            JSONArray classes = new JSONArray(responseData.getString("data"));

            RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.class_recycler_view);
            recyclerView1.setHasFixedSize(true);
            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            recyclerView1.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            adapter = new StudentAdapter(classes);
            recyclerView1.setAdapter(adapter);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setAddDuration(1000);
            itemAnimator.setRemoveDuration(1000);
            recyclerView1.setItemAnimator(itemAnimator);

//        recyclerView1.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                if(position==0) {
//                    Intent i=new Intent(
//                            ClassListActivity.this,
//                            StudentListActivity.class);
//                    startActivity(i);
//                }
//            }
//        });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
