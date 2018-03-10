package view;

import controller.CalendarController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Event;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ianona, wonsukcho
 */
public class ScheduleItem extends JPanel{
    private Event event;
    private CalendarController controller;
    private Font dom;
    
    private JLabel sName;
    private JLabel time;
    
    public ScheduleItem() {
        // add font from file
        try {
            dom = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("arial.ttf"))).deriveFont(Font.PLAIN, 15);
            
        }
        catch (Exception ex) {
            System.out.println("FONT NOT FOUND");
        }
        
        sName = new JLabel();
        time = new JLabel();
        
        time.setFont(dom);
        
        this.add(time);
        
        setPreferredSize(new Dimension(260,45));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
    }
    
    public ScheduleItem (CalendarController c, String time, List <Event> events) {
        this();
        this.controller = c;
        this.setBackground(Color.white);
        //this.setBorder(BorderFactory.createTitledBorder(time));
        //((javax.swing.border.TitledBorder) this.getBorder()).
        //setTitleFont(dom);
        this.time.setText(time);
        time = time.replaceAll(":","");
        int numTime = Integer.valueOf(time);
        
        for(int i=0; i < events.size(); i++)
        {
            if(events.get(i).getType().equalsIgnoreCase("event"))
            {
                if(numTime < events.get(i).getEndTime() && numTime > events.get(i).getStartTime()) {
                    this.setBackground(new Color(102,143,255));
                }
                    
                
                if(numTime == events.get(i).getStartTime())
                {
                    this.setBackground(new Color(102,143,255));
                    
                    if (events.get(i).isDone())
                        sName.setText("<html><strike>"+events.get(i).getName()+"</strike></html>");
                    else
                        sName.setText(events.get(i).getName());
                    sName.setFont(dom);
                    //this.add(new JLabel("          "));
                    this.add(sName);
                }
            } 
            else
            {
                if(numTime < events.get(i).getEndTime() && numTime > events.get(i).getStartTime()) {
                    this.setBackground(new Color(255,200,80));
                }
                    
                
                if(numTime == events.get(i).getStartTime())
                {
                    this.setBackground(new Color(255,200,80));
                    if (events.get(i).isDone())
                        sName.setText("<html><strike>"+events.get(i).getName()+"</strike></html>");
                    else
                        sName.setText(events.get(i).getName());
                    sName.setFont(dom);
                    //this.add(new JLabel("          "));
                    this.add(sName);
                }
            }
        }
    }
}
