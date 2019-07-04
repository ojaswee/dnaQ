package dnaQ.GUI.tables;

import java.util.ArrayList;

import dnaQ.Models.Mutation;

public class CancerTableModel extends CommonTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Mutation> mutations;
    private ArrayList<CommonTableModelColumn> columns;

    public CancerTableModel(ArrayList<Mutation> mutations){
        this.mutations = mutations;
        constructColumns();
    }

    private void constructColumns(){
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
        
        columns.add(new CommonTableModelColumn("cancerid",
                "cancerid",
                String.class,
                (Mutation request) -> request.cancerid));

        columns.add(new CommonTableModelColumn("cancerCount",
                "cancerCount",
                String.class,
                (Mutation request) -> request.cancerCount));

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