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

        Map<Integer, String> gene_pubCount_dic = new TreeMap<>(Comparator.reverseOrder());

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
            if(!(gene_pubCount_dic.containsValue(gene))){
                gene_pubCount_dic.put(currentPub+1, gene);
            }
        }

        //insert only top 5 values into dataset
        dataset = getTop5(gene_pubCount_dic, dataset);

        JFreeChart chart = createBarChart( "Scientific Evidence", "Biology Genes",
                "Publications", dataset);

        return chart;
    }

    private JFreeChart diseaseEvidencePlot() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String,Integer> disease_gene_dic = new TreeMap<>();

        for (int i = 0; i< mutations.size(); i++) {


            String[] disease_list = mutations.get(i).getClinicalDisease().trim().split(Pattern.quote("|"));

            if (disease_list[0].equals("")) {
                continue;
            }

            String current_gene = mutations.get(i).getGene().trim().split(",")[0];

            for (String disease : disease_list) {

                if (disease.matches(".*not_+.*")) {
                    continue;
                }

                String current_disease_gene_pair = disease + "/" + current_gene;
                int count = disease_gene_dic.containsKey(current_disease_gene_pair) ? disease_gene_dic.get(current_disease_gene_pair) : 0;
                disease_gene_dic.put(current_disease_gene_pair, count + 1);

            }
        }


        System.out.println(disease_gene_dic);
        dataset = putTop5(disease_gene_dic, dataset);
//
        JFreeChart chart = createBarChart("Disease Evidence","Clinical Disease / Biology Genes"
                ,"Count",dataset);

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
            gene = temp[0];
        }

        else if (gene.equals("")){
            gene="No gene data";
        }

        return gene.trim();
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

    //to sort the treemap by value uses the class ValueComparator
    public static Map sortByValue(Map unsortedMap) {
        Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
        sortedMap.putAll(unsortedMap);
        return sortedMap;
    }

    //first sorts the treemap using sortByValue then inserts top 5 values in dataset
    private DefaultCategoryDataset putTop5(Map< String,Integer> map,DefaultCategoryDataset dataset){
        Map sortedMap = sortByValue(map);
        int i = 0;

        for (Object key : sortedMap.keySet()) {
            String disease = key.toString();
            if (i<5){
                dataset.addValue((map.get(disease)), "Genes", disease);
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

class ValueComparator implements Comparator {
    Map map;

    public ValueComparator(Map map) {
        this.map = map;
    }

    public int compare(Object keyA, Object keyB) {
        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);
        return valueB.compareTo(valueA);
    }
}
