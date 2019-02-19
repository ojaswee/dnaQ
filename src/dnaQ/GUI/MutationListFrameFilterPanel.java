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
    public JCheckBox biologyCheckbox;

    public JPanel filterPanel;
    public JPanel cosmicFilterPanel;
    public JPanel clinvarFilterPanel;

    public JPanel g1000FilterPanel;
    public JComboBox g1000RegionCombobox;
    public JTextField g1000MaxTextField;

    public JButton g1000SubmitButton;

    public JPanel biologyFilterPanel;
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
        cosmicIDCheckbox = new JCheckBox("Cancer");
        clinvarIDCheckbox = new JCheckBox("Clinical");
        g1000IDCheckbox = new JCheckBox("Population Frequency");
        biologyCheckbox = new JCheckBox("Biology");

        filterList.addCosmicIDFilter(cosmicIDCheckbox);
        filterList.addClinvarIDFilter(clinvarIDCheckbox);
        filterList.addG1000IDFilter(g1000IDCheckbox);
        filterList.addG1000IDFilter(biologyCheckbox);


        cosmicFilterPanel = new JPanel(new GridLayout(1,0));
        clinvarFilterPanel = new JPanel(new GridLayout(1,0));
        g1000FilterPanel = new JPanel(new GridLayout(1,0));
        biologyFilterPanel = new JPanel(new GridLayout(1,0));

        g1000RegionCombobox = new JComboBox();
        g1000MaxTextField = new JTextField();
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
        databasefilterPanel.add(biologyCheckbox);
        filterPanel.add(databasefilterPanel);

    //Cosmic database filter
        cosmicFilterPanel.add(new Label("Cancer database"));
        filterPanel.add(cosmicFilterPanel);

    // Clinvar database filter
        clinvarFilterPanel.add(new Label("Clinical database"));
        filterPanel.add(clinvarFilterPanel);

    // G1000 database filter
        g1000FilterPanel.add(new Label("Pop Freq database"));

        String[] region = { "Global", "American", "Asian", "African", "European" };
        g1000RegionCombobox = new JComboBox(region);
        g1000FilterPanel.add(g1000RegionCombobox);

        g1000FilterPanel.add(g1000MaxTextField);
        g1000FilterPanel.add(g1000SubmitButton);
        filterPanel.add(g1000FilterPanel);

    // biology database filter
        biologyFilterPanel.add(new Label("Biology database"));
        filterPanel.add(biologyFilterPanel);

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

        biologyCheckbox.addActionListener(new ActionListener() {
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

        g1000SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String maxFreq = g1000MaxTextField.getText();
                filterList.addG1000ComboboxFilter(region[0],maxFreq);
                mutationList.filterSamples(filterList);
            }
        });
    }

    private void handleCheckBoxClick() {
        mutationList.filterSamples(filterList);
        datachart.updateCharts();
    }
}
