/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.utils;

/**
 *
 * @author bhk
 */
public class Statics {

    //    ________________________________________RECLAMATION____________________________________________
    public static final String ADDREC_URL="http://127.0.0.1:8000/reclamationJSON";
    public static final String RECBYUSERID_URL="http://127.0.0.1:8000/reclamationsParUserIdJSON";
    public static final String NEWREP_URL="http://127.0.0.1:8000/reclamationNewRepJSON";
    public static final String NEWREPADMIN_URL="http://127.0.0.1:8000/reclamationNewRepAdminJSON";
    public static final String REMOVEREC_URL="http://127.0.0.1:8000/deleteRecJSON";
    public static final String RECPLUSREPBYRECID_URL="http://127.0.0.1:8000/reclamationEtReponsesParRecIdJSON";
    public static final String RECSADMIN_URL="http://127.0.0.1:8000/reclamationsAdminJSON";
     public static final String CLOSEREC_URL="http://127.0.0.1:8000/adminClosereclamation";
    
    
    
//    ________________________________________USER____________________________________________
    
        public static final String USERS_URL="http://127.0.0.1:8000/ListUser";
       public static final String ADD_USER_URL="http://127.0.0.1:8000/newUserJson?";
       public static final String Delete_User="http://127.0.0.1:8000/DeleteUserJSON/";
       public static final String Update_User="http://127.0.0.1:8000/UpdateUserJSON/";
       public static final String Show_User ="http://127.0.0.1:8000/UserJSON/";
    
    //    ________________________________________EVENT____________________________________________
       //Aymen:
       public static final String EVENT_BASE_URL="http://localhost:8000/eventlist";
       public static final String ADD_EVENT_BASE_URL="http://localhost:8000/eventnew";
       public static final String SHOW_EVENT_BASE_URL="http://localhost:8000/eventshow/";
       public static final String UPDATE_EVENT_BASE_URL="http://localhost:8000/eventupdate/";
       public static final String DELETE_EVENT_BASE_URL="http://localhost:8000/eventdelete/";
       public static final String TICKET_BASE_URL="http://localhost:8000/ticketlist";
    
    
       //    ________________________________________Local____________________________________________
       //Arbia:
       public static final String BASE_URL="http://localhost:8000";
       
       
    //    ________________________________________Panier____________________________________________
       //Ashref:   
    public static final String BASE_URL_Panier="http://127.0.0.1:8000/mobile";
    public static final String PRODUIT_IMAGE_URL = "http://127.0.0.1/img/";
    public static final String EXERCICE_IMAGE_URL = "http://127.0.0.1:8000/media/";
    public static final String EMPLOYEE_IMAGE_URL = "http://127.0.0.1:8000/media/";
    public static final String SUJET_IMAGE_URL = "http://127.0.0.1:8000/media/";
    public static final String ARITCLE_IMAGE_URL = "http://127.0.0.1:8000/media/";
    
      //    ________________________________________Produit____________________________________________
       //Montassar:   
    public static final String BASE_URL_Produit="http://127.0.0.1:8000/mobile";
       
       
       
}
