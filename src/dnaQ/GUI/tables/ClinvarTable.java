package dnaQ.GUI.tables;

import dnaQ.GUI.SampleListFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class ClinvarTable extends CommonTable{

        private static final long serialVersionUID = 1L;

        protected SampleListFrame parent;

        protected ClinvarTableModel clinvarTableModel;


        public ClinvarTable (SampleListFrame parent, ClinvarTableModel clinvarTableModel){
            super();
            this.parent = parent;
            this.clinvarTableModel = clinvarTableModel;
            setModel(clinvarTableModel);

            resizeColumnWidths();
            constructRenderers();

            setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

}
