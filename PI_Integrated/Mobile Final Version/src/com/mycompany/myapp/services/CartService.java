/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.utils.Statics;
import com.stripe.exception.StripeException;
//import com.stripe.exception.StripeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ashre
 */
public class CartService {

    public static CartService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Produit> listProduit;

    private CartService() {
        cr = new ConnectionRequest();
    }

    public static CartService getInstance() {
        if (instance == null) {
            instance = new CartService();
        }
        return instance;
    }

    public ArrayList<Produit> getAll() {
        listProduit = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/cart");
        cr.setHttpMethod("GET");
        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listProduit = getList();
                }
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listProduit;
    }

    private ArrayList<Produit> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Produit produit = new Produit();

                produit.setQteStock((int) Float.parseFloat(obj.get("quantity").toString()));
                Map<String, Object> map = (Map<String, Object>) obj.get("product");
                //double d = (Double.parseDouble(map.get("id") + ""));
               // int x = (int) d;
               // produit.setId(x);
               
                produit.setCodeproduit((String) map.get("Nom produit"));
                listProduit.add(produit);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listProduit;
    }

    public int add(int idproduit) {

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/cart/add/" + idproduit);
        cr.setHttpMethod("POST");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.getMessage();
        }
        return cr.getResponseCode();
    }

    public int remove(int idproduit) {

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/cart/remove/" + idproduit);
        cr.setHttpMethod("POST");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                cr.removeResponseListener(this);
            }
        });

        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cr.getResponseCode();
    }

    public String Payer(String numCard, String expMois, String exAnnee, String cvv, int prix) {
        String nom = "sddsdsdsds";
        String email = "seif@gmail.com";
        String payed;
        PaymentService servicePayment = new PaymentService(email, nom, prix * 100, numCard, expMois, exAnnee, cvv);
        try {
            servicePayment.payer();
            payed = "true";
            try {
                cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
                NetworkManager.getInstance().addToQueueAndWait(cr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (StripeException ex) {
            ex.printStackTrace();
            payed = "error payment";
        }
        return payed;
    }
}
