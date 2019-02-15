package dnaQ.GUI;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Models.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import dnaQ.GUI.tables.*;

public class WelcomeFrame extends JFrame{

    private JPanel mainPanel;
    private JPanel logoPanel;
    private JPanel userInfoPanel;
    private JPanel openFilePanel;
    private JPanel uploadPanel;
    private JPanel queueTablePanel;

    private LoginFrame parent;

    private User currentuser;

    private ArrayList<TestQueue> queueTable;
    private TestQueueTable testQueueTable;
    private TestQueueTableModel queueTableModel;

    private JButton openButton;
    private JTextField fileNameTextField;
    private JLabel lblupload;
    private JFileChooser fs;
    private JComboBox testNameComboBox;
    private JComboBox testTypeComboBox;


    private JButton uploadButton;


    private JScrollPane userTestScrollPane;

    public ArrayList<Mutation> mutations;

    public MutationList mutationList;

//    private Thread refreshTestQueueTable;
//    private final int secondsToSleep = 30;
//    private long timeLastRefreshed = 0;
//    private JMenuItem refreshLabel;

    private JButton refreshButton;


    public WelcomeFrame(LoginFrame parent, User currentuser, ArrayList<TestQueue> queueTable) throws Exception {

        super ("Welcome to dnaQ");
        this.parent = parent;
        this.currentuser = currentuser;
        this.queueTable = queueTable;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();
        layoutComponents();

        getAllAvailableTestsName();
        getAllAvailableTestsType();

        activateComponents();

        pack();
        setResizable(true);
        setLocationRelativeTo(parent);

//        setupTestQueueTableUpdate();
    }

    private void createComponents(){
        mainPanel = new JPanel();

        logoPanel = new JPanel();

        userInfoPanel = new JPanel();

        uploadPanel = new JPanel();
        openFilePanel = new JPanel();
        lblupload = new JLabel();
        fileNameTextField = new JTextField();

        testNameComboBox = new JComboBox();
        testTypeComboBox = new JComboBox();

        openButton = new JButton("Open");
        uploadButton = new JButton("Upload");

        queueTablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        queueTableModel = new TestQueueTableModel(queueTable);

        testQueueTable = new TestQueueTable(this,queueTableModel);

        userTestScrollPane = new JScrollPane(testQueueTable);
        userTestScrollPane.setViewportView(testQueueTable);

        refreshButton = new JButton("Refresh");

    }

    private void layoutComponents() {

        int widthPanel, heightPanel;

        widthPanel = 600;
        heightPanel = 500;

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

        JLabel lblTestType = new JLabel("Select test type");
        uploadPanel.add(lblTestType);
        uploadPanel.add(testNameComboBox);
        uploadPanel.add(testTypeComboBox);

        uploadPanel.add(uploadButton);

        uploadPanel.add(refreshButton);

        //tests in queue
        queueTablePanel.add(userTestScrollPane);

        mainPanel.add(logoPanel);
        mainPanel.add(userInfoPanel);
        mainPanel.add (openFilePanel);
        mainPanel.add(uploadPanel);
        mainPanel.add(queueTablePanel);

        add(mainPanel);
    }

    private void activateComponents(){
        openButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    fs = new JFileChooser(new File("/home/ojaswee/masters_project/01_data/"));
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
                    String testName = testNameComboBox.getSelectedItem().toString();

                    String testType = testTypeComboBox.getSelectedItem().toString();

                    String filePath = fs.getSelectedFile().getAbsolutePath();
                    String fileName = fileNameTextField.getText();

                    new Uploads(currentuser.getUserID(),testName,testType,filePath,fileName);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Please select a file");
                    e.printStackTrace();
                }
            }
        });

        testQueueTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                String testid = testQueueTable.getValueAt(testQueueTable.getSelectedRow(), 0).toString();
                String run = testQueueTable.getValueAt(testQueueTable.getSelectedRow(), 3).toString();

                if (! event.getValueIsAdjusting()) {

                    displayMutationFrame(testid, run);
                }

            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent arg0){
//                JOptionPane.showMessageDialog(null, "Refresh button clicked");
                try {
                    queueTable = DatabaseConnections.getAllProcessingTest(currentuser.getUserID());
//                    queueTablePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

//                    queueTableModel = new TestQueueTableModel(queueTable);

                    queueTableModel.fireTableDataChanged();
                    testQueueTable.repaint();

//                    userTestScrollPane = new JScrollPane(testQueueTable);
//                    userTestScrollPane.setViewportView(testQueueTable);
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void displayMutationFrame(String testid, String run){
        String usertestid = "";
        try {
            usertestid = DatabaseConnections.getUsertestid(currentuser.getUserID(),testid,run);
            System.out.println(usertestid);
            mutations = DatabaseConnections.getAllMutation(usertestid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mutationList = new MutationList(mutations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MutationListFrame mutationlistframe = new MutationListFrame(this,mutationList);
        mutationlistframe.setVisible(true);

    }

    private void getAllAvailableTestsName() throws Exception {

        ArrayList<String> name = DatabaseConnections.getAllAvailableNameofTest();

        for(int i =0; i < name.size(); i++){
            testNameComboBox.addItem(name.get(i));
        }
    }

    private void getAllAvailableTestsType() throws Exception {

        ArrayList<String> type = DatabaseConnections.getAllAvailableTypeofTest(testNameComboBox.getSelectedItem().toString());

        for(int i =0; i < type.size(); i++){
            testTypeComboBox.addItem(type.get(i));
        }
    }
}

