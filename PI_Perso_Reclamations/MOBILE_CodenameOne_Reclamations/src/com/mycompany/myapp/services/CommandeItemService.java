/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.CommandeItem;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ashre
 */
public class CommandeItemService {


    public static CommandeItemService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<CommandeItem> listCommande;

    private CommandeItemService() {
        cr = new ConnectionRequest();
    }

    public static CommandeItemService getInstance() {
        if (instance == null) {
            instance = new CommandeItemService();
        }
        return instance;
    }

    public ArrayList<CommandeItem> getAllByID(int idCommande) {
        listCommande = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/commandeItem/showByCommande/" + idCommande);
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listCommande = getList();
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

        return listCommande;
    }

    private ArrayList<CommandeItem> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                CommandeItem commandeItem = new CommandeItem(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        "test",
                        (int) Float.parseFloat(obj.get("quantity").toString())
                );

                listCommande.add(commandeItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listCommande;
    }

    public int delete(int commandeItemId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/commandeItem/" + commandeItemId);
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

    public ArrayList<CommandeItem> getAll(int idC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

