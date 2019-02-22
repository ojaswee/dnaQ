package dnaQ.Models;

import javax.swing.*;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class FilterList {

    private ArrayList<MutationFilter> mutationFilters;

    public FilterList() {
        this.mutationFilters = new ArrayList<MutationFilter>();
    }

    private void addFilter(MutationFilter mutationFilter) {
        mutationFilters.add(mutationFilter);
    }

    public void filterSamples(ArrayList<Mutation> mutations, ArrayList<Mutation> filtered_mutations){

        ArrayList<Mutation> allsamples = new ArrayList<Mutation>(mutations.size()+ filtered_mutations.size());
        allsamples.addAll(mutations);
        allsamples.addAll(filtered_mutations);

        ArrayList<Mutation> tempfiltersamples = new ArrayList<Mutation>();

        for(int i=0;i<allsamples.size();i++){

            Mutation currentsample = allsamples.get(i);

            for (MutationFilter f: mutationFilters){

                if (f.exclude(currentsample)){

                    if (! tempfiltersamples.contains(currentsample)) {
                        tempfiltersamples.add(currentsample);
                    }
                }

            }

        }

        allsamples.removeAll(tempfiltersamples);
        mutations.clear();
        mutations.addAll(allsamples);
        filtered_mutations.clear();
        filtered_mutations.addAll(tempfiltersamples);

    }

    public void addCancerIDFilter(JCheckBox cancerIDFilter) {

        addFilter(new CosmicIDMutationFilter(cancerIDFilter));
    }

    public void addClinicalIDFilter(JCheckBox clinvarIDCheckBox) {

        addFilter(new ClinvarIDMutationFilter(clinvarIDCheckBox));
    }

    public void addPopulationFreqIDFilter(JCheckBox g1000IDCheckbox) {

        addFilter(new G1000IDMutationFilter(g1000IDCheckbox));
    }

    public void addGlobalFreqFilter(JCheckBox globalFreqCheckbox,JTextField populationFreqMaxTextField){
        addFilter(new GlobalFrequencyAndMaxFilter(globalFreqCheckbox,populationFreqMaxTextField));
    }

    public void addAmericanFreqFilter(JCheckBox americanFreqCheckbox,JTextField populationFreqMaxTextField){
        addFilter(new AmericanFrequencyAndMaxFilter(americanFreqCheckbox,populationFreqMaxTextField));
    }

    public void addAsianFreqFilter(JCheckBox asianFreqCheckbox,JTextField populationFreqMaxTextField){
        addFilter(new AsianFrequencyAndMaxFilter(asianFreqCheckbox,populationFreqMaxTextField));
    }

    public void addAfrFreqFilter(JCheckBox afrFreqCheckbox,JTextField populationFreqMaxTextField){
        addFilter(new AfrFrequencyAndMaxFilter(afrFreqCheckbox,populationFreqMaxTextField));
    }

    public void addEurFreqFilter(JCheckBox eurFreqCheckbox,JTextField populationFreqMaxTextField){
        addFilter(new EurFrequencyAndMaxFilter(eurFreqCheckbox,populationFreqMaxTextField));
    }

    public void addCancerCountFilter(JTextField cancerTextField){
        addFilter(new CancerCountFilter(cancerTextField));
    }

    public void addBenignFilter(JCheckBox benignCheckbox){
        addFilter(new BenignFilter(benignCheckbox));
    }

    public void addLikelyCancerFilter(JCheckBox likelyCheckbox){
        addFilter(new LikelyCancerFilter(likelyCheckbox));
    }

    public void addGeneFilter(JCheckBox geneCheckbox){
        addFilter(new GeneFilter (geneCheckbox));
    }

    public void addDiseaseFilter(JCheckBox diseaseCheckbox){
        addFilter(new DiseaseFilter (diseaseCheckbox));
    }

    public void addPublicationFilter(JTextField pubCountTextField){
        addFilter(new PublicationFilter (pubCountTextField));
    }

}

