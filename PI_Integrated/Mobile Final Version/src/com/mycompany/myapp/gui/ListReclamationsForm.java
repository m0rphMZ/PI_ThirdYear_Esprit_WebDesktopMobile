/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.IconHolder;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ServiceReclamation;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author bhk
 */
public class ListReclamationsForm extends Form {
    
    public ListReclamationsForm(Form previous) {
    setTitle("Toutes les réclamations");
    setLayout(BoxLayout.y());

    ArrayList<Reclamation> reclamations = ServiceReclamation.getInstance().getAllRecsAdmin();
    if (reclamations.isEmpty()) {
        Label noReclamationsLabel = new Label("Aucune réclamation trouvée");
        add(noReclamationsLabel);
    } else {
        for (Reclamation r : reclamations) {
            addElement(r);
        }
    }

    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AdminForm().show());
}
    
    public ListReclamationsForm() {
    setTitle("Toutes les réclamations");
    setLayout(BoxLayout.y());

    ArrayList<Reclamation> reclamations = ServiceReclamation.getInstance().getAllRecsAdmin();
    if (reclamations.isEmpty()) {
        Label noReclamationsLabel = new Label("Aucune réclamation trouvée");
        add(noReclamationsLabel);
    } else {
        for (Reclamation r : reclamations) {
            addElement(r);
        }
    }

    getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> new AdminForm().show());
}


   public void addElement(Reclamation reclamation) {
    Container c = new Container(BoxLayout.y());
    c.setUIID("ReclamationContainer");

    Label lblRecId = new Label("# " + String.valueOf(reclamation.getRec_id()));
    Label lblTitre = new Label("Title:");
    Label lblTitreValue = new Label(reclamation.getTitre_rec());
    Label lblType = new Label("Type:");
    Label lblTypeValue = new Label(reclamation.getType());
    Label lblDescriptionValue = new Label(reclamation.getDescription());
    Label lblDate = new Label("Date:");
    Label lblDateValue = new Label(new SimpleDateFormat("dd/MM/yyyy").format(reclamation.getDate_creation()));
    Label lblEtat = new Label("Status:");
    Label lblEtatValue = new Label(reclamation.getStatus());

    Container idAndTitle = new Container(BoxLayout.x());
    idAndTitle.setUIID("ReclamationContent");
    idAndTitle.addAll(lblRecId, lblTitre, lblTitreValue);

    Button btnView = new Button("Ouvrez le Réclamation");
    btnView.setUIID("ViewButton");
    btnView.getAllStyles().setFgColor(0x0000ff);
    btnView.addActionListener(e -> {
        ListOneRecAdmin oneRecAdmin = new ListOneRecAdmin(this, reclamation.getRec_id());
        oneRecAdmin.show();
    });

    Container btnContainer = new Container(new FlowLayout(Component.CENTER));
    btnContainer.addComponent(btnView);

    Container content = new Container(BoxLayout.y());
    content.setUIID("ReclamationContent");
    content.addAll(idAndTitle, lblType, lblTypeValue, lblDescriptionValue, lblDate, lblDateValue, lblEtat, lblEtatValue);
    content.getAllStyles().setMarginTop(5);

    c.addAll(content, btnContainer);
    add(c);
}


}
