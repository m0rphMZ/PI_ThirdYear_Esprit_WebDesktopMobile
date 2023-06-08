/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package services;

import entities.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.MyDB;

/**
 *
 * @author Aymen
 */
public class TicketService implements IService<Ticket>{
    Connection cnx;
    
    public TicketService() {
        cnx = MyDB.getInstance().getCnx();
    }

    @Override
    public void ajouter(Ticket t) throws SQLException {
        String req = "INSERT INTO ticket(event_id,user_id,price,qrCodeImg) VALUES (?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getEvent_id());
        ps.setInt(2, t.getUser_id());
        ps.setFloat(3, t.getPrice());
        ps.setString(4, t.getQrCodeImg());
        ps.executeUpdate();
    }

    @Override
    public void modifier(Ticket t) throws SQLException {
        String req = "UPDATE ticket SET event_id = ?,user_id = ?,price = ? WHERE ticket_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getEvent_id());
        ps.setInt(2, t.getUser_id());
        ps.setFloat(3, t.getPrice());
        ps.setInt(4, t.getTicket_id());
        ps.executeUpdate();
    }

    @Override
    public void supprimer(Ticket t) throws SQLException {
        String req = "DELETE FROM ticket WHERE ticket_id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, t.getTicket_id());
        ps.executeUpdate();
    }

    @Override
    public List<Ticket> recuperer() throws SQLException {
        List<Ticket> tickets = new ArrayList<>();
        String s = "select * from ticket";
        Statement st = cnx.createStatement();
        ResultSet rs =  st.executeQuery(s);
        while(rs.next()){
            Ticket t = new Ticket();
            t.setTicket_id(rs.getInt("ticket_id"));
            t.setEvent_id(rs.getInt("event_id"));
            t.setUser_id(rs.getInt("user_id"));
            t.setPrice(rs.getFloat("price"));
            t.setQrCodeImg(rs.getString("qrCodeImg"));
            tickets.add(t); 
        }
        return tickets;
    }
    
}
