package dnaQ.Models;

import java.util.ArrayList;

public class MutationList {

    private ArrayList<Mutation> mutations;
    private ArrayList<Mutation> filteredMutations;

    private boolean choice;

    public MutationList(ArrayList<Mutation> mutations) throws Exception {

        this.mutations = mutations;
        this.filteredMutations = new ArrayList<Mutation>();
    }

    public ArrayList<Mutation> getMutations(){
        return this.mutations;
    }

    public void filterSamples(FilterList filterList){

        filterList.filterSamples(this.mutations,this.filteredMutations);
    }

}
