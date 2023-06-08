/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;


import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

import java.util.Date;
import com.mycompany.myapp.entities.CategorieLocal;

import com.mycompany.myapp.entities.Local;
import com.mycompany.myapp.services.LocalService;
/**
/**
 *
 * @author Administrateur
 */
public class CategorieAddForm extends Form{
      Form current;
    
    
   
    
    
    public CategorieAddForm(Form previous){
    
       super("Add Categorie");
               setTitle("Ajouter une nouvelle categorie");
        setLayout(BoxLayout.y());
        Toolbar toolbar = getToolbar();
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
//       
//         getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK,
//                ev -> {previous.showBack();} );
       
        // Create text fields and a combo box for the evenement information
       // Label label=new Label("disponibilte");
          
         
          TextField code_localField = new TextField("", "code");
        TextField libelle_localField = new TextField("", "libelle");
        TextField color_localField = new TextField("", "color");
//          TextField nbpers_localField = new TextField("", "nbper");
//        Picker dateEventPicker = new Picker();
//        TextField prix_localField = new TextField("", "prix");
//         TextField imageField = new TextField("", "image");
//         TextField equipement_evenementField = new TextField("", "equipements");
//      //   TextField dispo_evenementField = new TextField("", "disponibilite");
//          ComboBox<String> recType = new ComboBox<>("oui", "non");
        // Create a button to add the evenement
        Button addBtn = new Button("Add new one");
        addBtn.addActionListener(e -> {
            // Validate the entered values
        //    try {
               // String code = code_localField.getText();
                String libelle = libelle_localField.getText();
                String color = color_localField.getText();
       
             
            int code = Integer.parseInt(code_localField.getText());


                // Create a new demande with the entered information
                
                
                 if (libelle_localField.getText().equals("") || color_localField.getText().equals("") ) {
            Dialog.show("Erreur", "Tous les champs sont obligatoires", new Command("OK"));
       } 
//else if (teltf.getText().length() != 8) {
//            Dialog.show("Erreur", "Le numéro de téléphone doit contenir 8 chiffres", new Command("OK"));
//        } else if (!emailtf.getText().contains("@")) {
//            Dialog.show("Erreur", "Email non valide", new Command("OK"));
//                
//        }
                
            else{
                
                      //CategorieLocal c=new CategorieLocal();
       
     
                     
                     
                
                CategorieLocal event = new CategorieLocal();
                event.setCode(code);
                event.setLibelle(libelle);
                event.setColor(color);
                   
     System.out.println(event.getCode());
     System.out.println(event.getLibelle());
//                event.setDate(date.toString());
//                event.setPrix(prix_loc);
//                event.setEquipements(equipements);
//               //  event.setDisponibilite(disponibilite);
//                event.setImage(image);
//                 String selectedType = (String) recType.getSelectedItem();
//                        event.setDisponibilite(selectedType);
//               // event.setNbper(99);
//               



//               
              // LocalService.getInstance().addLocal(event);
//                
                 if( LocalService.getInstance().addCategorie(event))
                        {
                          Dialog.show("Succès","Votre categorie a été ajoutée",new Command("OK"));
//                           //new ListReclamationsUserForm(LoginForm.getUserConnected().getId()).show();
                       }else{
                           Dialog.show("ERROR", "Server error", new Command("OK"));
//                    /*} catch (NumberFormatException e) {
//                        Dialog.show("ERROR", "Status must be a number");
                    }
                
                new  LocalListForm(this).show();


                // Close the form
               // this.close();
          //  } catch (NumberFormatException ex) {
                // Handle the case where the entered values are not valid numbers
               //Dialog.show("Error", "Please enter valid inputs", "OK", null);
         //   }
                    }
        });
       
        // Add the text fields and combo box to the form
        add(code_localField);
        add(libelle_localField);
        //add(dateEventPicker);
       // add(nbpers_localField);
        add(color_localField);
//        add(prix_localField);
//        add(imageField);
//        add(equipement_evenementField);
        //add(label);
        // add(recType);
         
        
        

        add(addBtn);
    
    
    
    }
    
  public void showAdd() {
     CategorieAddForm form = new CategorieAddForm(this);
    form.show();
}   
 
    
}
