/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Produit;

//import static com.mycompany.myapp.services.ServiceTask.instance;
import com.mycompany.myapp.utils.Statics;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Token;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ashre
 */
public class PaymentService {

    private String email;
    private String name;
    private int ammount;
    private String card;
    private String exp_month;
    private String exp_year;
    private String cvc;

    public PaymentService() {
    }

    public PaymentService(String email, String name, int ammount, String card, String exp_month, String exp_year, String cvc) {
        this.email = email;
        this.name = name;
        this.ammount = ammount;
        this.card = card;
        this.exp_month = exp_month;
        this.exp_year = exp_year;
        this.cvc = cvc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public boolean payer() throws StripeException {
        Stripe.apiKey = "sk_test_51Mzg2dKp9P2IwbgSp2yTRhqOPFbolK5qk54hhP5A4kEtaurZqvCKAf4ppCxjEa3OT1hLZcP6hhnqY8hK30QkMoNU00p7njd5Jh";

        Map<String, Object> EmailOptions = new HashMap<>();
        EmailOptions.put("email", email);
        List<Customer> customersEmailEx = Customer.list(EmailOptions).getData();

        Map<String, Object> params = new HashMap<>();
        params.put("email", email);
        params.put("name", name);

        Customer customer = Customer.create(params);

        Map<String, Object> EmailOptions2 = new HashMap<>();
        EmailOptions2.put("email", email);
        List<Customer> customersEmailNew = Customer.list(EmailOptions).getData();
        Map<String, Object> retrieveParams
                = new HashMap<>();
        List<String> expandList = new ArrayList<>();
        expandList.add("sources");
        retrieveParams.put("expand", expandList);
        Customer customerNew
                = Customer.retrieve(
                        customersEmailNew.get(0).getId(),
                        retrieveParams,
                        null
                );
        Map<String, Object> cardParam = new HashMap<String, Object>();
        cardParam.put("number", card);//4111111111111111
        cardParam.put("exp_month", this.exp_month);
        cardParam.put("exp_year", this.exp_year);
        cardParam.put("cvc", this.cvc);

        Map<String, Object> tokenParam = new HashMap<String, Object>();
        tokenParam.put("card", cardParam);

        Token token = Token.create(tokenParam);

        Map<String, Object> source = new HashMap<String, Object>();
        source.put("source", token.getId());
        Card card = (Card) customerNew.getSources().create(source);

        Map<String, Object> chargePram = new HashMap<>();
        chargePram.put("amount", ammount);
        chargePram.put("currency", "usd");
        chargePram.put("customer", customerNew.getId());
        Charge charge = Charge.create(chargePram);
        return charge.getPaid();

    }

}
