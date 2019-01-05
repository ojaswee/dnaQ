package dnaQ.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.ArrayList;

import dnaQ.Models.Sample;
import dnaQ.Models.SampleList;
import dnaQ.Connections.SSHConnection;
import dnaQ.Connections.DatabaseConnections;


public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;

	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
    private JButton loginButton;

	public ArrayList<Sample> samples;
	public SampleList sampleList;


	/**
	 * Create login Frame application.
	 */
	public LoginFrame() {
		super("dnaQ Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createComponents();
		layoutLoginComponents();
		activateComponents();

		setLocationRelativeTo(null);
	}

	private void createComponents(){
		panel = new JPanel();

		loginButton = new JButton("Login");
		usernameTextField = new JTextField();
		passwordTextField = new JPasswordField();
	}
	
	private void layoutLoginComponents(){

		int widthPanel, heightPanel;

		widthPanel = 600;
		heightPanel = 400;

		panel.setBackground(GUICommonTools.BackgroundColor2);
		setSize(widthPanel, heightPanel);
		panel.setLayout(new GridBagLayout());

		//fit logo as label background
		ImageIcon logoPicture = new ImageIcon(new ImageIcon("/home/ojaswee/masters_project/logo.png").getImage().getScaledInstance(widthPanel/2, heightPanel/2,Image.SCALE_SMOOTH));
		JLabel lblLogo= new JLabel(logoPicture);

		JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		logoPanel.add(lblLogo);
		panel.add(logoPanel);

		JPanel lowerPanel = new JPanel(new GridLayout(1,1));
		lowerPanel.setBackground(GUICommonTools.BackgroundColor1);
		lowerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JPanel loginPanel = new JPanel(new GridLayout(6,1));
		loginPanel.setBackground(GUICommonTools.BackgroundColor1);
		loginPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		loginPanel.setBorder(BorderFactory.createEmptyBorder(10,90,10,90));


		//credentials
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(GUICommonTools.TAHOMA_BOLD_14);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(GUICommonTools.TAHOMA_BOLD_14);

		//login button
		loginButton.setFont(GUICommonTools.TAHOMA_BOLD_14);
		loginPanel.add(lblUsername);
		loginPanel.add(usernameTextField);
		loginPanel.add(lblPassword);
		loginPanel.add(passwordTextField);
		loginPanel.add(new Label(""));
		loginPanel.add(loginButton);

		lowerPanel.add(loginPanel);
		panel.add(lowerPanel);

		add(panel);
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
		String userName= usernameTextField.getText();
		String passwd = new String(passwordTextField.getPassword());

		userName = "admin";
		passwd = "admin";

		boolean success = DatabaseConnections.connectLogin(userName, passwd);
		
		if (success){
			
			DatabaseConnections.connect();
			SSHConnection.connect();
			System.out.println("Login Successful");

			samples = DatabaseConnections.getAllSample();
			sampleList = new SampleList(samples);
			SampleListFrame samplelistframe = new SampleListFrame(this,sampleList);
			dispose();
			samplelistframe.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null,"Invalid username or password");
		}

	}

}
