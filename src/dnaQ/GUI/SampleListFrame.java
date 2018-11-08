package dnaQ.GUI;


import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dnaQ.GUI.GUI_Models.FilterList;
import dnaQ.GUI.GUI_Models.SampleTableModel;
import dnaQ.GUI.GUI_Models.SampleList;

public class SampleListFrame extends JDialog  {
	
	private static final long serialVersionUID = 1L;

	private JTable table;
	private SampleTableModel sampleTableModel;
	
    private JScrollPane tableScrollPane;

	private LoginFrame parent;

	private SampleList sampleList;
	private FilterList filterList;



	public JCheckBox cosmicIDCheckbox;
	public JCheckBox clinvarIDCheckbox;

	public SampleListFrame(LoginFrame parent, SampleList sampleList ) throws Exception {
		super(parent, "Sample List");
		this.sampleList=sampleList;
		this.filterList=new FilterList();
		this.parent = parent;
		this.sampleTableModel= new SampleTableModel(sampleList.getSamples());



		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		createComponents();
		layoutComponents();
		activateComponents();
		
		pack(); // need to pack all components and display
		setModalityType(ModalityType.APPLICATION_MODAL);
		setAlwaysOnTop(true);
		setResizable(true);
        setLocationRelativeTo(parent);
	}

	public void createComponents(){

		table = new JTable(sampleTableModel);
		tableScrollPane = new JScrollPane();
		tableScrollPane.setViewportView(table);

		cosmicIDCheckbox = new JCheckBox("Cosmic");
		clinvarIDCheckbox = new JCheckBox("Clinvar");

		filterList.addCosmicIDFilter(cosmicIDCheckbox);
		filterList.addClinvarIDFilter(clinvarIDCheckbox);


	}
	
	private void layoutComponents(){

		Rectangle bounds = GUICommonTools.getBounds(parent);
		setSize((int)(bounds.width*.100), (int)(bounds.height*.100));
		setMinimumSize(new Dimension(1900, getHeight()/2));


		JPanel upperPanel = new JPanel(new GridLayout(1,0));

		JPanel filterPanel = new JPanel();
		filterPanel.setSize(1000,200);
		filterPanel.add(new Label("Filter area"));
		filterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		filterPanel.add(cosmicIDCheckbox);
		filterPanel.add(clinvarIDCheckbox);

		JPanel featurePanel = new JPanel();
		featurePanel.setSize(200,200);
		featurePanel.add(new Label("Feature area"));
		featurePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		upperPanel.add(filterPanel);
		upperPanel.add(featurePanel);

		JPanel lowerPanel = new JPanel(new GridLayout(0,1));
		lowerPanel.add(tableScrollPane);

		setLayout(new GridLayout(0,1));
		add(upperPanel);
		add(lowerPanel);

       // resizeColumnWidths();
		
	}
//
//	public void resizeColumnWidths() {
//		TableColumnModel columnModel = table.getColumnModel();
//
//		for (int column = 0; column < table.getColumnCount(); column++) {
//			TableColumn tableColumn = columnModel.getColumn(column);
//
//			TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
//			Component headerComp = headerRenderer.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, 0);
//
//			int minWidth = headerComp.getPreferredSize().width;
//			int maxWidth = 1000;
//
//			int width = minWidth;
//			for (int row = 0; row < table.getRowCount(); row++) {
//				TableCellRenderer renderer = table.getCellRenderer(row, column);
//				Component comp = table.prepareRenderer(renderer, row, column);
//				width = Math.max(comp.getPreferredSize().width + 25 , width);
//			}
//			width = Math.min(maxWidth, width);
//			columnModel.getColumn(column).setPreferredWidth(width);
//		}
//	}
	
	private void activateComponents(){
		cosmicIDCheckbox.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				handleCheckBoxClick();
			}
		});

		clinvarIDCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCheckBoxClick();
			}
		});
	}

	private void handleCheckBoxClick() {
			sampleList.filterSamples(filterList);
			sampleTableModel.fireTableDataChanged();
	}

}
