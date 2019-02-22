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

    private MutationList mutationList;
    public DataChart datachart;

    public JCheckBox cancerIDCheckbox;
    public JCheckBox clinicalIDCheckbox;
    public JCheckBox populationFreqIDCheckbox;

    public JPanel filterPanel;

    public JPanel cancerFilterPanel;
    public JTextField cancerCountTextField;

    public JPanel clinicalFilterPanel;
    public JCheckBox benignCheckbox;
    public JCheckBox likelyCheckbox;

    public JPanel populationFreqFilterPanel;
    public JCheckBox globalFreqCheckbox;
    public JCheckBox americanFreqCheckbox;
    public JCheckBox asianFreqCheckbox;
    public JCheckBox afrFreqCheckbox;
    public JCheckBox eurFreqCheckbox;
    public JTextField populationFreqMaxTextField;

    public JPanel biologyFilterPanel;
    public JCheckBox diseaseCheckox;
    public JCheckBox geneCheckbox;
    public JTextField minPubTextField;
    private FilterList filterList;


    public MutationListFrameFilterPanel (JPanel filterPanel,MutationList mutationList,DataChart datachart) {

        this.filterPanel = filterPanel;
        this.mutationList = mutationList;
        this.datachart = datachart;

        this.filterList=new FilterList();

        createComponents();
        layoutComponents();
        activateComponents();
//        handleTextFieldValueChange();
    }

    public void createComponents(){
        populationFreqIDCheckbox = new JCheckBox("Population Frequency");
        cancerIDCheckbox = new JCheckBox("Cancer");
        clinicalIDCheckbox = new JCheckBox("Clinical");

        cancerFilterPanel = new JPanel(new GridLayout(1,0));
        clinicalFilterPanel = new JPanel(new GridLayout(1,0));
        populationFreqFilterPanel = new JPanel(new GridLayout(1,0));
        biologyFilterPanel = new JPanel(new GridLayout(1,0));

        globalFreqCheckbox = new JCheckBox("Global Freq");
        americanFreqCheckbox = new JCheckBox("American Freq");
        asianFreqCheckbox = new JCheckBox("Asian Freq");
        afrFreqCheckbox = new JCheckBox("African Freq");
        eurFreqCheckbox = new JCheckBox("European Freq");
        populationFreqMaxTextField = new JTextField("100");

        cancerCountTextField = new JTextField("Please input min count");

        benignCheckbox = new JCheckBox("No cancer");
        likelyCheckbox = new JCheckBox("Likely Cancer");

        geneCheckbox = new JCheckBox("Gene");
        diseaseCheckox = new JCheckBox("Biology Disease");
        minPubTextField = new JTextField("0");


        filterList.addPopulationFreqIDFilter(populationFreqIDCheckbox);
        filterList.addGlobalFreqFilter(globalFreqCheckbox,populationFreqMaxTextField);
        filterList.addAmericanFreqFilter(americanFreqCheckbox,populationFreqMaxTextField);
        filterList.addAsianFreqFilter(asianFreqCheckbox,populationFreqMaxTextField);
        filterList.addAfrFreqFilter(afrFreqCheckbox,populationFreqMaxTextField);
        filterList.addEurFreqFilter(eurFreqCheckbox,populationFreqMaxTextField);


        filterList.addCancerIDFilter(cancerIDCheckbox);
        filterList.addCancerCountFilter(cancerCountTextField);

        filterList.addClinicalIDFilter(clinicalIDCheckbox);
        filterList.addBenignFilter(benignCheckbox);
        filterList.addLikelyCancerFilter(likelyCheckbox);

        filterList.addGeneFilter(geneCheckbox);
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
        databasefilterPanel.add(cancerIDCheckbox);
        databasefilterPanel.add(clinicalIDCheckbox);
        databasefilterPanel.add(populationFreqIDCheckbox);
        filterPanel.add(databasefilterPanel);

    // Population Freq database filter
        populationFreqFilterPanel.add(new Label("Pop freq database"));
        populationFreqFilterPanel.add(globalFreqCheckbox);
        populationFreqFilterPanel.add(americanFreqCheckbox);
        populationFreqFilterPanel.add(asianFreqCheckbox);
        populationFreqFilterPanel.add(afrFreqCheckbox);
        populationFreqFilterPanel.add(eurFreqCheckbox);
        populationFreqFilterPanel.add(populationFreqMaxTextField);
        filterPanel.add(populationFreqFilterPanel);

    //Cancer database filter
        cancerFilterPanel.add(new Label("Cancer database"));
        cancerFilterPanel.add(cancerCountTextField);
        filterPanel.add(cancerFilterPanel);

    // Clinical database filter
        clinicalFilterPanel.add(new Label("Clinical database"));
        clinicalFilterPanel.add(benignCheckbox);
        clinicalFilterPanel.add(likelyCheckbox);
        filterPanel.add(clinicalFilterPanel);

    // Biology database filter
        biologyFilterPanel.add(new Label("Biology database"));
        biologyFilterPanel.add(geneCheckbox);
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
        benignCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        likelyCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        geneCheckbox.addActionListener(new ActionListener() {
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
        
        addPropertyChangeListener(populationFreqMaxTextField.getText(),e -> handleCheckBoxClick());
        addPropertyChangeListener(minPubTextField.getText(),e -> handleCheckBoxClick());

    }

    private void handleCheckBoxClick() {
        mutationList.filterSamples(filterList);
        datachart.updateCharts();
    }

}
