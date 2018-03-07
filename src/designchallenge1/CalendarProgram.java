/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package designchallenge1;

/**
 *
 * @author Arturo III
 */

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class CalendarProgram{
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
        public Subject data;
        private JPanel eventPanel;
        private JLabel eNameLabel, eColorLabel, eYearLabel, eMonthLabel, eDayLabel;
        private JTextField eName;
        private JComboBox eColor, eYear, eMonth, eDay;
        private JButton btnAdd, btnDelete;
        private JCheckBox chkHoliday;
        public int curRow = -1, curCol = -1;
        private JPanel panel;
        private JLabel lblAgenda;
        private JLabel lblTodoList;
        
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
            
            // SET THE CALENDAR EVENTS
            for (i = 0; i < data.getEvents().size(); i++) {
                int day = data.getEvents().get(i).getDay();
                int row = (day+som-2)/7;
                int column  =  (day+som-2)%7;
                /*
                
                String curVal = modelCalendarTable.getValueAt(row, column) + "";
                curVal = curVal.replaceAll("<html>", "");
                curVal = curVal.replaceAll("</html>", "");
                
                
                String newVal = "<html>" + curVal + "<font color=\"" + data.getEvents().get(i).getColor() + "\"> " + data.getEvents().get(i).getName() + "</font><br/></html>";
               */
                String newVal = modelCalendarTable.getValueAt(row, column) + data.getEvents().get(i).getName();
                if (year == data.getEvents().get(i).getYear() && month == data.getEvents().get(i).getMonth()-1 && !data.getEvents().get(i).isYearly()) {
                    modelCalendarTable.setValueAt(newVal, row, column);
                }
                else if (year >= data.getEvents().get(i).getYear() && month == data.getEvents().get(i).getMonth()-1 && data.getEvents().get(i).isYearly()) {
                    modelCalendarTable.setValueAt(newVal, row, column);
                }
            }
                
            cmbYear.setSelectedItem(""+year);
            eDay.setSelectedItem(""+1);
            eYear.setSelectedItem(""+year);
            eMonth.setSelectedItem(months[month]);
            eColor.setSelectedItem("none");

            calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer(data.getEvents()));
	}
        
	public CalendarProgram() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e) {}
                
            data = new Subject();
            
                
            frmMain = new JFrame ("Calendar Application");
            frmMain.setBackground(Color.WHITE);
            // frmMain.setSize(660, 750);
            frmMain.setSize(910, 838);
            pane = frmMain.getContentPane();
            pane.setLayout(null);
            frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            monthLabel = new JLabel ("January");
            monthLabel.setBounds(91, 7, 110, 50);
            yearLabel = new JLabel ("Change year:");
            yearLabel.setBounds(20, 610, 160, 40);
            cmbYear = new JComboBox();
            cmbYear.setBounds(460, 610, 160, 40);
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
            btnDelete = new JButton("Clear");
            btnDelete.setVisible(false);
                
            calendarTable.addMouseListener(new calListener());  
                
            scrollCalendarTable = new JScrollPane(calendarTable);
            scrollCalendarTable.setBounds(19, 47, 200, 200);
            calendarPanel = new JPanel(null);
            calendarPanel.setBorder(BorderFactory.createTitledBorder("Calendar"));
                
            eNameLabel = new JLabel("Name: ");
            eColorLabel = new JLabel("Color: ");
            eYearLabel = new JLabel("Year: ");
            eMonthLabel = new JLabel("Month: ");
            eDayLabel = new JLabel("Day: ");
            eYear = new JComboBox();
            eMonth = new JComboBox(months);
            eDay = new JComboBox();
            String [] colors = {"RED", "GREEN", "BLUE","none"};
            eColor = new JComboBox(colors);
            eName = new JTextField(15);
            chkHoliday = new JCheckBox("Holiday");
                
            eventPanel = new JPanel(null);
            eventPanel.setBorder(BorderFactory.createTitledBorder("Event"));
		
            btnPrev.addActionListener(new btnPrev_Action());
            btnNext.addActionListener(new btnNext_Action());
            cmbYear.addActionListener(new cmbYear_Action());
                
            btnAdd.addActionListener(new btnAdd_Action());
            btnDelete.addActionListener(new btnDelete_Action());
            eDay.addActionListener(new cmbMonth_Action());
		
            pane.add(calendarPanel);
            calendarPanel.setLayout(null);
            calendarPanel.add(monthLabel);
            calendarPanel.add(yearLabel);
            calendarPanel.add(cmbYear);
            calendarPanel.add(btnPrev);
            calendarPanel.add(btnNext);
            calendarPanel.add(scrollCalendarTable);
               
            calendarPanel.setBounds(0, 0, 640, 670);
                
            pane.add(eventPanel);
            eventPanel.add(eNameLabel);
            eventPanel.add(eColorLabel);
            eventPanel.add(eYearLabel);
            eventPanel.add(eMonthLabel);
            eventPanel.add(eDayLabel);
            eventPanel.add(eName);
            eventPanel.add(eColor);
            eventPanel.add(eYear);
            eventPanel.add(eMonth);
            eventPanel.add(eDay);
            eventPanel.add(btnAdd);
            eventPanel.add(btnDelete);
            eventPanel.add(chkHoliday);
                
            eventPanel.setBounds(0, 670, 640, 140);
            eNameLabel.setBounds(20,20, 160, 30);
            eColorLabel.setBounds(20,50, 160, 30);
            eYearLabel.setBounds(250,20, 160, 30);
            eMonthLabel.setBounds(250,50, 160, 30);
            eDayLabel.setBounds(250,80, 160, 30);
            chkHoliday.setBounds(20,80,100,35);
                
            eName.setBounds(70,20, 160, 30);
            eColor.setBounds(70,50, 160, 30);
            eYear.setBounds(300,20, 160, 30);
            eMonth.setBounds(300,50, 160, 30);
            eDay.setBounds(300,80, 160, 30);
            
                
            btnAdd.setBounds(480,20, 100, 35);
            btnDelete.setBounds(480,55, 100, 35);
            
                
            frmMain.setResizable(false);
            frmMain.setVisible(true);
            
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
            calendarTable.setRowHeight(20);
            
            JPanel panel_2 = new JPanel();
            panel_2.setBackground(Color.WHITE);
            panel_2.setBounds(230, 43, 390, 568);
            calendarPanel.add(panel_2);
            
            lblAgenda = new JLabel("Agenda");
            lblAgenda.setBounds(396, 24, 61, 16);
            calendarPanel.add(lblAgenda);
            
            lblTodoList = new JLabel("To-do List");
            lblTodoList.setBounds(86, 259, 94, 16);
            calendarPanel.add(lblTodoList);
            
            JButton btnCreate = new JButton("Create");
            btnCreate.setBounds(63, 284, 117, 29);
            calendarPanel.add(btnCreate);
            
            JButton btnRemove = new JButton("Remove");
            btnRemove.setBounds(63, 325, 117, 29);
            calendarPanel.add(btnRemove);
            
            JLabel lblEventsList = new JLabel("Event/s List");
            lblEventsList.setBounds(75, 391, 94, 16);
            calendarPanel.add(lblEventsList);
            
            JButton btnCreate_1 = new JButton("Create");
            btnCreate_1.setBounds(63, 419, 117, 29);
            calendarPanel.add(btnCreate_1);
            
            JButton btnRemove_1 = new JButton("Remove");
            btnRemove_1.setBounds(63, 460, 117, 29);
            calendarPanel.add(btnRemove_1);
            
            JPanel todoPanel = new JPanel();
            todoPanel.setBackground(Color.LIGHT_GRAY);
            todoPanel.setForeground(Color.GRAY);
            todoPanel.setBounds(644, 6, 260, 798);
            frmMain.getContentPane().add(todoPanel);
            todoPanel.setLayout(null);
            
            JLabel eventsLabel = new JLabel("Event/s");
            eventsLabel.setBounds(106, 5, 48, 16);
            todoPanel.add(eventsLabel);
            
            panel = new JPanel();
            panel.setBounds(6, 23, 248, 769);
            todoPanel.add(panel);
            modelCalendarTable.setColumnCount(7);
            modelCalendarTable.setRowCount(6);
		
            for (int i = yearBound-100; i <= yearBound+100; i++) {
		cmbYear.addItem(String.valueOf(i));
                eYear.addItem(String.valueOf(i));
            }
            
            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed (ActionEvent tc) {
                    data.updateAll();
                }
            });
            
            timer.start();
            refreshCalendar (monthBound, yearBound); //Refresh calendar
	}
        
        public void reset() {
            eName.setText("");
            eColor.setSelectedItem("none");
            chkHoliday.setSelected(false);
            refreshCalendar(monthToday, yearToday);
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
                reset();
		refreshCalendar(monthToday, yearToday);
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
                reset();
                refreshCalendar(monthToday, yearToday);
            }
	}
	class cmbYear_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
		if (cmbYear.getSelectedItem() != null) {
                    String b = cmbYear.getSelectedItem().toString();
                    yearToday = Integer.parseInt(b);
                    reset();
                    refreshCalendar(monthToday, yearToday);
		}
            }
	}
        class cmbMonth_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
            }
	}
        class btnAdd_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {   
                String name = eName.getText();
                String color = String.valueOf(eColor.getSelectedItem());
                int year = Integer.parseInt(eYear.getSelectedItem().toString());
                int day = Integer.parseInt(eDay.getSelectedItem().toString());
                int month = Arrays.asList(months).indexOf(String.valueOf(eMonth.getSelectedItem())) + 1;
                    
                Event event = new Event(name,color,year,month,day,chkHoliday.isSelected());
                data.addEvent(event);
                
                refreshCalendar(monthToday, yearToday);
                reset();
            }
	}
        class btnDelete_Action implements ActionListener {
            @Override
            public void actionPerformed (ActionEvent e) {
                String val = calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()).toString();
                data.deleteEvent(val);
                btnDelete.setVisible(false);
                refreshCalendar(monthToday, yearToday);
                reset();
            }
	}
        class calListener extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent evt) {  
                // IF USER SELECTS NEW CELL
                if (curCol != calendarTable.getSelectedColumn() || curRow != calendarTable.getSelectedRow()) {
                    curCol = calendarTable.getSelectedColumn();  
                    curRow = calendarTable.getSelectedRow();
                    btnDelete.setVisible(true);
                    
                    String val = calendarTable.getValueAt(calendarTable.getSelectedRow(), calendarTable.getSelectedColumn()).toString();
                    val = val.replaceAll("\\D+","");
                    eDay.setSelectedItem(val);
                }
                // IF USER CLICKS ON CELL AGAIN
                else {
                    curCol = -1;
                    curRow = -1;
                    reset();
                    btnDelete.setVisible(false);
                }
            }
        }        
}