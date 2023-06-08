/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;


import com.codename1.components.ImageViewer;
import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Produit;
import java.util.ArrayList;
import com.mycompany.myapp.services.ServiceProduit;
import com.mycompany.myapp.services.CartService;

/**
 *
 * @author ashre
 */
public class ListProduittForm extends Form {

    public static Produit currenrProduit = null;
    Label nomLabel, UserLabel, dateLabel, statutLabel,IdLabel,PrixLabel;
    Button addtoCartBtn;
    Container btnsContainer;
    Resources theme = UIManager.initFirstTheme("/theme");

    public ListProduittForm (Form previous) {
        super("Produit", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        ArrayList<Produit> listProduits = ServiceProduit.getInstance().getAll();
        if (listProduits.size() > 0) {
            for (Produit produit : listProduits) {
                this.add(makeProduitModel(produit));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private Component makeProduitModel(Produit produit) {
        

        Container produitModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        produitModel.setUIID("containerRounded");

        IdLabel = new Label("Id_produit : " + produit.getId());
        IdLabel.setUIID("labelDefault");
        nomLabel = new Label("nom_produit : " + produit.getCodeproduit());
        nomLabel.setUIID("labelDefault");
        PrixLabel = new Label("prix_produit : " + produit.getPrixUnitaire());
        PrixLabel.setUIID("labelDefault");
        

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        addtoCartBtn = new Button("Ajouter au panier");
        addtoCartBtn.setUIID("buttonDanger");
        addtoCartBtn.addActionListener(action -> {
            NewCartForm.getMaListeProduits().add(produit);
            System.out.println(NewCartForm.getMaListeProduits().size());
            
            
        });

        btnsContainer.add(BorderLayout.CENTER, addtoCartBtn);

        produitModel.addAll(
                nomLabel,
                IdLabel,
                PrixLabel,
                btnsContainer
        );

        return produitModel;
    }
}
