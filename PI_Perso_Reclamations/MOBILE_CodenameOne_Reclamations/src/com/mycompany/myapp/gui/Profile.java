/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;

/**
 *
 * @author Mohamed
 */
public class Profile extends Form{
 
 public Profile(Form previous) {
    setTitle("Profile");
    setLayout(BoxLayout.y());

    Toolbar toolbar = getToolbar();
    toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
System.out.println(LoginForm.getUserConnected());
    User user = ServiceUser.getInstance().getUserById(LoginForm.getUserConnected().getId());
    if (user != null) {
        Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
Button btnmodif = new Button("modifier");
btnmodif.addActionListener(e-> new UpdateForm (this).show());

Label nameLabel = new Label("" + user.getNom()+" "+user.getPrenom());
nameLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_PERSON, nameLabel.getStyle())); // add a user icon to the label
nameLabel.getStyle().setFgColor(0x333333);
nameLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM)); // set a bold font
nameLabel.setAlignment(CENTER);








Label emailLabel = new Label("Email: " + user.getEmail());
emailLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_EMAIL, emailLabel.getStyle())); // add an email icon to the label
emailLabel.getStyle().setFgColor(0x666666);
emailLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL)); // set a small font
emailLabel.setAlignment(CENTER);

Label telLabel = new Label("Telephone: " + String.valueOf(user.getTel()));
telLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_PHONE, telLabel.getStyle())); // add a phone icon to the label
telLabel.getStyle().setFgColor(0x666666);
telLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL)); // set a small font
telLabel.setAlignment(CENTER);

Label roleLabel = new Label("Role: " + user.getRole());
roleLabel.setIcon(FontImage.createMaterial(FontImage.MATERIAL_INFO, roleLabel.getStyle())); // add an info icon to the label
roleLabel.getStyle().setFgColor(0x666666);
roleLabel.getStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL)); // set a small font
roleLabel.setAlignment(CENTER);
container.getStyle().setBorder(Border.createLineBorder(2, 0x999999));

container.addAll(nameLabel,emailLabel, telLabel, roleLabel,btnmodif);
add(container);
    } else {
        Label errorLabel = new Label("User not found");
        add(errorLabel);
    }
}
}
    
    

