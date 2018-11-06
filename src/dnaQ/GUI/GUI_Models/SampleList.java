package dnaQ.GUI.GUI_Models;


import dnaQ.Objects.Sample;

import java.util.ArrayList;


public class SampleList {


    private ArrayList<Sample> samples;
    private ArrayList<Sample> filteredSamples;

    public SampleList(ArrayList<Sample> samples) throws Exception {

        this.samples = samples;
    }

    public ArrayList<Sample> getSamples(){
        return this.samples;
    }

    public void filterSamples(FilterList filterList){
        filterList.filterSamples(this.samples,this.filteredSamples);
    }

}
