package dnaQ.Models;

public class Report {

    public String report;
    public String [] disease;
    public String [] gene;

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

}
