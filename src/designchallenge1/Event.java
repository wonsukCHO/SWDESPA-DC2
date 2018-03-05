/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ianona
 */
public class Event {
    private int year, month, day;
    private String name, color;
    private boolean yearly;
    
    public Event(String name, String color, int year, int month, int day, boolean yearly) {
        this.name = name;
        this.color = color.replaceAll(" ", "").toLowerCase();
        this.year = year;
        this.month = month;
        this.day = day;
        this.yearly = yearly;
    }

    public boolean isYearly() {
        return yearly;
    }

    public void setYearly(boolean yearly) {
        this.yearly = yearly;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    public List<String> toReport() {
        List<String> report = new ArrayList<>();
	String date = month + "/" + day + "/" + year;
        report.add(date);
	report.add(name);
	report.add(color);
        
	return report;
    }
}
