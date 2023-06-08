/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;
import java.util.ArrayList;

/**
 *
 * @author Mohamed
 */
public class LoginForm extends Form{
    private static User User_connected;

    public static User getUserConnected() {
        return User_connected;
    }

    public static void setUserConnected(User user) {
        User_connected = user;
    }

    public LoginForm() {
        setTitle("Login");
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        getStyle().setBgColor(0xf2f2f2); 
        TextField emailtf = new TextField("","email");
        TextField passtf = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        Button btncn = new Button("Se connecter");
        Button btnins = new Button("S'inscrire");
        addAll(emailtf,passtf,btncn,btnins);

        btnins.addActionListener(e-> new AddUser (this).show());
        btncn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ArrayList<User> users = ServiceUser.getInstance().getAllUsers();
                String email = emailtf.getText();
                String mdp = passtf.getText();
                boolean found = false;

                if(email.equals("")||mdp.equals("")) {
                    Dialog.show("ERROR", "champs vides", new Command("OK"));
                }
                else if(!(email.contains("@"))) {
                    Dialog.show("ERROR", "email non valide", new Command("OK"));
                }
                else {
                    for (User user : users) {
                        if ((user.getEmail().equals(email)) && (user.getMdp().equals(mdp))){
                            User_connected=user;
                            found = true;
                            Dialog.show("Succès", "Bienvenue "+User_connected.getNom(), new Command("OK"));
                            if (User_connected.getRole().equals("Admin")){
                                new AdminForm().show();
                            }
                            else {
                                new HomeForm().show();
                                break;
                            }
                        }
                    }
                    if (!found) {
                        Dialog.show("Error", "email ou mdp incorrectes", new Command("OK"));
                    }
                }
            }
        });
    }
}