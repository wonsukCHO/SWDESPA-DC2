package view;

import controller.CalendarController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
 * @author ianona
 */
public class ScheduleItem extends JPanel{
    private Event event;
    private CalendarController controller;
    
    public ScheduleItem() {
        setPreferredSize(new Dimension(260,40));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
    }
    
    public ScheduleItem (CalendarController c, String time) {
        this();
        this.controller = c;
        this.setBackground(Color.white);
        this.setBorder(BorderFactory.createTitledBorder(time));
    }
}
