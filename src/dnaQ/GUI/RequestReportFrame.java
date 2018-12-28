package dnaQ.GUI;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Connections.SSHConnection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RequestReportFrame extends JFrame {

    public JPanel requestreportPanel;
    public JComboBox reportChoicesBox;
    public JButton reportSubmitButton;

    public SampleListFrame parent;


    public JProgressBar progressBar;

    public RequestReportFrame(SampleListFrame samplelistframe) throws Exception {

        super ("Request Report");
        this.parent = samplelistframe;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createComponents();
        layoutReportComponents();
        activateComponents();

        pack();
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);
        setReportsOption();

    }

    private void createComponents() {

        requestreportPanel = new JPanel(new GridLayout(0, 1));

        reportChoicesBox = new JComboBox();

        reportSubmitButton = new JButton("Submit");

    }

    private void layoutReportComponents() {

        setMinimumSize(new Dimension(500, 500));
        setLayout(new GridLayout(0, 1));

        requestreportPanel.add(new Label(""));

        JPanel dropdownPanel = new JPanel();

        dropdownPanel.add(new Label("Select Report:"));
        dropdownPanel.add(reportChoicesBox);
        dropdownPanel.add(reportSubmitButton);

        JPanel progressPanel = new JPanel();
        progressPanel.add(new Label("Progress Monitor"));

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        progressPanel.add(progressBar);
        progressPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        requestreportPanel.add(dropdownPanel);
        requestreportPanel.add(progressPanel);
        add(requestreportPanel);

    }

    private void setReportsOption() throws Exception {

        ArrayList<String> reports = DatabaseConnections.getReportOptions();

        for(int i =0; i < reports.size(); i++){
            reportChoicesBox.addItem(reports.get(i));
        }
    }
    private void activateComponents(){

        reportSubmitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
//                    runProgressBar();
                    reportSubmission();

                }catch (Exception e){
                    JOptionPane.showMessageDialog(RequestReportFrame.this, e.getMessage());
                }
            }
        });
    }

    private void reportSubmission() throws Exception {

        int random = (int)(Math.random() * 9999 + 1);

        String name = "global_" + random;

        progressBar.setValue(50);
        SSHConnection.generateReport(name);
        progressBar.setValue(80);
        SSHConnection.transferReport(name);
        progressBar.setValue(100);

//        displayReport();
    }

    private void runProgressBar(){
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            progressBar.setValue(10);
    }

/* this function activates ReportFrame which is not being used right now
//    private void displayReport(){
//        ReportFrame report = null;
//        try {
//            report = new ReportFrame(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        report.setVisible(true);
//    }
*/
}