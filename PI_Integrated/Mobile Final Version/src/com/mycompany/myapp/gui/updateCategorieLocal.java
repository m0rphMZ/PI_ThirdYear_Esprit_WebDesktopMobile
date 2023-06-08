/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.CategorieLocal;
import com.mycompany.myapp.services.LocalService;
/**
 *
 * @author Administrateur
 */
public class updateCategorieLocal extends Form{
    
    
    
    
    
      public updateCategorieLocal(Form previous, int eventId) {
        CategorieLocal ev = LocalService.getInstance().getCatglocalById(eventId);
         System.out.println("juste");
        
        setTitle("Modifier la categorie");
        setLayout(BoxLayout.y());
        
        Toolbar toolbar = getToolbar();
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        
        TextField libelle = new TextField(ev.getLibelle(),"libelle");
       // ComboBox<String> dispo = new ComboBox<>("oui", "non");
       // dispo.setSelectedItem(ev.getDisponibilite());
            TextField color = new TextField(ev.getColor(),"color");
      
        Button updateBtn = new Button("Mettre à jour");
        
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
//               

 
                CategorieLocal event = new CategorieLocal();
                  event.setCode(eventId);
                event.setLibelle(libelle.getText());
                event.setColor(color.getText());
               
          
                    
                 if(  LocalService.getInstance().updateCategorieLocal(event))
                        {
                          Dialog.show("Succès","Votre categorie a été modifie",new Command("OK"));
//                           //new ListReclamationsUserForm(LoginForm.getUserConnected().getId()).show();
                       }else{
                           Dialog.show("ERROR", "Server error", new Command("OK"));
//                    /*} catch (NumberFormatException e) {
//                        Dialog.show("ERROR", "Status must be a number");
                    }
                    
            
                    
               
                         
                  CategorieList form = new CategorieList();
    form.show();
//             LocalAddForm form = new LocalAddForm();
//    form.show();
           // new EventShowForm(new EventsListForm(new HomeForm()), eventId).show();    
            }
        });
        
        Container UpdateFormContainer = new Container(BoxLayout.y());
        UpdateFormContainer.setUIID("UFCont");

             UpdateFormContainer.addAll(libelle, color, updateBtn);
        add(UpdateFormContainer);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
