package dnaQ.GUI;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dnaQ.GUI.tables.*;

import dnaQ.Models.MutationList;

public class MutationListFrame extends JFrame  {

	private static final long serialVersionUID = 1L;

	public CommonTable commonTable;
	public CommonTableModel commonTableModel;

	public CancerTable cancerTable;
	public CancerTableModel cancerTableModel;

	public ClinicalTable clinicalTable;
	public ClinicalTableModel clinicalTableModel;

	public PopulationFreqTable populationFreqTable;
	public PopulationFreqTableModel populationFreqTableModel;

	public BiologyTable biologyTable;
	public BiologyTableModel biologyTableModel;

	private JScrollPane commonScrollPane;
	private JScrollPane cancerScrollPane;
	private JScrollPane clinicalScrollPane;
	private JScrollPane populationFreqScrollPane;
	private JScrollPane biologyScrollPane;

	private WelcomeFrame parent;

	private MutationList mutationList;
	private MutationList originaMutationlList;

	private JPanel panel;
	private JTabbedPane tabbedPane;

	public JButton overviewButton;
	public JButton detailsOnDemandButton;

	public JButton reportButton;

	public JButton exportButton;

	public DetailsOnDemandChart datachart;

	public OverviewChart overviewChart;



	public MutationListFrame(WelcomeFrame parent, MutationList mutationList)  {

		super ("Mutation List");
		this.originaMutationlList= mutationList;
		this.mutationList = mutationList;
		this.parent = parent;

		this.commonTableModel = new CommonTableModel(mutationList.getMutations());
		this.cancerTableModel = new CancerTableModel(mutationList.getMutations());
		this.clinicalTableModel = new ClinicalTableModel(mutationList.getMutations());
		this.populationFreqTableModel = new PopulationFreqTableModel(mutationList.getMutations());
		this.biologyTableModel = new BiologyTableModel(mutationList.getMutations());

		try {
			this.overviewChart = new OverviewChart(this,this.originaMutationlList);
			this.datachart = new DetailsOnDemandChart(this,this.mutationList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

		cancerTable = new CancerTable(this, cancerTableModel);
		cancerScrollPane = new JScrollPane(cancerTable);
		cancerScrollPane.setViewportView(cancerTable);

		clinicalTable = new ClinicalTable(this, clinicalTableModel);
		clinicalScrollPane = new JScrollPane(clinicalTable);
		clinicalScrollPane.setViewportView(clinicalTable);

		populationFreqTable = new PopulationFreqTable(this, populationFreqTableModel);
		populationFreqScrollPane = new JScrollPane(populationFreqTable);
		populationFreqScrollPane.setViewportView(populationFreqTable);

		biologyTable = new BiologyTable(this,biologyTableModel);
		biologyScrollPane = new JScrollPane(biologyTable);
		biologyScrollPane.setViewportView(biologyTable);

		overviewButton = new JButton("Overview");
		detailsOnDemandButton = new JButton("Details On Demand");
		reportButton = new JButton("Report");
		exportButton = new JButton("Export");
	}

	private void layoutComponents(){

		int widthPanel, heightPanel;

		widthPanel = 750;
		heightPanel = 750;

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

		MutationListFrameFilterPanel filterPanel1 = new MutationListFrameFilterPanel(this, filterPanel,mutationList,datachart);
		add(filterPanel1);

		JPanel featurePanel = new JPanel(new BorderLayout());
		featurePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        featurePanel.setBackground(GUICommonTools.BackgroundColor2);

		JPanel featurePanelHeading = new JPanel(new FlowLayout());
		featurePanelHeading.add(new Label("Please select one"));
		featurePanelHeading.setFont(GUICommonTools.TAHOMA_BOLD_16);
		featurePanelHeading.setBackground(GUICommonTools.BackgroundColor1);
		featurePanel.add(featurePanelHeading, BorderLayout.NORTH);

        JPanel featureButtonPanel = new JPanel(new GridLayout(0,1));

		JPanel overviewButtonPanel = new JPanel();
		overviewButtonPanel.add(overviewButton);
		featureButtonPanel.add(overviewButtonPanel);
//		featureButtonPanel.add(new Label(" "));

        JPanel detailsOnDemandButtonPanel = new JPanel();
        detailsOnDemandButtonPanel.add(detailsOnDemandButton);
		featureButtonPanel.add(detailsOnDemandButtonPanel);
//		featureButtonPanel.add(new Label(" "));

        JPanel reportButtonPanel = new JPanel();
        reportButtonPanel.add(reportButton);
        featureButtonPanel.add(reportButtonPanel);
//		featureButtonPanel.add(new Label(" "));

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
		tabbedPane.addTab("Pop_Freq", null, populationFreqScrollPane, null);
		tabbedPane.addTab("Cancer", null, cancerScrollPane, null);
		tabbedPane.addTab("Clinical", null, clinicalScrollPane, null);
		tabbedPane.addTab("Biology", null, biologyScrollPane, null);


		lowerPanel.add(tabbedPane, BorderLayout.CENTER);


		lowerPanel.setBackground(GUICommonTools.BackgroundColor1);
		panel.add(lowerPanel);

		add(panel);
	}


	private void activateComponents(){
		overviewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleOverviewButtonClick();
			}
		});

		detailsOnDemandButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleDashboardButtonClick();
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

	private void handleOverviewButtonClick() {
		OverviewFrame overviewFrame = new OverviewFrame(this, originaMutationlList,overviewChart);
		overviewFrame.setVisible(true);
	}

	private void handleDashboardButtonClick() {
		DetailsOnDemandFrame detailsOnDemandFrame = new DetailsOnDemandFrame(this, mutationList, datachart);
		detailsOnDemandFrame.setVisible(true);
	}


	private void handleReportButtonClick() throws Exception {
		RequestReportFrame reportFrame = new RequestReportFrame(this);
		reportFrame.setVisible(true);
	}
}


