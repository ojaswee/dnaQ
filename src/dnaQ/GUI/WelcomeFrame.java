package dnaQ.GUI;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Connections.SSHConnection;
import dnaQ.Models.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    private JPanel completedTestPanel;
    private JPanel processingTestPanel;

    private LoginFrame parent;

    private User currentuser;
    private ArrayList<Test> completedTest;
    private TestTable completedTestTable;
    private TestTableModel completedTestTableModel;

    private ArrayList<TestQueue>processingTest;
    private JTextArea processingTestTextArea;

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


    public WelcomeFrame(LoginFrame parent, User currentuser, ArrayList<Test> completedTest,
                        ArrayList<TestQueue>processingTest) throws Exception {

        super ("Welcome to dnaQ");
        this.parent = parent;
        this.currentuser = currentuser;
        this.completedTest = completedTest;
        this.processingTest= processingTest;


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(parent);

        createComponents();
        layoutComponents();

        getAllAvailableTestsName();
        getAllAvailableTestsType();

        activateComponents();

        pack();
        setResizable(true);
        setLocationRelativeTo(parent);
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

        completedTestPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        completedTestTableModel = new TestTableModel(completedTest);

        completedTestTable = new TestTable(this,completedTestTableModel);

        userTestScrollPane = new JScrollPane(completedTestTable);
        userTestScrollPane.setViewportView(completedTestTable);

        processingTestPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        processingTestTextArea = new JTextArea();
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

        //completed tests
        completedTestPanel.add(userTestScrollPane);



        //for tests that are in queue
        for (TestQueue a: processingTest){
            processingTestTextArea.append("("+a.testname +" , " + a.type + ")\n");
            processingTestTextArea.setFont(GUICommonTools.TAHOMA_BOLD_14);
        }
        processingTestTextArea.setEditable(false);
        processingTestTextArea.setPreferredSize(new Dimension(450,heightPanel/5));
        processingTestPanel.add(processingTestTextArea);



        mainPanel.add(logoPanel);
        mainPanel.add(userInfoPanel);
        mainPanel.add (openFilePanel);
        mainPanel.add(uploadPanel);
        mainPanel.add(completedTestPanel);
        mainPanel.add (processingTestPanel);

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
                    String testName = testNameComboBox.getSelectedItem().toString();
                    String testType = testTypeComboBox.getSelectedItem().toString();

                    String filePath = fs.getSelectedFile().getAbsolutePath();

                    new Uploads(currentuser.getUserID(),testName,testType,filePath);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Please select a file");
                    e.printStackTrace();
                }
            }
        });

        completedTestTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                String testname = completedTestTable.getValueAt(completedTestTable.getSelectedRow(), 0).toString();
                String testtype = completedTestTable.getValueAt(completedTestTable.getSelectedRow(), 1).toString();

                String testrun = completedTestTable.getValueAt(completedTestTable.getSelectedRow(), 2).toString();

                System.out.println(testname+"_"+testtype+"_"+testrun);

                if (! event.getValueIsAdjusting()) {

                    show2();
                }

            }
        });
    }

    private void show2(){

        try {
            mutations = DatabaseConnections.getAllMutation(completedTestTable.getValueAt(completedTestTable.getSelectedRow(),0).toString());
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

        ArrayList<String> type = DatabaseConnections.getAllAvailableTypeofTest();

        for(int i =0; i < type.size(); i++){
            testTypeComboBox.addItem(type.get(i));
        }
    }
}