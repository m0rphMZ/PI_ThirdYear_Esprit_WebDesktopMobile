/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

import java.util.ArrayList;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.PieChart;
import com.codename1.charts.views.XYChart;
import com.codename1.ui.Toolbar;

import java.util.HashMap;
import java.util.Map;

import com.mycompany.myapp.entities.Local;
import com.mycompany.myapp.services.LocalService;

/**
 *
 * @author Administrateur
 */
public class Statistic extends Form{
    
   private HashMap<String, Integer> locationCountMap;
    private ArrayList<Local> locaux;
    public Statistic(Form previous) {
        setTitle("Statistique des locaux");
        setLayout(BoxLayout.y());
        Toolbar toolbar = getToolbar();
        toolbar.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        locaux = LocalService.getInstance().getAllLocaux();
        if (locaux.isEmpty()) {
            Label noReclamationsLabel = new Label("Aucune réclamation trouvée");
            add(noReclamationsLabel);
        } else {
            countLocations();
            addElement();
        }
        
        
        
    }

    private void countLocations() {
        locationCountMap = new HashMap<>();
        for (Local r : locaux) {
            String lieu = r.getLieu();
            if (locationCountMap.containsKey(lieu)) {
                int count = locationCountMap.get(lieu);
                locationCountMap.put(lieu, count + 1);
            } else {
                locationCountMap.put(lieu, 1);
            }
        }
    }
    public void addElement() {
    Label statsLabel = new Label("Statistiques de"
            + "s locaux:");
    add(statsLabel);

    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(30);
    renderer.setLegendTextSize(30);
    renderer.setZoomButtonsVisible(false);
    renderer.setLabelsColor(0xff000000);

    CategorySeries series = new CategorySeries("Nombre de locaux par lieu");

    // Tableau de couleurs
    int[] colors = new int[] {
        ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.YELLOW, ColorUtil.MAGENTA, ColorUtil.CYAN
    };

    int colorIndex = 0;
    for (Map.Entry<String, Integer> entry : locationCountMap.entrySet()) {
        String lieu = entry.getKey();
        int count = entry.getValue();
        series.add(lieu + " (" + count + ")", count);
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(colors[colorIndex % colors.length]);
        renderer.addSeriesRenderer(r);
        colorIndex++;
    }

    PieChart chart = new PieChart(series, renderer);
    ChartComponent chartComponent = new ChartComponent(chart);
    chartComponent.getStyle().setMarginTop(10);
    chartComponent.getStyle().setMarginBottom(10);
    chartComponent.getStyle().setMarginLeft(10);
    chartComponent.getStyle().setMarginRight(10);

    add(chartComponent);
}


    
//    
//    public void addElement() {
//    Label statsLabel = new Label("Statistiques des locaux:");
//    add(statsLabel);
//
//    DefaultRenderer renderer = new DefaultRenderer();
//    renderer.setLabelsTextSize(30);
//    renderer.setLegendTextSize(30);
//    renderer.setZoomButtonsVisible(false);
//    renderer.setLabelsColor(0xff000000);
//
//    CategorySeries series = new CategorySeries("Nombre de locaux par lieu");
//    for (Map.Entry<String, Integer> entry : locationCountMap.entrySet()) {
//        String lieu = entry.getKey();
//        int count = entry.getValue();
//        series.add(lieu + " (" + count + ")", count);
//        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
//        r.setColor(ColorUtil.BLUE);
//        renderer.addSeriesRenderer(r);
//    }
//
//    PieChart chart = new PieChart(series, renderer);
//    ChartComponent chartComponent = new ChartComponent(chart);
//    chartComponent.getStyle().setMarginTop(10);
//    chartComponent.getStyle().setMarginBottom(10);
//    chartComponent.getStyle().setMarginLeft(10);
//    chartComponent.getStyle().setMarginRight(10);
//
//    add(chartComponent);
//}

}
    
