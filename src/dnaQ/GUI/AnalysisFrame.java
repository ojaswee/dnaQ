package dnaQ.GUI;

import dnaQ.GUI.GUI_Models.SampleList;
import dnaQ.Models.Sample;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AnalysisFrame extends JDialog {

    private SampleListFrame parent;
    private SampleList sampleList;
    public JPanel dataAPanel;
    public JPanel dataBPanel;
    public JPanel dataCPanel;
    public JPanel dataDPanel;


    public AnalysisFrame(SampleListFrame parent, SampleList sampleList) {

        this.parent = parent;
        this.sampleList = sampleList;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createComponents();
        layoutAnalysisComponents();

        pack();
        //setModalityType(ModalityType.APPLICATION_MODAL);
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);

    }

    private void createComponents(){

        dataAPanel = new JPanel();
        dataAPanel.add(new Label("Chart Area"));

        dataBPanel = new JPanel();
        dataBPanel.add(new Label("Chart Area"));

        dataCPanel = new JPanel();
        dataCPanel.add(new Label("Chart Area"));

        dataDPanel = new JPanel();
        dataDPanel.add(new Label("Chart Area"));

        createLinePlot();
        createHistoGram();
        createChromosomeMutationPlot();

    }

    private void layoutAnalysisComponents(){

       //setMinimumSize(new Dimension(2000,2000));

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(0,2));
        //dataPanel.setMinimumSize(new Dimension(1990,1990));

        dataAPanel.setMinimumSize(new Dimension(450,50));
        dataAPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataBPanel.setMinimumSize(new Dimension(450,50));
        dataBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataCPanel.setMinimumSize(new Dimension(450,50));
        dataCPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataDPanel.setMinimumSize(new Dimension(450,50));
        dataDPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataPanel.add(dataAPanel);
        dataPanel.add(dataBPanel);
        dataPanel.add(dataCPanel);
        dataPanel.add(dataDPanel);

        setLayout(new GridLayout(0,1));
        add(dataPanel);

    }

    private void createLinePlot(){

        XYSeries series = new XYSeries("Sample");

        series.add(1, 1);
        series.add(2, 2);
        series.add(5, 5);
        series.add(10, 10);

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(null, null, null, dataset, PlotOrientation.HORIZONTAL, true, true, true);
        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);

        dataAPanel.add(chartpanel);

    }

    private void createHistoGram() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(10, "USA", "2005");
        dataset.addValue(15, "USA", "2006");
        dataset.addValue(20, "USA", "2007");

        JFreeChart chart = ChartFactory.createBarChart(
                "Sample", "Sample", "Share", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);

        dataBPanel.add(chartpanel);
    }

    private void createChromosomeMutationPlot() {

        ArrayList<Sample> samples = sampleList.getSamples();

        Map<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();

        for (int i = 0; i<samples.size(); i++){

            String tempKey = samples.get(i).getChr();

            if(map.containsKey(tempKey)){
                map.get(tempKey).incrementAndGet();
            } else{
                map.put(tempKey,new AtomicInteger(1));
            }
        }



        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (String key : map.keySet()) {
            dataset.addValue(map.get(key), "Chromosomes", key);
        }


        JFreeChart chart = ChartFactory.createBarChart(
                "Chromosomes Bar Chart", "Chromosomes", "Share", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setDomainZoomable(true);
        dataCPanel.add(chartpanel);
    }


}
