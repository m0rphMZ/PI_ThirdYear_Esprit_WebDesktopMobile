package test;

import entities.Reclamation;
import entities.Reponses;
import java.sql.SQLException;
import java.util.List;
import services.ReclamationService;
import services.ReponsesService;

public class Test {
    
    public static void main(String[] args) {
        ReclamationService rs = new ReclamationService();
        ReponsesService reps = new ReponsesService();
        
        try {
            // Creer Nouvelle reclam
            Reclamation r = new Reclamation(10, "Probleme de paiment","Paiment","Probleme paiment quand je...", "Ouvert");
            rs.ajouter(r);
            System.out.println("Reclamation ajoutée");
//            
            // retrieve all the reclamations
            List<Reclamation> reclam = rs.recuperer();
            System.out.println("Liste des reclamations");
            for (Reclamation recl : reclam) {
                System.out.println(recl.toString());
            }
            
            rs.ModifierEtat(72);
            
            // Test recupererByStatus
                try {
                    List<Reclamation> reclamationsOuvertes = rs.recupererByStatus("Ouvert");
                    List<Reclamation> reclamationsFermees = rs.recupererByStatus("Fermé");

                    System.out.println("Reclamations Ouvertes:");
                    for (Reclamation recl : reclamationsOuvertes) {
                        System.out.println(recl.toString());
                    }

                    System.out.println("Reclamations Fermées:");
                    for (Reclamation recl : reclamationsFermees) {
                        System.out.println(recl.toString());
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }

                    

            
            
//            // recuperer par id test
//            Reclamation recl = rs.recupererParId(72);
//            System.out.println("Reclamation with id: " + recl.getRec_id()+ " " + recl.toString());

            
////            
////            // Mise a jour etat reclam LKOL
//            Reclamation rToUpdate = new Reclamation(61, 10, "Probleme de paiment","Paiment","Probleme paiment quand je...", "Closed");
//            rs.ajouter(rToUpdate);
////            rs.modifier(rToUpdate);
////            System.out.println("Reclamation mis a jour");

            rs.supprimerParRecId(61);

//            // recuperer reclamation Par user Id
//            List<Reclamation> reclamations = rs.recupererParUtilisateur(10);
//            System.out.println("Liste des reclamations");
//            for (Reclamation recl : reclamations) {
//                System.out.println(recl.toString());
//            }
            
////             delete a reclamation
//            
//            System.out.println("Reclamation supprimée");
//            

            
//            // creer nouvelle rep
//            Reponses rep = new Reponses(506,12,10,"Rep3");
//            reps.ajouter(rep);
//            System.out.println("Reponse ajoutée");
                
                reps.supprimerParRecId(64);
            
            // recuperer responses pour un utilisateur
            try {
                List<Reponses> responses = reps.recupererParRecId(50);
                System.out.println("Liste des réponses pour rec");
                for (Reponses reponse : responses) {
                    System.out.println("Réponse : " + reponse.getRep_desc() + " | Date : " + reponse.getDate_rep());
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


            
            
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
