/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Mohamed
 */
public class LivraisonCommandeBackForm extends Form{
    public LivraisonCommandeBackForm(){
    setTitle("Livraisons et commandes");
    setLayout(new BoxLayout(BoxLayout.Y_AXIS));
    getStyle().setBgColor(0xf2f2f2); 
    
    Button commande = new Button("Commande");
    Button livraison = new Button("Livraison");
    
    commande.addActionListener(e-> new CommandeBackForm(this).show());
    livraison.addActionListener(e-> new LivraisonBackForm(this).show());
    
    addAll(commande,livraison);
     
    
        
     getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AdminForm().show());   
        
    
    
    
    }
    
    
    
    
    
}
