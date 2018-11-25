package dnaQ.GUI;

import java.awt.*;

public class GUICommonTools {
	public static Font TAHOMA_BOLD_16 = new Font("Tahoma", Font.BOLD, 16);
	public static Font TAHOMA_BOLD_14 = new Font("Tahoma", Font.BOLD, 14);
	public static Color BackgroundColor1 = new java.awt.Color(67, 176, 49);

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

}