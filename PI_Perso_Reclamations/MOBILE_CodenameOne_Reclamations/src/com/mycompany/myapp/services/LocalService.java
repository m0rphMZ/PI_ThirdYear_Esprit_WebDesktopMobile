/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.services;

/**
 *
 * @author Administrateur
 */
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.mycompany.myapp.entities.Local;
import com.mycompany.myapp.utils.Statics;
public class LocalService {
     
    private ConnectionRequest req;
    private boolean resultOK;
    private static LocalService instance = null;
    private Resources res;
    private ArrayList<Local> locaux;
    
    private LocalService() {
        req = new ConnectionRequest();
    }
    
    public static LocalService getInstance() {
        if (instance == null) {
            instance = new LocalService();
        }
        return instance;
    }
    
    public ArrayList<Local> parseEvenement(String jsonText) {
        try {
            locaux = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> userListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) userListJson.get("root");
            for (Map<String, Object> obj : list) {
                //Création des Users et récupération de leurs données
                Local u = new Local();
                
                
                
             float id =  Float.parseFloat(obj.get("num_loc").toString());
            int idAsInt = (int) id;
            u.setNum((int) idAsInt);
                
                String descript_local = obj.get("descipt_loc").toString();
                u.setDescript((descript_local));
                
                String lieu_local = obj.get("lieu_loc").toString();
                u.setLieu(lieu_local);
                
          String date_loc = obj.get("date_Aloc").toString();
                u.setDate(date_loc);
                
               /* String lieux_evenement = obj.get("lieux_evenement").toString();
                u.setLieux_evenement(lieux_evenement);*/
                
                float surface_local = Float.parseFloat(obj.get("surface_loc").toString());
                u.setSurface((Float) surface_local);
                
                
                 float id1 =  Float.parseFloat(obj.get("nb_pers_loc").toString());
            int nbpers = (int) id1;
            u.setNbper((int) nbpers);
                
                        
                
               /* String image = obj.get("image").toString();
                u.setImage(image);*/
                
                  float categorie_id =  Float.parseFloat(obj.get("code_catg").toString());
            int categorir_idAsInt = (int) categorie_id;
            u.setCodec((int) categorir_idAsInt);
                
                
                
//            'prix_loc' => $location->getPrixLoc(),
//           'equipements' => $location->getEquipements(),
//            'disponibilite' => $location->getDisponibilite(),    
//                
            
            float prix_local = Float.parseFloat(obj.get("prix_loc").toString());
                u.setPrix((Float) prix_local);
//            
            String equipement = obj.get("equipements").toString();
              u.setEquipements((equipement));
                
                
                 String disponibilite = obj.get("disponibilite").toString();
                u.setDisponibilite((disponibilite));
                
//                v.setCouleur(obj.get("couleur").toString());
//                v.setEtat(obj.get("etat").toString());

                //Ajouter le vélo extrait de la réponse Json à la liste
                locaux.add(u);
            }

        } catch (IOException ex) {

        }
        
