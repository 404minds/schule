package com.a404minds.rinki.schule;

/**
 * Created by rinki on 02/12/16.
 */
public class Attendance {

    //private variables
    String _id;
    int _status;
    String _date;

    // Empty constructor
    public Attendance(){

    }
    // constructor
    public Attendance(String id, int status, String _date) {
        this._id = id;
        this._status = status;
        this._date = _date;
    }

    // getting ID
    public String getID(){
        return this._id;
    }

    // setting id
    public void setID(String id){
        this._id = id;
    }

    // getting status
    public int getStatus(){
        return this._status;
    }

    // setting status
    public void setStatus(int status){
        this._status = status;
    }

    // getting date
    public String getDate (){
        return this._date;
    }

    // setting date
    public void setDate(String date){
        this._date = date;
    }
}

