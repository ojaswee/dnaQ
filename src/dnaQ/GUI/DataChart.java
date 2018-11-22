package dnaQ.GUI;

import dnaQ.Models.Sample;
import dnaQ.Models.SampleList;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class DataChart extends JDialog {

    private SampleListFrame parent;
    private SampleList sampleList;
    private ArrayList<JFreeChart> charts;

    public DataChart(SampleListFrame parent, SampleList sampleList){

        this.parent= parent;
        this.sampleList = sampleList;
        this.charts = new ArrayList<JFreeChart>();

        createCharts();

    }

    public JFreeChart getChart(int indx){
        return charts.get(indx);
    }

    private void createCharts(){

        charts.add(createLinePlot());
        charts.add(createHistoGram());
        charts.add(createChromosomeMutationPlot());

    }

    private JFreeChart createLinePlot(){

        XYSeries series = new XYSeries("Sample");

        series.add(1, 1);
        series.add(2, 2);
        series.add(5, 5);
        series.add(10, 10);

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(null, null, null, dataset, PlotOrientation.HORIZONTAL, true, true, true);

        return chart;

    }

    private JFreeChart createHistoGram() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(10, "USA", "2005");
        dataset.addValue(15, "USA", "2006");
        dataset.addValue(20, "USA", "2007");

        JFreeChart chart = ChartFactory.createBarChart(
                "Sample", "Sample", "Share", dataset,
                PlotOrientation.VERTICAL, false, true, false);

        return chart;
    }


    private JFreeChart createChromosomeMutationPlot() {

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

        return chart;
    }

}
