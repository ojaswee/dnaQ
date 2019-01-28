package dnaQ.GUI;

import dnaQ.Models.MutationList;
import org.jfree.chart.ChartPanel;


import javax.swing.*;
import java.awt.*;


public class DashboardFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private MutationListFrame parent;
    private MutationList mutationList;

    private JPanel mainPanel;
    private JPanel logoPanel;
    private JPanel dataPanel;

    private DataChart datachart;


    public DashboardFrame(MutationListFrame parent, MutationList mutationList, DataChart datachart) {
        super ("Dashboard");
        this.parent = parent;
        this.mutationList = mutationList;
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

        mainPanel = new JPanel();

        dataPanel = new JPanel();
        dataPanel.add(new Label("Chart Area"));

        logoPanel = new JPanel();

    }

    private void layoutAnalysisComponents(){
        int widthPanel, heightPanel;
//        DEFAULT_WIDTH and DEFAULT_HEIGHT: 680 x 420.


        widthPanel = 1400;
        heightPanel = 900;

        setSize(widthPanel, heightPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(widthPanel,heightPanel/9);
        JLabel lblLogo= new JLabel(logoPicture);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.add(lblLogo);


        dataPanel = new JPanel(new GridLayout(0,2));


        JPanel dataAPanel = new JPanel();
        dataAPanel.add(new Label("Chart A"));
        dataAPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));



        JPanel dataBPanel = new JPanel();
        dataBPanel.add(new Label("Chart B"));
        dataBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));



        JPanel dataCPanel = new JPanel();
        dataCPanel.add(new Label("Chart C"));
        dataCPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));



        JPanel dataDPanel = new JPanel();
        dataDPanel.add(new Label("Chart D"));
        dataDPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        createChromosomeMutationPlot(dataAPanel);
        createLinePlot(dataBPanel);
        createPieChart(dataCPanel);
        createHistoGram(dataDPanel);


        dataPanel.add(dataAPanel);
        dataPanel.add(dataBPanel);
        dataPanel.add(dataCPanel);
        dataPanel.add(dataDPanel);

        dataPanel.setBackground(GUICommonTools.BackgroundColor1);
        setLayout(new GridLayout(0,1));

        mainPanel.add(logoPanel);
        mainPanel.add(dataPanel);

        add(mainPanel);

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