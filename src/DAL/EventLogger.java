/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Event;
import BE.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kristof
 */
public class EventLogger extends ConnectionManager {
    public void log(Event toLog) {
        String query = "insert into [log](date,desc) values (?,?)";
        try(Connection con = super.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setTimestamp(1, toLog.getTimestamp());
            ps.setString(2, toLog.getDescription());
            
            ps.execute();
            con.close();
        } catch (SQLException e) {
            Logger.getLogger(EventLogger.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public Event get(int id) {
        String query = "select * from [log] where [id] = "+id;
        try(Connection con = super.getConnection()) {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            Event event = null;
            while(rs.next()) {
                event = new Event(rs.getInt("id"), rs.getTimestamp("date"), rs.getString("desc"));
            }
            con.close();
            return event;
        } catch (SQLException e) {
            Logger.getLogger(EventLogger.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public List<Event> getAll() {
        String query = "select * from [log]";
        try(Connection con = super.getConnection()) {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            ArrayList<Event> events = new ArrayList<>();
            while(rs.next()) {
                events.add(new Event(rs.getInt("id"), rs.getTimestamp("date"), rs.getString("desc")));
            }
            con.close();
            return events;
        } catch (SQLException e) {
            Logger.getLogger(EventLogger.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
