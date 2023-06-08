package entities;

//import com.twilio.Twilio;
//import com.twilio.rest.api.v2010.account.Message;
//import com.twilio.type.PhoneNumber;



//public class SMSSender {
//    // Informations d'authentification pour votre compte Twilio
//    public static final String ACCOUNT_SID = "ACd9f8163e8cb8fa99b07dfb9ccfc8a1f4";
//    public static final String AUTH_TOKEN = "ACd9f8163e8cb8fa99b07dfb9ccfc8a1f4:70143dfba0b45b403f1bc05d8e941aca";
//    public static final String TWILIO_NUMBER = "+15674122488";
//
//   /* public static void main(String[] args) {
//        // Envoyer un SMS de confirmation
//        sendConfirmationSMS("+clientPhoneNumber");
//    }*/
//
//    public static void sendConfirmationSMS(String toPhoneNumber) {
//        // Initialisez la bibliothèque Twilio avec vos informations d'authentification
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        // Créez un nouveau message SMS
//        Message message = Message.creator(
//            new PhoneNumber(toPhoneNumber),
//            new PhoneNumber(TWILIO_NUMBER),
//            "Votre commande a été confirmée. Merci de votre achat!")
//                .create();
//
//        // Vérifiez que le message a été envoyé avec succès
//        if (message.getStatus() == Message.Status.FAILED) {
//            System.out.println("Failed to send message: " + message.getErrorCode() + " " + message.getErrorMessage());
//        } else {
//            System.out.println("Message sent to " + message.getTo());
//        }
//    }
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import services.CommandeService;
import java.net.URI;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.MyDB;

public class SMSSender {
    
    Connection cnx ; 
    public SMSSender() {
    cnx = MyDB.getInstance().getCnx(); 
}
  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "ACd9f8163e8cb8fa99b07dfb9ccfc8a1f4";
  public static final String AUTH_TOKEN = "70143dfba0b45b403f1bc05d8e941aca";

  public static void main(String[] args) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
     PhoneNumber clientPhoneNumber = new PhoneNumber("+21658749090");
        Message message = Message.creator(clientPhoneNumber, new PhoneNumber("+15674122488"), "Votre commande a été bien reçue").create();
        System.out.println(message.getSid());
  }
  
      public void SMSSender(String id_user) {
    // Remplacez les informations de compte et de numéro de téléphone par les vôtres
    String accountSid = "ACd9f8163e8cb8fa99b07dfb9ccfc8a1f4";
    String authToken = "70143dfba0b45b403f1bc05d8e941aca";
 
      try {
        // Récupérer le numéro de téléphone de l'utilisateur à partir de la base de données
        String clientPhoneNumber = id_user;
        
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
            new PhoneNumber("+216"+clientPhoneNumber),
            new PhoneNumber("+15674122488"),
            "Votre commande a été bien reçue"
        ).create();
        
        System.out.println("SID du message : " + message.getSid());
    } catch (Exception ex) {
        System.out.println("Erreur : " + ex.getMessage());
    }

      }
}
  


