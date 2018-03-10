/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ianona, wonsukcho
 */
public class CalendarService {
    private CalendarDB connection;
	
    public CalendarService (CalendarDB cdb) {
	this.connection = cdb;
    }
	
    private Event toEvent(ResultSet rs) throws SQLException {
	Event e = new Event(rs.getString(Event.COL_NAME),
                            rs.getString(Event.COL_TYPE),
                            rs.getBoolean(Event.COL_DONE),
                            rs.getInt(Event.COL_YEAR),
                            rs.getInt(Event.COL_MONTH),
                            rs.getInt(Event.COL_DAY),
                            rs.getInt(Event.COL_STIME),
                            rs.getInt(Event.COL_ETIME));

	return e;
    }

    public void deleteEvent(String name) {
	Connection connect = connection.getConnection();
	String query = 	"DELETE FROM " + 
			Event.TABLE +
			" WHERE " + Event.COL_NAME + " = ?";
		
	try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, name);
            statement.executeUpdate();
			
            statement.close();
            connect.close();
            System.out.println("[EVENTS] DELETE SUCCESS!");
        } catch (SQLException ev) {
            System.out.println("[EVENTS] DELETE FAILED!");
            ev.printStackTrace();
	}	
    }

    public List<Event> getAll() {
	List <Event> events = new ArrayList <Event> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * " + " FROM " + Event.TABLE;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		events.add(toEvent(rs));
	}
			
            rs.close();
            statement.close();
            connect.close();
			
            System.out.println("[EVENTS] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[EVENTS] SELECT FAILED!");
            return null;
		}	
		
		return events;
    }
    
    public List<Event> getEvents(int year, int month, int day) {
	List <Event> events = new ArrayList <Event> ();
	Connection connect = connection.getConnection();
	String query = 	"SELECT * FROM " + Event.TABLE + " WHERE " + 
                        Event.COL_YEAR + " = ? AND " +
                        Event.COL_MONTH + " = ? AND " +
                        Event.COL_DAY + " = ? ORDER BY " + Event.COL_STIME;

        try {
            PreparedStatement statement = connect.prepareStatement(query);
            
            statement.setInt(1, year);
            statement.setInt(2, month);
            statement.setInt(3, day);
            
            ResultSet rs = statement.executeQuery();
			
            while(rs.next()) {
		events.add(toEvent(rs));
	}
			
            rs.close();
            statement.close();
            connect.close();
			
            System.out.println("[EVENTS] SELECT SUCCESS!");
	} catch (SQLException e) {
            e.printStackTrace();
            System.out.println("[EVENTS] SELECT FAILED!");
            return null;
		}	
		
		return events;
    }

    public void addEvent(Event e) {
        Connection connect = connection.getConnection();
        String query = 	"INSERT INTO " + Event.TABLE +
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connect.prepareStatement(query);
			
            statement.setString(1, e.getName());
            statement.setString(2, e.getType());
            statement.setBoolean(3, e.isDone());
            statement.setInt(4, e.getYear());
            statement.setInt(5, e.getMonth());
            statement.setInt(6, e.getDay());
            statement.setInt(7, e.getStartTime());
            statement.setInt(8, e.getEndTime());
			
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("[EVENTS] INSERT SUCCESS!");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[EVENTS] INSERT FAILED!");
        }	
    }	
	
    public void updateEvent(Event e) {
        try {
            Connection connect = connection.getConnection();
            String query = "UPDATE " +  Event.TABLE + 
                           " SET " + Event.COL_DONE + " = ? " +
                           " WHERE " + Event.COL_NAME + " = ?";
			
            PreparedStatement statement = connect.prepareStatement(query);

            statement.setBoolean(1, e.isDone());
            statement.setString(2, e.getName());
			
            statement.executeUpdate();
            statement.close();
            connect.close();
            System.out.println("[EVENTS] UPDATE SUCCESS! ");
        } catch (SQLException ev) {
            ev.printStackTrace();
            System.out.println("[EVENTS] UPDATE FAILED! ");
        }
    }
}
