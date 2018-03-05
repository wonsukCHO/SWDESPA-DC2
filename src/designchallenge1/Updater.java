/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

import facebook.FBView;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import sms.SMS;
import sms.SMSView;

/**
 *
 * @author ianona
 */
public class Updater {
    public  void updateApps(ArrayList<Object> apps, Event e) {
        Color c = null;
        try {
            Field field = Class.forName("java.awt.Color").getField(e.getColor().trim().toLowerCase());
            c = (Color)field.get(null);
        } catch (Exception ex) {
            c = Color.black;
        }
        GregorianCalendar cal = new GregorianCalendar();
        int curYear = cal.get(GregorianCalendar.YEAR);
                
        for (Object app : apps) {
            if (app instanceof FBView) {
                ((FBView) app).showNewEvent(e.getName(), e.getMonth(), e.getDay(), curYear, c);
            } 
            else if (app instanceof SMSView) {
                SMS msg = new SMS(e.getName(),new GregorianCalendar(curYear,e.getMonth()-1,e.getDay()),c);
                ((SMSView) app).sendSMS(msg);
            }
        }
    }
}
