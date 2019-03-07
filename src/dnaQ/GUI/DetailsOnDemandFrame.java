package dnaQ.GUI;

import dnaQ.Models.MutationList;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;


public class DetailsOnDemandFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private MutationListFrame parent;
    private MutationList mutationList;

    private Integer screenWidth;
    private Integer screenHeight;

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
        screenWidth= screenSize.width;
        screenHeight =screenSize.height;
        setSize(screenSize.width,screenSize.height);

        createComponents();
        layoutDoDComponents();

        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);
    }

    private void createComponents(){

        mainPanel = new JPanel();

        dataPanel = new JPanel();
        dataPanel.add(new Label("Chart Area"));

        logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    private void layoutDoDComponents(){

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(screenWidth/2,screenHeight/11);
        JLabel lblLogo= new JLabel(logoPicture);

        logoPanel.add(lblLogo);

        dataPanel = new JPanel(new GridLayout(0,2));

        JPanel dataAPanel = new JPanel();
        dataAPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel dataBPanel = new JPanel();
        dataBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel dataCPanel = new JPanel();
        dataCPanel.add(new Label("Chart C"));
        dataCPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel dataDPanel = new JPanel();
        dataDPanel.add(new Label("Chart D"));
        dataDPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        chrLenVsMutationPlot(dataAPanel);
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

    private void chrLenVsMutationPlot(JPanel dataAPanel ){
        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(0));
        chartpanel.setDomainZoomable(true);
        dataAPanel.add(chartpanel);
    }

    private void createLinePlot(JPanel dataBPanel ) {

        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(1));
        chartpanel.setDomainZoomable(true);
        dataBPanel.add(chartpanel);
    }

    private void createPieChart(JPanel dataCPanel ) {

        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(2));
        chartpanel.setDomainZoomable(true);
        dataCPanel.add(chartpanel);
    }

    private void createHistoGram(JPanel dataCPanel ) {

        ChartPanel chartpanel = new ChartPanel(dodChart.getChart(3));
        chartpanel.setDomainZoomable(true);
        dataCPanel.add(chartpanel);
    }
}
