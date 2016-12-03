package com.a404minds.rinki.schule;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.List;

/**
 * Created by rinki on 02/12/16.
 */
public class AttendanceService extends IntentService {

    public AttendanceService() {
        super("Attendance Service");
    }

    public AttendanceService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Gets data from the incoming Intent
        //String dataString = intent.getDataString();
        System.out.println("Service Start");
        //System.out.println(dataString);
        DatabaseHandler db = new DatabaseHandler(this);
        List studentAttendance = db.getAllStudentsAttendance();
        Log.e("student Attendance",studentAttendance.toString());
    }
}
