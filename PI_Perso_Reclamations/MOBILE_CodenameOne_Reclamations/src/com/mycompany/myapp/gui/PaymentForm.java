/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

//import com.mycompany.myapp.gui.front.AccueilFront;
import com.mycompany.myapp.services.CartService;
import com.mycompany.myapp.services.CommandeService;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author ashre
 */


/**
 *
 * @author Houssem Charef
 */
public class PaymentForm extends Form {

    public static float prix = 600;
    Label numCardLabel, expMoisLabel, expanneeLabel, cvvLabel;
    TextField numCardField, expMoisField, expanneeField, cvvField;

    Button manageButton;

    Form previous;

    public PaymentForm(Form previous) {
        super("Payment");
        this.previous = previous;
        addGUIs();
        addActions();

        getToolbar().addMaterialCommandToLeftBar("  ", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private void addGUIs() {

        numCardLabel = new Label("numero Card : ");
        numCardLabel.setUIID("labelDefault");
        numCardField = new TextField();
        numCardField.setHint("1234 1334 1234 1234");

        expMoisLabel = new Label("Mois : ");
        expMoisLabel.setUIID("labelDefault");
        expMoisField = new TextField();
        expMoisField.setHint("MM");

        expanneeLabel = new Label("Annee : ");
        expanneeLabel.setUIID("labelDefault");
        expanneeField = new TextField();
        expanneeField.setHint("YY");

        cvvLabel = new Label("CVV : ");
        cvvLabel.setUIID("labelDefault");
        cvvField = new TextField();
        cvvField.setHint("CVC");

        manageButton = new Button("Payer");
        manageButton.setUIID("buttonMain");

        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        container.setUIID("containerRounded");

        container.addAll(
                numCardLabel, numCardField,
                expMoisLabel, expMoisField,
                expanneeLabel, expanneeField,
                cvvLabel, cvvField,
                manageButton
        );

        this.addAll(container);
    }

    private void addActions() {

        manageButton.addActionListener(action -> {
            Dialog ip = new InfiniteProgress().showInfiniteBlocking();
            ip.show();
            String numCard = numCardField.getText();
            String expMois = expMoisField.getText();
            String exAnnee = expanneeField.getText();
            String cvv = cvvField.getText();

            String message = CartService.getInstance().Payer(numCard, expMois, exAnnee, cvv, (int) prix);
            if (message.equals("true")) {
                Dialog.show("Success", "payment success", new Command("OK"));
                int responseCode = CommandeService.getInstance().order();
                if (responseCode == 200) {
                    Dialog.show("success", "chekout success", new Command("Ok"));
                   // showBackAndRefresh();
                    this.refreshTheme();
                } else {
                    Dialog.show("Erreur", "Erreur checkout. Code d'erreur : " + responseCode, new Command("Ok"));
                }

            } else {
                Dialog.show("ERROR", message, new Command("OK"));
                ip.dispose();

            }
        });
    }

   // private void showBackAndRefresh() {
    //    previous.showBack();
      //  new com.athlon.gui.front.cart.ShowAll(new AccueilFront()).show();
       // previous.refreshTheme();
 //   }
}