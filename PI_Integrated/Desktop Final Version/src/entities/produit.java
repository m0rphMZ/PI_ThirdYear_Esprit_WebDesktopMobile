/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Monta
 */
public class produit {
    private int id ;
    private String CodeProduit;
    private String Desgination ;
     private Categorie Categorie; 

    public Categorie getCategorie() {
        return Categorie;
    }

    public void setCategorie(Categorie Categorie) {
        this.Categorie = Categorie;
    }
    
    @Override
    public String toString() {
        return "produit{" + "id=" + id + ", CodeProduit=" + CodeProduit + ", Desgination=" + Desgination + ", QteStock=" + QteStock + ", QteMin=" + QteMin + ", PrixUnite=" + PrixUnite + ", idUnite=" + idUnite + ", image=" + image + ", idCat=" + idCat + '}';
    }
    private  int QteStock ;
    private int QteMin; 
    private float PrixUnite ;
    private String idUnite;
    private String image ; 
    private String idCat;

    public produit() {
    }

    public produit(String CodeProduit, String Desgination, Categorie Categorie, int QteStock, int QteMin, float PrixUnite, String idUnite, String image, String idCat) {
        this.CodeProduit = CodeProduit;
        this.Desgination = Desgination;
        this.Categorie = Categorie;
        this.QteStock = QteStock;
        this.QteMin = QteMin;
        this.PrixUnite = PrixUnite;
        this.idUnite = idUnite;
        this.image = image;
        this.idCat = idCat;
    }

    public produit(int id, String CodeProduit, String Desgination, int QteStock, int QteMin, float PrixUnite, String idUnite, String image, String idCat) {
        this.id = id;
        this.CodeProduit = CodeProduit;
        this.Desgination = Desgination;
        this.QteStock = QteStock;
        this.QteMin = QteMin;
        this.PrixUnite = PrixUnite;
        this.idUnite = idUnite;
        this.image = image;
        this.idCat = idCat;
    }
      public produit(int id, String CodeProduit, String Desgination, String image, int QteStock, int QteMin, float PrixUnite, String idUnite, String idCat) {
        this.id = id;
        this.CodeProduit = CodeProduit;
        this.Desgination = Desgination;
        this.QteStock = QteStock;
        this.QteMin = QteMin;
        this.PrixUnite = PrixUnite;
        this.idUnite = idUnite;
        this.image = image;
        this.idCat = idCat;
    }


    public produit(int id) {
        this.id = id;
    }
    
    public produit( String CodeProduit, String Desgination, int QteStock, int QteMin, float PrixUnite, String idUnite, String image, String idCat) {
      
        this.CodeProduit = CodeProduit;
        this.Desgination = Desgination;
        this.QteStock = QteStock;
        this.QteMin = QteMin;
        this.PrixUnite = PrixUnite;
        this.idUnite = idUnite;
        this.image = image;
        this.idCat = idCat;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//------------------ NEW CONSTRUCTOR ------------------------------------
    public produit(String CodeProduit, String Desgination, int QteStock, int QteMin, float PrixUnite, String idUnite, String image) {
        this.CodeProduit = CodeProduit;
        this.Desgination = Desgination;
        this.QteStock = QteStock;
        this.QteMin = QteMin;
        this.PrixUnite = PrixUnite;
        this.idUnite = idUnite;
        this.image = image;
    }

    public String getCodeProduit() {
        return CodeProduit;
    }

    public void setCodeProduit(String CodeProduit) {
        this.CodeProduit = CodeProduit;
    }

    public String getDesgination() {
        return Desgination;
    }

    public void setDesgination(String Desgination) {
        this.Desgination = Desgination;
    }

    public int getQteStock() {
        return QteStock;
    }

    public void setQteStock(int QteStock) {
        this.QteStock = QteStock;
    }

    public int getQteMin() {
        return QteMin;
    }

    public void setQteMin(int QteMin) {
        this.QteMin = QteMin;
    }

    public float getPrixUnite() {
        return PrixUnite;
    }

    public void setPrixUnite(float PrixUnite) {
        this.PrixUnite = PrixUnite;
    }

    public String getIdUnite() {
        return idUnite;
    }

    public String setIdUnite(String idUnite) {
      return  this.idUnite = idUnite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdCat() {
        return idCat;
    }

    public String setIdCat(String idCat) {
      return  this.idCat = idCat;
    }

   
     
    
}
