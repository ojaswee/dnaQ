package dnaQ.GUI;

import dnaQ.Models.FilterList;
import dnaQ.Models.MutationList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MutationListFrameFilterPanel extends JPanel {

    private MutationListFrame parent;

    private MutationList mutationList;
    public DetailsOnDemandChart datachart;

    public JPanel filterPanel;
    
    public JCheckBox populationFreqIDCheckbox;
    public JCheckBox cancerIDCheckbox;
    public JCheckBox clinicalIDCheckbox;
    public JCheckBox biologyGeneCheckbox;

    public JPanel populationFreqFilterPanel1;
    public JCheckBox globalFreqCheckbox;
    public JCheckBox americanFreqCheckbox;
    public JCheckBox asianFreqCheckbox;
    public JCheckBox afrFreqCheckbox;
    public JCheckBox eurFreqCheckbox;

    public JPanel populationFreqFilterPanel2;
    public JTextField populationFreqMaxTextField;
    
    public JPanel cancerFilterPanel;
    public JTextField cancerCountTextField;

    public JPanel clinicalFilterPanel;
    public JCheckBox specifiedCheckbox;
    public JCheckBox nonSpecifiedCheckbox;



    public JPanel biologyFilterPanel;
    public JCheckBox diseaseCheckox;
    public JTextField minPubTextField;
    private FilterList filterList;


    public MutationListFrameFilterPanel(MutationListFrame parent, JPanel filterPanel,
                                        MutationList mutationList, DetailsOnDemandChart datachart) {

        this.parent = parent;
        this.filterPanel = filterPanel;
        this.mutationList = mutationList;
        this.datachart = datachart;

        this.filterList=new FilterList();

        createComponents();
        layoutComponents();
        activateComponents();
    }

    public void createComponents(){
        populationFreqIDCheckbox = new JCheckBox("Population Frequency");
        cancerIDCheckbox = new JCheckBox("Cancer");
        clinicalIDCheckbox = new JCheckBox("Clinical");
        biologyGeneCheckbox = new JCheckBox("Biology");

        cancerFilterPanel = new JPanel(new BorderLayout(55,0));
        clinicalFilterPanel = new JPanel(new GridLayout(1,0));
        populationFreqFilterPanel1 = new JPanel(new GridLayout(1,0));
        populationFreqFilterPanel2 = new JPanel(new BorderLayout(5,0));
        biologyFilterPanel = new JPanel(new GridLayout(1,0));

        globalFreqCheckbox = new JCheckBox("Global Freq");
        americanFreqCheckbox = new JCheckBox("American Freq");
        asianFreqCheckbox = new JCheckBox("Asian Freq");
        afrFreqCheckbox = new JCheckBox("African Freq");
        eurFreqCheckbox = new JCheckBox("European Freq");
        populationFreqMaxTextField = new JTextField("100");
        populationFreqMaxTextField.setMaximumSize(new Dimension(20,20));

        cancerCountTextField = new JTextField("");

        specifiedCheckbox = new JCheckBox("Specified");
        nonSpecifiedCheckbox = new JCheckBox("Non-specified");

        diseaseCheckox = new JCheckBox("Biology Disease");
        minPubTextField = new JTextField("");


        filterList.addPopulationFreqIDFilter(populationFreqIDCheckbox);
        filterList.addGlobalFreqFilter(globalFreqCheckbox,populationFreqMaxTextField);
        filterList.addAmericanFreqFilter(americanFreqCheckbox,populationFreqMaxTextField);
        filterList.addAsianFreqFilter(asianFreqCheckbox,populationFreqMaxTextField);
        filterList.addAfrFreqFilter(afrFreqCheckbox,populationFreqMaxTextField);
        filterList.addEurFreqFilter(eurFreqCheckbox,populationFreqMaxTextField);


        filterList.addCancerIDFilter(cancerIDCheckbox);
        filterList.addCancerCountFilter(cancerCountTextField);

        filterList.addClinicalIDFilter(clinicalIDCheckbox);
        filterList.addSpecifiedFilter(specifiedCheckbox);
        filterList.addNonSpecifiedFilter(nonSpecifiedCheckbox);

        filterList.addGeneFilter(biologyGeneCheckbox);
        filterList.addDiseaseFilter(diseaseCheckox);
        filterList.addPublicationFilter(minPubTextField);
    }

    public void layoutComponents(){

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
        databasefilterPanel.add(populationFreqIDCheckbox);
        databasefilterPanel.add(cancerIDCheckbox);
        databasefilterPanel.add(clinicalIDCheckbox);
        databasefilterPanel.add(biologyGeneCheckbox);
        filterPanel.add(databasefilterPanel);

    // Population Freq database filter
        populationFreqFilterPanel1.add(new Label("Pop freq database"));
        populationFreqFilterPanel1.add(globalFreqCheckbox);
        populationFreqFilterPanel1.add(americanFreqCheckbox);
        populationFreqFilterPanel1.add(asianFreqCheckbox);
        populationFreqFilterPanel1.add(afrFreqCheckbox);
        populationFreqFilterPanel1.add(eurFreqCheckbox);
        filterPanel.add(populationFreqFilterPanel1);

        populationFreqFilterPanel2.add(new Label("Enter Pop freq Max Value"),BorderLayout.LINE_START);
        populationFreqFilterPanel2.add(populationFreqMaxTextField,BorderLayout.CENTER);
        JLabel label2 = new JLabel("");
        label2.setPreferredSize(new Dimension(550,1));
        populationFreqFilterPanel2.add(label2,BorderLayout.LINE_END);
        filterPanel.add(populationFreqFilterPanel2);

    //Cancer database filter
        cancerFilterPanel.add(new Label("CancerCount min"),BorderLayout.LINE_START);
        cancerFilterPanel.add(cancerCountTextField,BorderLayout.CENTER);
        JLabel label3 = new JLabel("");
        label3.setPreferredSize(new Dimension(500,1));
        cancerFilterPanel.add(label3,BorderLayout.LINE_END);
        filterPanel.add(cancerFilterPanel);

    // Clinical database filter
        clinicalFilterPanel.add(new Label("Clinical Disease"));
        clinicalFilterPanel.add(specifiedCheckbox);
        clinicalFilterPanel.add(nonSpecifiedCheckbox);
        filterPanel.add(clinicalFilterPanel);

    // Biology database filter
        biologyFilterPanel.add(new Label("Biology database"));
        biologyFilterPanel.add(diseaseCheckox);
        biologyFilterPanel.add(minPubTextField);
        filterPanel.add(biologyFilterPanel);
    }

    private void activateComponents() {
        populationFreqIDCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        cancerIDCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        clinicalIDCheckbox.addActionListener(new ActionListener() {
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

        americanFreqCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        asianFreqCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        afrFreqCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        eurFreqCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        cancerCountTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        //clinical checkboxes
        specifiedCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        nonSpecifiedCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        biologyGeneCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        diseaseCheckox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });
        

        populationFreqMaxTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }
        });

        minPubTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }
        });

        cancerCountTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                handleCheckBoxClick();
            }
        });
    }

    private void refreshTables(){
        parent.commonTableModel.fireTableDataChanged();
        parent.populationFreqTableModel.fireTableDataChanged();
        parent.cancerTableModel.fireTableDataChanged();
        parent.clinicalTableModel.fireTableDataChanged();
        parent.biologyTableModel.fireTableDataChanged();
    }

    private void handleCheckBoxClick() {
        mutationList.filterSamples(filterList);
        datachart.updateCharts();
        refreshTables();
    }
}
