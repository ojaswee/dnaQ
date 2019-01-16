package dnaQ.GUI.tables;

import dnaQ.Models.Sample;

import java.util.ArrayList;

public class ClinvarTableModel extends CommonTableModel{

    private static final long serialVersionUID = 1L;

    private ArrayList<Sample> samples;
    private ArrayList<CommonTableModelColumn> columns;

    public ClinvarTableModel(ArrayList<Sample> samples){
        this.samples = samples;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<CommonTableModelColumn>();

        columns.add(new CommonTableModelColumn("id",
                "id",
                Integer.class,
                (Sample request) -> request.id));

        columns.add(new CommonTableModelColumn("chromosome",
                "chr",
                String.class,
                (Sample request) -> request.chr));

        columns.add(new CommonTableModelColumn("position",
                "pos",
                Integer.class,
                (Sample request) -> request.pos));

        columns.add(new CommonTableModelColumn("ref",
                "ref",
                String.class,
                (Sample request) -> request.ref));

        columns.add(new CommonTableModelColumn("alt",
                "alt",
                String.class,
                (Sample request) -> request.alt));
        columns.add(new CommonTableModelColumn("clndn",
                "clndn",
                String.class,
                (Sample request) -> request.clndn));

        columns.add(new CommonTableModelColumn("clnsig",
                "clnsig",
                String.class,
                (Sample request) -> request.clnsig));

        columns.add(new CommonTableModelColumn("mc",
                "mc",
                String.class,
                (Sample request) -> request.mc));

        columns.add(new CommonTableModelColumn("origin",
                "origin",
                String.class,
                (Sample request) -> request.origin));
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
        return samples.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column).title;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Sample request = samples.get(row);
        return columns.get(column).getValue(request);
    }
}