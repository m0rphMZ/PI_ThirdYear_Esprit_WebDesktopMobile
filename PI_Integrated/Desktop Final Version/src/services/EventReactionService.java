/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package services;

import entities.Event;
import entities.EventReaction;
import entities.EventReaction.Reaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.MyDB;

/**
 *
 * @author Aymen
 */
public class EventReactionService {
    Connection cnx;
    
    public EventReactionService() {
        cnx = MyDB.getInstance().getCnx();
    }
    
    public void ajouter(EventReaction er) throws SQLException {
        String req = "INSERT INTO eventreaction (event_id, user_id, reaction) VALUES (?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, er.getEvent_id());
        ps.setInt(2, er.getUser_id());
        ps.setString(3, er.getReaction().name());
        ps.executeUpdate();
    }
    
    public void modifier(EventReaction er) throws SQLException {
        String req = "UPDATE eventreaction SET reaction = ? WHERE event_id = ? AND user_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, er.getReaction().name());
        ps.setInt(2, er.getEvent_id());
        ps.setInt(3, er.getUser_id());
        ps.executeUpdate();
    }
    
    public int reactionCount(int eventId, Reaction reaction) throws SQLException {
        String req = "SELECT COUNT(*) FROM eventreaction WHERE event_id = ? AND reaction = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, eventId);
        ps.setString(2, reaction.name());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
    }
    
    public boolean alreadyReacted(int eventId, int userId) throws SQLException {
        String req = "SELECT COUNT(*) FROM eventreaction WHERE event_id = ? AND user_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, eventId);
        ps.setInt(2, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0; // if the count is greater than 0, the user has already reacted
        } else {
            return false;
        }
    }

}
