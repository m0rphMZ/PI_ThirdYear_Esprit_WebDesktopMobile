package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;
import com.mycompany.myapp.gui.UsersForm;

public class AddUser extends Form{
    
    public AddUser(Form previous) {
        User UserCn = LoginForm.getUserConnected();
        setTitle("Ajouter un utilisateur");
        setLayout(BoxLayout.y());
        TextField nomtf = new TextField("","Nom");
        TextField prenomtf = new TextField("","Prenom");
        TextField emailtf = new TextField("","email");
        TextField teltf = new TextField("","Tel");
        TextField passtf = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        ComboBox<String> Role = new ComboBox<>("Artiste", "simple utilisateur");
        Button btnValider = new Button("S'inscrire");
     
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            if (nomtf.getText().equals("") || prenomtf.getText().equals("") || emailtf.getText().equals("") || teltf.getText().equals("") || passtf.getText().equals("")) {
            Dialog.show("Erreur", "Tous les champs sont obligatoires", new Command("OK"));
        } else if (teltf.getText().length() != 8) {
            Dialog.show("Erreur", "Le numéro de téléphone doit contenir 8 chiffres", new Command("OK"));
        } else if (!emailtf.getText().contains("@")) {
            Dialog.show("Erreur", "Email non valide", new Command("OK"));
        } else {
            try {
                User u = new User();
                u.setNom(nomtf.getText());
                u.setPrenom(prenomtf.getText());
                u.setEmail(emailtf.getText());
                u.setTel(Integer.parseInt(teltf.getText()));
                u.setMdp(passtf.getText());
                String role = (String) Role.getSelectedItem();
                u.setRole(role);
                if (ServiceUser.getInstance().addUser(u)) {
                    Dialog.show("Succès", "Utilisateur ajouté", new Command("OK"));
                    
                    if(LoginForm.getUserConnected() == null) {
    new LoginForm().show();
} else {
    UsersForm usersForm = new UsersForm(AddUser.this);
    usersForm.show();
}
                    
                    
                    
                    }
                
                
                
                
             
                
                
                else {
                    Dialog.show("Erreur", "Erreur serveur", new Command("OK"));
                }
            } catch (Exception e) {
                Dialog.show("Erreur", e.getMessage(), new Command("OK"));
            }
        }
    }
});
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());         
        addAll(nomtf, prenomtf, emailtf, teltf, passtf, Role, btnValider);
    }
}