package dnaQ.GUI;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Models.MutationList;
import dnaQ.Models.Mutation;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class DetailsOnDemandChart extends JDialog {

    private MutationListFrame parent;
    private MutationList mutationList;
    private ArrayList<JFreeChart> charts;
    private ArrayList<Mutation> mutations;

    public DetailsOnDemandChart(MutationListFrame parent, MutationList mutationList){

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

        charts.add(chrLenVsMutationPlot());
        charts.add(popFreqPieChart());
        charts.add(createPieChart());
        charts.add(createStackChart());
    }

    private JFreeChart chrLenVsMutationPlot() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();
        Integer mutationSize = mutations.size();

        for (int i = 0; i< mutationSize; i++){

            String tempKey = mutations.get(i).getChr();

            if(map.containsKey(tempKey)){
                map.get(tempKey).incrementAndGet();
            } else{
                map.put(tempKey,new AtomicInteger(1));
            }
        }

        for (String key : map.keySet()) {
            dataset.addValue((map.get(key)).doubleValue()*100/mutationSize, "Mutation", key);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Your Mutation and Length of Chromosomes", "Chromosomes", "Count", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        return chart;
    }


    private JFreeChart popFreqPieChart(){
        DefaultPieDataset dataset = new DefaultPieDataset( );

        Integer mutationSize = mutations.size();

        Double globalAmer=0.0,globalAsian=0.0, globalAfr =0.0,
                globalEuro=0.0, globalTotal=0.0;

        for (int i = 0; i< mutationSize; i++){
            String amer, asian, afr, euro;

            Double currentAmer = 0.0,currentAsian = 0.0,
                    currentAfr=0.0, currentEuro=0.0, currentTotal =0.0;

            amer = mutations.get(i).getAmericanFreq().trim();
            asian = mutations.get(i).getAsianFreq().trim();
            afr = mutations.get(i).getAfrFreq().trim();
            euro = mutations.get(i).getEurFreq().trim();
            
            currentTotal =0.0;
            
            if (amer.matches(".*\\d+.*")){
                currentAmer= Double.valueOf(amer);
                currentTotal = currentTotal+ currentAmer; 
            } else {
                currentAmer=0.0;
            }

            if (asian.matches(".*\\d+.*")){
                currentAsian= Double.valueOf(asian);
                currentTotal = currentTotal+ currentAsian;
            } else{
                currentAsian=0.0;
            }

            if (afr.matches(".*\\d+.*")){
                currentAfr= Double.valueOf(afr);
                currentTotal = currentTotal+ currentAfr;
            } else {
                currentAfr=0.0;
            }

            if (euro.matches(".*\\d+.*")){
                currentEuro= Double.valueOf(euro);
                currentTotal = currentTotal+ currentEuro;
            }else {
                currentEuro=0.0;
            }
            
            currentAmer = currentAmer/currentTotal;
            currentAsian= currentAsian/currentTotal;
            currentAfr = currentAfr/currentTotal;
            currentEuro = currentEuro/currentTotal;

            if (!(currentAmer.isNaN())) {
                globalAmer = globalAmer + currentAmer;
            }

            if (!(currentAsian.isNaN())) {
                globalAsian = globalAsian + currentAsian;
            }

            if (!(currentAfr.isNaN())) {
                globalAfr = globalAfr + currentAfr;
            }
            if (!(currentEuro.isNaN())) {
                globalEuro = globalEuro + currentEuro;
            }
            if (!(currentTotal.isNaN())) {
                globalTotal = globalTotal + currentTotal;
            }
        }

        dataset.setValue( "American",new Double(globalAmer));
        dataset.setValue( "Asian",new Double(globalAsian));
        dataset.setValue( "African",new Double(globalAfr));
        dataset.setValue("European", new Double(globalEuro));

        JFreeChart chart = ChartFactory.createPieChart(
                "Your mutation is most similar to",   // chart title
                dataset,          // data
                true,      // include legend
                true,
                false);

        //create labels for pie chart
        PiePlot plot = (PiePlot) chart.getPlot();
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", NumberFormat.getInstance(),
                NumberFormat.getPercentInstance());
        plot.setLabelGenerator(gen);

        return chart;
    }


    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset( );

        Integer mutationSize = mutations.size();

        Map<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();

        for (int i = 0; i< mutationSize; i++){

            String tempKey = mutations.get(i).getChr();

            if(map.containsKey(tempKey)){
                map.get(tempKey).incrementAndGet();
            } else{
                map.put(tempKey,new AtomicInteger(1));
            }
        }

        for (String key : map.keySet()) {
            dataset.setValue( key,(map.get(key)).doubleValue()/mutationSize);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Chromosomes in your mutation",   // chart title
                dataset,          // data
                true,      // include legend
                true,
                false);

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

// this function helps to redraw the charts, called from mutationlistframefilterpanel
    public void updateCharts(){
        charts.clear();
        createCharts();
    }

}
