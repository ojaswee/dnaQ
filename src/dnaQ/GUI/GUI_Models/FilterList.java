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

        System.out.println("checked");



    }

    public void addCosmicIDFilter(JCheckBox cosmicIDCheckBox) {
        addFilter(new CosmicIDSampleFilter(cosmicIDCheckBox));
    }

}

interface SampleFilter {
    public boolean exclude(Sample sample);
}

class CosmicIDSampleFilter implements SampleFilter {

    private JCheckBox cosmicIDCheckBox;

    public CosmicIDSampleFilter(JCheckBox cosmicIDCheckBox) {
        this.cosmicIDCheckBox = cosmicIDCheckBox;
    }

    @Override
    public boolean exclude(Sample sample) {
        return true;
    }

}

