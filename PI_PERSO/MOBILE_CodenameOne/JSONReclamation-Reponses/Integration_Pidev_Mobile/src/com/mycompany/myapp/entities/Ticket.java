/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.entities;

/**
 *
 * @author Aymen
 */
public class Ticket {
    private int ticket_id, event_id, user_id;
    private Float price;
    private String qrCodeImg;

    public Ticket(int ticket_id, int event_id, int user_id, Float price, String qrCodeImg) {
        this.ticket_id = ticket_id;
        this.event_id = event_id;
        this.user_id = user_id;
        this.price = price;
        this.qrCodeImg = qrCodeImg;
    }

    public Ticket(int event_id, int user_id, Float price, String qrCodeImg) {
        this.event_id = event_id;
        this.user_id = user_id;
        this.price = price;
        this.qrCodeImg = qrCodeImg;
    }

    public Ticket() {
    }

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getQrCodeImg() {
        return qrCodeImg;
    }

    public void setQrCodeImg(String qrCodeImg) {
        this.qrCodeImg = qrCodeImg;
    }

    @Override
    public String toString() {
        return "Ticket{" + "ticket_id=" + ticket_id + ", event_id=" + event_id + ", user_id=" + user_id + ", price=" + price + ", qrCodeImg=" + qrCodeImg + '}';
    }
    
    
}
