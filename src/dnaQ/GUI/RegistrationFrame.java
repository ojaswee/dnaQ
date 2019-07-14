package dnaQ.GUI;

import dnaQ.Connections.DatabaseConnections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationFrame extends JFrame {

    private LoginFrame parent;

    private Integer frameWidth;
    private Integer frameHeight;

    private JPanel mainPanel;
    private JPanel logoPanel;
    private JPanel userInputPanel;

    private JLabel lblFirstname;
    private JLabel lblLastname;
    private JLabel lblemail;
    private JLabel lblpassword;

    private JTextField firstnameTextField;
    private JTextField lastnameTextField;
    private JTextField emailTextField;
    private JTextField passwordTextField;

    private JButton registerButton;

    public RegistrationFrame(LoginFrame parent){
        super("dnaQ Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.parent = parent;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUICommonTools.setSizeOfScreen();

        frameWidth = (GUICommonTools.screenWidth)/3;
        frameHeight = (GUICommonTools.screenHeight)/4;

        createComponents();
        layoutComponents();
        activateComponents();

        pack();
        setResizable(true);
        setLocationRelativeTo(parent);

    }
    private void createComponents(){
        mainPanel = new JPanel();
        logoPanel = new JPanel();
        userInputPanel = new JPanel(new GridLayout(5,2,0,20));

        lblFirstname = new JLabel("Firstname");
        lblLastname = new JLabel("Lastname");
        lblemail = new JLabel("Email");
        lblpassword = new JLabel("Password");

        firstnameTextField = new JTextField();
        lastnameTextField = new JTextField();
        emailTextField = new JTextField();
        passwordTextField = new JTextField();

        registerButton = new JButton("Register");

    }
    private void layoutComponents(){
        mainPanel.setBackground(GUICommonTools.BackgroundColor2);
        setSize(frameWidth, frameHeight);

        //fit logo as label background
        ImageIcon logoPicture = GUICommonTools.getSquareLogo(frameWidth/2, frameHeight-80);
        JLabel lblLogo= new JLabel(logoPicture);
        logoPanel.add(lblLogo);

        lblFirstname.setFont(GUICommonTools.TAHOMA_BOLD_16);
        lblLastname.setFont(GUICommonTools.TAHOMA_BOLD_16);
        lblemail.setFont(GUICommonTools.TAHOMA_BOLD_16);
        lblpassword.setFont(GUICommonTools.TAHOMA_BOLD_16);
        JLabel lblSpace= new JLabel("");

        firstnameTextField.setColumns(2);

        userInputPanel.add(lblFirstname);
        userInputPanel.add(firstnameTextField);

        userInputPanel.add(lblLastname);
        userInputPanel.add(lastnameTextField);

        userInputPanel.add(lblemail);
        userInputPanel.add(emailTextField);

        userInputPanel.add(lblpassword);
        userInputPanel.add(passwordTextField);

        userInputPanel.add(lblSpace);
        userInputPanel.add(registerButton);
        userInputPanel.setBackground(GUICommonTools.BackgroundColor1);
        userInputPanel.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));

        mainPanel.add(logoPanel);

        mainPanel.add (userInputPanel);

        add(mainPanel);
    }

    private void activateComponents(){
        registerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {

                try{
                    if(checkForEmptyTextBox()){

                        JOptionPane.showMessageDialog(null, "Please provide information for all fields");

                    } else {
                        DatabaseConnections.registerNewUser(firstnameTextField.getText(),
                                lastnameTextField.getText(),
                                emailTextField.getText(), passwordTextField.getText());

                        JOptionPane.showMessageDialog(null, "New user created");
                        dispose();
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Email already exist");
                }
            }
        });
    }


    private boolean checkForEmptyTextBox() {

        boolean isempty = false;

        if (firstnameTextField.getText().equals("") ||
                lastnameTextField.getText().equals("") ||
                emailTextField.getText().equals("") ||
                passwordTextField.getText().equals("")) {

            isempty = true;
            }
        return isempty;
        }

}
