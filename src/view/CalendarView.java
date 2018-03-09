/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Arturo III
 */

import controller.CalendarController;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import model.Event;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;

public class CalendarView{
        /**** Day Components ****/
        public int yearBound, monthBound, dayBound, yearToday, monthToday;
        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        /**** Swing Components ****/
        public JLabel monthLabel, yearLabel;
	public JButton btnPrev, btnNext;
        public JComboBox cmbYear;
	public JFrame frmMain;
	public Container pane;
	public JScrollPane scrollCalendarTable;
	public JPanel calendarPanel;
        
        /**** Calendar Table Components ***/
	public JTable calendarTable;
        public DefaultTableModel modelCalendarTable;
        
        /*** ADDED COMPONENTS ***/
        private JPanel eventPanel;
        private JLabel eNameLabel, eYearLabel, eMonthLabel, eDayLabel;
        private JTextField eName;
        private JComboBox eYear, eMonth, eDay;
        private JButton btnAdd;
        public int curRow = -1, curCol = -1;
        
        /***DC2 COMPONENTS***/
        private JPanel agendaPanel;
        private JPanel schedPanel;
        private JButton btnCreateEvent, btnCreateTodo, btnViewEvent, btnViewTodo, btnViewAgenda;
        private JLabel eStart, eEnd;
        private JComboBox cmbStart, cmbEnd;
        private String addMode;
        private CalendarController controller;
        private int curYear;
        private int curMonth;
        private int curDay;
        private AgendaView av;
        private ScheduleView sv;
        private JScrollPane agendaScroll;
        private JScrollPane scheduleScroll;
        private JLabel errorLbl;
        private Font dom;
        private String viewMode;
        
        public void attach (CalendarController c) {
            this.controller = c;
            
            this.av = new AgendaView(c);
            agendaScroll = new JScrollPane(av);
            agendaScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            agendaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            agendaScroll.setPreferredSize(new Dimension(390,575));
            agendaPanel.add(agendaScroll);
            
            this.sv = new ScheduleView(c);
            scheduleScroll = new JScrollPane(sv);
            scheduleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scheduleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scheduleScroll.setPreferredSize(new Dimension(260,810));
            schedPanel.add(scheduleScroll);
        }
        
        public void setAgendaItems (List<Event> events) {
            av.setItems(events);
        }
        
        public void setScheduleItems (List<Event> events) {
            sv.setItems(events);
        }
        
        public void refresh() {
            frmMain.revalidate();
            frmMain.repaint();
        }
        
        public void refreshCalendar(int month, int year) {
            int nod, som, i, j;
            btnPrev.setEnabled(true);
            btnNext.setEnabled(true);
            
            if (month == 0 && year <= yearBound-10)
                btnPrev.setEnabled(false);
            if (month == 11 && year >= yearBound+100)
                btnNext.setEnabled(false);
                
            monthLabel.setText(months[month]);
                
            for (i = 0; i < 6; i++)
                for (j = 0; j < 7; j++)
                    modelCalendarTable.setValueAt(null, i, j);
                
            GregorianCalendar cal = new GregorianCalendar(year, month, 1);
            nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            som = cal.get(GregorianCalendar.DAY_OF_WEEK);
                
            eDay.removeAllItems();
            
            // SET THE CALENDAR NUMBERS
            for (i = 1; i <= nod; i++) {
		int row = (i+som-2)/7;
		int column  =  (i+som-2)%7;
		modelCalendarTable.setValueAt(i, row, column);
                eDay.addItem(String.valueOf(i));
            }
                
            cmbYear.setSelectedItem(""+year);
            eDay.setSelectedItem(""+1);
            eYear.setSelectedItem(""+year);
            
            curYear = year;
            curMonth = month + 1;
            curDay = 1;
            
            eMonth.setSelectedItem(months[month]);
            calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
	}
        
