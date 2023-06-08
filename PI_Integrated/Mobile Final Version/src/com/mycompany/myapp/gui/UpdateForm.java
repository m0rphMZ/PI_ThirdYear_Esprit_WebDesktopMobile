
package com.mycompany.myapp.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.User;
import com.mycompany.myapp.services.ServiceUser;

public class UpdateForm extends Form {

    private User user;
    private TextField nomTextField, prenomTextField, emailTextField, mdpTextField, telTextField;
    private ComboBox<String> roleComboBox;

    public UpdateForm(Form previous) {
        setTitle("Mise a jour");
        setLayout(BoxLayout.y());

       User user = ServiceUser.getInstance().getUserById(LoginForm.getUserConnected().getId());

        nomTextField = new TextField(user.getNom(), "Nom");
        add(nomTextField);

        prenomTextField = new TextField(user.getPrenom(), "Prenom");
        add(prenomTextField);

        emailTextField = new TextField(user.getEmail(), "Email");
        add(emailTextField);

        mdpTextField = new TextField(user.getMdp(), "Mot de passe");
        mdpTextField.setConstraint(TextField.PASSWORD);
        add(mdpTextField);

        telTextField = new TextField(String.valueOf(user.getTel()), "Téléphone");
        add(telTextField);

        roleComboBox = new ComboBox<>("Artiste", "simple utilisateur");
        roleComboBox.setSelectedItem(user.getRole());
        add(roleComboBox);
        System.out.println(user.getPrenom());

        Button updateBtn = new Button("Modifier");
        updateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
//                public User(int id, int tel, String nom, String prenom, String email, String mdp, String role, String image,String etat)
                User updatedUser = new User();
                updatedUser.setId(LoginForm.getUserConnected().getId());
                updatedUser.setEtat(LoginForm.getUserConnected().getEtat());
                updatedUser.setImage(LoginForm.getUserConnected().getImage());
                updatedUser.setPrenom(prenomTextField.getText());
                updatedUser.setNom(nomTextField.getText());
                updatedUser.setEmail(emailTextField.getText());
                updatedUser.setMdp(mdpTextField.getText());
                updatedUser.setTel(Integer.parseInt(telTextField.getText()));
                updatedUser.setRole(roleComboBox.getSelectedItem());
                ServiceUser.getInstance().updateUser(updatedUser);

               
                    new Profile(previous).show();
                
            }
        });
        add(updateBtn);

        Button cancelBtn = new Button("Annuler");
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new Profile(previous).show();
            }
        });
        add(cancelBtn);
    }
}