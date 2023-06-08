/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package services;

import entities.Event;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;

/**
 *
 * @author Aymen
 */
public class EventService implements IService<Event>{
    Connection cnx;

    public EventService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Event t) throws SQLException {
        String req = "INSERT INTO event(title,type,description,startDate,endDate,ticketCount,host_id,location_id,affiche,ticketPrice) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getTitle());
        ps.setString(2, t.getType());
        ps.setString(3, t.getDescription());
        ps.setDate(4, t.getStartDate());
        ps.setDate(5, t.getEndDate());
        ps.setInt(6, t.getTicketCount());
        ps.setInt(7, t.getHost_id());
        ps.setInt(8, t.getLocation_id());
        ps.setString(9, t.getAffiche());
        ps.setFloat(10, t.getTicketPrice());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Event t) throws SQLException {
        String req = "UPDATE event SET title = ?, type = ?, description = ?, startDate = ?, endDate = ?, ticketCount = ?, location_id = ?, affiche = ?, ticketPrice = ? WHERE event_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getTitle());
        ps.setString(2, t.getType());
        ps.setString(3, t.getDescription());
        ps.setDate(4, t.getStartDate());
        ps.setDate(5, t.getEndDate());
        ps.setInt(6, t.getTicketCount());
        ps.setInt(7, t.getLocation_id());
        ps.setString(8, t.getAffiche());
        ps.setFloat(9, t.getTicketPrice());
        ps.setInt(10, t.getEvent_id());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Event t) throws SQLException {
        String req = "DELETE FROM event WHERE event_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getEvent_id());
        ps.executeUpdate();
    }

    @Override
    public List<Event> recuperer() throws SQLException {
        List<Event> events = new ArrayList<>();
        String s = "select * from event";
        Statement st = cnx.createStatement();
        ResultSet rs =  st.executeQuery(s);
        while(rs.next()){
            Event e = new Event();
            e.setEvent_id(rs.getInt("event_id"));
            e.setTitle(rs.getString("title"));
            e.setType(rs.getString("type"));
            e.setDescription(rs.getString("description"));
            e.setStartDate(rs.getDate("startDate"));
            e.setEndDate(rs.getDate("endDate"));
            e.setTicketCount(rs.getInt("ticketCount"));
            e.setHost_id(rs.getInt("host_id"));
            e.setLocation_id(rs.getInt("location_id"));
            e.setTicketPrice(rs.getFloat("ticketPrice"));
            e.setAffiche(rs.getString("affiche"));
            
            events.add(e);
            
        }
        return events;
    }
    
    public Event getEvent(int id) throws SQLException {
        String req = "SELECT * FROM event WHERE event_id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        Event e = new Event();
        if (rs.next()) {
          e.setEvent_id(rs.getInt("event_id"));
          e.setTitle(rs.getString("title"));
          e.setStartDate(rs.getDate("startDate"));
          e.setEndDate(rs.getDate("endDate"));
          e.setTicketPrice(rs.getFloat("ticketPrice"));
        }
        return e;
    }
    
    public List<Event> rechercher(String search, String sortBy) throws SQLException {
        List<Event> filteredEvents = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE title LIKE ?";
        //if (sortBy != null) {
            switch (sortBy) {
                case "Title":
                    sql += " ORDER BY title ASC";
                    break;
                case "Type":
                    sql += " ORDER BY type ASC";
                    break;
                case "Start Date":
                    sql += " ORDER BY startDate ASC";
                    break;
                default:
                    // No sorting
                    break;
            }
        //}
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, "%" + search + "%");
        ResultSet rs =  ps.executeQuery();
        while (rs.next()) {
            Event e = new Event();
            e.setEvent_id(rs.getInt("event_id"));
            e.setTitle(rs.getString("title"));
            e.setType(rs.getString("type"));
            e.setDescription(rs.getString("description"));
            e.setStartDate(rs.getDate("startDate"));
            e.setEndDate(rs.getDate("endDate"));
            e.setTicketCount(rs.getInt("ticketCount"));
            e.setHost_id(rs.getInt("host_id"));
            e.setLocation_id(rs.getInt("location_id"));
            e.setTicketPrice(rs.getFloat("ticketPrice"));
            e.setAffiche(rs.getString("affiche"));
            
            filteredEvents.add(e);
        }
    
        return filteredEvents;
    }

}
