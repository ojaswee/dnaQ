package dnaQ.GUI;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportFrame extends JFrame {

    public JPanel reportPanel;
    public JComboBox reportChoicesBox;
    public JButton reportSubmitButton;

    public SampleListFrame parent;

    public ReportFrame(SampleListFrame samplelistframe) {

        this.parent = samplelistframe;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createComponents();
        layoutReportComponents();
//        activateComponents();

        pack();
        setAlwaysOnTop(true);
        setResizable(true);
        setLocationRelativeTo(parent);

    }

    private void createComponents() {

        reportPanel = new JPanel(new GridLayout(0, 1));

        String[] reportStrings = {"Cat", "Dog" };
        reportChoicesBox = new JComboBox(reportStrings);

        reportSubmitButton = new JButton("Submit");


    }

    private void layoutReportComponents() {

        setMinimumSize(new Dimension(500, 500));
        setLayout(new GridLayout(0, 1));

        reportPanel.add(new Label(""));

        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(new Label("Select Report:"));
        dropdownPanel.add(reportChoicesBox);
        dropdownPanel.add(reportSubmitButton);

        JPanel progressPanel = new JPanel();
        progressPanel.add(new Label("Progress Monitor"));
        progressPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        reportPanel.add(dropdownPanel);
        reportPanel.add(progressPanel);
        add(reportPanel);

    }


//    private void activateComponents(){
//
//        reportSubmitButton.addActionListener(new ActionListener(){
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                try{
//
//                    reportSubmission();
//
//                }catch (Exception e){
//                    JOptionPane.showMessageDialog(ReportFrame.this, e.getMessage());
//                }
//            }
//        });
//    }
//
//    private void reportSubmission(){
//
//    }
}