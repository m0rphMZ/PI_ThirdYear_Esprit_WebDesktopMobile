/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceEvent;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author Aymen
 */
public class EventAddForm extends Form{
    public EventAddForm(Form previous) {
        User UserCn = LoginForm.getUserConnected();
        setTitle("Ajouter un événement");
        setLayout(BoxLayout.y());
        TextField title = new TextField("","Titre");
        ComboBox<String> type = new ComboBox<>("A L'intérieur", "Conférence", "Exposition");
        TextField description = new TextField("","Déscription");
        Picker startDatePicker = new Picker();
        startDatePicker.setType(Display.PICKER_TYPE_DATE);
        Picker endDatePicker = new Picker();
        endDatePicker.setType(Display.PICKER_TYPE_DATE);
        TextField ticketCount = new TextField("","Nombre de tickets");
        ticketCount.setConstraint(TextField.NUMERIC);
        TextField ticketPrice = new TextField("","Prix du ticket");
        ticketPrice.setConstraint(TextField.NUMERIC);
        
        Button submitBtn = new Button("Créer");
     
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            if (title.getText().equals("") || description.getText().equals("") || ticketCount.getText().equals("") || ticketPrice.getText().equals("")) {
                Dialog.show("Erreur!", "Veuillez ne pas laisser de champs vides", new Command("OK"));
            } else if (!(isNumeric(ticketCount.getText()))) {
                Dialog.show("Erreur", "Nombre de tickets invalide", new Command("OK"));
            } else if (!(isFloat(ticketPrice.getText()))) {
                Dialog.show("Erreur", "Prix du ticket invalide", new Command("OK"));
            } else {
                try {
                Event e = new Event();
                e.setTitle(title.getText());
                String typeValue = (String) type.getSelectedItem();
                e.setType(typeValue);
                e.setDescription(description.getText());
                e.setStartDate(startDatePicker.getDate());
                e.setEndDate(endDatePicker.getDate());
                e.setTicketCount(Integer.parseInt(ticketCount.getText()));
                e.setTicketPrice(Float.parseFloat(ticketPrice.getText()));
                e.setHost_id(UserCn.getId());
                if (ServiceEvent.getInstance().addEvent(e)) {
                    Dialog.show("Succès", "Event ajouté", new Command("OK"));
                    if (LoginForm.getUserConnected().getRole() == "Admin") {
                        new EventHomeForm(new AdminForm()).show();
                    } else {
                        new EventHomeForm(new HomeForm()).show();
                    }
                } else {
                    Dialog.show("Erreur", "Erreur serveur", new Command("OK"));
                }
            } catch (Exception e) {
                Dialog.show("Erreur", e.getMessage(), new Command("OK"));
            }
        }
    }
});
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());         
        addAll(title, type, description, startDatePicker, endDatePicker, ticketCount, ticketPrice, submitBtn);
    }

// Method to check if a string is a valid integer
private boolean isNumeric(String str) {
    if (str == null || str.length() == 0) {
        return false;
    }
    try {
        Integer.parseInt(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}

// Method to check if a string is a valid float
private boolean isFloat(String str) {
    if (str == null || str.length() == 0) {
        return false;
    }
    try {
        Float.parseFloat(str);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
}
}
