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

import com.mycompany.myapp.entities.Local;
import com.mycompany.myapp.services.LocalService;
/**
 *
 * @author Administrateur
 */
public class LocalAddForm  extends Form{
    
    Form current;
    
    
   
    
    
    public LocalAddForm( ){
    
       super("Add Evenement");
               setTitle("Ajouter une nouvelle réclamation");
        setLayout(BoxLayout.y());
        
//       
//         getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK,
//                ev -> {previous.showBack();} );
       
        // Create text fields and a combo box for the evenement information
        Label label=new Label("disponibilte");
          
         
          TextField descript_localField = new TextField("", "descript");
        TextField lieu_localField = new TextField("", "lieu");
        TextField surface_localField = new TextField("", "surface");
          TextField nbpers_localField = new TextField("", "nbper");
        Picker dateEventPicker = new Picker();
        TextField prix_localField = new TextField("", "prix");
         TextField imageField = new TextField("", "image");
         TextField equipement_evenementField = new TextField("", "equipements");
      //   TextField dispo_evenementField = new TextField("", "disponibilite");
          ComboBox<String> recType = new ComboBox<>("oui", "non");
        // Create a button to add the evenement
        Button backBtn = new Button("back");
          backBtn.addActionListener(e -> {
          
          LocalListForm f=new LocalListForm(this);
          f.show();
          
          });
        Button addBtn = new Button("Add");
        addBtn.addActionListener(e -> {
            // Validate the entered values
        //    try {
                String descript = descript_localField.getText();
                String lieu = lieu_localField.getText();
                Date date = dateEventPicker.getDate();
               
                
                
                
                  float surface_loc = 0;
                 if (!surface_localField.getText().isEmpty()) {
        surface_loc = Float.parseFloat(surface_localField.getText());
    }
    
    float prix_loc = 0;
    if (!prix_localField.getText().isEmpty()) {
        prix_loc = Float.parseFloat(prix_localField.getText());
    }
                
                
                
                
//                float surface = Float.parseFloat(surface_localField.getText());
//              float prix = Float.parseFloat(prix_localField.getText());

                String image = imageField.getText();
                String equipements = equipement_evenementField.getText();
                // String disponibilite = dispo_evenementField.getText();

                // Check that the entered values are valid
//           if (descript.isEmpty() || lieu.isEmpty()|| equipements.isEmpty()) {
//                  Dialog.show("Error", "Please enter valid values for all fields", "OK", null);
//                   return;
//            }

                // Create a new demande with the entered information
                
                
                 if (descript_localField.getText().equals("") || lieu_localField.getText().equals("") || equipement_evenementField.getText().equals("") || recType.getSelectedIndex() == 0 ) {
            Dialog.show("Erreur", "Tous les champs sont obligatoires", new Command("OK"));
       } 
//else if (teltf.getText().length() != 8) {
//            Dialog.show("Erreur", "Le numéro de téléphone doit contenir 8 chiffres", new Command("OK"));
//        } else if (!emailtf.getText().contains("@")) {
//            Dialog.show("Erreur", "Email non valide", new Command("OK"));
//                
//        }
                
            else{
                
                
                Local event = new Local();
                event.setDescript(descript);
                event.setLieu(lieu);
                event.setSurface(surface_loc);
                event.setDate(date.toString());
                event.setPrix(prix_loc);
                event.setEquipements(equipements);
               //  event.setDisponibilite(disponibilite);
                event.setImage(image);
                 String selectedType = (String) recType.getSelectedItem();
                        event.setDisponibilite(selectedType);
               // event.setNbper(99);
               



//               
              // LocalService.getInstance().addLocal(event);
//                
                 if( LocalService.getInstance().addLocal(event))
                        {
                          Dialog.show("Succès","Votre local a été ajoutée",new Command("OK"));
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
        add(descript_localField);
        add(lieu_localField);
        add(dateEventPicker);
       // add(nbpers_localField);
        add(surface_localField);
        add(prix_localField);
        add(imageField);
        add(equipement_evenementField);
        add(label);
         add(recType);
         
        
        

        add(addBtn);
        add(backBtn);
    
       
    
    }
    
  public void showAdd() {
     LocalAddForm form = new LocalAddForm();
    form.show();
}   
}
