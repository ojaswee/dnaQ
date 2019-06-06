package dnaQ.Models;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

public class DiseaseAndGeneDecending {

    private MutationList mutationList;
    private ArrayList<Mutation> mutations;
    public static String [] top2Disease  = new String [2];
    public static String [] top2Gene = new String [2];

    public DiseaseAndGeneDecending(MutationList mutationList){

        this.mutationList = mutationList;
        mutations = mutationList.getMutations();

        disease();
        gene();
    }

    public Map<String, Integer> disease(){
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
        top2Disease = displayTop2(disease_gene_dic, top2Disease);


    return disease_gene_dic;
    }

    public Map<String, Integer> gene(){
        Map<String,Integer> gene_dic = new TreeMap<>();
        Integer count = 0;

        for (int i = 0; i< mutations.size(); i++) {

            String current_gene = mutations.get(i).getGene().trim().split(",")[0];

            if(gene_dic.containsKey(current_gene)){
                count = gene_dic.get(current_gene);
                gene_dic.replace(current_gene,count, count+1);

            } else{
                gene_dic.put(current_gene,1);
            }

            }

        top2Gene = displayTop2(gene_dic, top2Gene);


        return gene_dic;
    }

    //first sorts the treemap using sortByValue then inserts top 5 values in dataset
    private String [] displayTop2(Map< String,Integer> map, String [] dataset){

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
            String d = key.toString();
            if (i<2){
                dataset[i] = d;
//                System.out.println(d);
                i= i+1;
            } else {}
        }

        return dataset;
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

    public static String [] getTop2Disease(){
        return top2Disease;
    }

    public static String [] getTop2Genes(){
        return top2Gene;
    }
}
