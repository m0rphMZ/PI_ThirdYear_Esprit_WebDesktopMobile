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
import java.util.ArrayList;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Commande;
import com.mycompany.myapp.services.CommandeService;

import java.util.ArrayList;

/**
 *
 * @author ashre
 */

  

public class CommandeBackForm extends Form {

    public static Commande currentCommande = null;
    Label idLabel, UserLabel, dateLabel, statutLabel;
    Button itemsBtn, deleteBtn;
    Container btnsContainer;

    public CommandeBackForm(Form previous) {
        super("Commande", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        this.refreshTheme();
    }

    private void addGUIs() {

        ArrayList<Commande> listCommandes = CommandeService.getInstance().getAll();
        if (listCommandes.size() > 0) {
            for (Commande commande : listCommandes) {
                this.add(makeCommandeModel(commande));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeCommandeModel(Commande commande) {
        Container commandeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeModel.setUIID("containerRounded");

        idLabel = new Label("idC : " + commande.getId_c());
        idLabel.setUIID("labelDefault");
        dateLabel = new Label("date : " + commande.getDate());
        dateLabel.setUIID("labelDefault");
        UserLabel = new Label("user : " + commande.getUser());
        UserLabel.setUIID("labelDefault");
        statutLabel = new Label("statue : " + commande.getStatue());
        statutLabel.setUIID("labelDefault");

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        itemsBtn = new Button("Items");
        itemsBtn.setUIID("buttonMain");
        itemsBtn.addActionListener(action -> {
            currentCommande = commande;
            new com.mycompany.myapp.gui.CommandeItemBackForm(this).show();
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
                int responseCode = CommandeService.getInstance().delete(commande.getId_c());
                if (responseCode == 200) {
                    currentCommande = null;
                    dlg.dispose();
                    commandeModel.remove();
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

        btnsContainer.add(BorderLayout.CENTER, itemsBtn);
        btnsContainer.add(BorderLayout.EAST, deleteBtn);

        commandeModel.addAll(
                idLabel,
                dateLabel,
                UserLabel,
                statutLabel,
                btnsContainer
        );

        return commandeModel;
    }
}


