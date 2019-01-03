package dnaQ.GUI.tables;

import dnaQ.Models.Sample;

import java.util.ArrayList;

public class CommonTableModel extends CommonTable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Sample> samples;
    private ArrayList<CommonTableModelColumn> columns;

    public CommonTableModel(ArrayList<Sample> samples){
        this.samples = samples;
    }

}


