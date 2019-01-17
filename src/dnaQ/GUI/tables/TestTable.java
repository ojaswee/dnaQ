package dnaQ.GUI.tables;

import dnaQ.GUI.WelcomeFrame;

import javax.swing.*;

public class TestTable extends JTable{

    private static final long serialVersionUID = 1L;

    protected WelcomeFrame parent;

    protected TestTableModel userTestTableModel;


    public TestTable(WelcomeFrame parent, TestTableModel userTestTableModel){
        super();
        this.parent = parent;
        this.userTestTableModel = userTestTableModel;
        setModel(userTestTableModel);

//        setAutoResizeMode(JTable.AUTO_RESIZE_OFF); //do not resize columns use scrollbar instead
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}
