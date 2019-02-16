package dnaQ.GUI.tables;

import dnaQ.Models.TestQueue;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TestQueueTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<TestQueue> userTestQueue;
    private ArrayList<TestQueueTableModelColumn> columns;

    public TestQueueTableModel(ArrayList<TestQueue> userTestQueue){
        this.userTestQueue = userTestQueue;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<TestQueueTableModelColumn>();

        columns.add(new TestQueueTableModelColumn("testid",
                "testid",
                String.class,
                (TestQueue tq) -> tq.testid));

        columns.add(new TestQueueTableModelColumn("testname",
                "testname",
                String.class,
                (TestQueue tq) -> tq.testname));

        columns.add(new TestQueueTableModelColumn("testtype",
                "testtype",
                String.class,
                (TestQueue tq) -> tq.testtype));

        columns.add(new TestQueueTableModelColumn("run",
                "run",
                String.class,
                (TestQueue tq) -> tq.run));

        columns.add(new TestQueueTableModelColumn("status",
                "status",
                String.class,
                (TestQueue tq) -> tq.status));
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
        return userTestQueue.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column).title;
    }

    @Override
    public Object getValueAt(int row, int column) {
        TestQueue test = userTestQueue.get(row);
        return columns.get(column).getValue(test);
    }


}
