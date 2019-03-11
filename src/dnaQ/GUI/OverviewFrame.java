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
    private JPanel infoPanel;

    private JButton chart1Button;
    private JButton chart2Button;
    private JButton chart3Button;

    private Integer frameWidth;
    private Integer frameHeight;

    private JPanel dataPanel;
    private JPanel dataAPanel;
    private JPanel dataBPanel;
    private JPanel dataCPanel;

    private JTextArea infoTextArea;

    private OverviewChart overviewChart;

    public OverviewFrame(MutationListFrame parent, MutationList mutationList, OverviewChart overviewChart){
        super ("Overview");
        this.parent = parent;
        this.mutationList = mutationList;
        this.overviewChart = overviewChart;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        pack();

        frameWidth= GUICommonTools.screenWidth /3;
        frameHeight =GUICommonTools.screenHeight - 400;
        setSize(frameWidth,frameHeight);

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
        infoPanel = new JPanel();

        chartButtonPanel.setLayout(new BoxLayout(chartButtonPanel,BoxLayout.Y_AXIS));
        chart1Button = new JButton("Chart 1");
        chart2Button = new JButton("Chart 2");
        chart3Button = new JButton("Chart 3");

        dataPanel = new JPanel(new FlowLayout());

        dataAPanel = new JPanel(new FlowLayout());
        dataBPanel = new JPanel(new FlowLayout());
        dataCPanel = new JPanel(new FlowLayout());

        infoTextArea = new JTextArea();
    }

    private void layoutOverviewComponents(){
        mainPanel.setLayout(new BorderLayout());

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(frameWidth - 25,frameHeight/11);
        JLabel lblLogo= new JLabel(logoPicture);

        logoPanel.add(lblLogo);

        JPanel chart1ButtonPanel = new JPanel();
        chart1ButtonPanel.add(chart1Button);
        chart1ButtonPanel.setBackground(GUICommonTools.BackgroundColor1);
        chartButtonPanel.add(chart1ButtonPanel);

        JPanel chart2ButtonPanel = new JPanel();
        chart2ButtonPanel.add(chart2Button);
        chart2ButtonPanel.setBackground(GUICommonTools.BackgroundColor1);
        chartButtonPanel.add(chart2ButtonPanel);

        JPanel chart3ButtonPanel = new JPanel();
        chart3ButtonPanel.add(chart3Button);
        chart3ButtonPanel.setBackground(GUICommonTools.BackgroundColor1);
        chartButtonPanel.add(chart3ButtonPanel);

        chrLenVsMutationPlot();
        showPanel(dataAPanel);

        GUICommonTools.setBorder(dataPanel);
        setLayout(new GridLayout(0,1));

        mainPanel.add(logoPanel,BorderLayout.PAGE_START);
        mainPanel.add(chartButtonPanel,BorderLayout.LINE_START);
        mainPanel.add(dataPanel,BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.PAGE_END);

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
                createPieChart();
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
        chartpanel.setPreferredSize(new Dimension(frameWidth-120, frameHeight-230));

        dataAPanel.removeAll();
        dataAPanel.add(chartpanel);
        dataAPanel.revalidate();
        dataAPanel.repaint();

        addInfo(1);
    }

    private void createPieChart() {
        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(1));
        chartpanel.setDomainZoomable(true);
        chartpanel.setPreferredSize(new Dimension(frameWidth - 120, frameHeight - 150));

        dataBPanel.removeAll();
        dataBPanel.add(chartpanel);
        dataBPanel.revalidate();
        dataBPanel.repaint();

        addInfo(2);
    }

    private void createBarChart() {
        ChartPanel chartpanel = new ChartPanel(overviewChart.getChart(2));
        chartpanel.setDomainZoomable(true);
        chartpanel.setPreferredSize(new Dimension(frameWidth-120, frameHeight-210));

        dataCPanel.removeAll();
        dataCPanel.add(chartpanel);
        dataCPanel.revalidate();
        dataCPanel.repaint();

        addInfo(3);
    }


    // know which button was clicked
     private void addInfo(Integer chartButtonClicked){

        if (chartButtonClicked == 1){
//            infoTextArea.setText("");
            infoTextArea.setText("\nIn this chart, we take a look at chromosome column, " +
                    "to conduct the comparison between mutation " +
                    "in your chromosome with the length of chromosome. " +
                    "In ideal case, the length of chromosome will be similar to your mutation.\n");
        }

        else if (chartButtonClicked == 2){
            infoTextArea.setText("\nIn this chart, we take a look at Population Frequency database.\n");
        }

        else if (chartButtonClicked == 3){
            infoTextArea.setText("\nIn this chart we look at three databases, Population Frequency, Cancer " +
                    "and Clinical\n");
        }

        infoTextArea.setLineWrap(true);
        infoTextArea.setSize(new Dimension(frameWidth - 30, frameHeight));
        infoTextArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        infoTextArea.isVisible();

        infoPanel.add(infoTextArea);
        infoPanel.setBackground(GUICommonTools.BackgroundColor2);
        GUICommonTools.setBorder(infoPanel);
    }
}
