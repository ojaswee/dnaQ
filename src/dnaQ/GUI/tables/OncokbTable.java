package dnaQ.GUI.tables;

import dnaQ.GUI.SampleListFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class OncokbTable extends CommonTable{

    private static final long serialVersionUID = 1L;

    protected SampleListFrame parent;

    protected OncokbTableModel oncokbTableModel;


    public OncokbTable (SampleListFrame parent, OncokbTableModel oncokbTableModel){
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

