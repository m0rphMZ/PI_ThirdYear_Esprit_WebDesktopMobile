/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.utils.Statics;
import java.util.ArrayList;
import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.mycompany.myapp.entities.Ticket;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Aymen
 */
public class ServiceTicket {
    public ArrayList<Ticket> tickets;

    public static ServiceTicket instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceTicket() {
        req = new ConnectionRequest();
    }

    public static ServiceTicket getInstance() {
        if (instance == null) {
            instance = new ServiceTicket();
        }
        return instance;
    }
    
    // =========== TICKET PARSERS ==============
    public ArrayList<Ticket> parseTickets(String jsonText) {
        try {
            tickets = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ticketsListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) ticketsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Ticket e = new Ticket();
                Object idObj = obj.get("ticketId");
                if (idObj == null) {
                    e.setTicket_id(0);
                } else {
                    float ticket_id = Float.parseFloat(idObj.toString());
                    e.setTicket_id((int) ticket_id);
                }
                if (obj.get("price") == null) {
                    e.setPrice(Float.parseFloat("0"));
                } else {
                    float ticketprice = Float.parseFloat(obj.get("price").toString());
                    e.setPrice(ticketprice);
                }
                if (obj.get("qrcodeimg") == null) {
                    e.setQrCodeImg("error");
                } else {
                    e.setQrCodeImg(obj.get("qrcodeimg").toString());
                }
                
                if (obj.get("host") instanceof Map) {
                    Map<String, Object> host = (Map<String, Object>) obj.get("host");
                    Object userIdObj = host.get("idUser");
                    Object userNomObj = host.get("nom");
                    Object userPrenomObj = host.get("prenom");
                    if (userIdObj == null) {
                        e.setUser_id(0);
                    } else {
                        float userId = Float.parseFloat(userIdObj.toString());
                        e.setUser_id((int) userId);
                    }
                } else {
                    e.setUser_id(0);
                }
                
                if (obj.get("event") instanceof Map) {
                    Map<String, Object> event = (Map<String, Object>) obj.get("event");
                    Object numEvObj = event.get("eventId");
                    if (numEvObj == null) {
                        e.setEvent_id(0);
                    } else {
                        float evId = Float.parseFloat(numEvObj.toString());
                        e.setEvent_id((int) evId);
                    }
                } else {
                    e.setEvent_id(0);
                }
                
                
                tickets.add(e);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return tickets;
    }
    
    
    // =========== Ticket CRUD ==============
    public ArrayList<Ticket> getAllTickets() {
        String url = Statics.TICKET_BASE_URL;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tickets = parseTickets(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tickets;
    }
    
    public boolean addTicket(Ticket t) {
        int eventId = t.getEvent_id();
        int userId = t.getUser_id();
        
        
        
        String url = Statics.ADD_EVENT_BASE_URL 
                + "?eventId=" + eventId 
                + "&userId=" + userId;

        req.setUrl(url);
        req.setPost(true);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
