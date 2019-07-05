package dnaQ.GUI.tables;

import dnaQ.Connections.WebConnections;
import dnaQ.GUI.MutationListFrame;
import dnaQ.Models.Mutation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectionTable extends CommonTable{

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected SelectionTableModel selectionTableModel;


    public SelectionTable(MutationListFrame parent, SelectionTableModel selectionTableModel){
        super();
        this.parent = parent;
        this.selectionTableModel = selectionTableModel;
        setModel(selectionTableModel);

        resizeColumnWidths();
        constructRenderers();

        resizeColumnWidths();
        constructRenderers();

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


}



