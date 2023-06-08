/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.ServiceEvent;
import java.util.ArrayList;

/**
 *
 * @author Aymen
 */
public class EventsListForm extends Form{
    public EventsListForm(Form previous) {
        setTitle("Liste des événements");
        setLayout(BoxLayout.y());
        
        //Search Bar
        Container SearchContainer = new Container(BoxLayout.y());
        SearchContainer.setUIID("Event Search");
        Label SearchTitle = new Label("Rechercher un événement");
        SearchTitle.setUIID("SearchTitle");
        TextField searchField = new TextField("", "...");
        searchField.setHintIcon(FontImage.createMaterial(FontImage.MATERIAL_SEARCH, "Hint", 4.5f));

        Button searchButton = new Button("Rechercher");
        searchButton.addActionListener(e -> {
            String searchTerm = searchField.getText();
            updateList(searchTerm, SearchContainer);
        });
        SearchContainer.addAll(SearchTitle, searchField, searchButton);
        add(SearchContainer);
        
        ArrayList<Event> events = ServiceEvent.getInstance().getAllEvents();
        for (Event e : events) {
            addElement(e);
        }
        
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

    public void addElement(Event ev) {
        Container SingleEventContainer = new Container(BoxLayout.y());
        SingleEventContainer.setUIID("EventContainer");
        
        Label TitleValue = new Label(ev.getTitle());
        TitleValue.setUIID("TitleValue");
        Label TypeLabel = new Label("Type: " + ev.getType());
        Label DescLabel = new Label("Description: " + ev.getDescription());
        //Date Formatter:
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedStartDate = dateFormat.format(ev.getStartDate());
        String formattedEndDate = dateFormat.format(ev.getEndDate());
        Label startDateLabel = new Label("Date de début: " + formattedStartDate);
        Label endDateLabel = new Label("Date de fin: " + formattedEndDate);
        //======
        Label locationLabel = new Label("Lieu: " + ev.getLocationName());
        Label hostLabel = new Label("Host: " + ev.getHostNom() + " " + ev.getHostPrenom());
        Label ticketCountLabel = new Label("Tickets restants: " + ev.getTicketCount());
        Label ticketPriceLabel = new Label("Prix: " + ev.getTicketPrice() + " dt.");
        Button btnShowInfo = new Button("Voir plus d'information");
        btnShowInfo.addActionListener(e-> new EventShowForm(this, ev.getEvent_id()).show());
        
        Container TitleContainer = new Container(BoxLayout.x());
        TitleContainer.setUIID("TitleContainer");

        Container SingleEventContent = new Container(BoxLayout.y());
        SingleEventContent.setUIID("SEContent");

        TitleContainer.addAll(TitleValue);

        SingleEventContent.addAll(TitleContainer, TypeLabel, startDateLabel, endDateLabel, ticketPriceLabel, btnShowInfo);
        SingleEventContent.getAllStyles().setMarginTop(5);

        SingleEventContainer.addAll(SingleEventContent);

        add(SingleEventContainer);

    }
    
    public void updateList(String searchTerm, Container SearchContainer) {
        removeAll();
        add(SearchContainer);
        ArrayList<Event> events = ServiceEvent.getInstance().getAllEvents();
        for (Event e : events) {
            if (e.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                addElement(e);
            }
        }
        revalidate();
    }
}
