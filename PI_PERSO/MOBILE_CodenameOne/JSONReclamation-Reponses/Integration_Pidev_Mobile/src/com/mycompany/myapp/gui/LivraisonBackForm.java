/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.services.LivraisonService;
import java.util.ArrayList;

/**
 *
 * @author ashre
 */

    
public class LivraisonBackForm extends Form {

    public static Livraison currentLivraison = null;
    Label idLabel, dateLabel, confirmerLabel, emailLabel, addresselabel;
    Button itemsBtn, deleteBtn, confirmeBtn;
    Container btnsContainer;

    public LivraisonBackForm(Form previous) {
        super("livraison", new BoxLayout(BoxLayout.Y_AXIS));
        addGUIs();
        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Livraison> litLivraison = LivraisonService.getInstance().getAll();
        if (litLivraison.size() > 0) {
            for (Livraison livraison : litLivraison) {
                System.out.println(livraison);
                this.add(makeLivraisonModel(livraison));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeLivraisonModel(Livraison livraison) {
        Container livraisonModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        livraisonModel.setUIID("containerRounded");

        idLabel = new Label("id : " + livraison.getId());
        idLabel.setUIID("labelDefault");
        dateLabel = new Label("date : " + livraison.getDate());
        dateLabel.setUIID("labelDefault");
        confirmerLabel = new Label("confirmer : " + livraison.getConfirmer());
        confirmerLabel.setUIID("labelDefault");
        addresselabel = new Label("addresse : " + livraison.getAdresse());
        addresselabel.setUIID("labelDefault");

        emailLabel = new Label("email : " + livraison.getEmail());
        emailLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        confirmeBtn = new Button("confirmer");
        confirmeBtn.setUIID("buttonMain");
        confirmeBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous confirmer cette livraison ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = LivraisonService.getInstance().confirmer(livraison.getId());
                if (responseCode == 200) {
                    dlg.dispose();
                    refresh();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de confirmation du livraison. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });

        deleteBtn = new Button("Supprimer");
        deleteBtn.setUIID("buttonDanger");
        deleteBtn.addActionListener(action -> {
            InteractionDialog dlg = new InteractionDialog("Confirmer la suppression");
            dlg.setLayout(new BorderLayout());
            dlg.add(BorderLayout.CENTER, new Label("Voulez vous vraiment supprimer ce commande ?"));
            Button btnClose = new Button("Annuler");
            btnClose.addActionListener((ee) -> dlg.dispose());
            Button btnConfirm = new Button("Confirmer");
            btnConfirm.addActionListener(actionConf -> {
                int responseCode = LivraisonService.getInstance().delete(livraison.getId());
                if (responseCode == 200) {
                    currentLivraison = null;
                    dlg.dispose();
                    livraisonModel.remove();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur de suppression du commande. Code d'erreur : " + responseCode, new Command("Ok"));
                }
            });
            Container btnContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
            btnContainer.addAll(btnClose, btnConfirm);
            dlg.addComponent(BorderLayout.SOUTH, btnContainer);
            dlg.show(800, 800, 0, 0);
        });
        btnsContainer.add(BorderLayout.CENTER, confirmeBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        livraisonModel.addAll(
                idLabel,
                dateLabel,
                emailLabel,
                confirmerLabel,
                addresselabel,
                btnsContainer
        );

        return livraisonModel;
    }
}


