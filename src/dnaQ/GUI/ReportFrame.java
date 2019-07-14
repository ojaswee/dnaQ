package dnaQ.GUI;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Connections.SSHConnection;
import dnaQ.Models.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;


public class ReportFrame extends JFrame {

    public MutationListFrame parent;

    private Integer frameWidth;
    private Integer frameHeight;

    private String usertestid;
    private MutationList mutationList;
    private TestQueue testqueue;

    private JPanel mainPanel;
    private JPanel logoPanel;
    private JPanel reportPanel;
    private JPanel progressPanel;
    
    private JComboBox reportComboBox;
    private Report report;
    private JButton submitButton;

    private JProgressBar progressBar;
    private JTextArea progressTextArea;
    private JButton downloadButton;

    private boolean reportCompleted;

    public ReportFrame(MutationListFrame mutationlistframe,MutationList mutationList,
                       String usertestid,TestQueue testqueue) throws Exception {

        super ("Request Report");
        this.parent = mutationlistframe;

        this.usertestid = usertestid;
        this.mutationList=mutationList;
        this.testqueue = testqueue;


        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frameWidth = (GUICommonTools.screenWidth)/4;
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

        mainPanel.add(progressPanel);

        add(mainPanel);


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
    }

    private void setReportsOption() throws Exception {

        reportComboBox.addItem("--Select Report Type--");

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

                    reportSubmission();

                }catch (Exception e){
                    progressTextArea.append("Report not submitted, please try again");
                }
            }
        });

        downloadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try{

                    if(downloadButton.getText().equals("Open Report")){

                        JFileChooser  fs = new JFileChooser(new File(GUICommonTools.userDownloadFolder()));
                        fs.setDialogTitle("Save Report");
                        fs.showSaveDialog(null);

                    }else {

                        String fileLocation = "";

                        if (report.getReportName().equals("Global_Mutation_Pattern")) {
                            fileLocation = "COMMON_RUN_FILES/Global_Mutation_Pattern.pdf";

                        } else if (report.getReportName().equals("Gene_Report")) {
                            //create condition_gene.csv file locally
                            fileLocation = report.getReportOriginator()+"/Report/"+
                                    report.getReportIdentifier() +"/"+
                                    report.getReportOriginator()+"_"+report.getReportIdentifier() +
                                    "_Result.pdf";
                        }

                        SSHConnection.transferReportFromServer(fileLocation);
                        progressTextArea.append("Your report has been downloaded\n");
                        downloadButton.setText("Open Report");
                    }


                }catch (Exception e){
                    JOptionPane.showMessageDialog(ReportFrame.this, "Report not ready");
                }
            }
        });
    }



    private void reportSubmission() throws Exception {

        String reportname= String.valueOf(reportComboBox.getSelectedItem()).replaceAll(" ", "_");

        Report report = new Report(reportname,mutationList);

        this.report = report;

        setReportIdentifier(report);
        report.setReportOriginator(testqueue.userid +"_"+ testqueue.testid +"_RUN"+ testqueue.run);



        progressTextArea.append("You selected "+ reportname+" report.\n");


        if (reportComboBox.getSelectedItem().toString().equals("Global Mutation Pattern")){

            progressTextArea.append("Click download button to generate Mutation Pattern report....\n");

        } else if(reportComboBox.getSelectedItem().toString().equals("Gene Report")){

            ArrayList<Mutation> selectedMutation = new ArrayList<Mutation>();


            for(Mutation m:mutationList.getMutations()){

                if(m.isSelected()){
                    selectedMutation.add(m);
                }

            }



            if (selectedMutation.size()<1){

                String theMessage ="You did not select any mutation to report. " +
                        "Do you want to generate report for top 3 mutations from your filtered result?";
                int result = JOptionPane.showConfirmDialog((Component) null, theMessage,
                        "alert", JOptionPane.OK_CANCEL_OPTION);

                if(result==0){

                    report.generateInputList(true);
                }

            }else{
                report.generateInputList(false);

            }

            report.createReportFileLocally();

            SSHConnection.generateReport(report);
        }

        updateProgress(reportname);
    }


    private void updateProgress(String reportname) {
        Runnable runner = new Runnable() {

            public void run() {
                    progressBar.setValue(0);
                    int counter = 0;

                    while (counter < 1000) {
                        counter = counter + 1;

                        progressBar.setValue(counter);
                    }

                    if (counter == 1000) {
                        downloadButton.setVisible(true);
                    }
            }
        };

        Thread t = new Thread(runner, "Progressbar");
        t.start();
    }


    private void setReportIdentifier( Report report){

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        report.setReportIdentifier(dateFormat.format(date));

    }
}