/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package test;

import entities.Event;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import services.EventService;

/**
 *
 * @author Aymen
 */
public class Test {
    public static void main(String[] args) {
        try {
            
            
            //SQL Part
           
            EventService es = new EventService();
            
            
                //Read
            System.out.println(es.recuperer()); 
           
            
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