class MutationFilter {

    MutationFilter(){}

    public boolean exclude(Mutation mutation){return true;}
}

class CosmicIDMutationFilter extends MutationFilter {

    private JCheckBox cancerIDFilter;

    CosmicIDMutationFilter(JCheckBox cancerIDFilter) {
        this.cancerIDFilter = cancerIDFilter;
    }

    @Override
    public boolean exclude(Mutation mutation) {

        if (cancerIDFilter.isSelected()) {
            if (mutation.getCancerid().equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

class ClinvarIDMutationFilter extends MutationFilter {

        private JCheckBox clinvarIDCheckBox;

        ClinvarIDMutationFilter(JCheckBox clinvarIDCheckBox) {
            this.clinvarIDCheckBox = clinvarIDCheckBox;

        }

        @Override
        public boolean exclude(Mutation mutation) {

            if (clinvarIDCheckBox.isSelected()) {
                if (mutation.getClinicalid().equals("")) {
                    return true;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        }
}

class G1000IDMutationFilter extends MutationFilter {

    private JCheckBox g1000IDCheckbox;

    G1000IDMutationFilter(JCheckBox g1000IDCheckbox) {
        this.g1000IDCheckbox = g1000IDCheckbox;
    }

    @Override
    public boolean exclude(Mutation mutation) {

        if (g1000IDCheckbox.isSelected()) {
            if (mutation.getFreqid().equals("")) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}

class GlobalFrequencyAndMaxFilter extends MutationFilter {

    private JCheckBox globalFreqCheckbox;
    private JTextField populationFreqMaxTextField;

    GlobalFrequencyAndMaxFilter(JCheckBox globalFreqCheckbox,JTextField populationFreqMaxTextField) {
        this.globalFreqCheckbox = globalFreqCheckbox;
        this.populationFreqMaxTextField = populationFreqMaxTextField;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (globalFreqCheckbox.isSelected()) {
            if(!(mutation.getGlobalFreq().matches(".*\\d+.*")) ){
                return true;
            }
            else {
                Double currentValue = Double.valueOf(mutation.getGlobalFreq());
                Integer maxValue = Integer.valueOf(populationFreqMaxTextField.getText());
                if (currentValue>=maxValue){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else return false;
    }
}

class AmericanFrequencyAndMaxFilter extends MutationFilter {

    private JCheckBox americanFreqCheckbox;
    private JTextField populationFreqMaxTextField;

    AmericanFrequencyAndMaxFilter(JCheckBox americanFreqCheckbox,JTextField populationFreqMaxTextField) {
        this.americanFreqCheckbox = americanFreqCheckbox;
        this.populationFreqMaxTextField = populationFreqMaxTextField;

    }
    @Override
    public boolean exclude(Mutation mutation) {
        if (americanFreqCheckbox.isSelected()) {
            if(!(mutation.getAmericanFreq().matches(".*\\d+.*")) ){
                return true;
            }
            else {
                Double currentValue = Double.valueOf(mutation.getAmericanFreq());
                Integer maxValue = Integer.valueOf(populationFreqMaxTextField.getText());
                if (currentValue>=maxValue){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else return false;
    }
}

class AsianFrequencyAndMaxFilter extends MutationFilter {

    private JCheckBox asianFreqCheckbox;
    private JTextField populationFreqMaxTextField;

    AsianFrequencyAndMaxFilter(JCheckBox asianFreqCheckbox,JTextField populationFreqMaxTextField) {
        this.asianFreqCheckbox = asianFreqCheckbox;
        this.populationFreqMaxTextField = populationFreqMaxTextField;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (asianFreqCheckbox.isSelected()) {
            if(! (mutation.getAsianFreq().matches(".*\\d+.*"))) {
                return true;
            }
            else {
                    Double currentValue = Double.valueOf(mutation.getAsianFreq());
                    Integer maxValue = Integer.valueOf(populationFreqMaxTextField.getText());
                    if (currentValue>=maxValue){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        else return false;
    }
}

class AfrFrequencyAndMaxFilter extends MutationFilter {

    private JCheckBox afrFreqCheckbox;
    private JTextField populationFreqMaxTextField;

    AfrFrequencyAndMaxFilter(JCheckBox afrFreqCheckbox,JTextField populationFreqMaxTextField) {

        this.afrFreqCheckbox = afrFreqCheckbox;
        this.populationFreqMaxTextField = populationFreqMaxTextField;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (afrFreqCheckbox.isSelected()) {
            if(! (mutation.getAfrFreq().matches(".*\\d+.*"))) {
                return true;
            }else {
                    Double currentValue = Double.valueOf(mutation.getAfrFreq());
                    Integer maxValue = Integer.valueOf(populationFreqMaxTextField.getText());
                    if (currentValue>=maxValue){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
        else return false;
        }

}

class EurFrequencyAndMaxFilter extends MutationFilter {

    private JCheckBox eurFreqCheckbox;
    private JTextField populationFreqMaxTextField;

    EurFrequencyAndMaxFilter(JCheckBox eurFreqCheckbox,JTextField populationFreqMaxTextField) {

        this.eurFreqCheckbox = eurFreqCheckbox;
        this.populationFreqMaxTextField = populationFreqMaxTextField;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (eurFreqCheckbox.isSelected()) {
            if(!(mutation.getEurFreq().matches(".*\\d+.*"))) {
                return true;
            }else {
                Double currentValue = Double.valueOf(mutation.getEurFreq());
                Integer maxValue = Integer.valueOf(populationFreqMaxTextField.getText());
                if (currentValue>=maxValue){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else return false;
    }

}

class CancerCountFilter extends MutationFilter {

    private JTextField cancerCountTextField;

    CancerCountFilter(JTextField cancerCountTextField) {
        this.cancerCountTextField = cancerCountTextField;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (mutation.getCancerCount().matches(".*\\d+.*")) {
//            if(! (mutation.getCancerCount().matches(".*\\d+.*"))){
                return true;
//            } else {
//                return false;
          //  }
        } else {
            return false;
        }
    }
}

class BenignFilter extends MutationFilter {

    private JCheckBox benignCheckbox;

    BenignFilter(JCheckBox benignCheckbox) {
        this.benignCheckbox = benignCheckbox;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (benignCheckbox.isSelected()) {
            if(!(mutation.getSignficance().matches(".*enign+.*"))){
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

}

class LikelyCancerFilter extends MutationFilter {

    private JCheckBox likelyCheckbox;

    LikelyCancerFilter(JCheckBox likelyCheckbox) {
        this.likelyCheckbox = likelyCheckbox;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (likelyCheckbox.isSelected()) {
            if(mutation.getSignficance().matches(".*enign+.*") || mutation.getSignficance().equals("")) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

}

class GeneFilter extends MutationFilter {

    private JCheckBox geneCheckbox;

    GeneFilter(JCheckBox geneCheckbox) {
        this.geneCheckbox = geneCheckbox;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (geneCheckbox.isSelected()) {
            if (mutation.getGene().equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

class PublicationFilter extends MutationFilter {

    private JTextField pubCountTextField;

    PublicationFilter(JTextField pubCountTextField) {
        this.pubCountTextField = pubCountTextField;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (mutation.getPublicationCount().matches(".*\\d+.*")) {
                return true;
        } else {
            return false;
        }
    }
}

class DiseaseFilter extends MutationFilter {

    private JCheckBox diseseCheckbox;

    DiseaseFilter(JCheckBox diseseCheckbox) {
        this.diseseCheckbox = diseseCheckbox;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (diseseCheckbox.isSelected()) {
            if (mutation.getBiologyDisease().equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
