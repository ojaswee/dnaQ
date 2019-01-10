package dnaQ.GUI;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dnaQ.GUI.tables.*;
import dnaQ.Models.FilterList;
import dnaQ.Models.SampleList;

public class SampleListFrame extends JFrame  {

	private static final long serialVersionUID = 1L;

	private CommonTable commonTable;
	private CommonTableModel commonTableModel;

	private CosmicTable cosmicTable;
	private CosmicTableModel cosmicTableModel;

	private ClinvarTable clinvarTable;
	private ClinvarTableModel clinvarTableModel;

	private G1000Table g1000Table;
	private G1000TableModel g1000TableModel;

	private OncokbTable oncokbTable;
	private OncokbTableModel oncokbTableModel;

	private JScrollPane commonScrollPane;
	private JScrollPane cosmicScrollPane;
	private JScrollPane clinvarScrollPane;
	private JScrollPane g1000ScrollPane;
	private JScrollPane oncokbScrollPane;

	private LoginFrame parent;

	private SampleList sampleList;
	private FilterList filterList;

	private JPanel panel;
	private JTabbedPane tabbedPane;

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

		this.commonTableModel = new CommonTableModel(sampleList.getSamples());
		this.cosmicTableModel = new CosmicTableModel(sampleList.getSamples());
		this.clinvarTableModel = new ClinvarTableModel(sampleList.getSamples());
		this.g1000TableModel = new G1000TableModel(sampleList.getSamples());
		this.oncokbTableModel = new OncokbTableModel(sampleList.getSamples());

		this.datachart = new DataChart(this,this.sampleList);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		createComponents();
		layoutComponents();
		activateComponents();

		pack();
		setResizable(true);
        setLocationRelativeTo(parent);
	}

	public void createComponents(){

		panel = new JPanel();

		commonTable = new CommonTable(this,commonTableModel);

		commonScrollPane = new JScrollPane(commonTable);
		commonScrollPane.setViewportView(commonTable);

		cosmicTable = new CosmicTable(this,cosmicTableModel);
		cosmicScrollPane = new JScrollPane(cosmicTable);
		cosmicScrollPane.setViewportView(cosmicTable);

		clinvarTable = new ClinvarTable(this,clinvarTableModel);
		clinvarScrollPane = new JScrollPane(clinvarTable);
		clinvarScrollPane.setViewportView(clinvarTable);

		g1000Table = new G1000Table(this,g1000TableModel);
		g1000ScrollPane = new JScrollPane(g1000Table);
		g1000ScrollPane.setViewportView(g1000Table);

		oncokbTable = new OncokbTable(this,oncokbTableModel);
		oncokbScrollPane = new JScrollPane(oncokbTable);
		oncokbScrollPane.setViewportView(oncokbTable);


		cosmicIDCheckbox = new JCheckBox("Cosmic");
		clinvarIDCheckbox = new JCheckBox("Clinvar");
		g1000IDCheckbox = new JCheckBox("g1000");
		globalFreqCheckbox = new JCheckBox("Global Frequency");
		diseaseCheckbox = new JCheckBox("Disease");

		filterList.addCosmicIDFilter(cosmicIDCheckbox);
		filterList.addClinvarIDFilter(clinvarIDCheckbox);
		filterList.addG1000IDFilter(g1000IDCheckbox);
		filterList.addGlobalFreqFilter(globalFreqCheckbox);
		filterList.addG1000IDFilter(diseaseCheckbox);

		dashboardButton = new JButton("Dashboard");
		reportButton = new JButton("Report");
		exportButton = new JButton("Export");
	}

	private void layoutComponents(){

		int widthPanel, heightPanel;

		widthPanel = 1900;
		heightPanel = 1000;

		setMinimumSize(new Dimension(widthPanel,heightPanel));
		panel.setBackground(GUICommonTools.BackgroundColor2);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		//logo
		ImageIcon logoPicture = GUICommonTools.getSquareLogo(widthPanel/5, heightPanel/5);
		JLabel lblLogo= new JLabel(logoPicture);

		JPanel logoPanel = new JPanel();
		logoPanel.add(lblLogo);

//		upper panel includes filter and feature panel

		JPanel upperPanel = new JPanel(new FlowLayout());

		JPanel filterPanel = new JPanel(new GridLayout(0,1));
        filterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        filterPanel.setBackground(GUICommonTools.BackgroundColor2);

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
        filterPanel.add(filterPanel5);

		JPanel featurePanel = new JPanel(new BorderLayout());
		featurePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        featurePanel.setBackground(GUICommonTools.BackgroundColor2);

		JPanel featurePanelHeading = new JPanel(new FlowLayout());
		featurePanelHeading.add(new Label("Please select one"));
		featurePanelHeading.setFont(GUICommonTools.TAHOMA_BOLD_16);
		featurePanelHeading.setBackground(GUICommonTools.BackgroundColor1);
		featurePanel.add(featurePanelHeading, BorderLayout.NORTH);

        JPanel featureButtonPanel = new JPanel(new GridLayout(0,1));

        JPanel dashboardButtonPanel = new JPanel();
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

		featurePanel.add(featureButtonPanel, BorderLayout.SOUTH);

		upperPanel.add(logoPanel);
		upperPanel.add(filterPanel);

		upperPanel.add(featurePanel);

		panel.add(upperPanel);

		JPanel lowerPanel = new JPanel(new BorderLayout());

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Common", null, commonScrollPane, null);
		tabbedPane.addTab("Cosmic", null, cosmicScrollPane, null);
		tabbedPane.addTab("Clinvar", null, clinvarScrollPane, null);
		tabbedPane.addTab("G1000", null, g1000ScrollPane, null);
		tabbedPane.addTab("Oncokb", null, oncokbScrollPane, null);


		lowerPanel.add(tabbedPane, BorderLayout.CENTER);


		lowerPanel.setBackground(GUICommonTools.BackgroundColor1);
		panel.add(lowerPanel);

		add(panel);
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
				try {
					handleReportButtonClick();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	private void handleCheckBoxClick() {
			sampleList.filterSamples(filterList);
			cosmicTableModel.fireTableDataChanged();
			datachart.updateCharts();
	}


	private void handleChartButtonClick() {

		AnalysisFrame analysisFrame = new AnalysisFrame(SampleListFrame.this, sampleList,datachart);
		analysisFrame.setVisible(true);
	}


	private void handleReportButtonClick() throws Exception {

		RequestReportFrame reportframe = new RequestReportFrame(this);
		reportframe.setVisible(true);
	}

}


