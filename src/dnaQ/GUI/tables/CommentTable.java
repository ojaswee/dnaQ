package dnaQ.GUI.tables;

import dnaQ.Connections.WebConnections;
import dnaQ.GUI.CommentFrame;
import dnaQ.GUI.MutationListFrame;
import dnaQ.Models.Mutation;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CommentTable extends CommonTable{

    private static final long serialVersionUID = 1L;

    protected MutationListFrame parent;

    protected CommentTableModel commentTableModel;


    public CommentTable(MutationListFrame parent, CommentTableModel commentTableModel){
        super();
        this.parent = parent;
        this.commentTableModel = commentTableModel;
        setModel(commentTableModel);

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

        setDefaultRenderer(String.class, new TestQueueTableCellRenderer());
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


            if (column == 5) {
                c.setForeground(Color.BLUE);
            }

            else {
                c.setForeground(Color.BLACK);
            }

            return c;
        }
    }

    protected void handleMouseClick(int column) throws Exception{
        if(column == 5){
            updateComment();
        }
    }

    protected final Mutation getSelectedMutation(){
        int viewRow = getSelectedRow();
        int modelRow = convertRowIndexToModel(viewRow);
        Mutation mutation = commentTableModel.getMutation(modelRow);
        return mutation;
    }

    private void updateComment(){

        CommentFrame commentFrame = new CommentFrame(this,getSelectedMutation());
        commentFrame.setVisible(true);

    }



}

