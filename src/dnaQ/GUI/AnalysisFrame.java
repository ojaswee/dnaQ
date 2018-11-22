package dnaQ.GUI;

import dnaQ.Models.SampleList;
import dnaQ.Models.Sample;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AnalysisFrame extends JDialog {

    private static final long serialVersionUID = 1L;

    private SampleListFrame parent;
    private SampleList sampleList;
    public JPanel dataAPanel;
    public JPanel dataBPanel;
    public JPanel dataCPanel;
    public JPanel dataDPanel;

    public DataChart datachart;


    public AnalysisFrame(SampleListFrame parent, SampleList sampleList, DataChart datachart) {

        this.parent = parent;
        this.sampleList = sampleList;
        this.datachart = datachart;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createComponents();
        layoutAnalysisComponents();

        pack();
        //setModalityType(ModalityType.APPLICATION_MODAL);
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);

    }

    private void createComponents(){

        dataAPanel = new JPanel();
        dataAPanel.add(new Label("Chart Area"));

        dataBPanel = new JPanel();
        dataBPanel.add(new Label("Chart Area"));

        dataCPanel = new JPanel();
        dataCPanel.add(new Label("Chart Area"));

        dataDPanel = new JPanel();
        dataDPanel.add(new Label("Chart Area"));

        createLinePlot();
        createHistoGram();
        createChromosomeMutationPlot();

    }

    private void layoutAnalysisComponents(){

       //setMinimumSize(new Dimension(2000,2000));

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(0,2));
        //dataPanel.setMinimumSize(new Dimension(1990,1990));

        dataAPanel.setMinimumSize(new Dimension(450,50));
        dataAPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataBPanel.setMinimumSize(new Dimension(450,50));
        dataBPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataCPanel.setMinimumSize(new Dimension(450,50));
        dataCPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataDPanel.setMinimumSize(new Dimension(450,50));
        dataDPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        dataPanel.add(dataAPanel);
        dataPanel.add(dataBPanel);
        dataPanel.add(dataCPanel);
        dataPanel.add(dataDPanel);

        setLayout(new GridLayout(0,1));
        add(dataPanel);

    }

    private void createLinePlot(){
        ChartPanel chartpanel = new ChartPanel(datachart.getChart(0));
        chartpanel.setDomainZoomable(true);
        dataAPanel.add(chartpanel);
    }

    private void createHistoGram() {

        ChartPanel chartpanel = new ChartPanel(datachart.getChart(1));
        chartpanel.setDomainZoomable(true);
        dataBPanel.add(chartpanel);
    }

    private void createChromosomeMutationPlot() {

        ChartPanel chartpanel = new ChartPanel(datachart.getChart(2));
        chartpanel.setDomainZoomable(true);
        dataCPanel.add(chartpanel);
    }


}
