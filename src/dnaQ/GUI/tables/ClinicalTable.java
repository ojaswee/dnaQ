package dnaQ.GUI.tables;

import dnaQ.GUI.MutationListFrame;

import javax.swing.*;

public class ClinicalTable extends CommonTable{

        private static final long serialVersionUID = 1L;

        protected MutationListFrame parent;

        protected ClinicalTableModel clinicalTableModel;


        public ClinicalTable(MutationListFrame parent, ClinicalTableModel clinicalTableModel){
            super();
            this.parent = parent;
            this.clinicalTableModel = clinicalTableModel;
            setModel(clinicalTableModel);

            resizeColumnWidths();
            constructRenderers();

            setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

}
