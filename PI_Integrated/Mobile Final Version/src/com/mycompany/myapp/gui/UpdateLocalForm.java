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
import com.mycompany.myapp.entities.Local;
import com.mycompany.myapp.services.LocalService;

/**
 *
 * @author Administrateur
 */
public class UpdateLocalForm extends Form {
    
   
     public UpdateLocalForm(Form previous, int eventId) {
        Local ev = LocalService.getInstance().getEventById(eventId);
        setTitle("Modifier l'événement");
        setLayout(BoxLayout.y());
        
        Toolbar toolbar = getToolbar();
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        
        TextField descript = new TextField(ev.getDescript(),"Titre");
        ComboBox<String> dispo = new ComboBox<>("oui", "non");
        dispo.setSelectedItem(ev.getDisponibilite());
            TextField equipements = new TextField(ev.getEquipements(),"Titre");
        TextField lieu = new TextField(ev.getLieu(),"Déscription");
        TextField nbper = new TextField(String.valueOf(ev.getNbper()),"Nombre de tickets");
        nbper.setConstraint(TextField.NUMERIC);
        TextField surface = new TextField(String.valueOf(ev.getSurface()),"Prix du ticket");
        surface.setConstraint(TextField.NUMERIC);
         TextField prix = new TextField(String.valueOf(ev.getPrix()),"Prix du ticket");
        surface.setConstraint(TextField.NUMERIC);
        Button updateBtn = new Button("Mettre à jour");
        
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
//                public User(int id, int tel, String nom, String prenom, String email, String mdp, String role, String image,String etat)
//                Event newEv = new Event();
//                newEv.setEvent_id(eventId);
//                newEv.setHost_id(ev.getHost_id());
//                
//                newEv.setTitle(title.getText());
//                newEv.setType(type.getSelectedItem());
//                newEv.setDescription(description.getText());
//                newEv.setTicketCount(Integer.parseInt(ticketCount.getText()));
//                newEv.setTicketPrice(Float.parseFloat(ticketPrice.getText()));
//                
//                newEv.setStartDate(ev.getStartDate());
//                newEv.setEndDate(ev.getEndDate());
//                newEv.setAffiche(ev.getAffiche());
//                newEv.setLocation_id(ev.getLocation_id());

 
                Local event = new Local();
                  event.setNum(eventId);
                event.setDescript(descript.getText());
                event.setLieu(lieu.getText());
                event.setSurface(Float.parseFloat(surface.getText()));
                 event.setNbper(Integer.parseInt(nbper.getText()));
                event.setDate(event.getDate());
                event.setPrix(Float.parseFloat(prix.getText()));
                event.setEquipements(equipements.getText());
            // event.setDisponibilite(disponibilite);
                event.setImage(event.getImage());
                String selectedType = (String) dispo.getSelectedItem();
                        event.setDisponibilite(selectedType);

                  event.setCodec(ev.getCodec());
                    event.setUserid(ev.getUserid());
                    
                    
                 if(  LocalService.getInstance().updateEvent(event))
                        {
                          Dialog.show("Succès","Votre local a été modifie",new Command("OK"));
//                           //new ListReclamationsUserForm(LoginForm.getUserConnected().getId()).show();
                       }else{
                           Dialog.show("ERROR", "Server error", new Command("OK"));
//                    /*} catch (NumberFormatException e) {
//                        Dialog.show("ERROR", "Status must be a number");
                    }
                    
            
                    
               
                         
                  LocalListForm form = new LocalListForm(previous);
    form.show();
//             LocalAddForm form = new LocalAddForm();
//    form.show();
           // new EventShowForm(new EventsListForm(new HomeForm()), eventId).show();    
            }
        });
        
        Container UpdateFormContainer = new Container(BoxLayout.y());
        UpdateFormContainer.setUIID("UFCont");

             UpdateFormContainer.addAll(descript, lieu, dispo, surface, prix,nbper,equipements, updateBtn);
        add(UpdateFormContainer);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
