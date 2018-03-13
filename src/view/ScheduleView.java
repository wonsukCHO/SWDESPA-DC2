/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CalendarController;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import model.Event;

/**
 *
 * @author ianona, wonsukcho
 */
public class ScheduleView extends JPanel{
    CalendarController controller;
    List<ScheduleItem> items;
    
    public ScheduleView (CalendarController c) {
        this.controller = c;
        items = new ArrayList<ScheduleItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, 
	VerticalFlowLayout.TOP, 0, 0));
    }
    
    public void refresh (Color c1, Color c2) {
        for (int i = 0; i < items.size(); i++) {
            items.get(i).refresh(c1, c2);
            items.get(i).repaint();
            items.get(i).revalidate();
        }
    }
    
    public void setItems (List <Event> events) {
        for (int i = 0; i < items.size(); i++) {
                remove(items.get(i));
        }
			
        items.clear();
        
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String hourString = String.valueOf(hour);
                if (hourString.length() == 1)
                    hourString = "0"+hourString;
                String minString = String.valueOf(min);
                if (minString.length() == 1)
                    minString = "0"+minString;
                String time = hourString + ":" + minString;
                items.add(new ScheduleItem(controller, time, events));
            }
        }
        
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }
        
        setPreferredSize(new Dimension(260, items.size() * 45 + 30));
        
        revalidate();
        repaint();
    }
}
