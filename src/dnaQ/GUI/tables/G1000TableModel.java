package dnaQ.GUI.tables;

import dnaQ.Models.Sample;

import java.util.ArrayList;

public class G1000TableModel extends CommonTableModel {

    private static final long serialVersionUID = 1L;

    private ArrayList<Sample> samples;
    private ArrayList<CommonTableModelColumn> columns;

    public G1000TableModel(ArrayList<Sample> samples) {
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

        columns.add(new CommonTableModelColumn("g1000id",
                "g1000id",
                String.class,
                (Sample request) -> request.g1000id));

        columns.add(new CommonTableModelColumn("altCount",
                "altCount",
                String.class,
                (Sample request) -> request.altCount));

        columns.add(new CommonTableModelColumn("totalCount",
                "totalCount",
                String.class,
                (Sample request) -> request.totalCount));

        columns.add(new CommonTableModelColumn("altGlobalFreq",
                "altGlobalFreq",
                String.class,
                (Sample request) -> request.altGlobalFreq));

        columns.add(new CommonTableModelColumn("americanFreq",
                "americanFreq",
                String.class,
                (Sample request) -> request.americanFreq));

        columns.add(new CommonTableModelColumn("asianFreq",
                "asianFreq",
                String.class,
                (Sample request) -> request.asianFreq));

        columns.add(new CommonTableModelColumn("afrFreq",
                "afrFreq",
                String.class,
                (Sample request) -> request.afrFreq));

        columns.add(new CommonTableModelColumn("eurFreq",
                "eurFreq",
                String.class,
                (Sample request) -> request.eurFreq));
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