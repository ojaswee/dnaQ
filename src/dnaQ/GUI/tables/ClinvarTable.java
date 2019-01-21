package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;

public class ClinvarTable extends CommonTable{

        private static final long serialVersionUID = 1L;

        protected MutationListFrame parent;

        protected ClinvarTableModel clinvarTableModel;


        public ClinvarTable (MutationListFrame parent, ClinvarTableModel clinvarTableModel){
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
