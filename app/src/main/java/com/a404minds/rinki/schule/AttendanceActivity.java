package com.a404minds.rinki.schule;

import com.daprlabs.aaron.swipedeck.SwipeDeck;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AttendanceActivity extends AppCompatActivity {

    private static final String TAG = "AttendanceActivity";
    private SwipeDeck cardStack;
    private Context context = this;
    private SwipeDeckAdapter adapter;
    private ArrayList<String> testData;
    private CheckBox dragCheckbox;
    public Toolbar attendanceToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Window window = AttendanceActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(AttendanceActivity.this, R.color.colorStatusBar));
        }
        setContentView(R.layout.attendance_layout);
        attendanceToolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(attendanceToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String classid= getIntent().getStringExtra("EXTRA_SESSION_ID");
        String responseStr = null;
        try {
            responseStr = new NetworkingGet(AttendanceActivity.this).execute("/classes/"+classid.toString()+"/students").get();
            JSONObject responseData = new JSONObject(responseStr);
            final JSONArray students = new JSONArray(responseData.getString("data"));
            cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);


            adapter = new SwipeDeckAdapter(students, this);
            if(cardStack != null){
                cardStack.setAdapter(adapter);
            }
            cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
                @Override
                public void cardSwipedLeft(long stableId) {
//                    students.get(stableId)
                    //markAttendance(finalStudent.getString("_id").toString(), 2);
                    Log.i("Attendance", "card was swiped left, position in adapter: " + stableId);
                }

                @Override
                public void cardSwipedRight(long stableId) {
                    Log.i("Attendance", "card was swiped right, position in adapter: " + stableId);

                }
            });

            cardStack.setLeftImage(R.id.left_image);
            cardStack.setRightImage(R.id.right_image);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    public class SwipeDeckAdapter extends BaseAdapter {

        private JSONArray data;
        private Context context;

        public SwipeDeckAdapter(JSONArray data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.length();
        }

        @Override
        public Object getItem(int position) {
            try {
                return data.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater inflater = getLayoutInflater();
                // normally use a viewholder
                v = inflater.inflate(R.layout.swipdeck, parent, false);
            }
            //((TextView) v.findViewById(R.id.textView2)).setText(data.get(position));
            JSONObject student = null;
            TextView studentName;
            TextView studentRollNo;

            try {
                JSONObject classStudent = data.getJSONObject(position);
                student = classStudent.getJSONObject("student");
                ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
                studentName = (TextView) v.findViewById(R.id.student_name_text);
                studentRollNo = (TextView) v.findViewById(R.id.student_rollno);
                studentName.setText(student.getString("name"));
                studentRollNo.setText(student.getString("roll_no"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final JSONObject finalStudent = student;
            Button btnAbsent = (Button) v.findViewById(R.id.button_absent);
            btnAbsent.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    cardStack.swipeTopCardLeft(500);
                    try {
                        markAttendance(finalStudent.getString("_id").toString(), 2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Button btnPresent = (Button) v.findViewById(R.id.button_present);
            btnPresent.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    cardStack.swipeTopCardRight(500);
//                    try {
//                        markAttendance(finalStudent.getString("_id").toString(), 1);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                    Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
                    /*Intent i = new Intent(v.getContext(), BlankActivity.class);
                    v.getContext().startActivity(i);*/
                }
            });
            return v;
        }

        public void markAttendance(String student, int presence) {
            JSONObject studentAttendance = new JSONObject();
            String responseStr = null;
            try {
                studentAttendance.put("student", student);
                studentAttendance.put("status", presence);
                responseStr = new NetworkingPost().execute("/attendence", studentAttendance.toString()).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.e("responseData", responseStr);
        }
    }
}
