package dnaQ.Models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Report {

    public String report;
    public String [] disease;
    public String [] gene;

    public String localConditionGeneFolder= "/home/sishir/dnaq/condition_gene_files/";

    public Report(String report) {
        this.report = report;

    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setDisease(){
        disease = DiseaseAndGeneDecending.getTop2Disease();
        cleanValuesAndInsert(disease);
    }

    public void setGenes(){
        gene = DiseaseAndGeneDecending.getTop2Genes();
        cleanValuesAndInsert(gene);
    }

    private void cleanValuesAndInsert(String [] array){

        for (int i = 0; i< array.length; i++) {
            if (array[i].contains("/")) {
                String current = array[i].trim().split("/")[0];
                array[i] = current;
            }
            if (array[i].contains("_")){
                String current = array[i].replace("_","-");
                array[i] = current;
            }
        }
    }

    public String writeLocalConditionFile(TestQueue tq){
        String conditionFile = localConditionGeneFolder+tq.userid+"_"+tq.testid+"_RUN"+tq.run+"condition_gene.csv";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(conditionFile));
            for (int i = 0; i<2; i++){
                bw.write("condition,");
                bw.write(disease[i]);
                bw.write("\n");
            }

            for (int i = 0; i<2; i++){
                bw.write("gene,");
                bw.write(gene[i]);
                bw.write("\n");
            }
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return conditionFile;
    }

}
