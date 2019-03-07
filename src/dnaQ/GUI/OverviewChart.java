package dnaQ.GUI;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Models.Mutation;
import dnaQ.Models.MutationList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OverviewChart extends JDialog {

    private MutationListFrame parent;
    private MutationList mutationList;
    private ArrayList<JFreeChart> charts;
    private ArrayList<Mutation> mutations;

    public OverviewChart(MutationListFrame parent, MutationList mutationList){

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
        charts.add(createBarChart());
    }

    private JFreeChart chrLenVsMutationPlot() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Double lengthOfChr [] = new Double[24];


        Map<String, AtomicInteger> map = new HashMap<String, AtomicInteger>();
        Integer mutationSize = mutations.size();

        try {
            lengthOfChr = DatabaseConnections.lengthOfChr();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Double sum = 0.0;
        for (Double value : lengthOfChr) {
            sum += value;
        }


        for (int i = 0; i< mutationSize; i++){

            String tempKey = mutations.get(i).getChr();

            if(map.containsKey(tempKey)){
                map.get(tempKey).incrementAndGet();
            } else{
                map.put(tempKey,new AtomicInteger(1));
            }
        }

//        Double x =0.0;
        for (String key : map.keySet()) {
            dataset.addValue((map.get(key)).doubleValue()*100/mutationSize, "Mutation", key);
//            x = (map.get(key)).doubleValue()/mutationSize +x;
            dataset.addValue(lengthOfChr[Integer.parseInt(key)-1],"Length of chr",key);
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


    private JFreeChart createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Integer mutationSize = mutations.size();
        String cancer="", clinical="",popfreq="";

        Integer cancerOnly=0,clinicalOnly=0,popfreqOnly=0,
                cancerClinical=0,cancerPopfreq=0,clinicalPopfreq=0,
                all=0,none=0;

        for (int i = 0; i< mutationSize; i++) {
            cancer = mutations.get(i).getCancerid();
            clinical = mutations.get(i).getClinicalid();
            popfreq = mutations.get(i).getFreqid();

            if (cancer.equals("") & clinical.equals("") &popfreq.equals("")) {
                none=none+1;
            }

            //canceronly
            else if ((! cancer.equals("")) &clinical.equals("") & popfreq.equals("")){
                cancerOnly =cancerOnly+1;
            }

            else if (cancer.equals("") & (!clinical.equals("")) &popfreq.equals("")){
                clinicalOnly= clinicalOnly+1;
            }

            else if (cancer.equals("") & clinical.equals("") & (!popfreq.equals(""))){
                popfreqOnly= popfreqOnly+1;
            }

            else if ((! cancer.equals("")) & (!clinical.equals("")) &popfreq.equals("") ){
                cancerClinical =cancerClinical+1;
            }

            else if ((! cancer.equals("")) & clinical.equals("") & (!popfreq.equals("")) ){
                cancerPopfreq =cancerPopfreq+1;
            }
            else if (cancer.equals("") & (!clinical.equals("")) & (!popfreq.equals("")) ){
                clinicalPopfreq =clinicalPopfreq+1;
            }
            else if ((!cancer.equals("")) & (!clinical.equals("")) & (!popfreq.equals("")) ){
                all =all+1;
            }
        }

        dataset.addValue(none, "None","Count");
        dataset.addValue(cancerOnly, "CancerOnly","Count");
        dataset.addValue(clinicalOnly, "ClinicalOnly","Count");
        dataset.addValue(popfreqOnly, "Pop freq Only","Count");
        dataset.addValue(cancerClinical, "Cancer & Clinical","Count");
        dataset.addValue(cancerPopfreq, "Cancer & Pop freq","Count");
        dataset.addValue(clinicalPopfreq, "Clinical & Pop freq","Count");
        dataset.addValue(all,"All", "Count");

        JFreeChart chart = ChartFactory.createBarChart(
                "Mutations with cancer,clinical and pop_freq", "Chromosomes",
                "Count", dataset,PlotOrientation.VERTICAL, true,true,false);

        return chart;
    }

    public void updateCharts(){
        charts.clear();
//        createCharts();
    }
}
