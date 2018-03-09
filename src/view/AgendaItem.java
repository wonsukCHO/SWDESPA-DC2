/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CalendarController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Event;

/**
 *
 * @author ianona
 */
public class AgendaItem extends JPanel {
    private Event event;
    private CalendarController controller;
    
    private JLabel eName;
    private JButton deleteBtn;
    private JCheckBox chkDone;
    private JLabel eTime;
    
    private Font dom;
    
    
    public AgendaItem() {
        // add font from file
        try {
            dom = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("arial.ttf"))).deriveFont(Font.PLAIN, 15);
            
        }
        catch (Exception ex) {
            System.out.println("FONT NOT FOUND");
        }
            
        eName = new JLabel();
        eTime = new JLabel();
        chkDone = new JCheckBox();
        deleteBtn = new JButton();
        
       
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/trash.png")));
            deleteBtn.setIcon(new ImageIcon(icon.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT)));
        } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
        }
       
        
        
        eName.setPreferredSize(new Dimension(200,40));
        deleteBtn.setPreferredSize(new Dimension(30,30));
        eTime.setPreferredSize(new Dimension(90,40));
        
        chkDone.addActionListener(new chkDone_Action());
        deleteBtn.addActionListener(new deleteBtn_Action());
        
        add(chkDone);
        add(eName);
        add(deleteBtn);
        add(eTime);
        
        eTime.setFont(dom);
        eName.setFont(dom);
        
        setBorder(BorderFactory.createLineBorder(Color.white, 1)); 
        setPreferredSize(new Dimension(390,40));
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
    }
    public AgendaItem (CalendarController c, Event e) {
        this();
        this.controller = c;
        this.event = e;
        
        if (e.getType().equalsIgnoreCase("event")) {
            setBackground(new Color(102,143,255)); 
            chkDone.setEnabled(false);
        }
        else
            setBackground(new Color(255,200,80));
        
        String startTime = String.valueOf(e.getStartTime());
        String endTime = String.valueOf(e.getEndTime());
        while (startTime.length() < 4)
            startTime = "0" + startTime;
        while (endTime.length() < 4)
            endTime = "0" + endTime;
        
        if (e.isDone()) {
            eName.setText("<html><strike>"+event.getName()+"</strike><html>");
            chkDone.setSelected(true);
        }
        else 
            eName.setText(event.getName());
        eTime.setText(startTime+"-"+endTime);
    }
    public static final AgendaItem createEmpty() {
	AgendaItem item = new AgendaItem();
			
	item.eName.setText("NO ACTIVITIES FOR TODAY");
        item.deleteBtn.setVisible(false);
        item.chkDone.setVisible(false);

        item.setBackground(new Color(203,255,98));
			
	return item;
    }
    
    class chkDone_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (chkDone.isSelected()) {
                event.setDone(true);
                controller.updateEvent(event);
            }
            else {
                event.setDone(false);
                controller.updateEvent(event);
            }
        }
    }
    class deleteBtn_Action implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e) {
            controller.deleteEvent(event);
        }
    }
}
