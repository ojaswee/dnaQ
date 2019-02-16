package dnaQ.GUI.tables;

import java.util.ArrayList;

import dnaQ.Models.Mutation;

public class CosmicTableModel extends CommonTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Mutation> mutations;
    private ArrayList<CommonTableModelColumn> columns;

    public CosmicTableModel(ArrayList<Mutation> mutations){
        this.mutations = mutations;
        constructColumns();
    }

    private void constructColumns(){
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

        columns.add(new CommonTableModelColumn("cosmicid",
                "cosmicid",
                String.class,
                (Mutation request) -> request.cosmicid));

        columns.add(new CommonTableModelColumn("cds",
                "cds",
                String.class,
                (Mutation request) -> request.cds));

        columns.add(new CommonTableModelColumn("aa",
                "aa",
                String.class,
                (Mutation request) -> request.aa));

        columns.add(new CommonTableModelColumn("count",
                "count",
                String.class,
                (Mutation request) -> request.count));

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