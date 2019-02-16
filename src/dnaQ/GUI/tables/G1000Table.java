package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;

public class G1000Table extends CommonTable {

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected G1000TableModel g1000TableModel;


    public G1000Table(MutationListFrame parent, G1000TableModel g1000TableModel){
        super();
        this.parent = parent;
        this.g1000TableModel = g1000TableModel;
        setModel(g1000TableModel);

        resizeColumnWidths();
        constructRenderers();

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
