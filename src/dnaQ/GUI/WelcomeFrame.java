package dnaQ.GUI;

import dnaQ.Models.Test;
import dnaQ.Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import dnaQ.GUI.tables.*;

public class WelcomeFrame extends JFrame{

    private JPanel mainPanel;
    private JPanel logoPanel;
    private JPanel userInfoPanel;
    private JPanel openFilePanel;
    private JPanel uploadPanel;
    private JPanel userHistoryPanel;

    private LoginFrame parent;

    private User currentuser;
    private ArrayList<Test> userTest;

    private TestTable userTestTable;
    private TestTableModel userTestTableModel;

    private JButton openButton;
    private JTextField fileNameTextField;
    private JLabel lblupload;
    private JButton uploadButton;
    private JFileChooser fs;

    private JScrollPane userTestScrollPane;


    public WelcomeFrame(LoginFrame parent, User currentuser, ArrayList<Test> userTest){
        this.parent = parent;
        this.currentuser = currentuser;
        this.userTest = userTest;


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(parent);

        createComponents();
        layoutComponents();
        activateComponents();
    }

    private void createComponents(){
        mainPanel = new JPanel();
        
        logoPanel = new JPanel();
        
        userInfoPanel = new JPanel();
     
        uploadPanel = new JPanel();
        openFilePanel = new JPanel();
        lblupload = new JLabel();
        fileNameTextField = new JTextField();
        
        openButton = new JButton("Open");
        uploadButton = new JButton("Upload");

        userHistoryPanel = new JPanel();

        userTestTableModel = new TestTableModel(userTest);

        userTestTable = new TestTable(this,userTestTableModel);

        userTestScrollPane = new JScrollPane(userTestTable);
        userTestScrollPane.setViewportView(userTestTable);

    }

    private void layoutComponents() {

        int widthPanel, heightPanel;

        widthPanel = 600;
        heightPanel = 600;

        setSize(widthPanel, heightPanel);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));


        uploadPanel.setBackground(GUICommonTools.BackgroundColor2);

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(widthPanel, heightPanel/5);
        JLabel lblLogo= new JLabel(logoPicture);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.add(lblLogo);

        //userInfo mainPanel
        userInfoPanel.setBackground(GUICommonTools.BackgroundColor2);
        JLabel lblHelloUser = new JLabel();
        lblHelloUser.setFont(GUICommonTools.TAHOMA_BOLD_16);
        lblHelloUser.setText("Hello "+ currentuser.getFirstname() + "   userid: " + currentuser.getUserID());

        userInfoPanel.add(lblHelloUser);

        // upload mainPanel
        openFilePanel = new JPanel(new FlowLayout());

        lblupload = new JLabel("Select file to upload");
        lblupload.setFont(GUICommonTools.TAHOMA_BOLD_14);
        openFilePanel.add(lblupload);

        fileNameTextField.setPreferredSize( new Dimension( 200, 24 ) );
        openFilePanel.add(fileNameTextField);
        openFilePanel.add(openButton);

        uploadPanel.add(openFilePanel);
        uploadPanel.add(uploadButton);

        userHistoryPanel.add(userTestScrollPane);

        mainPanel.add(logoPanel);
        mainPanel.add(userInfoPanel);
        mainPanel.add(uploadPanel);
        mainPanel.add(userHistoryPanel);


        add(mainPanel);
    }

    private void activateComponents(){
        openButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    fs = new JFileChooser(new File("/home/ojaswee"));
                    fs.setDialogTitle("Select a File");
                    fs.showSaveDialog(null);

                    fileNameTextField.setText(fs.getSelectedFile().getName());

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        uploadButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String filePath = fs.getSelectedFile().getAbsolutePath();

        // TODO send to server from local computer
                    JOptionPane.showMessageDialog(null, filePath);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Please select a file");
                    e.printStackTrace();
                }
            }
        });
    }
}