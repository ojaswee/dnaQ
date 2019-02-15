package dnaQ.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReportFrame extends JFrame {

    public JPanel reportPanel;

    public JTextField reportTextField;

    public RequestReportFrame parent;

    public ReportFrame(RequestReportFrame requestreportframe) throws Exception{
        this.parent = requestreportframe;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createComponents();
        layoutReportComponents();
        display();

        pack();
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);
    }

    public void createComponents(){
        reportPanel = new JPanel();

        reportTextField = new JTextField();
    }

    public void layoutReportComponents(){

        reportPanel.setBackground(GUICommonTools.BackgroundColor1);


        reportPanel.add(new Label("Report"));
        reportPanel.setSize(500,500);

        reportTextField.setBounds(160, 74, 142, 30);

        reportPanel.add(reportTextField);
        add(reportPanel);
    }

    public void display(){

        try {
            Scanner read = new Scanner(new File("/home/ojaswee/masters_project/08_server_report_generator/" +
                    "transfered_files/output.txt"));
            while (read.hasNextLine()){
                System.out.println(read.nextLine());

                String value= "From Report dummy";
                reportTextField.setText(value);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
