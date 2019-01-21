package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;


public class CosmicTable extends CommonTable {

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected CosmicTableModel cosmicTableModel;


    public CosmicTable (MutationListFrame parent, CosmicTableModel cosmicTableModel){
        super();
        this.parent = parent;
        this.cosmicTableModel = cosmicTableModel;
        setModel(cosmicTableModel);

        resizeColumnWidths();
        constructRenderers();

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


}





