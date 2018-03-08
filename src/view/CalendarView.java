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
import model.Event;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;
import java.awt.event.*;
import java.util.Arrays;
import java.util.GregorianCalendar;

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
        private JLabel lblEventsList;
        private JLabel lblTodoList;
        private JButton btnCreateEvent;
        private JButton btnCreateTodo;
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
                
            frmMain = new JFrame ("Productivity Application");
            frmMain.setBackground(Color.WHITE);
            // frmMain.setSize(660, 750);
            frmMain.setSize(910, 546);
            pane = frmMain.getContentPane();
            pane.setLayout(null);
            frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            monthLabel = new JLabel ("January");
            monthLabel.setBounds(91, 7, 110, 50);
            yearLabel = new JLabel ("Change year:");
            yearLabel.setBounds(14, 257, 94, 27);
            cmbYear = new JComboBox();
            cmbYear.setBounds(117, 250, 107, 40);
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
                
            btnAdd = new JButton("Add");
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
                for (int min = 0; min < 60; min += 15) {
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
               
            calendarPanel.setBounds(0, 0, 640, 370);
                
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
                
            eventPanel.setBounds(0, 375, 640, 140);
            eNameLabel.setBounds(20,20, 160, 30);
            eStart.setBounds(20, 50, 160, 30);
            eEnd.setBounds(20, 80, 160, 30);
            eYearLabel.setBounds(250,20, 160, 30);
            eMonthLabel.setBounds(250,50, 160, 30);
            eDayLabel.setBounds(250,80, 160, 30);
                
            eName.setBounds(70,20, 160, 30);
            cmbStart.setBounds(90,50,150,30);
            cmbEnd.setBounds(90,80,150,30);
            eYear.setBounds(300,20, 160, 30);
            eMonth.setBounds(300,50, 160, 30);
            eDay.setBounds(300,80, 160, 30);
                
            btnAdd.setBounds(480,20, 100, 35);
            errorLbl.setBounds(480, 55, 160, 30);
            errorLbl.setForeground(Color.red);
            errorLbl.setVisible(false);
                
            pane.setBackground(new Color(220,220,220));
            eventPanel.setBackground(Color.LIGHT_GRAY);
            calendarPanel.setBackground(Color.LIGHT_GRAY);
		
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
            agendaPanel.setBounds(230, 35, 390, 317);
            agendaPanel.setBorder(BorderFactory.createTitledBorder("Agenda for Today"));
            calendarPanel.add(agendaPanel);
            
            lblTodoList = new JLabel("To-do List");
            lblTodoList.setBounds(25, 304, 94, 16);
            calendarPanel.add(lblTodoList);
            
            btnCreateTodo = new JButton("Create To-do");
            btnCreateTodo.setBounds(6, 321, 113, 29);
            btnCreateTodo.addActionListener(new btnCreateTodo_Action());
            calendarPanel.add(btnCreateTodo);
            
            lblEventsList = new JLabel("Event/s List");
            lblEventsList.setBounds(128, 303, 94, 16);
            calendarPanel.add(lblEventsList);
            
            btnCreateEvent = new JButton("Create Event");
            btnCreateEvent.setBounds(116, 320, 110, 29);
            btnCreateEvent.addActionListener(new btnCreateEvent_Action());
            calendarPanel.add(btnCreateEvent);
            
            schedPanel = new JPanel();
            schedPanel.setBackground(Color.LIGHT_GRAY);
            schedPanel.setBounds(645, 0, 260, 515);
            pane.add(schedPanel);
            
            schedPanel.setBorder(BorderFactory.createTitledBorder("Schedule Today"));
            
            modelCalendarTable.setColumnCount(7);
            modelCalendarTable.setRowCount(6);
            for (int i = yearBound-100; i <= yearBound+100; i++) {
		cmbYear.addItem(String.valueOf(i));
                eYear.addItem(String.valueOf(i));
            }
            yearToday += 100;
            
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
                calendarTable.changeSelection(-1, -1, true, true);

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
                    if (eTime % 100 > 60) {
                        int min = eTime % 100;
                        eTime = (eTime / 100) + 1;
                        if (eTime > 23)
                            eTime = 0;
                        eTime *= 100;
                        eTime += min % 60;
                    }
                }
                    
                Event event = new Event(name,addMode,false,year,month,day,sTime,eTime);
                if (!name.isEmpty()) {
                    errorLbl.setVisible(false);
                    controller.addEvent(event);
                    reset();
                }
                else {
                    errorLbl.setText("INVALID INPUT");
                    errorLbl.setVisible(true);
                }   
            }
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
                    curDay = Integer.valueOf(val.trim());
                    
                    System.out.println("SELECT * " + " FROM " + Event.TABLE + " WHERE " + 
                        Event.COL_YEAR + " = " + curYear + " AND " +
                        Event.COL_MONTH + " = " + curMonth + " AND " +
                        Event.COL_DAY + " = " + curDay);
                    controller.updateViews(curYear, curMonth, curDay);
            }
        }        
}