package dnaQ.GUI;

import dnaQ.Models.MutationList;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OverviewFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private MutationListFrame parent;
    private MutationList mutationList;

    private JPanel mainPanel;
    private JPanel logoPanel;

    private JPanel chartButtonPanel;
    private JButton chart1Button;
    private JButton chart2Button;
    private JButton chart3Button;

    private Integer screenWidth;
    private Integer screenHeight;

    private JPanel dataPanel;
    private JPanel dataAPanel;
    private JPanel dataBPanel;
    private JPanel dataCPanel;

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
        activateComponents();

        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);
    }

    private void createComponents(){

        mainPanel = new JPanel(new BorderLayout());
        logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        chartButtonPanel = new JPanel();
        chart1Button = new JButton("Chart 1");
        chart2Button = new JButton("Chart 2");
        chart3Button = new JButton("Chart 3");

        dataPanel = new JPanel(new FlowLayout());

        dataAPanel = new JPanel(new FlowLayout());
        dataBPanel = new JPanel(new FlowLayout());
        dataCPanel = new JPanel(new FlowLayout());

    }

    private void layoutOverviewComponents(){
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(screenWidth/2,screenHeight/11);
        JLabel lblLogo= new JLabel(logoPicture);

        logoPanel.add(lblLogo);

        dataAPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataCPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel chart1ButtonPanel = new JPanel();
        chart1ButtonPanel.add(chart1Button);
        chartButtonPanel.add(chart1ButtonPanel);

        JPanel chart2ButtonPanel = new JPanel();
        chart2ButtonPanel.add(chart2Button);
        chartButtonPanel.add(chart2ButtonPanel);

        JPanel chart3ButtonPanel = new JPanel();
        chart3ButtonPanel.add(chart3Button);
        chartButtonPanel.add(chart3ButtonPanel);

        dataPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dataPanel.setBackground(GUICommonTools.BackgroundColor1);
        setLayout(new GridLayout(0,1));

        mainPanel.add(logoPanel,BorderLayout.PAGE_START);
        mainPanel.add(chartButtonPanel,BorderLayout.LINE_START);
        mainPanel.add(dataPanel,BorderLayout.CENTER);

        add(mainPanel);

    }

    private void activateComponents() {

        chart1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hidePanels();
                chrLenVsMutationPlot();
                showPanel(dataAPanel);
            }
        });

        chart2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hidePanels();
                createLinePlot();
                showPanel(dataBPanel);
            }
        });

        chart3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hidePanels();
                createBarChart();
                showPanel(dataCPanel);
            }
        });
   }

   private void hidePanels(){
        dataAPanel.setVisible(false);
        dataBPanel.setVisible(false);
        dataCPanel.setVisible(false);
   }

   private void showPanel(JPanel currentPanel){
        currentPanel.setVisible(true);
        dataPanel.add(currentPanel);
   }

   private void chrLenVsMutationPlot(){
        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(0));
        chartpanel.setDomainZoomable(true);

        dataAPanel.removeAll();
        dataAPanel.add(chartpanel);
        dataAPanel.revalidate();
        dataAPanel.repaint();
    }

    private void createLinePlot() {
        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(1));
        chartpanel.setDomainZoomable(true);

        dataBPanel.removeAll();
        dataBPanel.add(chartpanel);
        dataBPanel.revalidate();
        dataBPanel.repaint();
    }

    private void createBarChart() {
        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(2));
        chartpanel.setDomainZoomable(true);

        dataCPanel.removeAll();
        dataCPanel.add(chartpanel);
        dataCPanel.revalidate();
        dataCPanel.repaint();
    }

}
