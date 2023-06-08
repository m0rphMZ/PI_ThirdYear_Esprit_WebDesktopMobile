/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.entities.Livraison;
import com.mycompany.myapp.services.CartService;
import com.mycompany.myapp.services.ProduitService;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Produit;
import com.mycompany.myapp.services.CartService;
import com.mycompany.myapp.services.ProduitService;
import java.util.ArrayList;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author ashre
 */



public class ListProduitForm extends Form {
    public ListProduitForm(Resources res){
    
////     super("Newsfeed",BoxLayout.y());
////
////      Toolbar tb = new Toolbar(true);
////         ListProduitForm     current = this;
////        setToolbar(tb);
////        getTitleArea().setUIID("Container");
////        setTitle("Ajout Reclamation");
////        getContentPane().setScrollVisible(false);
////   
////        tb.addSearchCommand(e ->  {
////            
////        });
//        
//        Tabs swipe = new Tabs();
//        
//        Label s1 = new Label();
//        Label s2 = new Label();
//        Label s3 = new Label();
//        
////        addTab(swipe,s1, res.getImage("download.jpg"),"","",res);
////          addTab(swipe,s2, res.getImage("monaliza.jpg"),"","",res);
////         addTab(swipe,s3, res.getImage("toto.jpg"),"","",res);
//        //
//        
//         swipe.setUIID("Container");
//        swipe.getContentPane().setUIID("Container");
//        swipe.hideTabs();
//
//        ButtonGroup bg = new ButtonGroup();
//        int size = Display.getInstance().convertToPixels(1);
//        Image unselectedWalkthru = Image.createImage(size, size, 0);
//        Graphics g = unselectedWalkthru.getGraphics();
//        g.setColor(0xffffff);
//        g.setAlpha(100);
//        g.setAntiAliased(true);
//        g.fillArc(0, 0, size, size, 0, 360);
//        Image selectedWalkthru = Image.createImage(size, size, 0);
//        g = selectedWalkthru.getGraphics();
//        g.setColor(0xffffff);
//        g.setAntiAliased(true);
//        g.fillArc(0, 0, size, size, 0, 360);
//        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
//        FlowLayout flow = new FlowLayout(CENTER);
//        flow.setValign(BOTTOM);
//        Container radioContainer = new Container(flow);
//        for (int iter = 0; iter < rbs.length; iter++) {
//            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
//            rbs[iter].setPressedIcon(selectedWalkthru);
//            rbs[iter].setUIID("Label");
//            radioContainer.add(rbs[iter]);
//        }
//
//        rbs[0].setSelected(true);
//        swipe.addSelectionListener((i, ii) -> {
//            if (!rbs[ii].isSelected()) {
//                rbs[ii].setSelected(true);
//            }
//        });
//
//        Component.setSameSize(radioContainer, s1, s2);
//        add(LayeredLayout.encloseIn(swipe, radioContainer));
//
//        ButtonGroup barGroup = new ButtonGroup();
//        RadioButton mesListes = RadioButton.createToggle("Mes Reclamations", barGroup);
//        mesListes.setUIID("SelectBar");
//        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
//        liste.setUIID("SelectBar");
//        RadioButton partage = RadioButton.createToggle("Reclamer", barGroup);
//        partage.setUIID("SelectBar");
//        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
//
//
//        mesListes.addActionListener((e) -> {
//               InfiniteProgress ip = new InfiniteProgress();
//        final Dialog ipDlg = ip.showInifiniteBlocking();
//        
//        //  ListReclamationForm a = new ListReclamationForm(res);
//          //  a.show();
//            refreshTheme();
//        });
//
//        add(LayeredLayout.encloseIn(
//                GridLayout.encloseIn(3, mesListes, liste, partage),
//                FlowLayout.encloseBottom(arrow)
//        ));
//
//        partage.setSelected(true);
//        arrow.setVisible(false);
//        addShowListener(e -> {
//            arrow.setVisible(true);
//            updateArrowPosition(partage, arrow);
//        });
//        bindButtonSelection(mesListes, arrow);
//        bindButtonSelection(liste, arrow);
//        bindButtonSelection(partage, arrow);
//        // special case for rotation
//        addOrientationListener(e -> {
//            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
//        });
        
        
        
        
        
          //Appel affichage methode
        ArrayList<Produit>list = ProduitService.getInstance().affichageReclamations();
        System.out.println("list");
        
        for(Produit rec : list ) {
             String urlImage ="back-logo.jpeg";//image statique pour le moment ba3d taw fi  videos jayin nwarikom image 
            
             Image placeHolder = Image.createImage(120, 90);
             EncodedImage enc =  EncodedImage.createFromImage(placeHolder,false);
             URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
             
              //  addButton(urlim,rec,res);
        
                ScaleImageLabel image = new ScaleImageLabel(urlim);
                
                Container containerImg = new Container();
                
                image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
    
    
    }
    }
}
    
    
  