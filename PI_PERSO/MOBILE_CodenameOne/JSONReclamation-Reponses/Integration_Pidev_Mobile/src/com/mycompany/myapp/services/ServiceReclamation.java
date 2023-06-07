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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.entities.Reponses;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 *
 * @author bhk
 */
public class ServiceReclamation {

    public ArrayList<Reclamation> reclamations;
    
    public ArrayList<Reponses> reponses;
    
    public Reclamation reclamation;


    public static ServiceReclamation instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceReclamation() {
        req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }

    public boolean addReclamation(Reclamation r, int userId) {

        String name = r.getTitre_rec();
        String type = r.getType();
        String desc = r.getDescription();
        
        String url = Statics.ADDREC_URL + "/new?titreRec=" + name + "&typeRec=" + type + "&descRec=" + desc + "&userId=" + userId;
//        String url = Statics.BASE_URL + "/new";
//        String url = Statics.BASE_URL + "create/" + status + "/" + name;

        req.setUrl(url);
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
    
    public boolean removeRec(int recId) {

        String url = Statics.REMOVEREC_URL + "/" + recId;
        req.setUrl(url);
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
    
    public boolean closeRec(int recId) {

        String url = Statics.CLOSEREC_URL + "/" + recId;
        req.setUrl(url);
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
    
    
       

    public ArrayList<Reclamation> parseReclamations(String jsonText) {
    try {
        reclamations = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String, Object> recsListJson
                = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) recsListJson.get("root");
        for (Map<String, Object> obj : list) {
            Reclamation r = new Reclamation();
            Object idObj = obj.get("recId");
            if (idObj == null) {
                r.setRec_id(0);
            } else {
                float rec_id = Float.parseFloat(idObj.toString());
                r.setRec_id((int) rec_id);
            }
            if (obj.get("titreRec") == null) {
                r.setTitre_rec("error");
            } else {
                r.setTitre_rec(obj.get("titreRec").toString());
            }
            if (obj.get("type") == null) {
                r.setType("error");
            } else {
                r.setType(obj.get("type").toString());
            }
            if (obj.get("status") == null) {
                r.setStatus("error");
            } else {
                r.setStatus(obj.get("status").toString());
            }
            if (obj.get("dateCreation") == null) {
                r.setDate_creation(new Date());
            } else {
                String dateString = obj.get("dateCreation").toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(dateString);
                    r.setDate_creation(date);
                } catch (ParseException e) {
                    System.err.println("Error parsing date: " + dateString);
                    r.setDate_creation(new Date());
                }
            }
            reclamations.add(r);
        }

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return reclamations;
}


    public ArrayList<Reclamation> getAllRecs(int userId) {
        String url = Statics.RECBYUSERID_URL + "/?userId=" + userId;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }
    
    
    
    
//    ________________________ONE RECLAMATION_________________________________________________
    
//    __________________________________________________________________________________________
    
    
    
    public Reclamation parseReclamation(String jsonText) {
    try {
        JSONParser j = new JSONParser();
        Map<String, Object> recJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        Reclamation r = new Reclamation();
        
        // Parse reclamation properties
        Object idObj = recJson.get("recId");
        if (idObj == null) {
            r.setRec_id(0);
        } else {
            float rec_id = Float.parseFloat(idObj.toString());
            r.setRec_id((int) rec_id);
        }
        if (recJson.get("titreRec") == null) {
            r.setTitre_rec("error");
        } else {
            r.setTitre_rec(recJson.get("titreRec").toString());
        }
        if (recJson.get("type") == null) {
            r.setType("error");
        } else {
            r.setType(recJson.get("type").toString());
        }
        if (recJson.get("status") == null) {
            r.setStatus("error");
        } else {
            r.setStatus(recJson.get("status").toString());
        }
        if (recJson.get("dateCreation") == null) {
            r.setDate_creation(new Date());
        } else {
            String dateString = recJson.get("dateCreation").toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateString);
                r.setDate_creation(date);
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + dateString);
                r.setDate_creation(new Date());
            }
        }
        
        // Parse user object and extract idUser field
        Map<String, Object> userJson = (Map<String, Object>) recJson.get("user");
        Object userIdObj = userJson.get("idUser");
        if (userIdObj == null) {
            r.setUser_id(0);
        } else {
            int userId = ((Double) userIdObj).intValue(); // convert to int
            r.setUser_id(userId);
        }

        return r;
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}



    public Reclamation getRecByRecId(int recId) {
    String url = Statics.RECPLUSREPBYRECID_URL + "/" + recId;
    req.setUrl(url);
    req.setPost(false);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            String jsonText = new String(req.getResponseData());
            reclamation = parseReclamation(jsonText);
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);

