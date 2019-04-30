package dnaQ.GUI;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Connections.SSHConnection;
import dnaQ.Models.TestQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ReportFrame extends JFrame {

    public MutationListFrame parent;

    private Integer frameWidth;
    private Integer frameHeight;

    private String usertestid;
    private TestQueue tq;
    private String filename;

    private JPanel mainPanel;
    private JPanel logoPanel;
    private JPanel reportPanel;
    private JPanel progressPanel;
    
    private JComboBox reportComboBox;
    private String reportname;
    private JButton submitButton;

    private JProgressBar progressBar;
    private JTextArea progressTextArea;
    private JButton downloadButton;

    private boolean reportCompleted;

    public ReportFrame(MutationListFrame mutationlistframe,String usertestid,TestQueue tq) throws Exception {

        super ("Request Report");
        this.parent = mutationlistframe;
        this.usertestid = usertestid;
        this.tq = tq;

        filename = tq.userid +"_"+tq.testid +"_RUN"+tq.run;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frameWidth = (GUICommonTools.screenWidth)/5;
        frameHeight = (GUICommonTools.screenHeight)/3;

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

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        logoPanel = new JPanel();

        reportPanel = new JPanel();
        reportComboBox = new JComboBox();
        submitButton = new JButton("Submit");

        progressPanel = new JPanel();
        progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));

        progressBar = new JProgressBar(0, 100);
        progressTextArea = new JTextArea();
        downloadButton = new JButton("Download Report");

        reportCompleted = false;

        reportname = "";
    }

    private void layoutReportComponents() {

        setMinimumSize(new Dimension(frameWidth, frameHeight));

        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(frameWidth -20, frameHeight/6);

        JLabel lblLogo= new JLabel(logoPicture);

        logoPanel.add(lblLogo);

        reportPanel.add(new Label("Select Report:"));
        reportPanel.add(reportComboBox);
        reportPanel.add(submitButton);

        progressPanel.add(new Label("Progress Monitor"));
        progressPanel.setSize(new Dimension(frameWidth - 100, frameHeight/2));

        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        progressTextArea.setText("Please select report to submit.\n");
        progressTextArea.setSize(new Dimension(frameWidth - 50, frameHeight/4));
        progressTextArea.isVisible();

        downloadButton.setVisible(false);

        progressPanel.add(progressBar);
        progressPanel.add(Box.createRigidArea(new Dimension(5,30)));
        progressPanel.add(progressTextArea);
        GUICommonTools.setBorder(progressPanel);
        progressPanel.add(downloadButton);

        mainPanel.add(logoPanel);
        mainPanel.add(reportPanel);
        mainPanel.add(progressPanel);

        add(mainPanel);

    }

    private void setReportsOption() throws Exception {

        ArrayList<String> reports = DatabaseConnections.getReportOptions();

        for(int i =0; i < reports.size(); i++){
            reportComboBox.addItem(reports.get(i));
        }
    }

    private void activateComponents(){

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
                    Integer selectedReport = reportComboBox.getSelectedIndex();
                    String temp = String.valueOf(reportComboBox.getSelectedItem());
                    reportname = temp;
                    reportname = reportname.replaceAll(" ", "_");

                    progressTextArea.append("You selected "+ temp+" report.\n");

                    reportSubmission(selectedReport);

                }catch (Exception e){
                    progressTextArea.append("Report not submitted, please try again");
                }
            }
        });

        downloadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{
                    SSHConnection.transferReportFromServer(reportname,filename);
                    progressTextArea.append("Your report has been downloaded\n");

                }catch (Exception e){
                    JOptionPane.showMessageDialog(ReportFrame.this, "Report not ready");
                }
            }
        });
    }

    private void reportSubmission(Integer report) throws Exception {
        if (report == 0){
            SSHConnection.generateReport(reportname,usertestid,filename);
            updateProgress();
        }
    }


    private void updateProgress() {
        Runnable runner = new Runnable() {
            public void run() {
                progressBar.setValue(0);

//            progressBar.setValue(50);

//            progressBar.setValue(100);

                int counter=0;
                while (counter < 100){
                    counter = counter + 1;

                    int c2=0;
                    while(c2<10000){
                        c2 = c2+1;
//                        System.out.println(c2);
                    }

                    progressBar.setValue(counter);
                }

                if (counter==100){
                    downloadButton.setVisible(true);
                }
            }
        };
        Thread t = new Thread(runner, "Progressbar");
        t.start();
    }
}