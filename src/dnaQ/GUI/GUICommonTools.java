package dnaQ.GUI;

import javax.swing.*;
import java.awt.*;


public class GUICommonTools {
	public static Font TAHOMA_BOLD_16 = new Font("Tahoma", Font.BOLD, 16);
	public static Font TAHOMA_BOLD_14 = new Font("Tahoma", Font.BOLD, 14);

	public static Color BackgroundColor1 = new java.awt.Color(176, 26, 159);
	public static Color BackgroundColor2 = new java.awt.Color(251, 255, 255);


	public static ImageIcon logo1 = new ImageIcon();
	public static ImageIcon logo2 = new ImageIcon();

	public static Integer screenWidth;
	public static Integer screenHeight;


	public static Rectangle getBounds(Component componentOrNull){
		Insets insets;
	    Rectangle bounds;

	    if (componentOrNull == null) {
	        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	        insets = Toolkit.getDefaultToolkit().getScreenInsets(ge.getDefaultScreenDevice().getDefaultConfiguration());
	        bounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
	    } else {
	        GraphicsConfiguration gc = componentOrNull.getGraphicsConfiguration();
	        insets = componentOrNull.getToolkit().getScreenInsets(gc);
	        bounds = gc.getBounds();
	    }
	    bounds.x += insets.left;
	    bounds.y += insets.top;
	    bounds.width -= (insets.left + insets.right);
	    bounds.height -= (insets.top + insets.bottom);
	    return bounds;
	}

	public static String getUsernameFromOS (){
		String user = "ojasweed";
		return user;
	}

	public static ImageIcon getSquareLogo(Integer width, Integer height){
		logo1 = new ImageIcon(new ImageIcon("/home/"+getUsernameFromOS()+"/dnaq/images/logo.png").getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH));
		return logo1;
	}

	public static ImageIcon getRectangularLogo(Integer width, Integer height){
		logo2 = new ImageIcon(new ImageIcon("/home/"+getUsernameFromOS()+"/dnaq/images/logo_2.png").getImage().getScaledInstance(width, height,Image.SCALE_SMOOTH));
		return logo2;
	}


	public static void setSizeOfScreen(){
		screenWidth = 0; screenHeight = 0;

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		screenWidth = screenSize.width;
		screenHeight = screenSize.height;

	}

	public static void setBorder(JPanel currentPanel){
		currentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public static String userDownloadFolder (){
		String downloadsFolder = "/home/"+getUsernameFromOS()+"/Downloads/";
		return downloadsFolder;
	}



}