	public CalendarView() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e) {}
            
            // add font from file
            try {
                dom = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(new File("arial.ttf"))).deriveFont(Font.PLAIN, 15);
            }
            catch (Exception ex) {
		System.out.println("FONT NOT FOUND");
            }
                
            frmMain = new JFrame ("Productivity Application");
            frmMain.setBackground(Color.WHITE);
            // frmMain.setSize(660, 750);
            frmMain.setSize(910, 681);
            pane = frmMain.getContentPane();
            pane.setLayout(null);
            frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            monthLabel = new JLabel ("January");
            monthLabel.setBounds(91, 7, 110, 50);
            yearLabel = new JLabel ("Change year:");
            yearLabel.setBounds(20, 250, 160, 40);
            cmbYear = new JComboBox();
            cmbYear.setBounds(130, 250, 90, 40);
            btnPrev = new JButton ("<<");
            btnPrev.setBounds(20, 19, 48, 29);
            btnNext = new JButton (">>");
            btnNext.setBounds(171, 20, 48, 27);
            
            modelCalendarTable = new DefaultTableModel() {
                public boolean isCellEditable(int rowIndex, int mColIndex) {
                    return false;
                }
            };
                
            calendarTable = new JTable(modelCalendarTable);
                
            btnAdd = new JButton("ADD");
            calendarTable.addMouseListener(new calListener());  
                
            scrollCalendarTable = new JScrollPane(calendarTable);
            scrollCalendarTable.setBounds(19, 47, 200, 200);
            calendarPanel = new JPanel(null);
            calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
                
            eNameLabel = new JLabel("Name: ");
            eYearLabel = new JLabel("Year: ");
            eMonthLabel = new JLabel("Month: ");
            eDayLabel = new JLabel("Day: ");
            eYear = new JComboBox();
            eMonth = new JComboBox(months);
            eDay = new JComboBox();
            eName = new JTextField(15);
            eStart = new JLabel("Start Time:");
            eEnd = new JLabel("End Time:");
            cmbStart = new JComboBox();
            cmbEnd = new JComboBox();
            errorLbl = new JLabel();
            
            for (int hour = 0; hour < 24; hour++) {
                for (int min = 0; min < 60; min += 30) {
                    String hourString = String.valueOf(hour);
                    if (hourString.length() == 1)
                        hourString = "0"+hourString;
                    String minString = String.valueOf(min);
                    if (minString.length() == 1)
                        minString = "0"+minString;
                    String time = hourString + ":" + minString;
                    cmbStart.addItem(time);
                    cmbEnd.addItem(time);
                }
            }
            
            eventPanel = new JPanel(null);
            eventPanel.setBorder(BorderFactory.createTitledBorder("Create Event"));
            addMode = "event";
		
            btnPrev.addActionListener(new btnPrev_Action());
            btnNext.addActionListener(new btnNext_Action());
            cmbYear.addActionListener(new cmbYear_Action());
                
            btnAdd.addActionListener(new btnAdd_Action());
		
            pane.add(calendarPanel);
            calendarPanel.setLayout(null);
            calendarPanel.add(monthLabel);
            calendarPanel.add(yearLabel);
            calendarPanel.add(cmbYear);
            calendarPanel.add(btnPrev);
            calendarPanel.add(btnNext);
            calendarPanel.add(scrollCalendarTable);
               
            calendarPanel.setBounds(0, 0, 640, 508);
                
            pane.add(eventPanel);
            eventPanel.add(eNameLabel);
            eventPanel.add(eYearLabel);
            eventPanel.add(eMonthLabel);
            eventPanel.add(eDayLabel);
            eventPanel.add(eName);
            eventPanel.add(eYear);
            eventPanel.add(eMonth);
            eventPanel.add(eDay);
            eventPanel.add(btnAdd);
            eventPanel.add(eStart);
            eventPanel.add(eEnd);
            eventPanel.add(cmbStart);
            eventPanel.add(cmbEnd);
            eventPanel.add(errorLbl);
                
            eventPanel.setBounds(0, 509, 640, 140);
            eNameLabel.setBounds(20,20, 160, 30);
            eStart.setBounds(20, 50, 160, 30);
            eEnd.setBounds(20, 80, 160, 30);
            eYearLabel.setBounds(250,20, 160, 30);
            eMonthLabel.setBounds(250,50, 160, 30);
            eDayLabel.setBounds(250,80, 160, 30);
                
            eName.setBounds(70,20, 160, 30);
            cmbStart.setBounds(90,50,150,30);
            cmbEnd.setBounds(90,80,150,30);
            eYear.setBounds(300,20, 110, 30);
            eMonth.setBounds(300,50, 110, 30);
            eDay.setBounds(300,80, 110, 30);
                
            btnAdd.setBounds(520,20, 100, 35);
            errorLbl.setBounds(520, 55, 160, 30);
            errorLbl.setForeground(Color.red);
            errorLbl.setVisible(false);
                
            pane.setBackground(new Color(220,220,220));
            eventPanel.setBackground(Color.WHITE);
            calendarPanel.setBackground(Color.WHITE);
		
            // SETS CURRENT DATE
            GregorianCalendar cal = new GregorianCalendar();
            dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
            monthBound = cal.get(GregorianCalendar.MONTH);
            yearBound = cal.get(GregorianCalendar.YEAR);
            monthToday = monthBound;    
            yearToday = yearBound;
		
            String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
            for (int i=0; i<7; i++) {
                modelCalendarTable.addColumn(headers[i]);
            }
		
            calendarTable.getParent().setBackground(calendarTable.getBackground()); //Set background

            calendarTable.getTableHeader().setResizingAllowed(false);
            calendarTable.getTableHeader().setReorderingAllowed(false);

            calendarTable.setColumnSelectionAllowed(true);
            calendarTable.setRowSelectionAllowed(true);
            calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            //calendarTable.setRowHeight(76);
            calendarTable.setRowHeight(30);
            
            agendaPanel = new JPanel();
            agendaPanel.setBounds(230, 35, 390, 463);
            agendaPanel.setBorder(BorderFactory.createTitledBorder("Agenda for Today"));
            agendaPanel.setBackground(Color.white);
            calendarPanel.add(agendaPanel);
            
            btnCreateTodo = new JButton();
            btnCreateTodo.setBounds(19, 290, 100, 70);
            btnCreateTodo.addActionListener(new btnCreateTodo_Action());
            calendarPanel.add(btnCreateTodo);
            
            try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/todoAdd.png")));
            btnCreateTodo.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100,70, Image.SCALE_DEFAULT)));
            } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
            }
            
            btnCreateEvent = new JButton();
            btnCreateEvent.setBounds(19, 360, 100, 70);
            btnCreateEvent.addActionListener(new btnCreateEvent_Action());
            calendarPanel.add(btnCreateEvent);
            
            try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/eventAdd.png")));
            btnCreateEvent.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100,70, Image.SCALE_DEFAULT)));
            } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
            }
            
            btnViewEvent = new JButton();
            btnViewEvent.setBounds(120, 360, 100, 70);
            // btnViewEvent.addActionListener(new btnCreateEvent_Action());
            calendarPanel.add(btnViewEvent);
            
            try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/event.png")));
            btnViewEvent.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100,70, Image.SCALE_DEFAULT)));
            } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
            }
            
            btnViewTodo = new JButton();
            btnViewTodo.setBounds(120, 290, 100, 70);
            // btnViewEvent.addActionListener(new btnCreateEvent_Action());
            calendarPanel.add(btnViewTodo);
            
            try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/todo.png")));
            btnViewTodo.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100,70, Image.SCALE_DEFAULT)));
            } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
            }
            
            btnViewAgenda = new JButton();
            btnViewAgenda.setBounds(120, 430, 100, 70);
            // btnViewEvent.addActionListener(new btnCreateEvent_Action());
            calendarPanel.add(btnViewAgenda);
            
            try {
            ImageIcon icon = new ImageIcon(ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("resources/agenda.png")));
            btnViewAgenda.setIcon(new ImageIcon(icon.getImage().getScaledInstance(100,70, Image.SCALE_DEFAULT)));
            } catch(IOException e) {
            System.out.println("FILE NOT FOUND");
            }
            
            schedPanel = new JPanel();
            schedPanel.setBackground(Color.white);
            schedPanel.setBounds(645, 0, 260, 653);
            pane.add(schedPanel);
            
            schedPanel.setBorder(BorderFactory.createTitledBorder("Schedule Today"));
            
            modelCalendarTable.setColumnCount(7);
            modelCalendarTable.setRowCount(6);
            for (int i = yearBound-100; i <= yearBound+100; i++) {
		cmbYear.addItem(String.valueOf(i));
                eYear.addItem(String.valueOf(i));
            }
            yearToday += 100;
            
            monthLabel.setFont(dom);
            yearLabel.setFont(dom);
            calendarTable.setFont(dom);
            ((javax.swing.border.TitledBorder) calendarPanel.getBorder()).
            setTitleFont(dom);
            btnCreateEvent.setFont(dom);
            btnCreateTodo.setFont(dom);
            cmbYear.setFont(dom);
            
            ((javax.swing.border.TitledBorder) schedPanel.getBorder()).
            setTitleFont(dom);
            ((javax.swing.border.TitledBorder) agendaPanel.getBorder()).
            setTitleFont(dom);
            ((javax.swing.border.TitledBorder) eventPanel.getBorder()).
            setTitleFont(dom);
            eNameLabel.setFont(dom);
            eYearLabel.setFont(dom);
            eMonthLabel.setFont(dom);
            eDayLabel.setFont(dom);
            eYear.setFont(dom);
            eMonth.setFont(dom);
            eDay.setFont(dom);
            eName.setFont(dom);
            eStart.setFont(dom);
            eEnd.setFont(dom);
            cmbStart.setFont(dom);
            cmbEnd.setFont(dom);
            errorLbl.setFont(dom);
            btnAdd.setFont(dom);
            
            pane.setBackground(Color.white);
            
            frmMain.setResizable(false);
            frmMain.setVisible(true);
            refreshCalendar(monthToday, yearToday);
	}
        
        public void reset() {
            eName.setText("");
        }
        
        /*** EVENT LISTENERS ***/
	class btnPrev_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (monthToday == 0) {
                    monthToday = 11;
                    yearToday -= 1;
		} else {
                    monthToday -= 1;
		}
                calendarTable.changeSelection(-1, -1, true, true);
                
		refreshCalendar(monthToday, yearToday);
                reset();
                controller.updateViews(curYear, curMonth, curDay);
            }
	}
	class btnNext_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                if (monthToday == 11) {
                    monthToday = 0;
                    yearToday += 1;
		} else {
                    monthToday += 1;
		}

                refreshCalendar(monthToday, yearToday);
                reset();
                controller.updateViews(curYear, curMonth, curDay);
            }
	}
	class cmbYear_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
		if (cmbYear.getSelectedItem() != null) {
                    String b = cmbYear.getSelectedItem().toString();
                    yearToday = Integer.parseInt(b);
                    refreshCalendar(monthToday, yearToday);
                    reset();
                    if (controller != null)
                        controller.updateViews(curYear, curMonth, curDay);
		}
            }
	}
        class btnAdd_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {   
                String name = eName.getText();
                int year = Integer.parseInt(eYear.getSelectedItem().toString());
                int day = Integer.parseInt(eDay.getSelectedItem().toString());
                int month = Arrays.asList(months).indexOf(String.valueOf(eMonth.getSelectedItem())) + 1;
                
                int sTime = Integer.parseInt(cmbStart.getSelectedItem().toString().replace(":", ""));
                int eTime = 0;
                if (addMode.equalsIgnoreCase("event"))
                    eTime = Integer.parseInt(cmbEnd.getSelectedItem().toString().replace(":", ""));
                else {
                    eTime = sTime + 30;
                    if (eTime % 100 >= 60) {
                        int min = eTime % 100;
                        eTime = (eTime / 100) + 1;
                        if (eTime > 23)
                            eTime = 0;
                        eTime *= 100;
                        eTime += min % 60;
                    }
                }
                    
                Event event = new Event(name,addMode,false,year,month,day,sTime,eTime);
                if (!name.isEmpty() && sTime != eTime && sTime < eTime) {
                    if (!overlap(event, controller.getEvents(year, month, day))) {
                        errorLbl.setVisible(false);
                        controller.addEvent(event);
                        reset();
                    }
                    else {
                        errorLbl.setText("<html>OVERLAPPING </br> EVENTS!</html>");
                        errorLbl.setVisible(true);
                    }
                }
                else {
                    errorLbl.setText("INVALID INPUT");
                    errorLbl.setVisible(true);
                }   
            }
	}
        
        // Checks if event e overlaps with any event in Events list events
        public boolean overlap(Event e, List<Event> events) {
            int i=0;
            
            while(i < events.size())
            {
                if(events.get(i).getYear() == e.getYear() && events.get(i).getMonth() == e.getMonth() && events.get(i).getDay() == e.getDay())
                {
                    // CASE: SAME TIME SLOT
                    if((events.get(i).getStartTime() == e.getStartTime()) && (events.get(i).getEndTime() == e.getEndTime()))
                        return true;
                    // CASE: EVENT IS WITHIN
                    if((events.get(i).getStartTime() <= e.getStartTime() && events.get(i).getEndTime() >= e.getEndTime()))
                        return true;
                    // CASE: EVENT IS WITHOUT
                    if((events.get(i).getStartTime() >= e.getStartTime() && events.get(i).getEndTime() <= e.getEndTime()))
                        return true;
                    
                }
                i++;
            }
            return false;
        }
        
        class btnCreateEvent_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                eventPanel.setBorder(BorderFactory.createTitledBorder("Create Event"));
                addMode = "event";
                eventPanel.remove(eEnd);
                eventPanel.add(eEnd);
                eventPanel.remove(cmbEnd);
                eventPanel.add(cmbEnd);
                ((javax.swing.border.TitledBorder) eventPanel.getBorder()).
                setTitleFont(dom);
                
                reset();
            }
	}
        class btnCreateTodo_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                eventPanel.remove(eEnd);
                eventPanel.remove(cmbEnd);
                eventPanel.setBorder(BorderFactory.createTitledBorder("Create To-do"));
                addMode = "todo";
                
                ((javax.swing.border.TitledBorder) eventPanel.getBorder()).
                setTitleFont(dom);
                
                reset();
            }
	}
        class calListener extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent evt) {  
                    curCol = calendarTable.getSelectedColumn();  
                    curRow = calendarTable.getSelectedRow();
                    
                    String val = calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()).toString();
                    val = val.replaceAll("\\D+","");
                    eDay.setSelectedItem(val);
                    eMonth.setSelectedIndex(curMonth-1);
                    curDay = Integer.valueOf(val.trim());
                    eYear.setSelectedItem(""+curYear);
                    
                    System.out.println("SELECT * " + " FROM " + Event.TABLE + " WHERE " + 
                        Event.COL_YEAR + " = " + curYear + " AND " +
                        Event.COL_MONTH + " = " + curMonth + " AND " +
                        Event.COL_DAY + " = " + curDay);
                    controller.updateViews(curYear, curMonth, curDay);
                    
                    agendaPanel.setBorder(BorderFactory.createTitledBorder("Agenda for " + curMonth + "/" + curDay + "/" + curYear));
                    ((javax.swing.border.TitledBorder) agendaPanel.getBorder()).
                    setTitleFont(dom);
            }
        }        
}