        return locaux;
    }

    public ArrayList<Local> getAllLocaux() {
        ArrayList<Local> listEvenement= new ArrayList<>();
        int id = 2;
        String url = Statics.BASE_URL + "/afficherJsonLocal";
//        req.setUrl(url);
//        req.setPost(false);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                locaux = parseEvenement(new String(req.getResponseData()));
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return locaux;
        
        
        
        
        
        
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                locaux = parseEvenement(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        
        return locaux;
        
    }
    
    
    public boolean addLocal(Local e) {
       // String date_evenement = e.getDate_evenement().toString().substring(0, 10);
       /*$local->setDesciptLoc($request->get('descipt_loc'));
        $local->setLieuLoc($request->get('lieu_loc'));
        $local->setDateAloc(new \DateTime($request->get('date_Aloc')));
        $local->setSurfaceLoc($request->get('surface_loc'));
        $local->setPrixLoc($request->get('prix_loc'));
        $local->setEquipements($request->get('equipements'));
        $local->setDisponibilite($request->get('disponibilite'));
        $local->setNbPersLoc($request->get('nb_pers_loc'));
        $local->setImage($request->get('image'));*/
       ///api/ajoutLocal?descipt_loc=888&lieu_loc=ggg&surface_loc=7.5&prix_loc=8&equipements=gggg&disponibilite=tt&nb_pers_loc=77&image=77&code_catg=0
                String url = Statics.BASE_URL + "/api/ajoutLocal?descipt_loc=" + e.getDescript()+ "&lieu_loc=" + e.getLieu()+ "&surface_loc=" + e.getSurface()+  "&prix_loc="+e.getPrix()+  "&equipements="+e.getPrix()+"&disponibilite="+e.getDisponibilite()+"&nb_pers_loc=" +8 +"&image=" + e.getImage()+ "&code_catg=" + 777 ; //création de l'URL
 //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        System.out.println(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;
//        
//        
//        
//          req.setUrl(url);
        req.setPost(true);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
        
        
        
        
        
        
    }
    
    
   public void deleteLocal(int id) {

        Dialog d = new Dialog();
        if (d.show("Delete Local"
                + "..", "Do you really want to remove this Local", "Yes", "No")) {

            req.setUrl(Statics.BASE_URL + "/api/delete/local/" + id);
       
            NetworkManager.getInstance().addToQueueAndWait(req);
        d.dispose();
    }
        }
   
   
   
   /*****update Local*********/
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
      public Local parseSingleEvent(String jsonText) {
          Local u = new Local();
            try {
            locaux = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> userListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

           //  Map<String, Object> eventJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
          //  List<Map<String, Object>> list = (List<Map<String, Object>>) userListJson.get("root");

                //Création des Users et récupération de leurs données
                  
                
                
             float id =  Float.parseFloat(userListJson.get("numLoc").toString());
            int idAsInt = (int) id;
            u.setNum((int) idAsInt);
                
                String descript_local = userListJson.get("desciptLoc").toString();
               
u.setDescript(descript_local);

                u.setDescript((descript_local));
                
                String lieu_local = userListJson.get("lieuLoc").toString();
                u.setLieu(lieu_local);
                
          String date_loc = userListJson.get("dateAloc").toString();
                u.setDate(date_loc);
                
               /* String lieux_evenement = obj.get("lieux_evenement").toString();
                u.setLieux_evenement(lieux_evenement);*/
                
                float surface_local = Float.parseFloat(userListJson.get("surfaceLoc").toString());
                u.setSurface((Float) surface_local);
                
                
                 float id1 =  Float.parseFloat(userListJson.get("nbPersLoc").toString());
            int nbpers = (int) id1;
            u.setNbper((int) nbpers);
                
                        
                
               /* String image = obj.get("image").toString();
                u.setImage(image);*/
//                  Object numLocObj = location.get("numLoc");
//                  float categorie_id =  Float.parseFloat(userListJson.get("codeCatg").toString());
//            int categorir_idAsInt = (int) categorie_id;
//            u.setCodec((int) categorir_idAsInt);
                  Map<String, Object> host = (Map<String, Object>) userListJson.get("codeCatg");
          Object userIdObj = host.get("codecLoc");
                 float categorie_id =  Float.parseFloat(userIdObj.toString());
                 int categorir_idAsInt = (int) categorie_id;
        u.setCodec((int) categorir_idAsInt);
//            'prix_loc' => $location->getPrixLoc(),
//           'equipements' => $location->getEquipements(),
//            'disponibilite' => $location->getDisponibilite(),    
//                
            
            float prix_local = Float.parseFloat(userListJson.get("prixLoc").toString());
                u.setPrix((Float) prix_local);
            
              String equipements = userListJson.get("equipements").toString();
                u.setEquipements((equipements));
                 String disponibilite = userListJson.get("disponibilite").toString();
                u.setDisponibilite((disponibilite));
                 
//                v.setCouleur(obj.get("couleur").toString());
//                v.setEtat(obj.get("etat").toString());
}
               
            
       catch (IOException ex) {

        }
         return u;
      }
    

            
        
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
//          
////          
////          
////    Local e = new Local();
////    try {
////        JSONParser j = new JSONParser();
////        Map<String, Object> eventJSON = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
////        
////        if (eventJSON.get("numLoc") == null) {
////            e.setNum(0);
////        } else {
////            float event_id = Float.parseFloat(eventJSON.get("numLoc").toString());
////            e.setNum((int) event_id);
////        }
////        if (eventJSON.get("desciptLoc") == null) {
////            e.setDescript("error");
////        } else {
////            e.setDescript(eventJSON.get("desciptLoc").toString());
////        }
////        if (eventJSON.get("lieuLoc") == null) {
////            e.setLieu("error");
////        } else {
////            e.setLieu(eventJSON.get("lieuLoc").toString());
////        }
////        if (eventJSON.get("equipements") == null) {
////            e.setEquipements("error");
////        } else {
////            e.setEquipements(eventJSON.get("equipements").toString());
////        }
////        
////         
////        
//////        if (eventJSON.get("startDate") == null) {
//////            e.setStartDate(new Date());
//////        } else {
//////            String dateString = eventJSON.get("startdate").toString();
//////            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//////            try {
//////                Date date = dateFormat.parse(dateString);
//////                e.setStartDate(date);
//////            } catch (ParseException ex) {
//////                System.err.println("Error parsing date: " + dateString);
//////                e.setStartDate(new Date());
//////            }
//////        }
//////        if (eventJSON.get("enddate") == null) {
//////            e.setEndDate(new Date());
//////        } else {
//////            String dateString = eventJSON.get("enddate").toString();
//////            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//////            try {
//////                Date date = dateFormat.parse(dateString);
//////                e.setEndDate(date);
//////            } catch (ParseException ex) {
//////                System.err.println("Error parsing date: " + dateString);
//////                e.setEndDate(new Date());
//////            }
//////        }
////        if (eventJSON.get("prixLoc") == null) {
////            e.setPrix(0);
////        } else {
////            float ticketCount = Float.parseFloat(eventJSON.get("prixLoc").toString());
////            e.setPrix((int) ticketCount);
////        }
////        if (eventJSON.get("image") == null) {
////            e.setImage("error");
////        } else {
////            e.setImage(eventJSON.get("image").toString());
////        }
////        if (eventJSON.get("surfaceLoc") == null) {
////            e.setSurface(0);
////        } else {
////            float ticketprice = Float.parseFloat(eventJSON.get("surfaceLoc").toString());
////            e.setSurface(ticketprice);
////        }
//////
////
////
////
////
////  String date_loc = eventJSON.get("dateAloc").toString();
////                e.setDate(date_loc);
////                  float categorie_id =  Float.parseFloat(eventJSON.get("codeCatg").toString());
////            int categorir_idAsInt = (int) categorie_id;
////            e.setCodec((int) categorir_idAsInt);
//////             float user_id =  Float.parseFloat(eventJSON.get("user").toString());
//////            int user_idAsInt = (int) user_id;
//////            e.setUserid((int) user_idAsInt);
////            
////            
//////             Object numLocObj = location.get("numLoc");
//////            float locId = Float.parseFloat(numLocObj.toString());
//////                e.setLocation_id((int) locId);
//////                e.setLocationName(lieuLocObj.toString());
////            
////            
////            
//////        if (eventJSON.get("host") instanceof Map) {
//////          Map<String, Object> host = (Map<String, Object>) eventJSON.get("host");
//////            Object userIdObj = host.get("idUser");
//////            Object userNomObj = host.get("nom");
//////            Object userPrenomObj = host.get("prenom");
//////            if (userIdObj == null) {
//////                e.setHost_id(0);
//////                e.setHostNom("error");
//////                e.setHostPrenom("error");
//////            } else {
//////                float userId = Float.parseFloat(userIdObj.toString());
//////                e.setHost_id((int) userId);
//////                e.setHostNom(userNomObj.toString());
//////                e.setHostPrenom(userPrenomObj.toString());
//////            }
//////        } else {
//////            e.setHost_id(0);
//////        }
////
//////        if (eventJSON.get("location") instanceof Map) {
//////            Map<String, Object> location = (Map<String, Object>) eventJSON.get("location");
//////            Object numLocObj = location.get("numLoc");
//////            Object lieuLocObj = location.get("lieuLoc");
//////            if (numLocObj == null) {
//////                e.setLocation_id(0);
//////                e.setLocationName("error");
//////            } else {
//////                float locId = Float.parseFloat(numLocObj.toString());
//////                e.setLocation_id((int) locId);
//////                e.setLocationName(lieuLocObj.toString());
//////            }
//////        } else {
//////            e.setLocation_id(0);
//////        }
////    } catch (IOException ex) {
////        System.out.println(ex.getMessage());
////    }
////    return e;}
//
//   
   
      
   
   
   
   
   
    public Local getEventById(int id) {
            String url ="http://127.0.0.1:8000/LocalShow/"+id;
            
        //String url = statics.SHOW_EVENT_BASE_URL + id;
        req.setUrl(url);
        req.setPost(false);
        final Local[] local = {null}; 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                local[0] = parseSingleEvent(new String(req.getResponseData())); 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return local[0]; 
    }
    
    public boolean updateEvent(Local e) {
        int id = e.getNum();
        String desc = e.getDescript();
        String dispo = e.getDisponibilite();
        String lieu = e.getLieu();
        float surface = e.getSurface();
         float prix = e.getPrix();
        String eq = e.getEquipements();
         int nbper = e.getNbper();

        String url ="http://127.0.0.1:8000/UpdateLocalJSON/"+ id      
                + "?descipt_loc=" + desc 
                    + "&lieu_loc=" + lieu
                    + "&surface_loc=" + surface
                    + "&nb_pers_loc=" + nbper
                    + "&prix_loc=" + prix
                     + "&ee=" + eq
                + "&disponibilite=" + dispo;

        req.setUrl(url);
        req.setPost(true);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
   
   
  
   
   
   
   
   
   
}
