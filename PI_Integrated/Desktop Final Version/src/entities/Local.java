/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.sql.Blob;

/**
 *
 * @author Administrateur
 */
public class Local { private int num;
    private String descript;
   
    private String lieu;
    private float surface;
    private int nbper;
    private int codec;
    private byte[] image;

    public Local(int num, String descript, String lieu, float surface, int codec, byte[] image) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.codec = codec;
        this.image = image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public Local() {
    }

    /*public Local(int num, String descript, String lieu, float surface, int nbper, int codec) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.nbper = nbper;
        this.codec = codec;
    }*/

    public Local(int num, String descript, String lieu, float surface, int nbper, int codec, byte[] image) {
        this.num = num;
        this.descript = descript;
        this.lieu = lieu;
        this.surface = surface;
        this.nbper = nbper;
        this.codec = codec;
        this.image = image;
    }

    public int getNum() {
        return num;
    }

    public String getDescript() {
        return descript;
    }

    public String getLieu() {
        return lieu;
    }

    public float getSurface() {
        return surface;
    }

    public int getNbper() {
        return nbper;
    }

    public int getCodec() {
        return codec;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public void setNbper(int nbper) {
        this.nbper = nbper;
    }

    public void setCodec(int codec) {
        this.codec = codec;
    }

    @Override
    public String toString() {
        return "Local{" + "num=" + num + ", descript=" + descript + ", lieu=" + lieu + ", surface=" + surface + ", nbper=" + nbper + ", codec=" + codec + ", image=" + image + '}';
    }

   
    
    
    
    
    
    
}
