package dnaQ.GUI.tables;

import dnaQ.Models.Sample;

import java.util.ArrayList;

public class OncokbTableModel extends CommonTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Sample> samples;
    private ArrayList<CommonTableModelColumn> columns;

    public OncokbTableModel(ArrayList<Sample> samples) {
        this.samples = samples;
        constructColumns();
    }

    private void constructColumns() {
        columns = new ArrayList<CommonTableModelColumn>();

        columns.add(new CommonTableModelColumn("drugs",
                "drugs",
                String.class,
                (Sample request) -> request.drugs));

        columns.add(new CommonTableModelColumn("clinicalSignificance",
                "clinicalSignificance",
                String.class,
                (Sample request) -> request.clinicalSignificance));

        columns.add(new CommonTableModelColumn("evidenceStatement",
                "evidenceStatement",
                String.class,
                (Sample request) -> request.evidenceStatement));


        columns.add(new CommonTableModelColumn("variantSummary",
                "variantSummary",
                String.class,
                (Sample request) -> request.variantSummary));

        columns.add(new CommonTableModelColumn("gene",
                "gene",
                String.class,
                (Sample request) -> request.gene));

        columns.add(new CommonTableModelColumn("proteinChange",
                "proteinChange",
                String.class,
                (Sample request) -> request.proteinChange));

        columns.add(new CommonTableModelColumn("oncogenecity",
                "oncogenecity",
                String.class,
                (Sample request) -> request.oncogenecity));


        columns.add(new CommonTableModelColumn("mutationEffect",
                "mutationEffect",
                String.class,
                (Sample request) -> request.mutationEffect));
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
