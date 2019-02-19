package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;


public class CancerTable extends CommonTable {

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected CancerTableModel cancerTableModel;


    public CancerTable(MutationListFrame parent, CancerTableModel cancerTableModel){
        super();
        this.parent = parent;
        this.cancerTableModel = cancerTableModel;
        setModel(cancerTableModel);

        resizeColumnWidths();
        constructRenderers();

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


}





