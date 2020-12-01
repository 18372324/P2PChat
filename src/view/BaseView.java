package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controller.Sendable;

/**
 * a basic view with common components
 * 
 * @author Aisingioro
 *
 */
public class BaseView implements Sendable{
	public static final int DEFAULT_MAX = 10;   // the default value of max people
	public static final int DEFAULT_PORT = 6370;// the default port number
	public static final String DEFAULT_TARGET = "ALL"; 			//the default target to send message
	public static final String DEFAULT_USERNAME = "Aisingioro";
	public static final String DEFAULT_IP = "127.0.0.1";
	
	//Font
	static Font f1 = new Font("Fixedsys", Font.BOLD, 14);
	
	//components
	public JFrame frame;
	protected JPanel panel_setting,
				     panel_message,
				     panel_btn;
	protected JSplitPane panel_split; 		//SplitPanel
	protected JScrollPane panel_user,	   	//the panel for showing user
						  panel_log; 		//the panel for showing logs and message
	protected JTextArea ta_log;   			//text area for logs and messages
	public JTextField tf_msg;
	public JButton btn_send;				//button for send message
	public JList list_user;  				//a list of user that is changing dynamically

	//a model in the user list
	public DefaultListModel<String> listModel;
	
	/**
	 * ctor
	 * 
	 * @param str - title
	 */
	public BaseView(String str) {
		initialize(str);
	}
	
	/**
	 * initialize the view
	 * @param str - title
	 */
	private void initialize(String str) {
		//Set the server window title
		frame = new JFrame(str);  
		//default window size
		frame.setSize(720, 560);
		//cannot be resized
		frame.setResizable(false);  
		frame.setLayout(new BorderLayout());
		//set the frame centered
		frame.setLocationRelativeTo(null);
		//close the window
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Online user  
        listModel = new DefaultListModel<String>();  

        list_user = new JList(listModel);  
        panel_user = new JScrollPane(list_user); 
        
        //set title
        TitledBorder tb_online = new TitledBorder("Online");
        tb_online.setTitleFont(f1);
        panel_user.setBorder(tb_online); 

        //the panel for outputting system commands and logs   
		ta_log = new JTextArea();
		//cannot be edited
		ta_log.setEditable(false);  
		ta_log.setForeground(Color.blue);
		ta_log.setFont(f1);

		//system log panel
		panel_log = new JScrollPane(ta_log);
		
		//set title
        TitledBorder tb_logs = new TitledBorder("Logs and Messages");
        tb_logs.setTitleFont(f1);
		panel_log.setBorder(tb_logs);  


        //Send message
		tf_msg = new JTextField(); 
		tf_msg.setFont(f1);
		
		panel_btn = new JPanel(new BorderLayout(10, 0));
		btn_send = new JButton("Send");
        btn_send.setFont(f1);
        panel_btn.add(btn_send, "West");
  
        panel_message = new JPanel(new BorderLayout(10, 0));//horizontal gaps
        panel_message.add(tf_msg, "Center");
		panel_message.add(panel_btn, "East");
		  
		  
        //combine the user panel and log panel    
		panel_split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel_log, panel_user);  
		panel_split.setDividerLocation(500);  //margin left 500px
		  
		frame.setVisible(true);
	}

	@Override
	public void logMessage(String msg) {
		// TODO Auto-generated method stub
		ta_log.append(msg + "\r\n");
	}

	@Override
	public void showErrorMessage(String msg) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
}
