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
import com.mycompany.myapp.utils.Statics;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Monta
 */
public class ProduitService {
    
    ArrayList<Produit> products;
     public static ProduitService instance = null ;
    
      //initilisation connection request 
    private ConnectionRequest req;
       public static boolean resultOk = true;
    public static ProduitService getInstance() {
        if(instance == null )
            instance = new ProduitService();
        return instance ;
    }
    public ProduitService(){
    
     req = new ConnectionRequest();
    }
    
    //ajout 
   // public void ajoutReclamation(Produit rec) {
        
    //    String url =Statics.BASE_URL+"/produit/addProductJSON?Codeproduit="+rec.getCodeproduit()+"&description="+rec.getDes(); // aa sorry n3adi getId lyheya mech ta3 user ta3 reclamation
        
     //   req.setUrl(url);
     //   req.addResponseListener((e) -> {
            
     //       String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
       //     System.out.println("data == "+str);
    //    });
        
     //   NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
   // }
    
      //affichage
    
    public ArrayList<Produit>affichageReclamations() {
        ArrayList<Produit> result = new ArrayList<>();
        
        String url = Statics.BASE_URL_Panier+"/produit";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                try {
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        Produit re = new Produit();
                        
                        //dima id fi codename one float 5outhouha ff*
                       
                       float id = Float.parseFloat(obj.get("id").toString());
                        
                        String objet = obj.get("nom_produit").toString();
                        
                        float prix = Float.parseFloat(obj.get("prix_produit").toString());
                       // float id = Float.parseFloat(obj.get("id").toString());
                             
                  //     float etat = Float.parseFloat(obj.get("quantite").toString());
                        
                        re.setId((int)id);
                       re.setCodeproduit(objet);
                     re.setPrixUnitaire((int)prix);
                       //  re.setPrixunitaire((int) etat);
                        
                   //     re.setPrixunitaire((int)etat);
                        
                        //Date 
//                        String DateConverter =  obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10 , obj.get("date").toString().lastIndexOf("}"));
//                        
//                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
//                        
//                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//                        String dateString = formatter.format(currentTime);
//                        re.setDate(dateString);
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
    
    //Delete 
    public boolean deleteReclamation(int id ) {
        String url = Statics.BASE_URL_Panier +"/produit/deleteProductJSON/"+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        boolean resultOk = false;
        return  resultOk;
    }
    
    

    //Update 
    public boolean modifierReclamation(Produit rec) {
    String url = Statics.BASE_URL_Panier + "/produit/updateProductJSON/" + rec.getId() + "?Codeproduit=" + rec.getCodeproduit() + "&description=" + rec.getDes();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    
    
    
    
}
