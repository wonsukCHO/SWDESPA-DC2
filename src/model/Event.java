/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author ianona
 */
public class Event {
    private int year, month, day, startTime, endTime;
    private String name, type;
    private boolean done;
    
    // FOR THE DATABASE
    public static final String TABLE = "EVENTS";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "Type";
    public static final String COL_DONE = "Done";
    public static final String COL_YEAR = "Year";
    public static final String COL_MONTH = "Month";
    public static final String COL_DAY = "Day";
    public static final String COL_STIME = "StartTime";
    public static final String COL_ETIME = "EndTime";
    
    public Event(String name, String type,Boolean done, int year, int month, int day, int startTime, int endTime) {
        this.name = name;
        this.done = done;
        this.year = year;
        this.month = month;
        this.day = day;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
    
    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "event info: "+ name +"("+ type +")"+":"+done +":" +year +"/"+ month+"/"+day +" "+startTime + " " + endTime;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
