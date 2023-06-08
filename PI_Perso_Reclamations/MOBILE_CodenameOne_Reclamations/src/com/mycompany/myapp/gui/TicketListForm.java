/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.entities.Ticket;
import com.mycompany.myapp.services.ServiceEvent;
import com.mycompany.myapp.services.ServiceTicket;
import java.util.ArrayList;

/**
 *
 * @author Aymen
 */
public class TicketListForm extends Form{
    public TicketListForm(Form previous) {
        setTitle("Liste des tickets");
        setLayout(BoxLayout.y());
        
        ArrayList<Ticket> tickets = ServiceTicket.getInstance().getAllTickets();
        for (Ticket e : tickets) {
            addElement(e);
        }
        
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
    
    public void addElement(Ticket ev) {
        Event event = ServiceEvent.getInstance().getEventById(ev.getEvent_id());
        Container SingleEventContainer = new Container(BoxLayout.y());
        SingleEventContainer.setUIID("EventContainer");
        
        Label TitleValue = new Label("Ticket #" + ev.getTicket_id());
        TitleValue.setUIID("TitleValue");
        Label eventTitle = new Label("Titre d'événement: " + event.getTitle());
        Label startDateLabel = new Label("Date de début: " + event.getStartDate());
        Label endDateLabel = new Label("Date de fin: " + event.getEndDate());
        Label locationLabel = new Label("Lieu: " + event.getLocationName());
        Label ticketPriceLabel = new Label("Prix: " + ev.getPrice()+ " dt.");
        
        Container TitleContainer = new Container(BoxLayout.x());
        TitleContainer.setUIID("TitleContainer");

        Container SingleEventContent = new Container(BoxLayout.y());
        SingleEventContent.setUIID("SEContent");

        TitleContainer.addAll(TitleValue);

        SingleEventContent.addAll(TitleContainer, eventTitle, startDateLabel, endDateLabel, locationLabel, ticketPriceLabel);
        SingleEventContent.getAllStyles().setMarginTop(5);

        SingleEventContainer.addAll(SingleEventContent);

        add(SingleEventContainer);

    }
    

}
