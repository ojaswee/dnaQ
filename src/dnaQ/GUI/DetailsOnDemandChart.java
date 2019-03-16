package dnaQ.GUI;

import dnaQ.Models.MutationList;
import dnaQ.Models.Mutation;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import java.util.*;
import java.util.regex.Pattern;


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

        charts.add(scientificEvidencePlot());
        charts.add(diseaseEvidencePlot());
        charts.add(cancerEvidencePlot()); 
        charts.add(popfreqEvidencePlot());
    }

    private JFreeChart scientificEvidencePlot() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());

        Integer mutationSize = mutations.size();

        for (int i = 0; i< mutationSize; i++) {

            String gene = mutations.get(i).getGene().trim();

            Integer currentPub = 0;

            String pubCount = mutations.get(i).getPublicationCount().trim();

            //check values of gene
            gene = geneValue(gene);

            //check values of pubCount
            currentPub = addAllIntegerValues(pubCount);

            // make key value pair
            if(!(map.containsValue(gene))){
                map.put(currentPub+1, gene);
            }
        }

        //insert only top 5 values into dataset
        dataset = getTop5(map, dataset);

        JFreeChart chart = createBarChart( "Scientific Evidence", "Biology Genes",
                "Publications", dataset);

        return chart;
    }

    private JFreeChart diseaseEvidencePlot() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());

        Integer mutationSize = mutations.size();
        Integer currentPub = 0;

        for (int i = 0; i< mutationSize; i++){

            String disease = mutations.get(i).getClinicalDisease().trim();
            String gene = mutations.get(i).getGene().trim();
            String tempKey = disease+" / "+gene;
            String pubCount = mutations.get(i).getPublicationCount().trim();

           //check values of gene
           gene = geneValue(gene);

            //check values of pubCount
            currentPub = addAllIntegerValues(pubCount);

            //if disease has many values
            if (disease.matches(".*\\|+.*")) {
                String[] diseaseValues = disease.split(Pattern.quote("|"));
                for (String d : diseaseValues) {
                    disease = d;

                    tempKey = disease + " / " + gene;

                    if (map.containsValue(tempKey)) {
                        map.replace(currentPub,tempKey);

                    } else if (tempKey.matches(".*not_+.*")) {
                    } else {
                        map.put(currentPub,tempKey);
                    }
                }
            }

            else if (disease.equals("")){
                tempKey = "Disease not found / "+gene;
            }


            if(map.containsValue(tempKey)){
                map.replace(currentPub,tempKey);

            }else if (tempKey.matches(".*not_+.*")){

            }
            else{
                map.put(currentPub,tempKey);
            }
        }

        dataset = getTop5(map, dataset);

        JFreeChart chart = createBarChart("Disease Evidence","Clinical Disease / Biology Genes"
                ,"Publications",dataset);

        return chart;
    }

    private JFreeChart cancerEvidencePlot(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());

        String cancerID= "";
        String cancerCount= "";
        String gene="";
        Integer count = 0;

        for (int i = 0; i< mutations.size(); i++) {
            cancerID = mutations.get(i).getCancerid().trim();
            cancerCount = mutations.get(i).getCancerCount();
            gene = mutations.get(i).getGene();

            if (cancerID.equals("")){
                count = 0;
            }

            //if cancerid is not empty
            else {
                //check values of gene
                gene = geneValue(gene);

                //check values of pubCount
                count = addAllIntegerValues(cancerCount);

                // only put in map if cancerid is present
                if(map.containsValue(gene)) {
                    map.replace(count,gene);

                }else{
                    map.put(count,gene);
                }
            }
        }

        dataset = getTop5(map, dataset);

        JFreeChart chart = createBarChart("Cancer Evidence","Biology Genes"
                ,"Cancer Count",dataset);

        return chart;
    }

    private JFreeChart popfreqEvidencePlot() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());

        String popfreqID= "";
        String globalfreq= "";
        String gene="";
        Integer count = 0;

        for (int i = 0; i< mutations.size(); i++) {
            popfreqID = mutations.get(i).getFreqid().trim();
            globalfreq = mutations.get(i).getGlobalFreq().trim();
            gene = mutations.get(i).getGene();

            if (popfreqID.equals("")){
                count = 0;
            }

            //if popfreqID is not empty
            else {
                count = Double.valueOf(globalfreq).intValue();

                gene = geneValue(gene);

                // only put in map if popfreqID is present

                if(map.containsValue(gene)) {
                    Object key = getKeyFromValue(map,gene);
                    Integer n = Integer.valueOf(key.toString()) + count;
                    map.replace(n,gene);

                }else{
                    map.put(count,gene);
                }
            }
        }

        dataset = getTop5(map, dataset);

        JFreeChart chart = createBarChart("Population Frequency Evidence","Biology Genes"
                ,"Global frequency",dataset);

        return chart;
    }

    public static Object getKeyFromValue(Map map, Object value) {
        for (Object o : map.keySet()) {
            if (map.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }

    // get gene value
    private String geneValue (String gene){

        if (gene.matches(".*,+.*")) {
            String[] temp = gene.split(",");
            gene = temp[1];
        }

        else if (gene.equals("")){
            gene="No gene data";
        }

        return gene;
    }

//    if multiple Integer values exits sum else return
    private Integer addAllIntegerValues (String publication){
        Integer pubCount = 0;

        if (publication.matches(".*,+.*")) {
            String[] arr = publication.split(",");
            for (String s : arr) {
                Integer value = Integer.valueOf(s.trim());
                pubCount = value + pubCount;
            }

        } else if (!(publication.matches(".*\\d+.*"))) {
            pubCount = 0;

        }else {
            pubCount = Integer.valueOf(publication);
        }

        return pubCount;
    }

    private DefaultCategoryDataset getTop5(Map< Integer,String> map,DefaultCategoryDataset dataset){

        int i = 0;
        for (Integer key : map.keySet()) {
            if (i<5){
                dataset.addValue(key, "Genes", (map.get(key)));
                i= i+1;
            } else {}
        }

        return dataset;
    }

    //this function makes barchart from dataset
    private JFreeChart createBarChart(String title, String xaxisLabel, String yaxisLabel, DefaultCategoryDataset dataset){
        JFreeChart chart = ChartFactory.createBarChart(
                title, xaxisLabel, yaxisLabel, dataset,
                PlotOrientation.VERTICAL, false, false, false);


        BarRenderer renderer = new BarRenderer();
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE12, TextAnchor.TOP_CENTER));

        renderer.setBaseItemLabelsVisible(true);
        chart.getCategoryPlot().setRenderer(renderer);

        CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();
        categoryPlot.getDomainAxis().setMaximumCategoryLabelLines(3);
        chart.setBackgroundPaint(null);

        return chart;
    }

    // this function helps to redraw the charts if filters are changed, called from mutationlistframefilterpanel
    public void updateCharts(){
        charts.clear();
        createCharts();
    }
}

