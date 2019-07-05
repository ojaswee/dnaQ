package dnaQ.GUI.tables;

import dnaQ.Models.Mutation;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CommentTableModel extends CommonTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Mutation> mutations;
    private ArrayList<CommonTableModelColumn> columns;

    public CommentTableModel(ArrayList<Mutation> mutations) {
        this.mutations = mutations;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<CommonTableModelColumn>();

        columns.add(new CommonTableModelColumn("chr",
                "chr",
                String.class,
                (Mutation mutation) -> mutation.chr));

        columns.add(new CommonTableModelColumn("pos",
                "pos",
                String.class,
                (Mutation mutation) -> mutation.pos));

        columns.add(new CommonTableModelColumn("ref",
                "ref",
                String.class,
                (Mutation mutation) -> mutation.ref));

        columns.add(new CommonTableModelColumn("alt",
                "alt",
                String.class,
                (Mutation mutation) -> mutation.alt));

        columns.add(new CommonTableModelColumn("gene",
                "gene",
                String.class,
                (Mutation mutation) -> mutation.gene));

        columns.add(new CommonTableModelColumn("comment",
                "comment",
                String.class,
                (Mutation mutation) -> mutation.comment));

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

    public final Mutation getMutation(int row){
        return mutations.get(row);
    }


}
