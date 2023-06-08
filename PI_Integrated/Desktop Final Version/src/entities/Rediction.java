/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author sbekr
 */
public class Rediction {
    private int idr ;
           private String  coder;
            private float valr ;

    public Rediction() {
    }

    public Rediction(int idr, String coder, float valr) {
        this.idr = idr;
        this.coder = coder;
        this.valr = valr;
    }

    public Rediction(String coder, float valr) {
        this.coder = coder;
        this.valr = valr;
    }
            

    public int getIdr() {
        return idr;
    }

    public void setIdr(int idr) {
        this.idr = idr;
    }

    public String getCoder() {
        return coder;
    }

    public void setCoder(String coder) {
        this.coder = coder;
    }

    public float getValr() {
        return valr;
    }

    public void setValr(float valr) {
        this.valr = valr;
    }

    @Override
    public String toString() {
        return "Rediction{" + "idr=" + idr + ", coder=" + coder + ", valr=" + valr + '}';
    }
    
            
    
}
