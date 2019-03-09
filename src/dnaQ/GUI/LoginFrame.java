package dnaQ.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.ArrayList;

import dnaQ.Models.*;
import dnaQ.Connections.DatabaseConnections;


public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private Integer frameWidth;
	private Integer frameHeight;
	
	private JPanel mainPanel;
	private JPanel logoPanel;

	private JTextField emailTextField;
	private JPasswordField passwordTextField;
    private JButton loginButton;

	/**
	 * Create login Frame application.
	 */
	public LoginFrame() {
		super("dnaQ Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GUICommonTools.setSizeOfScreen();

		frameWidth = (GUICommonTools.screenWidth)/7;
		frameHeight = (GUICommonTools.screenHeight)/4;

		createComponents();
		layoutLoginComponents();
		activateComponents();

		setLocationRelativeTo(null);
	}

	private void createComponents(){
		mainPanel = new JPanel();
		logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		loginButton = new JButton("Login");
		emailTextField = new JTextField();
		passwordTextField = new JPasswordField();
	}
	
	private void layoutLoginComponents(){

		mainPanel.setBackground(GUICommonTools.BackgroundColor2);
		setSize(frameWidth, frameHeight);

		//fit logo as label background
		ImageIcon logoPicture = GUICommonTools.getSquareLogo(frameWidth/2, frameHeight-70);

		JLabel lblLogo= new JLabel(logoPicture);

		logoPanel.add(lblLogo);
		mainPanel.add(logoPanel);

		JPanel lowerPanel = new JPanel(new GridLayout(1,1));
		lowerPanel.setBackground(GUICommonTools.BackgroundColor1);
		lowerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JPanel loginPanel = new JPanel(new GridLayout(6,1));
		loginPanel.setBackground(GUICommonTools.BackgroundColor1);
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(20,70,20,70));


		//credentials
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(GUICommonTools.TAHOMA_BOLD_14);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(GUICommonTools.TAHOMA_BOLD_14);

		//login button
		loginButton.setFont(GUICommonTools.TAHOMA_BOLD_14);
		loginPanel.add(lblUsername);
		loginPanel.add(emailTextField);
		loginPanel.add(lblPassword);
		loginPanel.add(passwordTextField);
		loginPanel.add(new Label(""));
		loginPanel.add(loginButton);

		lowerPanel.add(loginPanel);
		mainPanel.add(lowerPanel);

		add(mainPanel);
	}

	private void activateComponents(){
		loginButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		});
	}
	
	private void login() throws Exception{
		String email= emailTextField.getText();
		String passwd = new String(passwordTextField.getPassword());

		email = "admin@dnaq.com";
		passwd = "admin";

		//User with no userHistory
//		email = "maryanderson@dnaq.com";
//		passwd = "mary";

		User currentuser = null;

		if (!(email.isEmpty()) && !(passwd.isEmpty())) {
			currentuser = DatabaseConnections.connectLogin(email, passwd);
		}

		else {
			if (email.isEmpty()){
				JOptionPane.showMessageDialog(null,"Email is empty");
			}
			else {
				JOptionPane.showMessageDialog(null, "Password is empty");
			}
		}

		if (currentuser.getUserID().length()>0){

			System.out.println("Login Successful");

			//create an arraylist of all queue items before moving to welcome frame
			ArrayList <TestQueue> testQueue = DatabaseConnections.getAllProcessingTest(currentuser.getUserID().toString());

			WelcomeFrame welcomeFrame = new WelcomeFrame(this, currentuser,testQueue);

			dispose();
			welcomeFrame.setVisible(true);

		}
		else {
			JOptionPane.showMessageDialog(null,"Invalid email or password");
		}

	}

}
