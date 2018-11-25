package dnaQ.GUI;

import dnaQ.Models.SampleList;
import org.jfree.chart.ChartPanel;


import javax.swing.*;
import java.awt.*;


public class AnalysisFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private SampleListFrame parent;
    private SampleList sampleList;
    public JPanel dataPanel;
    public DataChart datachart;


    public AnalysisFrame(SampleListFrame parent, SampleList sampleList, DataChart datachart) {

        this.parent = parent;
        this.sampleList = sampleList;
        this.datachart = datachart;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createComponents();
        layoutAnalysisComponents();

        pack();
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);

    }

    private void createComponents(){

        dataPanel = new JPanel();
        dataPanel.add(new Label("Chart Area"));


    }

    private void layoutAnalysisComponents(){

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(0,2));


        JPanel dataAPanel = new JPanel();
        dataAPanel.add(new Label("Chart Area"));
        dataAPanel.setMinimumSize(new Dimension(450,50));
        dataAPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));



        JPanel dataBPanel = new JPanel();
        dataBPanel.add(new Label("Chart Area"));
        dataBPanel.setMinimumSize(new Dimension(450,50));
        dataBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));




        JPanel dataCPanel = new JPanel();
        dataCPanel.add(new Label("Chart Area"));
        dataCPanel.setMinimumSize(new Dimension(450,50));
        dataCPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));



        JPanel dataDPanel = new JPanel();
        dataDPanel.add(new Label("Chart Area"));
        dataDPanel.setMinimumSize(new Dimension(450,50));
        dataDPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        createChromosomeMutationPlot(dataAPanel);
        createLinePlot(dataBPanel);
        createPieChart(dataCPanel);
        createHistoGram(dataDPanel);


        dataPanel.add(dataAPanel);
        dataPanel.add(dataBPanel);
        dataPanel.add(dataCPanel);
        dataPanel.add(dataDPanel);

        dataPanel.setBackground(new java.awt.Color(125, 204, 78));
        setLayout(new GridLayout(0,1));
        add(dataPanel);

    }

    private void createChromosomeMutationPlot(JPanel dataAPanel ){
        ChartPanel chartpanel = new ChartPanel(datachart.getChart(0));
        chartpanel.setDomainZoomable(true);
        dataAPanel.add(chartpanel);
    }

    private void createLinePlot(JPanel dataBPanel ) {

        ChartPanel chartpanel = new ChartPanel(datachart.getChart(1));
        chartpanel.setDomainZoomable(true);
        dataBPanel.add(chartpanel);
    }

    private void createPieChart(JPanel dataCPanel ) {

        ChartPanel chartpanel = new ChartPanel(datachart.getChart(2));
        chartpanel.setDomainZoomable(true);
        dataCPanel.add(chartpanel);
    }

    private void createHistoGram(JPanel dataCPanel ) {

        ChartPanel chartpanel = new ChartPanel(datachart.getChart(3));
        chartpanel.setDomainZoomable(true);
        dataCPanel.add(chartpanel);
    }
}
