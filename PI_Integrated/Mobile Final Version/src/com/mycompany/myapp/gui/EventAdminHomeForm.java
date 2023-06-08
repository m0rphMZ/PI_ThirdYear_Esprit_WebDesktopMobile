/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Aymen
 */
public class EventAdminHomeForm extends Form{
    public EventAdminHomeForm(Form previous) {    
        setTitle("Gestion des événements");
        setLayout(BoxLayout.y());
        Toolbar toolbar = getToolbar();
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        
        
        Button btnAddEvent = new Button("Ajouter un événement");
        Button btnListEvents = new Button("Liste des événements");
        Button btnListTickets = new Button("Liste des tickets");
        
        btnAddEvent.addActionListener(e-> new EventAddForm(this).show());
        btnListEvents.addActionListener(e-> new EventsListForm(this).show());
        btnListTickets.addActionListener(e-> new TicketListForm(this).show());
        
        addAll(btnListEvents, btnListTickets, btnAddEvent);
    }
}
