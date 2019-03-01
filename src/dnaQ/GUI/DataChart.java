package dnaQ.GUI;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Models.MutationList;
import dnaQ.Models.Mutation;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class DataChart extends JDialog {

    private MutationListFrame parent;
    private MutationList mutationList;
    private ArrayList<JFreeChart> charts;
    private ArrayList<Mutation> mutations;

    public DataChart(MutationListFrame parent, MutationList mutationList){

        this.parent= parent;
        this.mutationList = mutationList;
        this.charts = new ArrayList<JFreeChart>();

        mutations = mutationList.getMutations();
        createCharts();

    }

    public JFreeChart getChart(int indx){
        return charts.get(indx);
    }

    private void createCharts(){

        charts.add(createChromosomeMutationPlot());
        charts.add(createLinePlot());
        charts.add(createPieChart());
        charts.add(createStackChart());

    }

    private JFreeChart createChromosomeMutationPlot() {

        String series1= "Mutation";
        final String series2= "Length of chr";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Double lengthOfChr [] = new Double[24];

        Integer mutationSize = mutations.size();

        try {
            lengthOfChr = DatabaseConnections.lengthOfChr();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();

        for (int i = 0; i< mutationSize; i++){

            String tempKey = mutations.get(i).getChr();

            if(map.containsKey(tempKey)){
                map.get(tempKey).incrementAndGet();
            } else{
                map.put(tempKey,new AtomicInteger(1));
            }
        }

        mutationSize=map.size();
        for (String key : map.keySet()) {
            dataset.addValue((map.get(key)).doubleValue()/mutationSize, series1, key);
            dataset.addValue(lengthOfChr[Integer.parseInt(key)-1],series2,key);
        }


        JFreeChart chart = ChartFactory.createBarChart(
                "Your Mutation and Length of Chromosomes", "Chromosomes", "Count", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }


    private JFreeChart createLinePlot(){

        String series1= "Mutation";
        final String series2= "Length of chr";

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Double lengthOfChr [] = new Double[24];

        Integer mutationSize = mutations.size();

        try {
            lengthOfChr = DatabaseConnections.lengthOfChr();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();

        for (int i = 0; i< mutationSize; i++){

            String tempKey = mutations.get(i).getChr();

            if(map.containsKey(tempKey)){
                map.get(tempKey).incrementAndGet();
            } else{
                map.put(tempKey,new AtomicInteger(1));
            }
        }

        mutationSize=map.size();
        for (String key : map.keySet()) {
            dataset.addValue((map.get(key)).doubleValue()/mutationSize, series1, key);
            dataset.addValue(lengthOfChr[Integer.parseInt(key)-1],series2,key);
        }


        JFreeChart chart = ChartFactory.createBarChart(
                "Your Mutation and Length of Chromosomes", "Chromosomes", "Count", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        return chart;
    }

    private JFreeChart createStackChart() {

        ArrayList<Mutation> mutations = mutationList.getMutations();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String tempChr = mutations.get(0).getChr();

        int countCos =0, countCli=0,countG1000 = 0;


        dataset.addValue(32.399999999999999D, "Cosmic", "Category 1");
        dataset.addValue(17.800000000000001D, "Clinvar", "Category 1");
        dataset.addValue(27.699999999999999D, "G1000", "Category 1");
        dataset.addValue(43.200000000000003D, "Cosmic", "Category 2");
        dataset.addValue(15.6D, "Clinvar", "Category 2");
        dataset.addValue(18.300000000000001D, "G1000", "Category 2");
        dataset.addValue(23D, "Cosmic", "Category 3");
        dataset.addValue(11.300000000000001D, "Clinvar", "Category 3");
        dataset.addValue(25.5D, "G1000", "Category 3");

        JFreeChart chart = ChartFactory.createStackedBarChart("Stacked Chart",
                "Category", "Value", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        return chart;
    }


    private JFreeChart createPieChart() {

        DefaultPieDataset dataset = new DefaultPieDataset( );
        dataset.setValue( "IPhone 5s" , new Double( 20 ) );
        dataset.setValue( "SamSung Grand" , new Double( 20 ) );
        dataset.setValue( "MotoG" , new Double( 40 ) );
        dataset.setValue( "Nokia Lumia" , new Double( 10 ) );

        JFreeChart chart = ChartFactory.createPieChart(
                "Mobile Sales",   // chart title
                dataset,          // data
                true,      // include legend
                true,
                false);

        return chart;
    }

    public void updateCharts(){

        charts.clear();
        createCharts();

    }

}
