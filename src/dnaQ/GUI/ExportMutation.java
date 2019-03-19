package dnaQ.GUI;

import dnaQ.Models.MutationList;

import javax.swing.*;
import java.io.*;

public class ExportMutation {

    private MutationListFrame parent;

    private MutationList mutationList;

    private String outputFile;

    public ExportMutation(MutationListFrame parent, MutationList mutationList){
        this.parent = parent;
        this.mutationList = mutationList;

        outputFile = "/home/ojaswee/Downloads/your_mutation.txt";

        exportFile();
    }


    private void exportFile(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            String []columnHeader = {"chr","pos","ref","alt",
                    "pop_freq_id","global_freq","american_freq", "asian_freq","afr_freq","eur_freq",
                    "cancer_id","cancer_count",
                    "clinical_id", "clinical_disease", "clinical_significance",
                    "gene", "biology_disease","publication_count"};

            for (String colHead: columnHeader)
                bw.write(colHead + "\t");
            bw.write("\n");

            for (int i = 0; i<mutationList.getMutations().size(); i++) {
                bw.write(String.valueOf(mutationList.getMutations().get(i).getChr()));
                bw.write(String.valueOf("\t" +mutationList.getMutations().get(i).getPos()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getRef()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getAlt()));

                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getFreqid()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getGlobalFreq()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getAmericanFreq()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getAsianFreq()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getAfrFreq()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getEurFreq()));

                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getCancerid()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getCancerCount()));

                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getClinicalid()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getClinicalDisease()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getSignficance()));

                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getGene()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getBiologyDisease()));
                bw.write(String.valueOf("\t"+mutationList.getMutations().get(i).getPublicationCount()));

                bw.write(String.valueOf("\n"));
            }

            bw.close();
            JOptionPane.showMessageDialog(null, "Downloaded on " + outputFile);

        } catch (IOException e) {
                e.printStackTrace();
        }

    }
}
