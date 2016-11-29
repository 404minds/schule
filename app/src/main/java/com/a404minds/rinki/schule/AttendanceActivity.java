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

import java.util.ArrayList;
import java.util.List;

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
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        testData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            testData.add(String.valueOf(i));
        }

        adapter = new SwipeDeckAdapter(testData, this);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }
        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
            @Override
            public void cardSwipedLeft(long stableId) {
                Log.i("Attendance", "card was swiped left, position in adapter: " + stableId);
            }

            @Override
            public void cardSwipedRight(long stableId) {
                Log.i("Attendance", "card was swiped right, position in adapter: " + stableId);

            }
        });

        cardStack.setLeftImage(R.id.left_image);
        cardStack.setRightImage(R.id.right_image);

//        View inflatedView = getLayoutInflater().inflate(R.layout.swipdeck, null);
//        Button btn = (Button) inflatedView.findViewById(R.id.button_absent);
//        Button btn2 = (Button) inflatedView.findViewById(R.id.button_present);

//        btn.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                System.out.print("button clicked");
//                //cardStack.swipeTopCardLeft(500);
//            }
//        });

//        btn2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                cardStack.swipeTopCardRight(180);
//            }
//        });


    }

    public class SwipeDeckAdapter extends BaseAdapter {

        private List<String> data;
        private Context context;

        public SwipeDeckAdapter(List<String> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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
            ImageView imageView = (ImageView) v.findViewById(R.id.offer_image);
            TextView textView = (TextView) v.findViewById(R.id.student_name_text);
            String item = (String)getItem(position);
            textView.setText(item);

            Button btnAbsent = (Button) v.findViewById(R.id.button_absent);
            btnAbsent.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    cardStack.swipeTopCardLeft(500);
                }
            });

            Button btnPresent = (Button) v.findViewById(R.id.button_present);
            btnPresent.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Do something in response to button click
                    cardStack.swipeTopCardRight(500);
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
    }
}
