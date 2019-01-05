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

    public void resizeColumnWidths() {


        TableColumnModel columnModel = getColumnModel();
        int buffer = 12;

        for (int column = 0; column < getColumnCount(); column++) {
            TableColumn tableColumn = columnModel.getColumn(column);

            TableCellRenderer headerRenderer = getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(this, tableColumn.getHeaderValue(), false, false, 0, 0);

            int minWidth = headerComp.getPreferredSize().width + buffer;
            int maxWidth = 200;

            int width = minWidth;
            for (int row = 0; row < getRowCount(); row++) {
                TableCellRenderer renderer = getCellRenderer(row, column);
                Component comp = prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + buffer , width);
            }
            width = Math.min(maxWidth, width);
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    private void constructRenderers(){
        ((DefaultTableCellRenderer)getDefaultRenderer(Integer.class)).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer)getDefaultRenderer(Double.class)).setHorizontalAlignment(SwingConstants.CENTER);
        ((DefaultTableCellRenderer)getDefaultRenderer(String.class)).setHorizontalAlignment(SwingConstants.CENTER);
    }
}
