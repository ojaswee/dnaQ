package dnaQ.GUI.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import dnaQ.Models.Sample;

public class CommonTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Sample> samples;
	private ArrayList<CommonTableModelColumn> columns;

	public CommonTableModel(){}

	public CommonTableModel(ArrayList<Sample> samples){
		this.samples = samples;
		constructColumns();
	}

	private void constructColumns(){
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

		columns.add(new CommonTableModelColumn("cosmicid",
				"cosmicid",
				String.class,
				(Sample request) -> request.cosmicid));

		columns.add(new CommonTableModelColumn("clinvarid",
				"clinvarid",
				String.class,
				(Sample request) -> request.clinvarid));

		columns.add(new CommonTableModelColumn("g1000id",
				"g1000id",
				String.class,
				(Sample request) -> request.g1000id));

		columns.add(new CommonTableModelColumn("altGlobalFreq",
				"globalFreq",
				String.class,
				(Sample request) -> request.altGlobalFreq));

		columns.add(new CommonTableModelColumn("disease",
				"disease",
				String.class,
				(Sample request) -> request.disease));

		columns.add(new CommonTableModelColumn("comment",
				"comment",
				String.class,
				(Sample sample) -> sample.comment));

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
