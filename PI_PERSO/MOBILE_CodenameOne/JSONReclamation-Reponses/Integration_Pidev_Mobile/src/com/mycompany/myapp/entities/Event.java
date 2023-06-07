/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author Aymen
 */
public class Event {
    private int event_id, ticketCount, host_id, location_id;
    private String title, type, description, affiche;
    private Date startDate, endDate;
    private float ticketPrice;
    
    //Extra Attributes:
    private String hostNom, hostPrenom, locationName;

    public Event(int event_id, int ticketCount, int host_id, int location_id, String title, String type, String description, String affiche, Date startDate, Date endDate, float ticketPrice) {
        this.event_id = event_id;
        this.ticketCount = ticketCount;
        this.host_id = host_id;
        this.location_id = location_id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.affiche = affiche;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ticketPrice = ticketPrice;
    }

    public Event(int ticketCount, int host_id, int location_id, String title, String type, String description, String affiche, Date startDate, Date endDate, float ticketPrice) {
        this.ticketCount = ticketCount;
        this.host_id = host_id;
        this.location_id = location_id;
        this.title = title;
        this.type = type;
        this.description = description;
        this.affiche = affiche;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ticketPrice = ticketPrice;
    }

    public Event() {
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getHost_id() {
        return host_id;
    }

    public void setHost_id(int host_id) {
        this.host_id = host_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAffiche() {
        return affiche;
    }

    public void setAffiche(String affiche) {
        this.affiche = affiche;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(float ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public String toString() {
        return "Event{" + "event_id=" + event_id + ", ticketCount=" + ticketCount + ", host_id=" + host_id + ", location_id=" + location_id + ", title=" + title + ", type=" + type + ", description=" + description + ", affiche=" + affiche + ", startDate=" + startDate + ", endDate=" + endDate + ", ticketPrice=" + ticketPrice + '}';
    }

    public String getHostNom() {
        return hostNom;
    }

    public void setHostNom(String hostNom) {
        this.hostNom = hostNom;
    }

    public String getHostPrenom() {
        return hostPrenom;
    }

    public void setHostPrenom(String hostPrenom) {
        this.hostPrenom = hostPrenom;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    
    
}
