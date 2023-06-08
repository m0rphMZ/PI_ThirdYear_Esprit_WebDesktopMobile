/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;

/**
 *
 * @author Mohamed
 */
public class HomeForm extends Form{
    public HomeForm() {
        
        setTitle("Home");
        setLayout(BoxLayout.y());
      
        getToolbar().addCommandToOverflowMenu("Déconnecter", null, ev -> {
    LoginForm loginForm = new LoginForm();
    loginForm.show();
});
        
        add(new Label("Dashboard"));
        
        
        Button list = new Button("Profile");
        Button event = new Button("Evenements");
        Button cart = new Button("Panier");
        Button reclamation = new Button("Reclamations");
        Button produit = new Button("Produit");
        Button locaux = new Button("Locaux");
        Button mesCommandes = new Button("Mes Commandes");
        
    
        
        list.addActionListener(e-> new Profile (this).show());
        reclamation.addActionListener(e-> new RecFrontHomeForm (this).show());
        event.addActionListener(e-> new EventHomeForm (this).show());
        cart.addActionListener(e-> new NewCartForm (this).show());
        locaux.addActionListener(e-> new FrontLoc (this).show());
        produit.addActionListener(e-> new ListProduittForm (this).show());
        mesCommandes.addActionListener(e-> new mesCommandeForm (this).show());
        addAll(list, cart,event,reclamation,produit,locaux,mesCommandes);
        
        
    }
    
    
    
}
