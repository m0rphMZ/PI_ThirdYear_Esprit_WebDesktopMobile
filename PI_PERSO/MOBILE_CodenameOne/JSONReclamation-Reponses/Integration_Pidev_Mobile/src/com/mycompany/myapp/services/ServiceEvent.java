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
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Aymen
 */
public class ServiceEvent {
    public ArrayList<Event> events;

    public static ServiceEvent instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceEvent() {
        req = new ConnectionRequest();
    }

    public static ServiceEvent getInstance() {
        if (instance == null) {
            instance = new ServiceEvent();
        }
        return instance;
    }
    
    // =========== EVENT PARSERS ==============
    public ArrayList<Event> parseEvents(String jsonText) {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> eventsListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) eventsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Event e = new Event();
                Object idObj = obj.get("eventId");
                if (idObj == null) {
                    e.setEvent_id(0);
                } else {
                    float event_id = Float.parseFloat(idObj.toString());
                    e.setEvent_id((int) event_id);
                }
                if (obj.get("title") == null) {
                    e.setTitle("error");
                } else {
                    e.setTitle(obj.get("title").toString());
                }
                if (obj.get("type") == null) {
                    e.setType("error");
                } else {
                    e.setType(obj.get("type").toString());
                }
                if (obj.get("description") == null) {
                    e.setDescription("error");
                } else {
                    e.setDescription(obj.get("description").toString());
                }
                if (obj.get("startDate") == null) {
                    e.setStartDate(new Date());
                } else {
                    String dateString = obj.get("startdate").toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = dateFormat.parse(dateString);
                        e.setStartDate(date);
                    } catch (ParseException ex) {
                        System.err.println("Error parsing date: " + dateString);
                        e.setStartDate(new Date());
                    }
                }
                if (obj.get("enddate") == null) {
                    e.setEndDate(new Date());
                } else {
                    String dateString = obj.get("enddate").toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date date = dateFormat.parse(dateString);
                        e.setEndDate(date);
                    } catch (ParseException ex) {
                        System.err.println("Error parsing date: " + dateString);
                        e.setEndDate(new Date());
                    }
                }
                if (obj.get("ticketcount") == null) {
                    e.setTicketCount(0);
                } else {
                    float ticketCount = Float.parseFloat(obj.get("ticketcount").toString());
                    e.setTicketCount((int) ticketCount);
                }
                if (obj.get("affiche") == null) {
                    e.setAffiche("error");
                } else {
                    e.setAffiche(obj.get("affiche").toString());
                }
                if (obj.get("ticketprice") == null) {
                    e.setTicketPrice(0);
                } else {
                    float ticketprice = Float.parseFloat(obj.get("ticketprice").toString());
                    e.setTicketPrice(ticketprice);
                }
                
                if (obj.get("host") instanceof Map) {
                    Map<String, Object> host = (Map<String, Object>) obj.get("host");
                    Object userIdObj = host.get("idUser");
                    Object userNomObj = host.get("nom");
                    Object userPrenomObj = host.get("prenom");
                    if (userIdObj == null) {
                        e.setHost_id(0);
                        e.setHostNom("error");
                        e.setHostPrenom("error");
                    } else {
                        float userId = Float.parseFloat(userIdObj.toString());
                        e.setHost_id((int) userId);
                        e.setHostNom(userNomObj.toString());
                        e.setHostPrenom(userPrenomObj.toString());
                    }
                } else {
                    e.setHost_id(0);
                }
                
                if (obj.get("location") instanceof Map) {
                    Map<String, Object> location = (Map<String, Object>) obj.get("location");
                    Object numLocObj = location.get("numLoc");
                    Object lieuLocObj = location.get("lieuLoc");
                    if (numLocObj == null) {
                        e.setLocation_id(0);
                        e.setLocationName("error");
                    } else {
                        float locId = Float.parseFloat(numLocObj.toString());
                        e.setLocation_id((int) locId);
                        e.setLocationName(lieuLocObj.toString());
                    }
                } else {
                    e.setLocation_id(0);
                }
                
                
                events.add(e);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return events;
    }
    
    public Event parseSingleEvent(String jsonText) {
    Event e = new Event();
    try {
        JSONParser j = new JSONParser();
        Map<String, Object> eventJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
        if (eventJSON.get("eventId") == null) {
            e.setEvent_id(0);
        } else {
            float event_id = Float.parseFloat(eventJSON.get("eventId").toString());
            e.setEvent_id((int) event_id);
        }
        if (eventJSON.get("title") == null) {
            e.setTitle("error");
        } else {
            e.setTitle(eventJSON.get("title").toString());
        }
        if (eventJSON.get("type") == null) {
            e.setType("error");
        } else {
            e.setType(eventJSON.get("type").toString());
        }
        if (eventJSON.get("description") == null) {
            e.setDescription("error");
        } else {
            e.setDescription(eventJSON.get("description").toString());
        }
        if (eventJSON.get("startDate") == null) {
            e.setStartDate(new Date());
        } else {
            String dateString = eventJSON.get("startdate").toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = dateFormat.parse(dateString);
                e.setStartDate(date);
            } catch (ParseException ex) {
                System.err.println("Error parsing date: " + dateString);
                e.setStartDate(new Date());
            }
        }
        if (eventJSON.get("enddate") == null) {
            e.setEndDate(new Date());
        } else {
            String dateString = eventJSON.get("enddate").toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = dateFormat.parse(dateString);
                e.setEndDate(date);
            } catch (ParseException ex) {
                System.err.println("Error parsing date: " + dateString);
                e.setEndDate(new Date());
            }
        }
        if (eventJSON.get("ticketcount") == null) {
            e.setTicketCount(0);
        } else {
            float ticketCount = Float.parseFloat(eventJSON.get("ticketcount").toString());
            e.setTicketCount((int) ticketCount);
        }
        if (eventJSON.get("affiche") == null) {
            e.setAffiche("error");
        } else {
            e.setAffiche(eventJSON.get("affiche").toString());
        }
        if (eventJSON.get("ticketprice") == null) {
            e.setTicketPrice(0);
        } else {
            float ticketprice = Float.parseFloat(eventJSON.get("ticketprice").toString());
            e.setTicketPrice(ticketprice);
        }

        if (eventJSON.get("host") instanceof Map) {
            Map<String, Object> host = (Map<String, Object>) eventJSON.get("host");
            Object userIdObj = host.get("idUser");
            Object userNomObj = host.get("nom");
            Object userPrenomObj = host.get("prenom");
            if (userIdObj == null) {
                e.setHost_id(0);
                e.setHostNom("error");
                e.setHostPrenom("error");
            } else {
                float userId = Float.parseFloat(userIdObj.toString());
                e.setHost_id((int) userId);
                e.setHostNom(userNomObj.toString());
                e.setHostPrenom(userPrenomObj.toString());
            }
        } else {
            e.setHost_id(0);
        }

        if (eventJSON.get("location") instanceof Map) {
            Map<String, Object> location = (Map<String, Object>) eventJSON.get("location");
            Object numLocObj = location.get("numLoc");
            Object lieuLocObj = location.get("lieuLoc");
            if (numLocObj == null) {
                e.setLocation_id(0);
                e.setLocationName("error");
            } else {
                float locId = Float.parseFloat(numLocObj.toString());
                e.setLocation_id((int) locId);
                e.setLocationName(lieuLocObj.toString());
            }
        } else {
            e.setLocation_id(0);
        }
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return e;
}   
    // =========== EVENT CRUD ==============
    public ArrayList<Event> getAllEvents() {
        String url = Statics.EVENT_BASE_URL;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                events = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return events;
    }
    
    public boolean addEvent(Event e) {

        String title = e.getTitle();
        String type = e.getType();
        String desc = e.getDescription();
        Date startDate = e.getStartDate();
        Date endDate = e.getEndDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedStartDate = sdf.format(startDate);
        String formattedEndDate = sdf.format(endDate);
        int tCount = e.getTicketCount();
        float tPrice = e.getTicketPrice();
        //Foreign Keys
        int host = e.getHost_id();
        
        
        String url = Statics.ADD_EVENT_BASE_URL 
                + "?title=" + title 
                + "&type=" + type
                + "&desc=" + desc
                + "&startDate=" + formattedStartDate
                + "&endDate=" + formattedEndDate
                + "&tCount=" + tCount
                + "&tPrice=" + tPrice
                + "&host=" + host;

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
    
    public Event getEventById(int id) {
        String url = Statics.SHOW_EVENT_BASE_URL + id;
        req.setUrl(url);
        req.setPost(false);
        final Event[] event = {null}; 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                event[0] = parseSingleEvent(new String(req.getResponseData())); 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return event[0]; 
    }
    
    public boolean updateEvent(Event e) {
        int id = e.getEvent_id();
        String title = e.getTitle();
        String type = e.getType();
        String desc = e.getDescription();
        Date startDate = e.getStartDate();
        Date endDate = e.getEndDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedStartDate = sdf.format(startDate);
        String formattedEndDate = sdf.format(endDate);
        int tCount = e.getTicketCount();
        float tPrice = e.getTicketPrice();

        String url = Statics.UPDATE_EVENT_BASE_URL + id 
                    + "?title=" + title 
                    + "&type=" + type
                    + "&desc=" + desc
                    + "&startDate=" + formattedStartDate
                    + "&endDate=" + formattedEndDate
                    + "&tCount=" + tCount
                    + "&tPrice=" + tPrice;

        req.setUrl(url);
        req.setPost(true);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public boolean deleteEvent(int eventId) {
        String url = Statics.DELETE_EVENT_BASE_URL + eventId;
        
        req.setUrl(url);
        req.setPost(false); 

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
}
