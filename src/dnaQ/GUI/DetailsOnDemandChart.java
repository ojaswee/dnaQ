package dnaQ.GUI;

import dnaQ.Models.MutationList;
import dnaQ.Models.Mutation;

import javax.swing.*;

import dnaQ.Models.Report;
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

        Map<String,Integer> gene_pubCount_dic = new TreeMap<>();

        for (int i = 0; i< mutations.size(); i++) {

            String current_gene = mutations.get(i).getGene().trim().split(",")[0];

            String pubCount = mutations.get(i).getPublicationCount().trim();

            //check values of pubCount
            Integer current_publication = addAllIntegerValues(pubCount);

            // put if the key is not in dictionary
            if(!(gene_pubCount_dic.containsKey(current_gene))){
                gene_pubCount_dic.put(current_gene,current_publication);
            }
        }

        //insert only top 5 values into dataset
        dataset = displayTop5(gene_pubCount_dic, dataset);

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

//        System.out.println(disease_gene_dic);
        dataset = displayTop5(disease_gene_dic, dataset);

        JFreeChart chart = createBarChart("Disease Evidence","Clinical Disease / Biology Genes"
                ,"Count",dataset);

        return chart;
    }

    private JFreeChart cancerEvidencePlot(){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String,Integer> cancer_count_dic = new TreeMap<>();

        String cancerID= "";
        String cancerCount= "";
        Integer count = 0;

        for (int i = 0; i< mutations.size(); i++) {
            cancerID = mutations.get(i).getCancerid().trim();
            cancerCount = mutations.get(i).getCancerCount();
            String current_gene = mutations.get(i).getGene().trim().split(",")[0];

            if (cancerID.equals("")){
                count = 0;
            }

            //if cancerid is not empty
            else {
                //check values of pubCount
                count = addAllIntegerValues(cancerCount);

                // only put in map if cancerid is present
                if(cancer_count_dic.containsValue(current_gene)) {
                    cancer_count_dic.replace(current_gene,count);

                }else{
                    cancer_count_dic.put(current_gene,count);
                }
            }
        }

        dataset = displayTop5(cancer_count_dic, dataset);

        JFreeChart chart = createBarChart("Cancer Evidence","Biology Genes"
                ,"Cancer Count",dataset);

        return chart;
    }

    private JFreeChart popfreqEvidencePlot() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Map<String,Integer> gene_globalfreq_dic = new TreeMap<>();

        Integer count = 0;

        for (int i = 0; i< mutations.size(); i++) {
            String popfreqID = mutations.get(i).getFreqid().trim();
            String globalfreq = mutations.get(i).getGlobalFreq().trim();
            String current_gene = mutations.get(i).getGene().trim().split(",")[0];

            if (popfreqID.equals("")){
                count = 0;
            }

            //if popfreqID is not empty
            else {
                count = Double.valueOf(globalfreq).intValue();

                // only put in map if popfreqID is not present

                if(!(gene_globalfreq_dic.containsKey(current_gene))) {
                    gene_globalfreq_dic.put(current_gene,count);
                }
            }
        }

        dataset = displayTop5(gene_globalfreq_dic, dataset);

        JFreeChart chart = createBarChart("Population Frequency Evidence","Biology Genes"
                ,"Global frequency",dataset);

        return chart;
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


    //first sorts the treemap using sortByValue then inserts top 5 values in dataset
    private DefaultCategoryDataset displayTop5(Map< String,Integer> map,DefaultCategoryDataset dataset){

        Map sortedMap = sortByValues(map);
        // Get Set of entries
        Set set = sortedMap.entrySet();
        // Get iterator
        Iterator it = set.iterator();
        while(it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
        }

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

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator =
                new Comparator<K>() {
                    public int compare(K k1, K k2) {
                        int compare =
                                map.get(k2).compareTo(map.get(k1));
                        if (compare == 0)
                            return 1;
                        else
                            return compare;
                    }
                };
        Map<K, V> sortedByValues =
                new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }

}



