/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;


import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;

import java.util.Date;

import com.mycompany.myapp.entities.CategorieLocal;
import com.mycompany.myapp.services.LocalService;
/**
 *
 * @author Administrateur
 */
public class CategorieList extends Form{
    
     private final ArrayList<CategorieLocal> catego;

    public CategorieList() {
        super("Categorie List");

        getContentPane().setScrollVisible(false);

        // Get all the evenements from the server
        catego = LocalService.getInstance().getAllCategories();

        Button ajout = new Button("Ajouter  nouvelle categorie");

        ajout.addActionListener(b -> {
            CategorieAddForm a=new CategorieAddForm(this);
            a.show();
        });

        add(new Label("______________________________________________________________________________________________________________________"));

        add(ajout);
        add(new Label("______________________________________________________________________________________________________________________"));

        // Create a container to hold the evenements
        Container evenementContainer = new Container();
        evenementContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
//        evenementContainer.setScrollableY(true);        
        // Add each evenement to the container with buttons to reserve, edit, and delete it
//           Button reserveBtn = new Button("Add");
//            reserveBtn.addActionListener(e -> {
        //  Reserve the evenement
//           });
        for (CategorieLocal local : catego) {

           // Create a container to hold the evenement's information and buttons
            Container evenementRow = new Container(new BorderLayout());
            evenementRow.setUIID("UserBox");

            // Create labels to display the evenement's information
            Label code_localLabel = new Label("code_categorie: " + local.getCode());
            code_localLabel.setUIID("code_localLabel");

            Label libelle_localLabel = new Label("libelle_categorie: " + local.getLibelle());
            libelle_localLabel.setUIID("libelle_localLabel");

            Label color_localLabel = new Label("color_categorie: " + local.getColor());
            color_localLabel.setUIID("color_localLabel");

            

            Label separator = new Label("******");
            //categorie_idLabel.setUIID("separator");

            System.out.println("test");


Button remove = new Button("remove");
                System.out.println(local.getCode());
            remove.addActionListener(e -> {

              LocalService.getInstance().deleteCatg(local.getCode());
                 CategorieList a = new CategorieList();
                a.show();
                
                evenementContainer.removeComponent(evenementRow);
            });
            
         
            Button modifier = new Button("modifier");
            //modifier.addActionListener(e -> {
              //   modifierLocal h = new modifierEvenement(res,current,p);
                //h.show();
             modifier.addActionListener(e-> new updateCategorieLocal(this, local.getCode()).show());
                System.out.println("cccc");
                
          //  });



            // Add the labels to the evenement row
            Container labelsContainer = new Container(new GridLayout(5, 1));
            labelsContainer.add(code_localLabel);
            labelsContainer.add(libelle_localLabel);
            labelsContainer.add(color_localLabel);
           
          //  labelsContainer.add(new Label("****"));
            
            labelsContainer.add(separator);
            

            /*labelsContainer.add(description_evenementLabel);
            labelsContainer.add(utilisateur_idLabel);
            labelsContainer.add(imageLabel);
            labelsContainer.add(deleteBtn);*/
            evenementRow.add(BorderLayout.CENTER, labelsContainer);

//           //  Create a container to hold the buttons
            Container buttonsContainer = new Container(new GridLayout(3, 1));
            buttonsContainer.setUIID("evenementButtonBox");

            
            //    add(modifier);

            buttonsContainer.add(remove);
            buttonsContainer.add(modifier);
//           // buttonsContainer.add(deleteBtn);
//         
            evenementRow.add(BorderLayout.EAST, buttonsContainer);

            // Add the evenement row to the container
            evenementContainer.add(evenementRow);

        }

        // Add the container to the form
        add(evenementContainer);

    }

    public void showList() {
        CategorieList form = new CategorieList();
        form.show();

    }
    
    
    
    
    
    
}
