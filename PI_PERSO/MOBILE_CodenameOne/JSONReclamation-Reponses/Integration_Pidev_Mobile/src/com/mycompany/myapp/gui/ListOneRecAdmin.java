/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Form;
import com.codename1.components.ImageViewer;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.IconHolder;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.Reponses;
import com.mycompany.myapp.services.ServiceReclamation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Theto
 * 
 * 
 */


public class ListOneRecAdmin extends Form {
    

    
    public ListOneRecAdmin(Form previous, int recId) {
        setTitle("Reclamation " + recId);
        setLayout(BoxLayout.y());

        Reclamation r = ServiceReclamation.getInstance().getRecByRecId(recId);
        addElement(r);
        
         ArrayList<Reponses> reponses = ServiceReclamation.getInstance().getRepsByRecid(recId);
        for (Reponses rep : reponses) {
            addElement(rep);
        }

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
    
    private void addElement(Object element) {
    if (element instanceof Reclamation) {
        Reclamation r = (Reclamation) element;
        // create the UI elements to display the Reclamation object
        Container card = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        card.setUIID("Card");
        
        Label userLabel = new Label("UserId: " + r.getUser_id());
        card.add(userLabel);
    
        Label titleLabel = new Label("Titre: " + r.getTitre_rec());
        Font fnt = titleLabel.getUnselectedStyle().getFont();
        Font newFont = fnt.derive(Display.getInstance().convertToPixels(5), Font.STYLE_BOLD);
        titleLabel.getUnselectedStyle().setFont(newFont);
        card.add(titleLabel);
    
        Label typeLabel = new Label("Type: " + r.getType());
        card.add(typeLabel);
    
        Label statusLabel = new Label("Status: " + r.getStatus());
        card.add(statusLabel);
        
        if (r.getStatus().equals("Fermée")) {
        statusLabel.getAllStyles().setFgColor(0x00FF00);
        } else {statusLabel.getAllStyles().setFgColor(0xff0000);}
    
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Label dateCreationLabel = new Label("Créé le: " + sdf.format(r.getDate_creation()));
        card.add(dateCreationLabel);
    
        Container responseContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        responseContainer.setUIID("Card");
    
        Label responseLabel = new Label("Ajouter plus d'informations");
        responseContainer.add(responseLabel);
    
        TextArea responseArea = new TextArea(5, 20);
        responseArea.setHint("Tapez votre réponse ici");
        responseContainer.add(responseArea);
        
        Label closedLabel = new Label("Réclamation clôturée par vous.");
        closedLabel.getAllStyles().setFgColor(0xff0000); // set text color to red
        Label closedLabel2 = new Label("Vous ne pouvez plus répondre.");
        closedLabel2.getAllStyles().setFgColor(0xff0000); // set text color to red
    
        Button sendButton = new Button("Envoyer une réponse");
        Button closeButton = new Button("Fermeé la reclamation");
        
        if (r.getStatus().equals("Fermée")) {
            sendButton.setEnabled(false);
            closeButton.setEnabled(false);
            responseArea.setEnabled(false);
            responseContainer.add(closedLabel);
            responseContainer.add(closedLabel2);
        }
        
       
        
        closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (Dialog.show("Alerte", "Voulez-vous vraiment Fermez le Réclamation ?", "Fermer", "Annuler")) {
                            boolean deleted = ServiceReclamation.getInstance().closeRec(r.getRec_id());
                            if (deleted) {
                                Dialog.show("Succès", "Récupération définie a \"Fermé\"", "OK", null);
//                                ListOneRec.this.getParent().removeComponent(ListOneRec.this);
                                    new ListReclamationsForm().show();
                                

                                
                            } else {
                                Dialog.show("Erreur", "Erreur lors de la fermeture de la réclamation", "OK", null);
                            }
                        }
                    }
                });


        
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (responseArea.getText().length() < 5)
                         Dialog.show("Alerte", "Le reponse doit comporter au moins 5 caractères", new Command("OK"));
                 else
                    {
                        try {
                            Reponses rep = new Reponses();
                            rep.setRep_desc(responseArea.getText());
                            rep.setDate_rep(new Date());
                            rep.setIsAdminReponse(true);
                            rep.setRec_id(r.getRec_id());
                            rep.setUser_id(r.getUser_id());
                            if( ServiceReclamation.getInstance().addReponseAdmin(rep))
                            {
                                 Dialog.show("Succès","Votre reponse a été ajoutée, un email vous a été envoyé",new Command("OK"));
                                 new ListReclamationsForm().show();
                                 
                            }
                            else
                                Dialog.show("ERROR", "Server error", new Command("OK"));
                        }catch (NumberFormatException e) {
                            Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                        }
                    }
            }
            // implement the logic to send the response
        });
        responseContainer.add(sendButton);
        responseContainer.add(closeButton);
    
        card.add(responseContainer);
    
        this.add(card);
    } else if (element instanceof Reponses) {
        Reponses rep = (Reponses) element;
        // create the UI elements to display the response object
        Container responseCard = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        responseCard.setUIID("Card");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Label dateLabel = new Label("Envoyé le: " + sdf.format(rep.getDate_rep()));
        responseCard.add(dateLabel);
        
        if(rep.isIsAdminReponse()== true) {
        Label userLabel = new Label("Reponse admin #: " + rep.getRep_id());
        responseCard.add(userLabel);
        }
        else {
            Label userLabel = new Label("Reponse Utilisateur #: " + rep.getRep_id());
            responseCard.add(userLabel);
        }
        Label descLabel = new Label("Description: " + rep.getRep_desc());
        responseCard.add(descLabel);
    

    this.add(responseCard);
}}}



