package dnaQ.GUI.GUI_Models;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import dnaQ.Objects.Sample;

public class SampleTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Sample> samples;
	private ArrayList<SampleTableModelColumn> columns;

	public SampleTableModel(ArrayList<Sample> samples){
		this.samples = samples;
		constructColumns();
	}

	private void constructColumns(){
		columns = new ArrayList<SampleTableModelColumn>();

		columns.add(new SampleTableModelColumn("id",
				"id",
				Integer.class,
				(Sample request) -> request.id));

		columns.add(new SampleTableModelColumn("chromosome",
				"chr",
				String.class,
				(Sample request) -> request.chr));

		columns.add(new SampleTableModelColumn("position",
				"pos",
				Integer.class,
				(Sample request) -> request.pos));

		columns.add(new SampleTableModelColumn("ref",
				"ref",
				String.class,
				(Sample request) -> request.ref));

		columns.add(new SampleTableModelColumn("alt",
				"alt",
				String.class,
				(Sample request) -> request.alt));

		columns.add(new SampleTableModelColumn("cosmicid",
				"cosmicid",
				String.class,
				(Sample request) -> request.cosmicid));

		columns.add(new SampleTableModelColumn("cds",
				"cds",
				String.class,
				(Sample request) -> request.cds));

		columns.add(new SampleTableModelColumn("aa",
				"aa",
				String.class,
				(Sample request) -> request.aa));

		columns.add(new SampleTableModelColumn("count",
				"count",
				String.class,
				(Sample request) -> request.count));

		columns.add(new SampleTableModelColumn("clinvarid",
				"clinvarid",
				String.class,
				(Sample request) -> request.clinvarid));

		columns.add(new SampleTableModelColumn("clndn",
				"clndn",
				String.class,
				(Sample request) -> request.clndn));

		columns.add(new SampleTableModelColumn("clnsig",
				"clnsig",
				String.class,
				(Sample request) -> request.clnsig));

		columns.add(new SampleTableModelColumn("mc",
				"mc",
				String.class,
				(Sample request) -> request.mc));

		columns.add(new SampleTableModelColumn("origin",
				"origin",
				String.class,
				(Sample request) -> request.origin));

		columns.add(new SampleTableModelColumn("g1000id",
				"g1000id",
				String.class,
				(Sample request) -> request.g1000id));

		columns.add(new SampleTableModelColumn("altCount",
				"altCount",
				String.class,
				(Sample request) -> request.altCount));

		columns.add(new SampleTableModelColumn("totalCount",
				"totalCount",
				String.class,
				(Sample request) -> request.totalCount));

		columns.add(new SampleTableModelColumn("altGlobalFreq",
				"altGlobalFreq",
				String.class,
				(Sample request) -> request.altGlobalFreq));

		columns.add(new SampleTableModelColumn("americanFreq",
				"americanFreq",
				String.class,
				(Sample request) -> request.americanFreq));

		columns.add(new SampleTableModelColumn("asianFreq",
				"asianFreq",
				String.class,
				(Sample request) -> request.asianFreq));

		columns.add(new SampleTableModelColumn("afrFreq",
				"afrFreq",
				String.class,
				(Sample request) -> request.afrFreq));

		columns.add(new SampleTableModelColumn("eurFreq",
				"eurFreq",
				String.class,
				(Sample request) -> request.eurFreq));

		columns.add(new SampleTableModelColumn("disease",
				"disease",
				String.class,
				(Sample request) -> request.disease));

		columns.add(new SampleTableModelColumn("drugs",
				"drugs",
				String.class,
				(Sample request) -> request.drugs));

		columns.add(new SampleTableModelColumn("clinicalSignificance",
				"clinicalSignificance",
				String.class,
				(Sample request) -> request.clinicalSignificance));

		columns.add(new SampleTableModelColumn("evidenceStatement",
				"evidenceStatement",
				String.class,
				(Sample request) -> request.evidenceStatement));


		columns.add(new SampleTableModelColumn("variantSummary",
				"variantSummary",
				String.class,
				(Sample request) -> request.variantSummary));

		columns.add(new SampleTableModelColumn("gene",
				"gene",
				String.class,
				(Sample request) -> request.gene));

		columns.add(new SampleTableModelColumn("proteinChange",
				"proteinChange",
				String.class,
				(Sample request) -> request.proteinChange));

		columns.add(new SampleTableModelColumn("oncogenecity",
				"oncogenecity",
				String.class,
				(Sample request) -> request.oncogenecity));


		columns.add(new SampleTableModelColumn("mutationEffect",
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
