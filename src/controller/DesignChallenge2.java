/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.CalendarDB;
import model.CalendarService;
import view.CalendarView;

/**
 *
 * @author ianona, wonsukcho
 */
public class DesignChallenge2 {
    public static void main (String args[]) {
        CalendarView cv = new CalendarView();
        CalendarService cs = new CalendarService(new CalendarDB());
        CalendarController cc = new CalendarController(cs, cv);
        cc.start();
    }
}
