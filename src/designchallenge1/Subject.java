/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author ianona
 */
public class Subject {
    private ArrayList<Event> events;
    private ArrayList<Object> attachedObservers;
    private CSVDataParser csvDP;
    private PSVDataParser psvDP;
    private ArrayList<Event> updatedEvents;
    private Updater updater;
    
    public Subject () {
        events = new ArrayList<> ();
        updatedEvents = new ArrayList<> ();
        updater = new Updater();
        attachedObservers = new ArrayList<>();
        csvDP = new CSVDataParser();
        psvDP = new PSVDataParser();
        
        psvDP.readData(events, "DLSU Unicalendar.psv");
        csvDP.readData(events, "Philippine Holidays.csv");
    }
    
    public void addEvent (Event e) {
        events.add(e);
        csvDP.writeData(events, "DLSU Unicalendar.psv", "Philippine Holidays.csv");
    }
   
    public void deleteEvent (String toDelete) {
        for (int i = 0; i < events.size(); i++) {
            if (toDelete.contains(events.get(i).getName())) {
                events.remove(i);
                break;
            }
        }
        csvDP.writeData(events, "DLSU Unicalendar.psv", "Philippine Holidays.csv");
    }
    
    public ArrayList<Event> getEvents () {
        return events;
    }
    
    public void attach (Object o) {
        attachedObservers.add(o);
    }

    public void updateAll () {
        for (int i = 0; i < events.size(); i++) {
            if (isSameDay(events.get(i)) && !eventInArray(updatedEvents, events.get(i))) {
                Event e = events.get(i);
                updater.updateApps(attachedObservers, e);
                updatedEvents.add(e);
            }
        }
    }
    
    public boolean isSameDay(Event e) {
        GregorianCalendar cal = new GregorianCalendar();
        int curDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int curMonth = cal.get(GregorianCalendar.MONTH) + 1;
        int curYear = cal.get(GregorianCalendar.YEAR);
        
        return (e.getYear() == curYear && e.getMonth() == curMonth && e.getDay() == curDay && !e.isYearly()) || (curYear >= e.getYear() && e.getMonth() == curMonth && e.getDay() == curDay && e.isYearly());
    }
    
    public boolean eventInArray (ArrayList<Event> events, Event e) {
        for (int i = 0; i<events.size();i++) {
            if (e.getName().equalsIgnoreCase(events.get(i).getName()) && e.getColor().equalsIgnoreCase(events.get(i).getColor()) && e.getYear() == events.get(i).getYear() && e.getDay() == events.get(i).getDay())
                return true;
        }
        return false;
    }
}