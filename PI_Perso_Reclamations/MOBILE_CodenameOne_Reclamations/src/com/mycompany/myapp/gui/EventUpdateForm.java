/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.services.ServiceEvent;

/**
 *
 * @author Aymen
 */
public class EventUpdateForm extends Form{
    public EventUpdateForm(Form previous, int eventId) {
        Event ev = ServiceEvent.getInstance().getEventById(eventId);
        setTitle("Modifier l'événement");
        setLayout(BoxLayout.y());
        
        Toolbar toolbar = getToolbar();
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        
        TextField title = new TextField(ev.getTitle(),"Titre");
        ComboBox<String> type = new ComboBox<>("A L'intérieur", "Conférence", "Exposition");
        type.setSelectedItem(ev.getType());
        TextField description = new TextField(ev.getDescription(),"Déscription");
        
        Picker startDatePicker = new Picker();
        startDatePicker.setType(Display.PICKER_TYPE_DATE);
        startDatePicker.setDate(ev.getStartDate());
        Picker endDatePicker = new Picker();
        endDatePicker.setType(Display.PICKER_TYPE_DATE);
        endDatePicker.setDate(ev.getEndDate());
        TextField ticketCount = new TextField(String.valueOf(ev.getTicketCount()),"Nombre de tickets");
        ticketCount.setConstraint(TextField.NUMERIC);
        TextField ticketPrice = new TextField(String.valueOf(ev.getTicketPrice()),"Prix du ticket");
        ticketPrice.setConstraint(TextField.NUMERIC);
        Button updateBtn = new Button("Mettre à jour");
        
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Event newEv = new Event();
                newEv.setEvent_id(eventId);
                newEv.setHost_id(ev.getHost_id());
                
                newEv.setTitle(title.getText());
                newEv.setType(type.getSelectedItem());
                newEv.setDescription(description.getText());
                newEv.setTicketCount(Integer.parseInt(ticketCount.getText()));
                newEv.setTicketPrice(Float.parseFloat(ticketPrice.getText()));
                
                newEv.setStartDate(startDatePicker.getDate());
                newEv.setEndDate(endDatePicker.getDate());
                newEv.setAffiche(ev.getAffiche());
                newEv.setLocation_id(ev.getLocation_id());
                
                ServiceEvent.getInstance().updateEvent(newEv);
                if (LoginForm.getUserConnected().getRole() == "Admin") {
                    new EventShowForm(new EventsListForm(new AdminForm()), eventId).show();   
                } else {
                    new EventShowForm(new EventsListForm(new HomeForm()), eventId).show();
                }
                 
            }
        });
        
        Container UpdateFormContainer = new Container(BoxLayout.y());
        UpdateFormContainer.setUIID("UFCont");

        UpdateFormContainer.addAll(title, type, description, startDatePicker, endDatePicker, ticketCount, ticketPrice, updateBtn);
        add(UpdateFormContainer);
    }
}
