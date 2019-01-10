package dnaQ.GUI;

import dnaQ.Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class WelcomeFrame extends JFrame{

    private JPanel panel;
    private JPanel logoPanel;
    private JPanel userInfoPanel;
    private JPanel uploadPanel;
    private JPanel openFilePanel;

    private LoginFrame parent;

    private User currentuser;

    private JButton openButton;
    private JTextField fileNameTextField;
    private JLabel lblupload;
    private JButton uploadButton;
    private JFileChooser fs;


    public WelcomeFrame(LoginFrame parent, User currentuser){
        this.parent = parent;
        this.currentuser = currentuser;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(parent);

        createComponents();
        layoutComponents();
        activateComponents();
    }

    private void createComponents(){
        panel = new JPanel();
        logoPanel = new JPanel();

        userInfoPanel = new JPanel();

        uploadPanel = new JPanel();
        lblupload = new JLabel();
        fileNameTextField = new JTextField();
        openButton = new JButton("Open");
        uploadButton = new JButton("Upload");

        openFilePanel = new JPanel();
    }

    private void layoutComponents() {

        int widthPanel, heightPanel;

        widthPanel = 600;
        heightPanel = 600;

        setSize(widthPanel, heightPanel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));




        uploadPanel.setBackground(GUICommonTools.BackgroundColor2);

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(widthPanel, heightPanel/5);
        JLabel lblLogo= new JLabel(logoPicture);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.add(lblLogo);

        //userInfo panel
        userInfoPanel.setBackground(GUICommonTools.BackgroundColor2);
        JLabel lblHelloUser = new JLabel();
        lblHelloUser.setFont(GUICommonTools.TAHOMA_BOLD_16);
        lblHelloUser.setText("Hello "+ currentuser.getUserName() +
                "   userid: " + currentuser.getUserID());

        userInfoPanel.add(lblHelloUser);

        // upload panel
        openFilePanel = new JPanel(new FlowLayout());

        lblupload = new JLabel("Select file to upload");
        lblupload.setFont(GUICommonTools.TAHOMA_BOLD_14);
        openFilePanel.add(lblupload);

        fileNameTextField.setPreferredSize( new Dimension( 200, 24 ) );
        openFilePanel.add(fileNameTextField);
        openFilePanel.add(openButton);

        JPanel previousUploadsPanel = new JPanel();
        JLabel lblpreviousPanel = new JLabel("Your previous uploads");
        previousUploadsPanel.add(lblpreviousPanel);

        uploadPanel.add(openFilePanel);
        uploadPanel.add(uploadButton);

        panel.add(logoPanel);
        panel.add(userInfoPanel);
        panel.add(uploadPanel);
        panel.add(previousUploadsPanel);

        add(panel);
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