    return reclamation;
    
   
}
    
    
    //    _______________________________REPONSES_____________________________________
//    ________________________________________________________________________________
    
    
    
    
    public boolean addReponse(Reponses rep) {

        int recId = rep.getRec_id();
        int userId = rep.getUser_id();
        String repDesc = rep.getRep_desc();
        boolean adminResp = rep.isIsAdminReponse();
        
        String url = Statics.NEWREP_URL + "/?recId=" + recId + "&userId=" + userId + "&repDesc=" + repDesc + "&adminResp=" + adminResp;

        req.setUrl(url);
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
    
    public boolean addReponseAdmin(Reponses rep) {

        int recId = rep.getRec_id();
        int userId = rep.getUser_id();
        String repDesc = rep.getRep_desc();
        boolean adminResp = rep.isIsAdminReponse();
        
        String url = Statics.NEWREPADMIN_URL + "/?recId=" + recId + "&userId=" + userId + "&repDesc=" + repDesc + "&adminResp=" + adminResp;

        req.setUrl(url);
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
    

    
    
    public ArrayList<Reponses> parseReponses(String jsonText) {
    ArrayList<Reponses> reponses = new ArrayList<>();
    try {
        JSONParser j = new JSONParser();
        Map<String, Object> repsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        List<Map<String, Object>> list = (List<Map<String, Object>>) repsListJson.get("reponses");
        
        
        for (Map<String, Object> obj : list) {
            Reponses rep = new Reponses();


            
            // Get description
            Object adminRepObj = obj.get("isadminreponse");
            boolean isAdminResponse = (adminRepObj != null) && Boolean.parseBoolean(adminRepObj.toString());
            rep.setIsAdminReponse(isAdminResponse);        
            
                    
            // Get repId
            Object repIdObj = obj.get("repId");
            if (repIdObj == null) {
                rep.setRep_id(0);
            } else {
                float repId = Float.parseFloat(repIdObj.toString());
                rep.setRep_id((int) repId);
            }

            // Get description
            Object descriptionObj = obj.get("repDescription");
            if (descriptionObj == null) {
                rep.setRep_desc("error");
            } else {
                rep.setRep_desc(descriptionObj.toString());
            }
            
            // Get date
            Object dateObj = obj.get("dateRep");
            if (dateObj == null) {
                rep.setDate_rep(new Date());
            } else {
            String dateString = obj.get("dateRep").toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateString);
                rep.setDate_rep(date);
            } catch (ParseException e) {
                System.err.println("Error parsing date: " + dateString);
                rep.setDate_rep(new Date());
            }
        }
            

            reponses.add(rep);
        }

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return reponses;
}

    
    
    public ArrayList<Reponses> getRepsByRecid(int recId) {
        String url = Statics.RECPLUSREPBYRECID_URL + "/" + recId;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reponses = parseReponses(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reponses;
    }
    
    
    
//    ______________________________________________________________________________________
//            
//                               ADMIN                                                     
//    ________________________________________________________________________________________
    
    
    public ArrayList<Reclamation> parseReclamationsAdmin(String jsonText) {
    try {
        reclamations = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String, Object> recsListJson
                = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) recsListJson.get("root");
        for (Map<String, Object> obj : list) {
            Reclamation r = new Reclamation();
            Object idObj = obj.get("recId");
            if (idObj == null) {
                r.setRec_id(0);
            } else {
                float rec_id = Float.parseFloat(idObj.toString());
                r.setRec_id((int) rec_id);
            }
            if (obj.get("titreRec") == null) {
                r.setTitre_rec("error");
            } else {
                r.setTitre_rec(obj.get("titreRec").toString());
            }
            if (obj.get("type") == null) {
                r.setType("error");
            } else {
                r.setType(obj.get("type").toString());
            }
            if (obj.get("status") == null) {
                r.setStatus("error");
            } else {
                r.setStatus(obj.get("status").toString());
            }
            if (obj.get("dateCreation") == null) {
                r.setDate_creation(new Date());
            } else {
                String dateString = obj.get("dateCreation").toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(dateString);
                    r.setDate_creation(date);
                } catch (ParseException e) {
                    System.err.println("Error parsing date: " + dateString);
                    r.setDate_creation(new Date());
                }
            }
            reclamations.add(r);
        }

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return reclamations;
}


    public ArrayList<Reclamation> getAllRecsAdmin() {
        String url = Statics.RECSADMIN_URL + "/"  ;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                reclamations = parseReclamationsAdmin(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return reclamations;
    }
    
    
    



    
    
    
    

}
