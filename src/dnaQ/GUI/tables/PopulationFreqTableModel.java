package dnaQ.GUI.tables;

import dnaQ.Models.Mutation;

import java.util.ArrayList;

public class PopulationFreqTableModel extends CommonTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Mutation> mutations;
    private ArrayList<CommonTableModelColumn> columns;

    public PopulationFreqTableModel(ArrayList<Mutation> mutations) {
        this.mutations = mutations;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<CommonTableModelColumn>();

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

        columns.add(new CommonTableModelColumn("freqid",
                "freqid",
                String.class,
                (Mutation mutation) -> mutation.freqid));

        columns.add(new CommonTableModelColumn("globalFreq",
                "globalFreq",
                String.class,
                (Mutation mutation) -> mutation.globalFreq));

        columns.add(new CommonTableModelColumn("americanFreq",
                "americanFreq",
                String.class,
                (Mutation mutation) -> mutation.americanFreq));

        columns.add(new CommonTableModelColumn("asianFreq",
                "asianFreq",
                String.class,
                (Mutation mutation) -> mutation.asianFreq));

        columns.add(new CommonTableModelColumn("afrFreq",
                "afrFreq",
                String.class,
                (Mutation mutation) -> mutation.afrFreq));

        columns.add(new CommonTableModelColumn("eurFreq",
                "eurFreq",
                String.class,
                (Mutation mutation) -> mutation.eurFreq));
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