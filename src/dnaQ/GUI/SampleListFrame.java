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
    public JCheckBox globalFreqCheckbox;
	public JCheckBox diseaseCheckbox;


	public JButton dashboardButton;

	public JButton reportButton;

	public JButton exportButton;

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
		globalFreqCheckbox = new JCheckBox("Global Frequency");
		diseaseCheckbox = new JCheckBox("Disease");

		filterList.addCosmicIDFilter(cosmicIDCheckbox);
		filterList.addClinvarIDFilter(clinvarIDCheckbox);
		filterList.addG1000IDFilter(g1000IDCheckbox);
		filterList.addG1000IDFilter(globalFreqCheckbox);
		filterList.addG1000IDFilter(diseaseCheckbox);

		dashboardButton = new JButton("Dashboard");
		reportButton = new JButton("Report");
		exportButton = new JButton("Export");
	}
	
	private void layoutComponents(){

        setLayout(new GridLayout(0,1));
		setMinimumSize(new Dimension(1900,500));

		JPanel upperPanel = new JPanel(new GridLayout(1,0));

		JPanel filterPanel = new JPanel(new GridLayout(0,1));
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JPanel filterPanelHeading = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterPanelHeading.add(new Label("Choose filter to select results"));
        filterPanelHeading.setFont(GUICommonTools.TAHOMA_BOLD_16);
        filterPanelHeading.setPreferredSize(new Dimension(20, 30));
        filterPanelHeading.setMinimumSize(new Dimension(20, 30));
        filterPanelHeading.setMaximumSize(new Dimension(20, 30));
        filterPanelHeading.setBackground(GUICommonTools.BackgroundColor1);
        filterPanel.add(filterPanelHeading);


		JPanel databasefilterPanel = new JPanel(new GridLayout(1,0));
        databasefilterPanel.add(new Label("Select database"));
        databasefilterPanel.add(cosmicIDCheckbox);
        databasefilterPanel.add(clinvarIDCheckbox);
        databasefilterPanel.add(g1000IDCheckbox);
        databasefilterPanel.add(globalFreqCheckbox);
        databasefilterPanel.add(diseaseCheckbox);
        filterPanel.add(databasefilterPanel);


        JPanel filterPanel2 = new JPanel(new GridLayout(1,0));
        filterPanel2.add(new Label("Select"));
        filterPanel.add(filterPanel2);


        JPanel filterPanel3 = new JPanel(new GridLayout(1,0));
        filterPanel3.add(new Label("Select"));
        filterPanel.add(filterPanel3);

        JPanel filterPanel4 = new JPanel(new GridLayout(1,0));
        filterPanel4.add(new Label("Select"));
        filterPanel.add(filterPanel4);

        JPanel filterPanel5 = new JPanel(new GridLayout(1,0));
        filterPanel5.add(new Label("Select"));
        filterPanel.add(filterPanel4);


		JPanel featurePanel = new JPanel(new GridLayout(0,1));
		featurePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        featurePanel.setBackground(GUICommonTools.BackgroundColor1);


        JPanel featurePanelHeading = new JPanel(new FlowLayout(FlowLayout.CENTER));
        featurePanelHeading.add(new Label("Please select one"));
        featurePanelHeading.setFont(GUICommonTools.TAHOMA_BOLD_16);
        featurePanelHeading.setBackground(GUICommonTools.BackgroundColor1);
        featurePanel.add(featurePanelHeading);

        JPanel featureButtonPanel = new JPanel(new GridLayout(0,1));


        JPanel dashboardButtonPanel = new JPanel();
        dashboardButton.setMinimumSize(new Dimension(100,100));
        dashboardButtonPanel.add(dashboardButton);
		featureButtonPanel.add(dashboardButtonPanel);
		featureButtonPanel.add(new Label(" "));

        JPanel reportButtonPanel = new JPanel();
        reportButtonPanel.add(reportButton);
        featureButtonPanel.add(reportButtonPanel);
		featureButtonPanel.add(new Label(" "));

        JPanel exportButtonPanel = new JPanel();
        exportButtonPanel.add(exportButton);
        featureButtonPanel.add(exportButtonPanel);

		featurePanel.add(featureButtonPanel);
		upperPanel.add(filterPanel);
		upperPanel.add(featurePanel);

		JPanel lowerPanel = new JPanel(new GridLayout(0,1));
		lowerPanel.add(tableScrollPane);

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

		globalFreqCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleCheckBoxClick();
			}
		});

		diseaseCheckbox.addActionListener(new ActionListener() {
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

		ReportFrame reportframe = new ReportFrame(this);
		reportframe.setVisible(true);
	}

}


