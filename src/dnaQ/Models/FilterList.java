package dnaQ.Models;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    public void addPopulationFreqIDFilter(JCheckBox g1000IDCheckbox) {
        addFilter(new PopFreqIDFilter(g1000IDCheckbox));
    }
    public void addCancerIDFilter(JCheckBox cancerIDFilter) {
        addFilter(new CosmicIDMutationFilter(cancerIDFilter));
    }

    public void addClinicalIDFilter(JCheckBox clinvarIDCheckBox) {
        addFilter(new ClinvarIDMutationFilter(clinvarIDCheckBox));
    }

    public void addGeneFilter(JCheckBox geneCheckbox){
        addFilter(new GeneFilter (geneCheckbox));
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

    public void addSpecifiedFilter(JCheckBox specifiedCheckbox){
        addFilter(new SpecifiedFilter(specifiedCheckbox));
    }

    public void addNonSpecifiedFilter(JCheckBox nonSpecifiedCheckbox){
        addFilter(new NonSpecifiedFilter(nonSpecifiedCheckbox));
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

class PopFreqIDFilter extends MutationFilter {

    private JCheckBox g1000IDCheckbox;

    PopFreqIDFilter(JCheckBox g1000IDCheckbox) {
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
        }else{
            return false;
        }
    }
}

class GetValueFromPopFreqTextField {
    private String input;
    private Double currentValue;

    GetValueFromPopFreqTextField(String populationFreqMaxTextField){
        this.input=populationFreqMaxTextField;
        currentValue = 100.0;
    }

    public Double getPopFreqValue (){
        if(input.matches(".*\\d+.*")){
            currentValue = Double.valueOf(input.trim());
            return currentValue;
        }
        return currentValue;
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
            else if (mutation.getGlobalFreq().matches(".*\\d+.*")){
                Double currentValue = Double.valueOf(mutation.getGlobalFreq());

                Double maxValue = new GetValueFromPopFreqTextField(populationFreqMaxTextField.getText()).getPopFreqValue();
                if (currentValue>=maxValue){
                    return true;
                }
            }

        }
        return false;
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
                Double maxValue = new GetValueFromPopFreqTextField(populationFreqMaxTextField.getText()).getPopFreqValue();
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
                Double maxValue = new GetValueFromPopFreqTextField(populationFreqMaxTextField.getText()).getPopFreqValue();
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
                    Double maxValue = new GetValueFromPopFreqTextField(populationFreqMaxTextField.getText()).getPopFreqValue();
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
                Double maxValue = new GetValueFromPopFreqTextField(populationFreqMaxTextField.getText()).getPopFreqValue();
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
        String cancerCountValue = cancerCountTextField.getText();
//        if textbox has digit
//        cancerCountTextField.getPropertyChangeListeners()
        if((cancerCountValue.matches(".*\\d+.*"))) {
            if (!(mutation.getCancerCount().matches(".*\\d+.*"))) {
                return true;
            }
            else {
                Integer cancer = Integer.valueOf(cancerCountValue);
                if (mutation.getCancerCount().matches(".*,+.*")) {
                    String[] cancerCount = mutation.getCancerCount().trim().split("\\s*,\\s*");
                    int array[] = Arrays.stream(cancerCount).mapToInt(Integer::parseInt).toArray();
                    int maxValue = Arrays.stream(array).max().getAsInt();

                    if (maxValue < cancer)
                        return true;
                } else if (!(mutation.getCancerCount().matches(".*,+.*"))) {
                    Integer maxValue = Integer.valueOf(mutation.getCancerCount());
                    if (maxValue < cancer)
                        return true;
                }
                return false;
            }
        }
        return false;
    }
}

class SpecifiedFilter extends MutationFilter {

    private JCheckBox specifiedCheckbox;
    private String specified ="";

    SpecifiedFilter(JCheckBox specifiedCheckbox) {
        this.specifiedCheckbox = specifiedCheckbox;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (specifiedCheckbox.isSelected()) {
            specified = mutation.getClinicalDisease().trim();
            if (specified.equals("not_specified|not_provided")){
                return true;
            }
            else if (specified.matches("not_specified")){
                return true;
            }
            else if (specified.matches("")){
                return true;
            }
        }
        return false;
    }
}

class NonSpecifiedFilter extends MutationFilter {

    private JCheckBox nonSpecifiedCheckbox;
    private String nonSpecified ="";

    NonSpecifiedFilter(JCheckBox nonSpecifiedCheckbox) {
        this.nonSpecifiedCheckbox = nonSpecifiedCheckbox;
    }

    @Override
    public boolean exclude(Mutation mutation) {
        if (nonSpecifiedCheckbox.isSelected()) {
            nonSpecified = mutation.getClinicalDisease().trim();
            if(nonSpecified.equals("not_specified")){
                return false;
            } else if (nonSpecified.equals("not_specified|not_provided")){
                return false;
            }
            else if (nonSpecified.matches("")){
                return true;
            }
            else return true;
       }
        return false;
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
            if (mutation.getBiologyDisease().equals("") || mutation.getBiologyDisease().trim().equals(",")) {
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
        String pubCount = pubCountTextField.getText();
        //if textbox has digit
        if (pubCount.matches(".*\\d+.*")) {
            String currentPubCount = mutation.getPublicationCount();
            //current row does not have digit exclude it
            if (!(currentPubCount.matches(".*\\d+.*"))) {
                return true;
            }
            else {
                Integer count = Integer.valueOf(pubCount);
                if (currentPubCount.matches(".*,+.*")) {
                    String[] currentCount = mutation.getPublicationCount().trim().split("\\s*,\\s*");
                    int array[] = Arrays.stream(currentCount).mapToInt(Integer::parseInt).toArray();
                    int maxValue = Arrays.stream(array).max().getAsInt();

                    if (maxValue < count) {
                        return true;
                    }
                }

                else if (!(currentPubCount.matches(".*,+.*"))) {
                        Integer maxValue = Integer.valueOf(currentPubCount);
                        if (maxValue <count) return true;
                    }
                }
            }
            return false;
    }
}
