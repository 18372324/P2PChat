package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * The view of client end
 * 
 * @author Aisingioro - Shi Xuhui 18206370
 *
 */
public class ClientView extends BaseView{
	//components
	public JLabel lb_username;	//text filed for ip address
	public JLabel lb_to;       		//the label for "To..."
	public JButton 	btn_connect,	//buttons
					btn_disconnect;
    
    
	/**
	 * ctor
	 * 
	 * @param str - title
	 */
    public ClientView(String str) {
    	super(str);
    	initialize(str);  
    } 
    
    /**
	 * initialize the client view
	 * @param str - title
	 */
    private void initialize(String str) {  
    	
        
        //set the default parameters 
        
        lb_username = new JLabel(DEFAULT_USERNAME);
        lb_username.setFont(f1);
        
        btn_connect = new JButton("Connect");
        btn_connect.setFont(f1);
        
        btn_disconnect = new JButton("DisConnect");
        btn_disconnect.setFont(f1);
  
        //the setting panel
        panel_setting = new JPanel();  
        panel_setting.setLayout(new FlowLayout());
        JLabel lb_name = new JLabel("Name:");
        lb_name.setFont(f1);
        panel_setting.add(lb_name);
        panel_setting.add(lb_username);
        
        panel_setting.add(btn_connect);  
        panel_setting.add(btn_disconnect);

        //set title
        TitledBorder tb_setting = new TitledBorder("Setting Client");
        tb_setting.setTitleFont(f1);
        panel_setting.setBorder(tb_setting);

        //the components for send message  
        lb_to = new JLabel("To: " + DEFAULT_TARGET);
        lb_to.setFont(f1);
        
        
        panel_message.add(lb_to, "West");
        
        //set title
        TitledBorder tb_msg = new TitledBorder("Send Message");
        tb_msg.setTitleFont(f1);
        panel_message.setBorder(tb_msg);  
  
        frame.add(panel_setting, "North"); 
        frame.add(panel_split, "Center");
        frame.add(panel_message, "South"); 
   
        serviceUISetting(false); //setting the initial status
    }
    
    public void serviceUISetting(boolean connected) {  
        btn_connect.setEnabled(!connected);  
        btn_disconnect.setEnabled(connected);  
        tf_msg.setEnabled(connected);  
        btn_send.setEnabled(connected);  
    }
    
}
