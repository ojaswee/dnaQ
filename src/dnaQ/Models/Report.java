package dnaQ.Models;

public class Report {

    public String report;
    public String disease1;
    public String disease2;
    public String gene1;
    public String gene2;

    public Report(String report) {

        this.report = report;
    }

    public void getTopValues (String disease1, String disease2, String gene1, String gene2){
        this.disease1 = disease1;
        this.disease2 = disease2;
        this.gene1 = gene1;
        this.gene2 = gene2;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

}
