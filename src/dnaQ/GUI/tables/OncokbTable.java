package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;

public class OncokbTable extends CommonTable{

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected OncokbTableModel oncokbTableModel;


    public OncokbTable (MutationListFrame parent, OncokbTableModel oncokbTableModel){
        super();
        this.parent = parent;
        this.oncokbTableModel = oncokbTableModel;
        setModel(oncokbTableModel);

        resizeColumnWidths();
        constructRenderers();

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}

