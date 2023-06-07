/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;


import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Reclamation;
import com.mycompany.myapp.services.ServiceReclamation;
import java.util.ArrayList;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.XYChart;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Theto
 */
public class StatReclamationForm extends Form {
    
    private int numOpen = 0;
    private int numClosed = 0;
    private ArrayList<Reclamation> reclamations;
    
    public StatReclamationForm(Form previous) {
        setTitle("Statistique des Reclamations");
        setLayout(BoxLayout.y());

        reclamations = ServiceReclamation.getInstance().getAllRecsAdmin();
        if (reclamations.isEmpty()) {
            Label noReclamationsLabel = new Label("Aucune réclamation trouvée");
            add(noReclamationsLabel);
        } else {
            for (Reclamation r : reclamations) {
                countStatus(r.getStatus());
            }
            addElement();
        }

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new AdminForm().show());
    }

    private void countStatus(String status) {
        switch (status) {
            case "Ouverte":
                numOpen++;
                break;
            case "Fermée":
                numClosed++;
                break;
        }
    }
    
    public void addElement() {
    Label statsLabel = new Label("Statistiques des réclamations:");
    add(statsLabel);

    Label openLabel = new Label("Ouvertes: " + numOpen);
    add(openLabel);

    Label closedLabel = new Label("Fermées: " + numClosed);
    add(closedLabel);

    int total = numOpen + numClosed;
    Label totalLabel = new Label("Total: " + total);
    add(totalLabel);

    if (total > 0) {

    HashMap<String, Integer> countByType = new HashMap<>();
    for (Reclamation r : reclamations) {
        String type = r.getType();
        if (countByType.containsKey(type)) {
            int count = countByType.get(type);
            countByType.put(type, count + 1);
        } else {
            countByType.put(type, 1);
        }
    }
    String mostCommonType = null;
    int maxCount = 0;
    for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
        if (entry.getValue() > maxCount) {
            mostCommonType = entry.getKey();
            maxCount = entry.getValue();
        }
    }
    Label mostCommonTypeLabel = new Label("Type de réclamation le plus courant: " + mostCommonType);
    add(mostCommonTypeLabel);


        
        // Create a chart to display the percentage of open and closed reclamations
        // Create a dataset
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        CategorySeries openSeries = new CategorySeries("Ouvertes");
        CategorySeries closedSeries = new CategorySeries("Fermées");
        openSeries.add("Ouvertes", numOpen);
        closedSeries.add("Fermées", numClosed);
        dataset.addSeries(openSeries.toXYSeries());
        dataset.addSeries(closedSeries.toXYSeries());

        // Create a renderer for the dataset
        int[] colors = new int[] {ColorUtil.BLUE, ColorUtil.GREEN};
        XYMultipleSeriesRenderer renderer = buildCategoryRenderer(colors);

        // Create a chart component and add it to the form
        ChartComponent chart = new ChartComponent(buildBarChart(dataset, renderer));
        chart.getStyle().setMarginTop(10);
        chart.getStyle().setMarginBottom(10);
        chart.getStyle().setMarginLeft(10);
        chart.getStyle().setMarginRight(10);
        add(chart);
    }
}

// Helper method to create a renderer for a category dataset
        protected XYMultipleSeriesRenderer buildCategoryRenderer(int[] colors) {
            XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
            renderer.setLabelsTextSize(30);
            renderer.setLegendTextSize(30);
            renderer.setMargins(new int[] {20, 30, 15, 0});
            renderer.setZoomButtonsVisible(false);
            renderer.setLabelsColor(0xff000000);
            for (int color : colors) {
                XYSeriesRenderer r = new XYSeriesRenderer();
                r.setColor(color);
                renderer.addSeriesRenderer(r);
            }
            return renderer;
        }



// Helper method to create a bar chart
            protected XYChart buildBarChart(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer) {
                BarChart chart = new BarChart(dataset, renderer, BarChart.Type.DEFAULT);
                return chart;
            }


}
