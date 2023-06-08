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
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ashre
 */
public class LivraisonService {

    public static LivraisonService instance = null;
    public int resultCode;
    private ConnectionRequest cr;
    private ArrayList<Livraison> listLivraison;

    private LivraisonService() {
        cr = new ConnectionRequest();
    }

    public static LivraisonService getInstance() {
        if (instance == null) {
            instance = new LivraisonService();
        }
        return instance;
    }

    public ArrayList<Livraison> getAll() {
        listLivraison = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/livraison");
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listLivraison = getList();
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

        return listLivraison;
    }

    private ArrayList<Livraison> getList() {
        try {
            Map<String, Object> parsedJson = new JSONParser().parseJSON(new CharArrayReader(
                    new String(cr.getResponseData()).toCharArray()
            ));
            List<Map<String, Object>> list = (List<Map<String, Object>>) parsedJson.get("root");

            for (Map<String, Object> obj : list) {
                Livraison livraison = new Livraison(
                        (int) Float.parseFloat(obj.get("id").toString()),
                        (String) obj.get("adresse"),
                        (String) obj.get("date"),
                        1,
                        (String) obj.get("confirmer"),
                        (String) obj.get("email")
                );

                listLivraison.add(livraison);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listLivraison;
    }

    public int delete(int livraisonId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/livraison/" + livraisonId);
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

    public int confirmer(int livraisonId) {
        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/livraison/" + livraisonId + "/editConfirmation");
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

    public int add(Livraison livraison) {
        return manage(livraison, false, 0);
    }

    public int edit(Livraison livraison, int idCommande) {
        return manage(livraison, true, idCommande);
    }

    public int manage(Livraison livraison, boolean isEdit, int idCommande) {

        cr = new ConnectionRequest();

        cr.setHttpMethod("POST");
        if (isEdit) {
            cr.setUrl(Statics.BASE_URL_Panier + "/livraison/" + idCommande + "/edit");
        } else {
            cr.setUrl(Statics.BASE_URL_Panier + "/livraison/new/" + com.mycompany.myapp.gui.mesCommandeForm.currentCommande.getId_c());
        }
        cr.addArgument("adresse", livraison.getAdresse());
        cr.addArgument("email", livraison.getEmail());

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultCode = cr.getResponseCode();
                cr.removeResponseListener(this);
            }
        });
        try {
            cr.setDisposeOnCompletion(new InfiniteProgress().showInfiniteBlocking());
            NetworkManager.getInstance().addToQueueAndWait(cr);
        } catch (Exception ignored) {

        }
        return resultCode;
    }

    public Livraison getbyIdCommande(int idCOmmande) {
        listLivraison = new ArrayList<>();

        cr = new ConnectionRequest();
        cr.setUrl(Statics.BASE_URL_Panier + "/livraison/" + idCOmmande);
        cr.setHttpMethod("GET");

        cr.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (cr.getResponseCode() == 200) {
                    listLivraison = getList();
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

        return listLivraison.get(0);
    }
}
