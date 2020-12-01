package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import constant.Constant;
import constant.LabelPro;
import main.ClientMain;
import main.ServerMain;

/**
 * 
 * @ClassName:  LoginFrame   
 * @Description: implement Login feature   
 * @author: Shi Xuhui 18206370
 */
public class LoginFrame extends JFrame implements MouseListener{
	
	/**
	 * serial ID
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panel_bck; 									// background panel
	
	private JLabel lb_background, lb_mini, lb_close, lb_title;	//background gif, minimize, close, title
	private JLabel lb_user;										//show username
	private JLabel lb_userLine;									//the underline for username, password, code
	private JLabel lb_login;									//the login button
	
	private JTextField tf_user;									//username
	private JRadioButton rb_client, rb_server;					//the Radio Button for client end and server end
	
	private Point origin = new Point();//for drag the windows
	
	private Boolean user_click = false;
	
	
	public LoginFrame() {
		//add components
		lb_close = LabelPro.createLabel(Constant.ICON_CLOSE, 396, 3, 32, 32);
		lb_close.setBackground(Color.RED);
		lb_close.addMouseListener(this);
		this.add(lb_close);
		
		lb_mini = LabelPro.createLabel(Constant.ICON_MINI, 364, 2, 32, 32);
		lb_mini.setBackground(Color.GRAY);
		lb_mini.addMouseListener(this);
		this.add(lb_mini);
		
		lb_title = LabelPro.createLabel("Login", Constant.ICON_LOGIN, 10, 10, 112, 32);
		lb_title.setFont(Constant.TITLE);
		this.add(lb_title);
		
		
		/**
		 * user name
		 */
		lb_user = LabelPro.createLabel(Constant.ICON_USER1, 100, 170, 20, 20);
		this.add(lb_user);
		
		tf_user = new JTextField(BaseView.DEFAULT_USERNAME);
		tf_user.setBounds(130, 160, 225, 40);
		tf_user.setFont(Constant.NORMAL);
		tf_user.setForeground(Color.gray);
		// Transparent Background
		tf_user.setOpaque(false);
		// remove the border
		tf_user.setBorder(null);
		tf_user.addMouseListener(this);
		
		tf_user.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {//lost focus
				lb_user.setIcon(Constant.ICON_USER1);
				lb_userLine.setIcon(Constant.ICON_GREYLINE);
				user_click = false;
				if (tf_user.getText().isEmpty()) {// Determine whether it is empty (in order to set the default prompt)
					tf_user.setForeground(Color.gray);
					tf_user.setText(BaseView.DEFAULT_USERNAME);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {//gain the focus
				tf_user.setForeground(Color.black);
				lb_userLine.setIcon(Constant.ICON_BLUELINE);
				lb_user.setIcon(Constant.ICON_USER2);
				user_click = true;
				if (tf_user.getText().equals(BaseView.DEFAULT_USERNAME)) {
					tf_user.setText("");
				} else {
					tf_user.selectAll();
				}
				
			}
		});
		
		this.add(tf_user);
		
		lb_userLine = LabelPro.createLabel(Constant.ICON_GREYLINE, 100, 190, 255, 10);
		this.add(lb_userLine);
		
		/**
		 * Radio buttons
		 */
		//button group
		ButtonGroup group = new ButtonGroup();
		
		rb_client = new JRadioButton("Client");
		rb_client.setBounds(100, 210, 100, 30);
		rb_client.setFont(Constant.NORMAL);
		rb_client.setForeground(Color.cyan);
		rb_client.setOpaque(false);
		rb_client.setSelected(true);
		
		group.add(rb_client);
		this.add(rb_client);
		
		
		rb_server = new JRadioButton("Server");
		rb_server.setBounds(100, 250, 100, 30);
		rb_server.setFont(Constant.NORMAL);
		rb_server.setForeground(Color.cyan);
		rb_server.setOpaque(false);
		
		group.add(rb_server);
		this.add(rb_server);
		
		
		
		/**
		 * Login button (implemented by label)
		 */
		lb_login = LabelPro.createLabel("Login", 100, 300, 257, 35);
		lb_login.setVerticalAlignment(JLabel.CENTER);
		lb_login.setHorizontalAlignment(JLabel.CENTER);
		lb_login.setFont(new Font("Fixedsys", Font.BOLD, 16));
		lb_login.setBackground(new Color(9, 173, 243));
		lb_login.setOpaque(true);
		lb_login.addMouseListener(this);
		this.add(lb_login);
		
		
		/**
		 * bottom panel
		 */
		panel_bck = new JPanel();
		panel_bck.setBounds(0, 125, 430, 400);
		panel_bck.setBackground(Color.WHITE);
		this.add(panel_bck);
		
		/**
		 * top background
		 */
		lb_background = LabelPro.createLabel(Constant.ICON_NIGHT,-35, -123, 500, 250);
		this.add(lb_background);
		
		
		/**
		 * drag the windows
		 */
		this.addMouseListener(this);
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});
		
		
		//set null layout
		this.setLayout(null);
		//set Frame size
		this.setSize(430, 360);
		//set icon image - login
		this.setIconImage(Toolkit.getDefaultToolkit().createImage("img/login.png"));
		//set the frame centered
		this.setLocationRelativeTo(null);
		//remove the top
		this.setUndecorated(true);
		//get the focus
		this.setFocusable(true);
		//set background color
		this.setBackground(Color.WHITE);
		//close operation
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		//cannot be resized
		this.setResizable(false);
		//be visible
		this.setVisible(true);
		
		
		
	}
	
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {//Hover somewhere
		if(e.getSource() == tf_user && !user_click) {
			lb_userLine.setIcon(Constant.ICON_BLACKLINE);
		}
		else if(e.getSource() == lb_close) {
			lb_close.setOpaque(true);
		}
		else if(e.getSource() == lb_mini) {
			lb_mini.setOpaque(true);
		}
		else if(e.getSource() == lb_login) {
			lb_login.setBackground(new Color(5, 186, 251));
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {//move away
		if(e.getSource() == tf_user && !user_click) {
			lb_userLine.setIcon(Constant.ICON_GREYLINE);
		}
		else if(e.getSource() == lb_close) {
			lb_close.setOpaque(false);
		}
		else if(e.getSource() == lb_mini) {
			lb_mini.setOpaque(false);
		}
		else if(e.getSource() == lb_login) {
			lb_login.setBackground(new Color(9, 173, 243));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//if click minimize
		if(e.getSource() == lb_mini) {
			setExtendedState(JFrame.ICONIFIED);
		}
		//if click close
		else if(e.getSource() == lb_close) {
			System.exit(0);
		}
		//if drag the window
		else if(e.getSource() == this) {
			origin.x = e.getX();
			origin.y = e.getY();
		}
		//if login
		else if(e.getSource() == lb_login) {
			if(rb_client.isSelected()) {
				String username = tf_user.getText().trim();
				  
		        if (username == null || username.length() == 0) {
		        	JOptionPane.showMessageDialog(this, "The name can't be empty", "Error", JOptionPane.ERROR_MESSAGE); 
		            return;  
		        }  
				ClientMain.getInstance(username);
			}else {
				ServerMain.getInstance();
			}
			this.dispose();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
