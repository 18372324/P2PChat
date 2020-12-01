package view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * The user interface of Server
 * 
 * @author Aisingioro - Shi Xuhui 18206370
 *
 */
public class ServerView extends BaseView{
	
	public JButton btn_start;			// buttons				  
	public JButton btn_stop;
	public JButton btn_kick;//kick out a client
    
	/**
	 * ctor
	 * 
	 * @param str - title
	 */
    public ServerView(String str) {
    	super(str);
    	initialize(str);  
    }
    
    /**
	 * initialize the client view
	 * @param str - title
	 */
    private void initialize(String str) {
        
        
        
        //set the default parameters  
        btn_start = new JButton("Start");
        btn_start.setFont(f1);
        btn_stop = new JButton("Stop");
        btn_stop.setFont(f1);
  
        //the setting panel
        panel_setting = new JPanel();
        
        panel_setting.setLayout(new FlowLayout());
        
        panel_setting.add(btn_start);  
        panel_setting.add(btn_stop);
        
        //Set the title
    	TitledBorder tb_setting = new TitledBorder("Setting Server");
    	tb_setting.setTitleFont(f1);
        panel_setting.setBorder(tb_setting);  

        //Set the title
    	TitledBorder tb_msg = new TitledBorder("BROADCAST");
    	tb_msg.setTitleFont(f1);
        panel_message.setBorder(tb_msg);  
        
        //add kick button
        btn_kick = new JButton("Kick");
        btn_kick.setFont(f1);
        btn_kick.setEnabled(false);
        
        panel_btn.add(btn_kick, "East");

  
        frame.add(panel_setting, "North");
        frame.add(panel_split, "Center");
        frame.add(panel_message, "South");
  
        serviceUISetting(false);  //setting the initial status
    }  
  
    public void serviceUISetting(boolean started) {  
        btn_start.setEnabled(!started);  
        btn_stop.setEnabled(started);  
        tf_msg.setEnabled(started);  
        btn_send.setEnabled(started);  
    }
    
}
