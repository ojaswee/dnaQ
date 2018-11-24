package dnaQ.GUI;

import java.awt.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dnaQ.Models.FilterList;
import dnaQ.GUI.GUI_Models.SampleTableModel;
import dnaQ.Models.SampleList;

public class SampleListFrame extends JFrame  {
	
	private static final long serialVersionUID = 1L;

	private JTable table;
	private SampleTableModel sampleTableModel;
	
    private JScrollPane tableScrollPane;

	private LoginFrame parent;

	private SampleList sampleList;
	private FilterList filterList;


	public JCheckBox cosmicIDCheckbox;
	public JCheckBox clinvarIDCheckbox;
	public JCheckBox g1000IDCheckbox;


	public JButton dashboardButton;

	public JButton reportButton;

	public DataChart datachart;



	public SampleListFrame(LoginFrame parent, SampleList sampleList )  {
		this.sampleList=sampleList;
		this.filterList=new FilterList();
		this.parent = parent;
		this.sampleTableModel= new SampleTableModel(sampleList.getSamples());
		this.datachart = new DataChart(this,this.sampleList);


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		createComponents();
		layoutComponents();
		activateComponents();
		
		pack();
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
		g1000IDCheckbox = new JCheckBox("g1000");

		filterList.addCosmicIDFilter(cosmicIDCheckbox);
		filterList.addClinvarIDFilter(clinvarIDCheckbox);
		filterList.addG1000IDFilter(g1000IDCheckbox);

		dashboardButton = new JButton("Dashboard");
		reportButton = new JButton("Report");
	}
	
	private void layoutComponents(){

		setMinimumSize(new Dimension(1900,500));

		JPanel upperPanel = new JPanel(new GridLayout(1,0));

		JPanel filterPanel = new JPanel();
		filterPanel.add(new Label("Filter area"));
		filterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		filterPanel.add(cosmicIDCheckbox);
		filterPanel.add(clinvarIDCheckbox);
		filterPanel.add(g1000IDCheckbox);

		JPanel featurePanel = new JPanel();
		featurePanel.add(new Label("Feature area"));
		featurePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		featurePanel.add(dashboardButton);
		featurePanel.add(reportButton);

		upperPanel.add(filterPanel);
		upperPanel.add(featurePanel);

		JPanel lowerPanel = new JPanel(new GridLayout(0,1));
		lowerPanel.add(tableScrollPane);

		setLayout(new GridLayout(0,1));
		add(upperPanel);
		add(lowerPanel);

	}

	
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

		g1000IDCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCheckBoxClick();
			}
		});


		dashboardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleChartButtonClick();
			}
		});

		reportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleReportButtonClick();
			}
		});
	}

	private void handleCheckBoxClick() {
			sampleList.filterSamples(filterList);
			sampleTableModel.fireTableDataChanged();
			datachart.updateCharts();
	}


	private void handleChartButtonClick() {

		AnalysisFrame analysisFrame = new AnalysisFrame(SampleListFrame.this, sampleList,datachart);
		analysisFrame.setVisible(true);
	}


	private void handleReportButtonClick() {

		System.out.println("Report button clicked");
	}

}


