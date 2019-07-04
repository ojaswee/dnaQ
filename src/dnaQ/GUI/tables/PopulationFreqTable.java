package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;

public class PopulationFreqTable extends CommonTable {

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected PopulationFreqTableModel populationFreqTableModel;


    public PopulationFreqTable(MutationListFrame parent, PopulationFreqTableModel populationFreqTableModel){
        super();
        this.parent = parent;
        this.populationFreqTableModel = populationFreqTableModel;
        setModel(populationFreqTableModel);

        resizeColumnWidths();
        constructRenderers();

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
