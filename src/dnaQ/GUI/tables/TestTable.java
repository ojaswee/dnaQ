package dnaQ.GUI.tables;

import dnaQ.GUI.WelcomeFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class TestTable extends JTable {

    private static final long serialVersionUID = 1L;

    protected WelcomeFrame parent;

    protected TestTableModel userTestTableModel;


    public TestTable(WelcomeFrame parent, TestTableModel userTestTableModel) {
        super();
        this.parent = parent;
        this.userTestTableModel = userTestTableModel;
        setModel(userTestTableModel);

//        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        setDefaultRenderer(String.class, new YourTableCellRenderer());

    }


    public class YourTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            Component c = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);


            if (column == 4) {
                Object o = getValueAt(row, 4).toString();
                if (((String) o).matches("1")) {
                    c.setForeground(Color.BLUE);
                }
            }
            else {
                    c.setForeground(Color.BLACK);
                }

            return c;
        }
    }
}
