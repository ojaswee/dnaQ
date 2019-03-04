package dnaQ.GUI;

import dnaQ.Models.MutationList;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

public class OverviewFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private MutationListFrame parent;
    private MutationList mutationList;

    private JPanel mainPanel;
    private JPanel logoPanel;

    private Integer screenWidth;
    private Integer screenHeight;
    private JPanel dataPanel;

    private OverviewChart overviewChart;

    public OverviewFrame(MutationListFrame parent, MutationList mutationList, OverviewChart overviewChart){
        super ("Overview");
        this.parent = parent;
        this.mutationList = mutationList;
        this.overviewChart = overviewChart;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        pack();

        screenWidth= screenSize.width;
        screenHeight =screenSize.height;
        setSize(screenWidth,screenHeight);

        createComponents();
        layoutOverviewComponents();

        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);
    }

    private void createComponents(){

        mainPanel = new JPanel();
        logoPanel = new JPanel();

        dataPanel = new JPanel();
        dataPanel.add(new Label("Chart Area"));
    }

    private void layoutOverviewComponents(){
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(screenWidth/2,screenHeight/11);
        JLabel lblLogo= new JLabel(logoPicture);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.add(lblLogo);

        dataPanel = new JPanel(new GridLayout(0,3));

        JPanel dataAPanel = new JPanel();
        dataAPanel.add(new Label("Chart A"));
        dataAPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel dataBPanel = new JPanel();
        dataBPanel.add(new Label("Chart B"));
        dataBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel dataCPanel = new JPanel();
        dataCPanel.add(new Label("Chart C"));
        dataCPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        chrLenVsMutationPlot(dataAPanel);
        createLinePlot(dataBPanel);
        createPieChart(dataCPanel);

        dataPanel.add(dataAPanel);
        dataPanel.add(dataBPanel);
        dataPanel.add(dataCPanel);

        dataPanel.setBackground(GUICommonTools.BackgroundColor1);
        setLayout(new GridLayout(0,1));

        mainPanel.add(logoPanel);
        mainPanel.add(dataPanel);

        add(mainPanel);

    }

    private void chrLenVsMutationPlot(JPanel dataAPanel ){
        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(0));
        chartpanel.setPreferredSize(new Dimension(screenWidth/4,screenHeight/3));
        chartpanel.setDomainZoomable(true);
        dataAPanel.add(chartpanel);
    }

    private void createLinePlot(JPanel dataBPanel ) {

        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(1));
        chartpanel.setPreferredSize(new Dimension(screenWidth/4,screenHeight/3));
        chartpanel.setDomainZoomable(true);
        dataBPanel.add(chartpanel);
    }

    private void createPieChart(JPanel dataCPanel ) {

        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(2));
        chartpanel.setSize(new Dimension(screenWidth/5,screenHeight/3));
        chartpanel.setDomainZoomable(true);
        dataCPanel.add(chartpanel);
        dataCPanel.setBounds(1,10,200,300);
    }

}
