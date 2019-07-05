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

    protected void handleMouseClick(int column) throws Exception{
        if(column == 5){
            updateMutationSelection();
        }
    }

    protected final Mutation getSelectedMutation(){
        int viewRow = getSelectedRow();
        int modelRow = convertRowIndexToModel(viewRow);
        Mutation mutation = selectionTableModel.getMutation(modelRow);
        return mutation;
    }

    private void updateMutationSelection() throws Exception{

        Mutation mutation = getSelectedMutation();
        if (mutation.isSelected()){
            mutation.setSelected(false);
        }else{
            mutation.setSelected(true);
        }
    }
}


