package dnaQ.GUI.GUI_Models;

import dnaQ.Objects.Sample;

import javax.swing.*;
import java.util.ArrayList;

public class FilterList {

    private ArrayList<SampleFilter> sampleFilters;

    public FilterList() {
        this.sampleFilters = new ArrayList<SampleFilter>();
    }

    private void addFilter(SampleFilter sampleFilter) {
        sampleFilters.add(sampleFilter);
    }

    public void filterSamples(ArrayList<Sample> samples,ArrayList<Sample> filtered_samples){

        System.out.println(samples.size());
        System.out.println(filtered_samples.size());

        ArrayList<Sample> allsamples = new ArrayList<Sample>(samples.size()+filtered_samples.size());
        allsamples.addAll(samples);
        allsamples.addAll(filtered_samples);

        ArrayList<Sample> tempfiltersamples = new ArrayList<Sample>();

        for(int i=0;i<allsamples.size();i++){

            Sample currentsample = allsamples.get(i);

            for (SampleFilter f:sampleFilters){

                if (f.exclude(currentsample)){

                    if (! tempfiltersamples.contains(currentsample)) {
                        tempfiltersamples.add(currentsample);
                    }
                }

            }

        }

        allsamples.removeAll(tempfiltersamples);
        samples.clear();
        samples.addAll(allsamples);
        filtered_samples.clear();
        filtered_samples.addAll(tempfiltersamples);

        System.out.println(samples.size());
        System.out.println(filtered_samples.size());

    }

    public void addCosmicIDFilter(JCheckBox cosmicIDCheckBox) {

        addFilter(new CosmicIDSampleFilter(cosmicIDCheckBox));
    }

    public void addClinvarIDFilter(JCheckBox clinvarIDCheckBox) {

        addFilter(new ClinvarIDSampleFilter(clinvarIDCheckBox));
    }

}



class SampleFilter {

    SampleFilter(){}

    public boolean exclude(Sample sample){return true;}
}

class CosmicIDSampleFilter extends SampleFilter {

    private JCheckBox cosmicIDCheckBox;

    CosmicIDSampleFilter(JCheckBox cosmicIDCheckBox) {
        this.cosmicIDCheckBox = cosmicIDCheckBox;

    }

    @Override
    public boolean exclude(Sample sample) {

        if (cosmicIDCheckBox.isSelected()) {
            if (sample.getCosmicid().equals("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

class ClinvarIDSampleFilter extends SampleFilter {

        private JCheckBox clinvarIDCheckBox;

        ClinvarIDSampleFilter(JCheckBox clinvarIDCheckBox) {
            this.clinvarIDCheckBox = clinvarIDCheckBox;

        }

        @Override
        public boolean exclude(Sample sample) {

            if (clinvarIDCheckBox.isSelected()) {
                if (sample.getClinvarid().equals("")) {
                    return true;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        }

}

