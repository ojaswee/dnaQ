package dnaQ.Models;

import javax.swing.*;
import java.util.ArrayList;

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

    public void addCosmicIDFilter(JCheckBox cosmicIDCheckBox) {

        addFilter(new CosmicIDMutationFilter(cosmicIDCheckBox));
    }

    public void addClinvarIDFilter(JCheckBox clinvarIDCheckBox) {

        addFilter(new ClinvarIDMutationFilter(clinvarIDCheckBox));
    }

    public void addG1000IDFilter(JCheckBox g1000IDCheckbox) {

        addFilter(new G1000IDMutationFilter(g1000IDCheckbox));
    }

    public void addGlobalFreqFilter(JCheckBox globalFreqCheckbox) {

        addFilter(new GlobalFreqMutationFilter(globalFreqCheckbox));
    }

}



class MutationFilter {

    MutationFilter(){}

    public boolean exclude(Mutation mutation){return true;}
}

class CosmicIDMutationFilter extends MutationFilter {

    private JCheckBox cosmicIDCheckBox;

    CosmicIDMutationFilter(JCheckBox cosmicIDCheckBox) {
        this.cosmicIDCheckBox = cosmicIDCheckBox;

    }

    @Override
    public boolean exclude(Mutation mutation) {

        if (cosmicIDCheckBox.isSelected()) {
            if (mutation.getCosmicid().equals("")) {
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
                if (mutation.getClinvarid().equals("")) {
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
            if (mutation.getG1000id().equals("")) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}

class GlobalFreqMutationFilter extends MutationFilter {

    private JCheckBox globalFreqCheckbox;

    GlobalFreqMutationFilter(JCheckBox globalFreqCheckbox) {
        this.globalFreqCheckbox = globalFreqCheckbox;

    }

    @Override
    public boolean exclude(Mutation mutation) {

        if (globalFreqCheckbox.isSelected()) {
            if (mutation.getAltGlobalFreq().equals("")) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }
}

