/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.ServiceEvent;

/**
 *
 * @author Aymen
 */
public class EventShowForm extends Form{
    public EventShowForm(Form previous, int eventId) {
        Event ev = ServiceEvent.getInstance().getEventById(eventId);
        setTitle(ev.getTitle());
        setLayout(BoxLayout.y());

        Toolbar toolbar = getToolbar();
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        
        Container SingleEventContainer = new Container(BoxLayout.y());
        SingleEventContainer.setUIID("EventContainer");
        
        Label TypeLabel = new Label("Type: " + ev.getType());
        Label DescLabel = new Label("Description: " + ev.getDescription());
        Label startDateLabel = new Label("Date de début: " + ev.getStartDate());
        Label endDateLabel = new Label("Date de fin: " + ev.getEndDate());
        Label locationLabel = new Label("Lieu: " + ev.getLocationName());
        Label hostLabel = new Label("Host: " + ev.getHostNom() + " " + ev.getHostPrenom());
        Label ticketCountLabel = new Label("Tickets restants: " + ev.getTicketCount());
        Label ticketPriceLabel = new Label("Prix: " + ev.getTicketPrice() + " dt.");
        //Update
        Button btnUpdate = new Button("Modifier");
        btnUpdate.addActionListener(e-> new EventUpdateForm(this, ev.getEvent_id()).show());
        //Delete
        Button btnDelete = new Button("Supprimer");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                boolean success = ServiceEvent.getInstance().deleteEvent(eventId); 
                if (success) {
                    Dialog.show("Succés!", "L'événement a été supprimé avec succès", new Command("OK"));
                    if (LoginForm.getUserConnected().getRole() == "Admin") {
                        new EventsListForm(new AdminForm()).show();   
                    } else {
                        new EventsListForm(new HomeForm()).show();
                    }
                }
            }
        });
        
        Container TitleContainer = new Container(BoxLayout.x());
        TitleContainer.setUIID("TitleContainer");

        Container SingleEventContent = new Container(BoxLayout.y());
        SingleEventContent.setUIID("SEContent");

        SingleEventContent.addAll(TypeLabel, DescLabel, startDateLabel, endDateLabel, locationLabel, hostLabel, ticketCountLabel, ticketPriceLabel, btnUpdate, btnDelete);
        SingleEventContent.getAllStyles().setMarginTop(5);

        SingleEventContainer.addAll(SingleEventContent);

        add(SingleEventContainer);
    }
}
