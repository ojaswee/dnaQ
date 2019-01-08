package dnaQ.GUI.tables;

import dnaQ.GUI.SampleListFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class G1000Table extends CommonTable {

    private static final long serialVersionUID = 1L;

    protected SampleListFrame parent;

    protected G1000TableModel g1000TableModel;


    public G1000Table(SampleListFrame parent, G1000TableModel g1000TableModel){
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
