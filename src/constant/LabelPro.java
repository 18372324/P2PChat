package constant;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * some methods to create a new label
 */
public class LabelPro {
	public static JLabel createLabel(int x,int y, int w, int h) {
		JLabel lb = new JLabel();
		lb.setBounds(x, y, w, h);
		lb.setForeground(Color.white);
		return lb;
	}
	
	public static JLabel createLabel(String str, int x,int y, int w, int h) {
		JLabel lb = new JLabel(str);
		lb.setBounds(x, y, w, h);
		lb.setForeground(Color.white);
		return lb;
	}
	
	public static JLabel createLabel(ImageIcon img, int x,int y, int w, int h) {
		JLabel lb = new JLabel(img);
		lb.setBounds(x, y, w, h);
		lb.setForeground(Color.white);
		return lb;
	}
	
	public static JLabel createLabel(String str,ImageIcon img, int x,int y, int w, int h) {
		JLabel lb = new JLabel(str);
		lb.setBounds(x, y, w, h);
		lb.setIcon(img);
		lb.setForeground(Color.white);
		return lb;
	}
	
	
}
