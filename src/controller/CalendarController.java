/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.GregorianCalendar;
import java.util.List;
import model.CalendarService;
import model.Event;
import view.CalendarView;

/**
 *
 * @author ianona, wonsukcho
 */
public class CalendarController {
    private CalendarService model;
    private CalendarView view;
	
    public CalendarController (CalendarService model, CalendarView view) {
        this.model = model;
        this.view = view;
    }
	
    public void deleteEvent(Event e) {
        model.deleteEvent(e.getName());
        view.setAgendaItems(model.getEvents(e.getYear(), e.getMonth(), e.getDay()));
        view.setScheduleItems(model.getEvents(e.getYear(), e.getMonth(), e.getDay()));
        view.refresh();
    }
	
    public void addEvent(Event e) {
	model.addEvent(e);
        view.setAgendaItems(model.getEvents(e.getYear(), e.getMonth(), e.getDay()));
        view.setScheduleItems(model.getEvents(e.getYear(), e.getMonth(), e.getDay()));
        view.refresh();
    }
	
    public void updateEvent(Event e) {
        model.updateEvent(e);
        view.setAgendaItems(model.getEvents(e.getYear(), e.getMonth(), e.getDay()));
        view.setScheduleItems(model.getEvents(e.getYear(), e.getMonth(), e.getDay()));
        view.refresh();
    }

    public void start() {
	view.attach(this);
        
        GregorianCalendar cal = new GregorianCalendar();
        int dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int monthBound = cal.get(GregorianCalendar.MONTH);
        int yearBound = cal.get(GregorianCalendar.YEAR);
	view.setAgendaItems(model.getEvents(yearBound, monthBound, dayBound));
        view.setScheduleItems(model.getEvents(yearBound, monthBound, dayBound));
        view.refresh();
    }
    
    public void updateViews (int year, int month, int day) {
        view.setAgendaItems(model.getEvents(year, month, day));
        view.setScheduleItems(model.getEvents(year, month, day));
    }
    
    public List<Event> getEvents (int year, int month, int day) {
        return model.getEvents(year, month, day);
    }
}
