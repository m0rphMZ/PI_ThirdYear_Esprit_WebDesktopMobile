/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author bhk
 */
public class RecBackHomeForm extends Form{

    public RecBackHomeForm(Form previous) {
        
        setTitle("Admin - Reclamations");
        setLayout(BoxLayout.y());
        
        add(new Label("Choisis une option"));
        Button btnListRec = new Button("Consulter toutes les réclamations");
        Button btnStatRec = new Button("Statistiques");
        
        
        btnStatRec.addActionListener(e-> new StatReclamationForm(this).show());
        
        btnListRec.addActionListener(e -> {
        ListReclamationsForm listRecForm = new ListReclamationsForm(this);
        listRecForm.show();
    });
        
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        addAll(btnListRec, btnStatRec);
        
        
    }
    
    
}
