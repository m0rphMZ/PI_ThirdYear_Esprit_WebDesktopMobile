/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ashre
 */
public class ServiceProduit {
      public ArrayList<Produit> produits;

    public static ServiceProduit instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    
     public ArrayList<Produit> parseProduit(String jsonText) {
        try {
            produits = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> produitListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) produitListJson.get("root");
            for (Map<String, Object> obj : list) {
                Produit t = new Produit();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int) id);
                t.setPrixUnitaire(((int) Float.parseFloat(obj.get("status").toString())));
                if (obj.get("name") == null) {
                    t.setCodeproduit("null");
                } else {
                    t.setCodeproduit(obj.get("name").toString());
                }
                produits.add(t);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return produits;
    }
     
     public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }
    
    
    public ArrayList<Produit> getAllProduit() {
        String url = Statics.BASE_URL_Panier + "get/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                produits = parseProduit(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return produits;
    }
}
