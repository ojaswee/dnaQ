package dnaQ.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UploadFrame extends JFrame{
    public JPanel uploadPanel;
    private JPanel openFilePanel;

    private LoginFrame parent;

    private JButton openButton;
    private JTextField fileLocationtextField;
    private JLabel lblupload;
    private JButton uploadButton;
    private JFileChooser fs;

    public UploadFrame(LoginFrame parent){
        this.parent = parent;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(parent);

        createComponents();
        layoutComponents();
        activateComponents();
    }

    private void createComponents(){
//        uploadPanel = new JPanel(new BoxLayout(uploadPanel,BoxLayout.Y_AXIS));
        uploadPanel = new JPanel();
        openFilePanel = new JPanel();

        lblupload = new JLabel();
        fileLocationtextField = new JTextField();
        openButton = new JButton("Open");

        uploadButton = new JButton("Upload");
    }

    private void layoutComponents() {

        int widthPanel, heightPanel;

        widthPanel = 600;
        heightPanel = 200;

        uploadPanel.setBackground(GUICommonTools.BackgroundColor2);
        setSize(widthPanel, heightPanel);

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getRectangularLogo(widthPanel, heightPanel/3);
        JLabel lblLogo= new JLabel(logoPicture);

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.add(lblLogo);

        // add fileseclector
//        fileLocationtextField = new JTextField();
        openFilePanel = new JPanel(new FlowLayout());

        lblupload = new JLabel("Select file to upload");
        lblupload.setFont(GUICommonTools.TAHOMA_BOLD_14);
        openFilePanel.add(lblupload);

        fileLocationtextField.setPreferredSize( new Dimension( 200, 24 ) );
        openFilePanel.add(fileLocationtextField);
        openFilePanel.add(openButton);

        JPanel previousUploadsPanel = new JPanel();
        JLabel lblpreviousPanel = new JLabel("Your previous uploads");
        previousUploadsPanel.add(lblpreviousPanel);

        uploadPanel.add(logoPanel);

        uploadPanel.add(openFilePanel);

        uploadPanel.add(uploadButton);

        uploadPanel.add(previousUploadsPanel);

        add(uploadPanel);
    }

    private void activateComponents(){
        openButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    fs = new JFileChooser(new File("/home/ojaswee"));
                    fs.setDialogTitle("Select a File");
                    fs.showSaveDialog(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}