package dnaQ.Models;

import dnaQ.GUI.GUICommonTools;

import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Report {

    private String reportName;
    private String reportIdentifier;
    private String reportOriginator;
    private MutationList reportMutationList;
    private ArrayList<String> inputList;



    public Report(String reportName, MutationList mutationList) {
        this.reportName = reportName;
        this.reportMutationList = mutationList;
        this.inputList = new ArrayList<>();
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public ArrayList<String> getInputList() {
        return inputList;
    }

    public void setInputList(ArrayList<String> inputList) {
        this.inputList = inputList;
    }

    public String getReportIdentifier() {
        return reportIdentifier;
    }

    public void setReportIdentifier(String reportIdentifier) {
        this.reportIdentifier = reportIdentifier;
    }

    public String getReportOriginator() {
        return reportOriginator;
    }

    public void setReportOriginator(String reportOriginator) {
        this.reportOriginator = reportOriginator;
    }

    public void generateInputList(boolean basic){
        // if user doesn't select any record
        if(basic){

            for(Mutation mutation:reportMutationList.getMutations()){
                if (!mutation.getCancerid().equals("")){
                    if(!mutation.gene.equals("")){
                        String gene = mutation.gene.split(",")[0];
                        if (!inputList.contains("gene,"+gene)) {
                            inputList.add("gene,"+gene);
                        }
                    }
                    if(!mutation.clinicalDisease.equals("")){
                        String condition = mutation.clinicalDisease.split("\\|")[0];
                        if (!inputList.contains("condition,"+condition))
                        inputList.add("condition,"+condition);
                    }

                }
            }
        }else{ //if user selects some records

            for(Mutation mutation:reportMutationList.getMutations()){
                if (mutation.isSelected()){

                    if(!mutation.gene.equals("")){
                        String gene = mutation.gene.split(",")[0];
                        if (!inputList.contains("gene,"+gene)) {
                            inputList.add("gene,"+gene);
                        }
                    }
                    if(!mutation.clinicalDisease.equals("")){
                        String condition = mutation.getClinicalDisease().split("\\|")[0];
                        if (!inputList.contains("condition,"+condition))
                            inputList.add("condition,"+condition);
                    }

                }
            }

        }


    }
    //create condition_gene_csv file locally

    public String createReportFileLocally() {


        String filelocation = "/home/" + GUICommonTools.getUsernameFromOS()
                + "/dnaq/report_upload/"+this.reportOriginator+"_"+this.reportIdentifier;

        try {
            FileWriter fileWriter = new FileWriter(filelocation);
            for (String line : inputList) {
                fileWriter.write(line);
                fileWriter.write(System.lineSeparator());
            }
            fileWriter.close();


        } catch (Exception e) {
            System.out.println("Cannot write file locally");
        }

        return filelocation;

    }


}
