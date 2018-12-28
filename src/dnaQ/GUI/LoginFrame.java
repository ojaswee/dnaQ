package dnaQ.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.ArrayList;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
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

		int width_panel, height_panel;

		width_panel = 600;
		height_panel = 400;

		panel.setBackground(GUICommonTools.BackgroundColor2);
		setSize(width_panel, height_panel);
		panel.setLayout(new GridBagLayout());

		//fit logo as label background
		ImageIcon logoPicture = new ImageIcon(new ImageIcon("/home/ojaswee/masters_project/logo.png").getImage().getScaledInstance(width_panel/2, height_panel/2,Image.SCALE_SMOOTH));
		JLabel lblLogo= new JLabel(logoPicture);

		JPanel logo_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//		logo_panel.setBackground(GUICommonTools.BackgroundColor1);
		logo_panel.add(lblLogo);
		panel.add(logo_panel);

		JPanel lower_panel = new JPanel(new GridLayout(1,1));
		lower_panel.setBackground(GUICommonTools.BackgroundColor1);
		lower_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		JPanel login_panel = new JPanel(new GridLayout(6,1));
		login_panel.setBackground(GUICommonTools.BackgroundColor1);
		login_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		login_panel.setBorder(BorderFactory.createEmptyBorder(10,90,10,90));





		//credentials
		JLabel lblUsername = new JLabel("UserName");
		lblUsername.setFont(GUICommonTools.TAHOMA_BOLD_14);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(GUICommonTools.TAHOMA_BOLD_14);

		//login button
		loginButton.setFont(GUICommonTools.TAHOMA_BOLD_14);
		login_panel.add(lblUsername);
		login_panel.add(usernameTextField);
		login_panel.add(lblPassword);
		login_panel.add(passwordTextField);
		login_panel.add(new Label(""));
		login_panel.add(loginButton);

		lower_panel.add(login_panel);
		panel.add(lower_panel);

		add(panel);

//		getRootPane().setDefaultButton(loginButton);
//
//		Rectangle bounds = GUICommonTools.getBounds(this);
//		setLocation(bounds.width/2-getSize().width/2, bounds.height/2-getSize().height/2);
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
