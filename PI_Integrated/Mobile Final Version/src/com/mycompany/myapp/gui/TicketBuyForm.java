/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Event;
import com.mycompany.myapp.entities.Ticket;
import com.mycompany.myapp.services.ServiceEvent;
import com.mycompany.myapp.services.ServiceTicket;

/**
 *
 * @author Aymen
 */
public class TicketBuyForm extends Form{
    public TicketBuyForm(Form previous, int eventId) {
        Event ev = ServiceEvent.getInstance().getEventById(eventId);
        setTitle("Acheter un ticket");
        setLayout(BoxLayout.y());
        
        Label Label1 = new Label("Numéro de la carte");
        TextField cardNumberField = new TextField("","num carte..");
        
        Label Label2 = new Label("Mois (MM)");
        TextField exprMonthField = new TextField("","MM");
        
        Label Label3 = new Label("Année (AA)");
        TextField exprYearField = new TextField("","AA");
        
        Label Label4 = new Label("CVV/CVC");
        TextField cvvField = new TextField("","cvv/cvc");
        
        Button submitBtn = new Button("Payer");
     
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            if (cardNumberField.getText().equals("") || exprMonthField.getText().equals("") || exprYearField.getText().equals("") || cvvField.getText().equals("")) {
                Dialog.show("Erreur!", "Veuillez ne pas laisser de champs vides", new Command("OK"));
            } else {
                String cardNumber = cardNumberField.getText();
                String expMonth = exprMonthField.getText();
                String expYear = exprYearField.getText();
                String cvv = cvvField.getText();
                int price = Math.round(ev.getTicketPrice());     
                
                String message = ServiceTicket.getInstance().BuyTicket(cardNumber, expMonth, expYear, cvv, (int) price);
            if (message.equals("true")) {
                Dialog.show("Success", "payment success", new Command("OK"));
                try {
                Ticket t = new Ticket();
                t.setEvent_id(ev.getEvent_id());
                t.setUser_id(LoginForm.getUserConnected().getId());
                if (ServiceTicket.getInstance().addTicket(t)) {
                    Dialog.show("Succès", "Ticket acheté!", new Command("OK"));
                    if (LoginForm.getUserConnected().getRole().equals("Admin")) {
                        new EventShowForm(new EventsListForm(new AdminForm()), eventId).show();
                    } else {
                        new EventShowForm(new EventsListForm(new HomeForm()), eventId).show();
                    }
                } else {
                    Dialog.show("Erreur", "Erreur serveur", new Command("OK"));
                }
            } catch (Exception e) {
                Dialog.show("Erreur", e.getMessage(), new Command("OK"));
            }

            } else {
                Dialog.show("ERROR", message, new Command("OK"));
            }
                
        }
    }
});
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());         
        addAll(Label1, cardNumberField, Label2, exprMonthField, Label3, exprYearField, Label4, cvvField, submitBtn);
    }
}
