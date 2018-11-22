package dnaQ.Models;


import java.util.ArrayList;


public class SampleList {


    private ArrayList<Sample> samples;
    private ArrayList<Sample> filteredSamples;

    private boolean choice;

    public SampleList(ArrayList<Sample> samples) throws Exception {

        this.samples = samples;
        this.filteredSamples = new ArrayList<Sample>();
    }

    public ArrayList<Sample> getSamples(){
        return this.samples;
    }

    public void filterSamples(FilterList filterList){

        filterList.filterSamples(this.samples,this.filteredSamples);
    }

}
