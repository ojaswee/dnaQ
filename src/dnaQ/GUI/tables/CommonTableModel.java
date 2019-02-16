package dnaQ.GUI.tables;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import dnaQ.Models.Mutation;

public class CommonTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Mutation> mutations;
	private ArrayList<CommonTableModelColumn> columns;

	public CommonTableModel(){}

	public CommonTableModel(ArrayList<Mutation> mutations){
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

		columns.add(new CommonTableModelColumn("clinvarid",
				"clinvarid",
				String.class,
				(Mutation request) -> request.clinvarid));

		columns.add(new CommonTableModelColumn("g1000id",
				"g1000id",
				String.class,
				(Mutation request) -> request.g1000id));

		columns.add(new CommonTableModelColumn("altGlobalFreq",
				"globalFreq",
				String.class,
				(Mutation request) -> request.altGlobalFreq));

		columns.add(new CommonTableModelColumn("disease",
				"disease",
				String.class,
				(Mutation request) -> request.disease));

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
		Mutation request = mutations.get(row);
		return columns.get(column).getValue(request);
	}

}
