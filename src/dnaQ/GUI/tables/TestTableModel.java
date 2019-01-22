package dnaQ.GUI.tables;

import dnaQ.Models.Test;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TestTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Test> userTest;
    private ArrayList<TestTableModelColumn> columns;

    public TestTableModel(ArrayList<Test> userTest){
        this.userTest = userTest;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<TestTableModelColumn>();

        columns.add(new TestTableModelColumn("usertestid",
                "usertestid",
                String.class,
                (Test test) -> test.usertestid));

        columns.add(new TestTableModelColumn("testname",
                "testname",
                String.class,
                (Test test) -> test.testname));

        columns.add(new TestTableModelColumn("type",
                "type",
                String.class,
                (Test test) -> test.type));

        columns.add(new TestTableModelColumn("testrun",
                "testrun",
                String.class,
                (Test test) -> test.testrun));


        columns.add(new TestTableModelColumn("status",
                "status",
                String.class,
                (Test test) -> test.status));

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return columns.get(column).columnClass;
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public int getRowCount() {
        return userTest.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column).title;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Test test = userTest.get(row);
        return columns.get(column).getValue(test);
    }
}
