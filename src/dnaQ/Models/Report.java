package dnaQ.Models;

import dnaQ.GUI.DetailsOnDemandChart;

public class Report {

    public String report;
    public String disease1;
    public String disease2;
    public String gene1;
    public String gene2;

    public Report(String report) {
        this.report = report;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setDisease1(){
        DetailsOnDemandChart.firstDiseaseName();
    }

    public void setDisease2(){
        DetailsOnDemandChart.secondDiseaseName();
    }

    public void setGene1(){
        DetailsOnDemandChart.firstGeneName();
    }

    public void setGene2(){
        DetailsOnDemandChart.secondGeneName();
    }
}
