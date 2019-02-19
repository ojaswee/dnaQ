package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;

public class BiologyTable extends CommonTable{

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected BiologyTableModel biologyTableModel;


    public BiologyTable(MutationListFrame parent, BiologyTableModel biologyTableModel){
        super();
        this.parent = parent;
        this.biologyTableModel = biologyTableModel;
        setModel(biologyTableModel);

        resizeColumnWidths();
        constructRenderers();

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}

