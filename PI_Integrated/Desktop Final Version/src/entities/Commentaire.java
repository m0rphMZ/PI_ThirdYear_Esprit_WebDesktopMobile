/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

/**
 *
 * @author Mohamed
 */
public class Commentaire {
    
    private int id_com;
    private int id_user;
    private int id_event;
    private String commentaire;
    private int LikeCount;

    public Commentaire() {}

    public Commentaire(int id_com, int id_user, int id_event, String commentaire) {
        this.id_com = id_com;
        this.id_user = id_user;
        this.id_event = id_event;
        this.commentaire = commentaire;
        
    }

    public Commentaire(int id_user, int id_event, String commentaire) {
        this.id_user = id_user;
        this.id_event = id_event;
        this.commentaire = commentaire;
        
    }

    public int getId_com() {
        return id_com;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int LikeCount) {
        this.LikeCount = LikeCount;
    }

    
    
    
    @Override
    public String toString() {
        return "Commentaire{" + "id_com=" + id_com + ", id_user=" + id_user + ", id_event=" + id_event + ", commentaire=" + commentaire + '}';
    }
    
    
    
    
    
    
}


