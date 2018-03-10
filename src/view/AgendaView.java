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
public class AgendaView extends JPanel{
    CalendarController controller;
    List<AgendaItem> items;
    
    public AgendaView (CalendarController c) {
        this.controller = c;
        items = new ArrayList<AgendaItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, 
	VerticalFlowLayout.TOP, 0, 0));
    }
    
    public void setItems (List <Event> events, String mode) {
        for (int i = 0; i < items.size(); i++) {
                remove(items.get(i));
        }
			
        items.clear();
        
        if (events.isEmpty()) {
            items.add(AgendaItem.createEmpty());
            add(items.get(0));
        }
        else {
            for (int i = 0; i < events.size();i++) {
                System.out.println(events.get(i).toString());
            }
            
            for (int i = 0; i < events.size(); i++) {
                if (mode.equalsIgnoreCase("agenda"))
                    items.add(new AgendaItem(controller, events.get(i)));
                else {
                    if (events.get(i).getType().equalsIgnoreCase(mode))
                        items.add(new AgendaItem(controller, events.get(i)));
                }
            }
			
            for (int i = 0; i < items.size(); i++) {
		add(items.get(i));
            }
        }
        
        setPreferredSize(new Dimension(390, items.size() * 40 + 30));
        
        revalidate();
        repaint();
    }
}
