package dnaQ.GUI;

import dnaQ.Models.MutationList;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;


public class DetailsOnDemandFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private MutationListFrame parent;
    private MutationList mutationList;

    private Integer frameWidth;
    private Integer frameHeight;

    private JPanel mainPanel;
    private JPanel logoPanel;
    private JPanel dataPanel;

    private DetailsOnDemandChart dodChart;


    public DetailsOnDemandFrame(MutationListFrame parent, MutationList mutationList, DetailsOnDemandChart dodChart) {
        super ("Details On Demand");
        this.parent = parent;
        this.mutationList = mutationList;
        this.dodChart = dodChart;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pack();
        frameWidth= screenSize.width;
        frameHeight =screenSize.height;
        setSize(screenSize.width,screenSize.height);

        createComponents();
        layoutDoDComponents();

        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);
    }

    private void createComponents(){

        mainPanel = new JPanel();

        logoPanel = new JPanel();

        dataPanel = new JPanel(new GridLayout(2,2));
    }

    private void layoutDoDComponents(){

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(frameWidth/2 - 400,frameHeight/20);
        JLabel lblLogo= new JLabel(logoPicture);

        logoPanel.add(lblLogo);

        JPanel dataAPanel = new JPanel();
        GUICommonTools.setBorder(dataAPanel);

        JPanel dataBPanel = new JPanel();
        GUICommonTools.setBorder(dataBPanel);

        JPanel dataCPanel = new JPanel();
        GUICommonTools.setBorder(dataCPanel);

        JPanel dataDPanel = new JPanel();
        GUICommonTools.setBorder(dataDPanel);

        scientificEvidence(dataAPanel);
        diseaseEvidence(dataBPanel);
        cancerEvidence(dataCPanel);
        popfreqEvidence(dataDPanel);

        dataPanel.add(dataAPanel);
        dataPanel.add(dataBPanel);
        dataPanel.add(dataCPanel);
        dataPanel.add(dataDPanel);

        mainPanel.add(logoPanel);
        mainPanel.add(dataPanel);

        add(mainPanel);
    }


    private void scientificEvidence(JPanel dataAPanel ) {

        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(0));
        chartpanel.setDomainZoomable(true);
        chartpanel.setPreferredSize(new Dimension(frameWidth/2 - 20, frameHeight/3 - 5));
        dataAPanel.add(chartpanel);
    }

    private void diseaseEvidence(JPanel dataBPanel ){
        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(1));
        chartpanel.setDomainZoomable(true);
        chartpanel.setPreferredSize(new Dimension(frameWidth/2 -20, frameHeight/3 ));
        dataBPanel.add(chartpanel);
    }

    private void cancerEvidence(JPanel dataCPanel ) {

        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(2));
        chartpanel.setDomainZoomable(true);
        chartpanel.setPreferredSize(new Dimension(frameWidth/2 -20, frameHeight/3 - 5));
        dataCPanel.add(chartpanel);
    }

    private void popfreqEvidence(JPanel dataDPanel ) {

        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(3));
        chartpanel.setDomainZoomable(true);
        chartpanel.setPreferredSize(new Dimension(frameWidth/2 -20, frameHeight/3 - 5));
        dataDPanel.add(chartpanel);
    }
}
