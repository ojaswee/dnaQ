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

		columns.add(new CommonTableModelColumn("cds",
				"cds",
				String.class,
				(Sample request) -> request.cds));

		columns.add(new CommonTableModelColumn("aa",
				"aa",
				String.class,
				(Sample request) -> request.aa));

		columns.add(new CommonTableModelColumn("count",
				"count",
				String.class,
				(Sample request) -> request.count));

		columns.add(new CommonTableModelColumn("clinvarid",
				"clinvarid",
				String.class,
				(Sample request) -> request.clinvarid));

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

		columns.add(new CommonTableModelColumn("disease",
				"disease",
				String.class,
				(Sample request) -> request.disease));

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
