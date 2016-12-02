package com.a404minds.rinki.schule;

/**
 * Created by rinki on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "schule";

    // Contacts table name
    private static final String TABLE_ATTENDANCE = "attendance";

    // Contacts Table Columns names
    private static final String STUDENT_ID = "id";
    private static final String STUDENT_STATUS = "status";
    private static final String DATE = "date";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + TABLE_ATTENDANCE + "("
                + STUDENT_ID + " INTEGER PRIMARY KEY," + STUDENT_STATUS + " TEXT,"
                + DATE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_ATTENDANCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    void addAttendance(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_STATUS, attendance.getStatus()); // attendance status
        values.put(DATE, attendance.getDate()); // attendance date

        // Inserting Row
        db.insert(TABLE_ATTENDANCE, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Studence Attendance
    public List<Attendance> getAllStudentsAttendance() {
        List<Attendance> attendanceList = new ArrayList<Attendance>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ATTENDANCE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Attendance attendance = new Attendance();
                attendance.setID(cursor.getString(0));
                attendance.setStatus(Integer.parseInt(String.valueOf(cursor.getInt(1))));
                attendance.setDate(cursor.getString(2));
                // Adding contact to list
                attendanceList.add(attendance);
            } while (cursor.moveToNext());
        }

        return attendanceList;
    }

    public int updateStudentAttendance(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STUDENT_STATUS, attendance.getStatus());
        values.put(DATE, attendance.getDate());

        // updating row
        return db.update(TABLE_ATTENDANCE, values, STUDENT_ID + " = ?",
                new String[] { String.valueOf(attendance.getID()) });
    }

    public void deleteStudentAttendance(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ATTENDANCE, STUDENT_ID + " = ?",
                new String[] { String.valueOf(attendance.getID()) });
        db.close();
    }


    // Getting Student Count
    public int getStudentsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ATTENDANCE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }
}
