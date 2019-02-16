package dnaQ.GUI;

import dnaQ.Models.FilterList;
import dnaQ.Models.MutationList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MutationListFrameFilterPanel extends JPanel {

    private MutationList mutationList;
    public DataChart datachart;

    public JCheckBox cosmicIDCheckbox;
    public JCheckBox clinvarIDCheckbox;
    public JCheckBox g1000IDCheckbox;
    public JCheckBox diseaseCheckbox;

    public JPanel filterPanel;
    public JPanel cosmicFilterPanel;
    public JPanel clinvarFilterPanel;

    public JPanel g1000FilterPanel;
    public JComboBox g1000RegionCombobox;
    public JComboBox g1000MaxCombobox;
    public JButton g1000SubmitButton;

    public JPanel oncokbFilterPanel;
    private FilterList filterList;


    public MutationListFrameFilterPanel (JPanel filterPanel,MutationList mutationList,DataChart datachart) {

        this.filterPanel = filterPanel;
        this.mutationList = mutationList;
        this.datachart = datachart;

        this.filterList=new FilterList();

        createComponents();
        layoutComponents();
        activateComponents();
    }

    public void createComponents(){
        cosmicIDCheckbox = new JCheckBox("Cosmic");
        clinvarIDCheckbox = new JCheckBox("Clinvar");
        g1000IDCheckbox = new JCheckBox("g1000");
        diseaseCheckbox = new JCheckBox("Disease");

        filterList.addCosmicIDFilter(cosmicIDCheckbox);
        filterList.addClinvarIDFilter(clinvarIDCheckbox);
        filterList.addG1000IDFilter(g1000IDCheckbox);
//        filterList.addGlobalFreqFilter(globalFreqCheckbox);
        filterList.addG1000IDFilter(diseaseCheckbox);

//        filterList.addG1000ComboboxFilter(g1000RegionCombobox,g1000MaxCombobox);

        cosmicFilterPanel = new JPanel(new GridLayout(1,0));
        clinvarFilterPanel = new JPanel(new GridLayout(1,0));
        g1000FilterPanel = new JPanel(new GridLayout(1,0));
        oncokbFilterPanel = new JPanel(new GridLayout(1,0));

        g1000RegionCombobox = new JComboBox();
        g1000MaxCombobox = new JComboBox();
        g1000SubmitButton = new JButton("Submit");
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
        databasefilterPanel.add(cosmicIDCheckbox);
        databasefilterPanel.add(clinvarIDCheckbox);
        databasefilterPanel.add(g1000IDCheckbox);
        databasefilterPanel.add(diseaseCheckbox);
        filterPanel.add(databasefilterPanel);

    //Cosmic database filter
        cosmicFilterPanel.add(new Label("Select Cosmic database"));
        filterPanel.add(cosmicFilterPanel);

    // Clinvar database filter
        clinvarFilterPanel.add(new Label("Select Clinvar database"));
        filterPanel.add(clinvarFilterPanel);

    // G1000 database filter
        g1000FilterPanel.add(new Label("Select G1000 database"));

        String[] region = { "Global", "American", "Asian", "African", "European" };
        g1000RegionCombobox = new JComboBox(region);
        g1000FilterPanel.add(g1000RegionCombobox);

        Integer [] maxFreq = {0,5,10,15,20,25};
        g1000MaxCombobox= new JComboBox(maxFreq);
        g1000FilterPanel.add(g1000MaxCombobox);
        g1000FilterPanel.add(g1000SubmitButton);
        filterPanel.add(g1000FilterPanel);

    // Oncokb database filter
        oncokbFilterPanel.add(new Label("Select Oncokb database"));
        filterPanel.add(oncokbFilterPanel);

    }

    private void activateComponents() {
        final String[] region = {""};

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

        diseaseCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBoxClick();
            }
        });

        g1000RegionCombobox.addActionListener(new ActionListener() {
            String[] dbSelection ={"altGlobalFreq", "americanFreq","asianFreq" ,"afrFreq","eurFreq"};
            public void actionPerformed(ActionEvent e) {
               region[0] = (String) g1000RegionCombobox.getSelectedItem();

                if (region[0].equals("Global")) {
                    region[0] = dbSelection[0];
                }
                else if (region[0].equals("American")){
                    region[0] = dbSelection[1];
                }
                else if (region[0].equals("Asian")){
                    region[0] = dbSelection[2];
                }
                else if (region[0].equals("African")){
                    region[0] = dbSelection[3];
                }
                else if (region[0].equals("European")){
                    region[0] = dbSelection[4];
                }
                System.out.println(region[0]);
            }
        });

        g1000MaxCombobox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Integer maxSelection = (Integer) g1000MaxCombobox.getSelectedItem();
            }
        });

        g1000SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer maxSelection = (Integer) g1000MaxCombobox.getSelectedItem();
                String maxValue = maxSelection.toString();
                filterList.addG1000ComboboxFilter(region[0],maxValue);
                mutationList.filterSamples(filterList);
            }
        });
    }

    private void handleCheckBoxClick() {
        mutationList.filterSamples(filterList);
        datachart.updateCharts();
    }
}
