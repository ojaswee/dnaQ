package dnaQ.GUI.tables;

import dnaQ.Models.Mutation;

import java.util.ArrayList;

public class ClinvarTableModel extends CommonTableModel{

    private static final long serialVersionUID = 1L;

    private ArrayList<Mutation> mutations;
    private ArrayList<CommonTableModelColumn> columns;

    public ClinvarTableModel(ArrayList<Mutation> mutations){
        this.mutations = mutations;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<CommonTableModelColumn>();

        columns.add(new CommonTableModelColumn("id",
                "id",
                Integer.class,
                (Mutation mutation) -> mutation.id));

        columns.add(new CommonTableModelColumn("chromosome",
                "chr",
                String.class,
                (Mutation mutation) -> mutation.chr));

        columns.add(new CommonTableModelColumn("position",
                "pos",
                Integer.class,
                (Mutation mutation) -> mutation.pos));

        columns.add(new CommonTableModelColumn("ref",
                "ref",
                String.class,
                (Mutation mutation) -> mutation.ref));

        columns.add(new CommonTableModelColumn("alt",
                "alt",
                String.class,
                (Mutation mutation) -> mutation.alt));

        columns.add(new CommonTableModelColumn("clndn",
                "clndn",
                String.class,
                (Mutation mutation) -> mutation.clndn));

        columns.add(new CommonTableModelColumn("clnsig",
                "clnsig",
                String.class,
                (Mutation mutation) -> mutation.clnsig));

        columns.add(new CommonTableModelColumn("mc",
                "mc",
                String.class,
                (Mutation mutation) -> mutation.mc));

        columns.add(new CommonTableModelColumn("origin",
                "origin",
                String.class,
                (Mutation mutation) -> mutation.origin));
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
        return mutations.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column).title;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Mutation mutation = mutations.get(row);
        return columns.get(column).getValue(mutation);
    }
}