//    private HashMap<String, Integer> locationCountMap;
//     private ArrayList<Local> locaux;
//   
//        
//        public  Statistic() {
//         //private int numClosed = 0;
//    //private ArrayList<Local> local;
//    
//  
//        setTitle("Statistique des locaux");
//        setLayout(BoxLayout.y());
//
//        locaux = LocalService.getInstance().getAllLocaux();
//        if (locaux.isEmpty()) {
//            Label noReclamationsLabel = new Label("Aucune rÃ©clamation trouvÃ©e");
//            add(noReclamationsLabel);
//        } else {
//            for (Local r : locaux) {
//                countLocations();
//                
//            }
//            addElement();
//        }
//
//         
//   
//}
//
//        private void countLocations() {
//        locationCountMap = new HashMap<>();
//        for (Local r : locaux) {
//            String lieu = r.getLieu();
//            if (locationCountMap.containsKey(lieu)) {
//                int count = locationCountMap.get(lieu);
//                locationCountMap.put(lieu, count + 1);
//            } else {
//                locationCountMap.put(lieu, 1);
//            }
//        }
//    }
//        
////        protected XYChart buildBarChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
////    BarChart chart = new BarChart(dataset, renderer, BarChart.Type.DEFAULT);
////    return chart;
//        
//        public void addElement() {
//        Label statsLabel = new Label("Statistiques des locaux:");
//        add(statsLabel);
//
//        for (Map.Entry<String, Integer> entry : locationCountMap.entrySet()) {
//            String lieu = entry.getKey();
//            int count = entry.getValue();
//            Label locationLabel = new Label(lieu + ": " + count);
//            add(locationLabel);
//        }
//        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
//        CategorySeries series = new CategorySeries("Nombre de locaux par lieu");
//        for (Map.Entry<String, Integer> entry : locationCountMap.entrySet()) {
//            String lieu = entry.getKey();
//            int count = entry.getValue();
//            series.add(count);
//        }
//        dataset.addSeries(series.toXYSeries());
//
//        
//                
//                
//                
////                XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
////    renderer.setLabelsTextSize(30);
////    renderer.setLegendTextSize(30);
////    renderer.setMargins(new int[]{20, 30, 15, 0});
////    renderer.setZoomButtonsVisible(false);
////    renderer.setLabelsColor(0xff000000);
////    XYSeriesRenderer r = new XYSeriesRenderer();
////    r.setColor(0xff3366cc);
////    renderer.addSeriesRenderer(r);
//    
//    
//    
//    
//    
//    
//
//    
//
//        // Create a renderer for the dataset
//        int[] colors = new int[] {ColorUtil.BLUE};
//        XYMultipleSeriesRenderer renderer = buildCategoryRenderer(colors);
//
//        // Create a chart component and add it to the form
//        ChartComponent chart = new ChartComponent(buildBarChart(dataset, renderer));
//        chart.getStyle().setMarginTop(10);
//        chart.getStyle().setMarginBottom(10);
//        chart.getStyle().setMarginLeft(10);
//        chart.getStyle().setMarginRight(10);
//
////
////    ChartComponent chart = new ChartComponent(buildBarChart(dataset, renderer));
////    chart.getStyle().setMarginTop(10);
////    chart.getStyle().setMarginBottom(10);
////    chart.getStyle().setMarginLeft(10);
////    chart.getStyle().setMarginRight(10);
//    add(chart);
//}
//                
//    
//// Helper method to create a renderer for a category dataset
//        protected XYMultipleSeriesRenderer buildCategoryRenderer(int[] colors) {
//            XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
//            renderer.setLabelsTextSize(30);
//            renderer.setLegendTextSize(30);
//            renderer.setMargins(new int[] {20, 30, 15, 0});
//            renderer.setZoomButtonsVisible(false);
//            renderer.setLabelsColor(0xff000000);
//            for (int color : colors) {
//                XYSeriesRenderer r = new XYSeriesRenderer();
//                r.setColor(color);
//                renderer.addSeriesRenderer(r);
//            }
//            return renderer;
//        }
//
//         
//            protected XYChart buildBarChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
//                BarChart chart = new BarChart(dataset, renderer, BarChart.Type.DEFAULT);
//                return chart;
//            }

         




      
