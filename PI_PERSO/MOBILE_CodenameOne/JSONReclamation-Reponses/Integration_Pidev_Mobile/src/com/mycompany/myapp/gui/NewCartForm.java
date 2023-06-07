package com.mycompany.myapp.gui;

import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.CartService;
import com.mycompany.myapp.services.CommandeService;
import com.mycompany.myapp.utils.Statics;
import com.codename1.components.ImageViewer;
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
import java.util.ArrayList;

/**
 *
 * @author ashre
 */
public class NewCartForm extends Form {

    Label quantiteLabel, NomProduitLabel;
    Button removeBtn;
    Container btnsContainer;
    Button addBtn;
    Resources theme = UIManager.initFirstTheme("/theme");

    public NewCartForm(Form previous) {
        super("cart", new BoxLayout(BoxLayout.Y_AXIS));

        addGUIs();
        addActions();

        super.getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public void refresh() {
        this.removeAll();
        addGUIs();
        addActions();
        this.refreshTheme();
    }

    private void addGUIs() {

        addBtn = new Button("commander");
        addBtn.setUIID("buttonWhiteCenter");

        this.add(addBtn);
        ArrayList<Produit> listProduit = CartService.getInstance().getAll();
        if (listProduit.size() > 0) {
            for (Produit produit : listProduit) {
                this.add(makeCommandeModel(produit));
            }
        } else {
            this.add(new Label("Aucune donnée"));
        }
    }

    private void addActions() {
        addBtn.addActionListener(action -> {
            new PaymentForm(this).show();
            this.refresh();
        });
    }

    private Component makeCommandeModel(Produit produit) {
        ImageViewer imageIV;

        Container commandeModel = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        commandeModel.setUIID("containerRounded");

        NomProduitLabel = new Label("Nom produit : " + produit.getCodeproduit());
        NomProduitLabel.setUIID("labelDefault");
        quantiteLabel = new Label("quantité : " + produit.getQteStock());
        quantiteLabel.setUIID("labelDefault");
        if (false) {
            String url = Statics.PRODUIT_IMAGE_URL;
//                    + cour.getImage_cours();
            Image image = URLImage.createToStorage(
                    EncodedImage.createFromImage(theme.getImage("default.jpg").fill(1100, 500), false),
                    url,
                    url,
                    URLImage.RESIZE_SCALE
            );
            imageIV = new ImageViewer(image);
        } else {
            imageIV = new ImageViewer(theme.getImage("default.jpg").fill(1100, 500));
        }
        imageIV.setFocusable(false);

        btnsContainer = new Container(new BorderLayout());
        btnsContainer.setUIID("containerButtons");

        removeBtn = new Button("suprimer");
        removeBtn.setUIID("buttonMain");
        removeBtn.addActionListener(action -> {
            int responseCode = CartService.getInstance().remove(produit.getId());
            if (responseCode == 200) {
                this.refresh();
            }
        });

        btnsContainer.add(BorderLayout.CENTER, removeBtn);

        commandeModel.addAll(
                imageIV,
                NomProduitLabel,
                quantiteLabel,
                btnsContainer
        );

        return commandeModel;
    }
}