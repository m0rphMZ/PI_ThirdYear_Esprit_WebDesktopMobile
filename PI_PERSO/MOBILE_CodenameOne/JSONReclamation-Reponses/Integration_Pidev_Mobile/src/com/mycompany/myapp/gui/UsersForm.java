/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;
import java.util.ArrayList;

/**
 *
 * @author Mohamed
 */
public class UsersForm extends Form {
    public UsersForm(Form previous) {
        
     
        setTitle("Liste des utilisateurs");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0xf2f2f2); 
        Button ajout = new Button("ajouter un utilisateur");
        ajout.setAlignment(LEFT);
         
        ajout.addActionListener(e-> new AddUser(this).show());
         
        add(ajout);
         
         
         
        ArrayList<User> users = ServiceUser.getInstance().getAllUsers();
        for (User user : users) {
            add(createUserContainer(user));
        }

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
      
       
    }

    private Container createUserContainer(User user) {
    Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    container.getStyle().setMarginTop(10); 
    Button supp = new Button("Supprimer");
    

    Label nameLabel = new Label(user.getNom() + " : " + user.getRole());
    nameLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_PERSON, nameLabel.getStyle())); // add a user icon to the label
    nameLabel.getStyle().setFgColor(0x333333);
    nameLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM)); // set a bold font
    nameLabel.setAlignment(CENTER);

    Label emailLabel = new Label(user.getEmail());
    emailLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_EMAIL, emailLabel.getStyle())); // add an email icon to the label
    emailLabel.getStyle().setFgColor(0x666666); 
    emailLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL)); // set a small font
    emailLabel.setAlignment(CENTER);

    Label telLabel = new Label(String.valueOf(user.getTel()));
    telLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_PHONE, telLabel.getStyle())); // add a phone icon to the label
    telLabel.getStyle().setFgColor(0x666666); 
    telLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL)); // set a small font
    telLabel.setAlignment(CENTER);

   
  supp.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent evt) {
        boolean success = ServiceUser.getInstance().deleteUser(user.getId()); 
        if (success) {
              Dialog.show("Succés", "Utilisateur supprimé", new Command("OK"));
            Container parent = container.getParent();
            parent.removeComponent(container);
            parent.revalidate();
            parent.repaint();
        }
    }
});

    container.addAll(nameLabel, emailLabel, telLabel, supp);
    container.getStyle().setBorder(Border.createLineBorder(2, 0x999999));
    return container;
}
}