package dnaQ.GUI.tables;

import dnaQ.Connections.WebConnections;
import dnaQ.GUI.MutationListFrame;
import dnaQ.Models.Mutation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        setDefaultRenderer(String.class, new TestQueueTableCellRenderer());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent c) {
                try{
                    int column = columnAtPoint(c.getPoint());
                    int row = rowAtPoint(c.getPoint());
                    if(getValueAt(row, column) == null){
                        return;
                    }
                    handleMouseClick(column);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(parent, "error");

                }
            }
        });
    }
    public class TestQueueTableCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            Component c = super.getTableCellRendererComponent(table, value,
                    isSelected, hasFocus, row, column);


            if (column == 2) {
                Object o = getValueAt(row, 4).toString();
                c.setForeground(Color.BLUE);
            }

            else {
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    }

    protected void handleMouseClick(int column) throws Exception{
        if(column == 2){
            searchGene();
        }
    }

    protected final Mutation getSelectedMutation(){
        int viewRow = getSelectedRow();
        int modelRow = convertRowIndexToModel(viewRow);
        Mutation mutation = biologyTableModel.getMutation(modelRow);
        return mutation;
    }

    private void searchGene() throws Exception{

        Mutation mutation = getSelectedMutation();
        String gene = mutation.getGene().trim().split(",")[0];
        if(!gene.equals("") && !gene.equals("null")){
            WebConnections.searchGene(gene);
        }
    }
}

