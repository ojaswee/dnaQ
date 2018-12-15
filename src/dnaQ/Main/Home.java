package dnaQ.Main;

import dnaQ.GUI.LoginFrame;

import javax.swing.*;

public class Home {

	public static void main(String[] args) throws Exception {
		try {

			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

		} catch(Exception e){
			//Error loading windows theme. Using Java default.
		}
				LoginFrame window = new LoginFrame();
				window.setVisible(true);
				window.setResizable(false);
	}
}
