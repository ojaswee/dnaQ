package dnaQ.GUI.tables;

import dnaQ.Models.Mutation;

import java.util.ArrayList;

public class G1000TableModel extends CommonTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Mutation> mutations;
    private ArrayList<CommonTableModelColumn> columns;

    public G1000TableModel(ArrayList<Mutation> mutations) {
        this.mutations = mutations;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<CommonTableModelColumn>();

        columns.add(new CommonTableModelColumn("id",
                "id",
                Integer.class,
                (Mutation request) -> request.id));

        columns.add(new CommonTableModelColumn("chromosome",
                "chr",
                String.class,
                (Mutation request) -> request.chr));

        columns.add(new CommonTableModelColumn("position",
                "pos",
                Integer.class,
                (Mutation request) -> request.pos));

        columns.add(new CommonTableModelColumn("ref",
                "ref",
                String.class,
                (Mutation request) -> request.ref));

        columns.add(new CommonTableModelColumn("alt",
                "alt",
                String.class,
                (Mutation request) -> request.alt));

        columns.add(new CommonTableModelColumn("g1000id",
                "g1000id",
                String.class,
                (Mutation request) -> request.g1000id));

        columns.add(new CommonTableModelColumn("altCount",
                "altCount",
                String.class,
                (Mutation request) -> request.altCount));

        columns.add(new CommonTableModelColumn("totalCount",
                "totalCount",
                String.class,
                (Mutation request) -> request.totalCount));

        columns.add(new CommonTableModelColumn("altGlobalFreq",
                "globalFreq",
                String.class,
                (Mutation request) -> request.altGlobalFreq));

        columns.add(new CommonTableModelColumn("americanFreq",
                "americanFreq",
                String.class,
                (Mutation request) -> request.americanFreq));

        columns.add(new CommonTableModelColumn("asianFreq",
                "asianFreq",
                String.class,
                (Mutation request) -> request.asianFreq));

        columns.add(new CommonTableModelColumn("afrFreq",
                "afrFreq",
                String.class,
                (Mutation request) -> request.afrFreq));

        columns.add(new CommonTableModelColumn("eurFreq",
                "eurFreq",
                String.class,
                (Mutation request) -> request.eurFreq));
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
        Mutation request = mutations.get(row);
        return columns.get(column).getValue(request);
    }
}