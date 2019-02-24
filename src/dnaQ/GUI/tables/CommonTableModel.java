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
				"popFreqid",
				String.class,
				(Mutation mutation) -> mutation.freqid));

		columns.add(new CommonTableModelColumn("cancerid",
				"cancerid",
				String.class,
				(Mutation mutation) -> mutation.cancerid));

		columns.add(new CommonTableModelColumn("clinicalid",
				"clinicalid",
				String.class,
				(Mutation mutation) -> mutation.clinicalid));

		columns.add(new CommonTableModelColumn("clinicalDisease",
				"clinicalDisease",
				String.class,
				(Mutation mutation) -> mutation.clinicalDisease));

		columns.add(new CommonTableModelColumn("gene",
				"biologyGene",
				String.class,
				(Mutation mutation) -> mutation.getGene()));

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
