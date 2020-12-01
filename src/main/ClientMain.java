package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.MeThread;
import model.User;
import view.BaseView;
import view.ClientView;

/**
 * The client end of P2P
 * 
 * @author Aisingioro - Shi Xuhui 18206370
 *
 */
public class ClientMain{
	//singleton pattern
	private static ClientMain instance = new ClientMain("Client End");
	
	public static ClientView view;
	
	private User self;// current user
	
    public ConcurrentHashMap<String, User> onlineUsers = new ConcurrentHashMap<String, User>();//All online clients
    public String sendTarget = BaseView.DEFAULT_TARGET;
    private int port = BaseView.DEFAULT_PORT;
    private String ip = BaseView.DEFAULT_IP;
    private static String username = BaseView.DEFAULT_USERNAME;
    
   
    private Socket socket;			//Socket
    //IO Stream
    private PrintWriter writer;   
    public BufferedReader reader;
    
    private MeThread meThread;
    
    //Status  
    private boolean isConnected;   //whether is connected to server
    
    /**
     * ctors
     * @param str
     */
    private ClientMain(String str) {
    	view = new ClientView(str);
    	meThread = new MeThread(this);
    	
    	//add button click event listener
    	view.btn_connect.addActionListener(e -> {
    		connect();
    	});
    	
    	view.btn_disconnect.addActionListener(e -> {
    		disconnect();
    	});
    	
    	view.btn_send.addActionListener(e -> {
    		send();
    	});
    	
    	//if the window is closed
    	view.frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			if(isConnected)disconnect();
    			System.exit(0);
    		}
		});
    	
    	//if the online user is selected
    	view.list_user.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// get the selected user
				int index = view.list_user.getSelectedIndex();
				if(index < 0)return;//this is invalid index
				//print the index
				//System.out.println(index);
				if(index == 0) {
					//send to all
					sendTarget = "ALL";  
	                view.lb_to.setText("To: ALL");
				}else {
					String uname = (String)view.listModel.getElementAt(index);
					if(onlineUsers.containsKey(uname)) {
						//if the user is onlie
						sendTarget = onlineUsers.get(uname).toString();  
		                view.lb_to.setText("To: " + uname);
					}else {
						sendTarget = "ALL";  
		                view.lb_to.setText("To: ALL");
					}
				}
				
			}
		});
    	
    }
    
    public static ClientMain getInstance(String name) {
    	username = name;
    	view.lb_username.setText(username);
    	return instance;
    }
    
    /**
     * connect to server
     */
    private void connect() {
        
        try {  
        	view.listModel.addElement("All");  
  
            self = new User(username, ip);
            //Create a thread based on the specified IP address and port number
            socket = new Socket(ip, port);
            //IO Stream
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            
            //Broadcast the online info
            sendMessage("LOGIN@" + self.toString());
            
            //start the thread
            meThread = new MeThread(this);
            meThread.start();
            isConnected = true;  
  
        } catch(Exception e) {  
            isConnected = false;  
            view.logMessage("Failed: in connection!");
            //remove all users in the online panel
            view.listModel.removeAllElements();
            e.printStackTrace();  
            return;  
        }  

        view.logMessage("Successfully connected!!!");
        view.serviceUISetting(isConnected);
    }  
    
    /**
     * disconnect to server
     */
    public synchronized void disconnect() {
        try {  
        	//Send the offline message to others
            sendMessage("LOGOUT");
  
            meThread.close();
            view.listModel.removeAllElements();
            onlineUsers.clear();
  
            reader.close();
            writer.close();
            socket.close();
            isConnected = false;
            
            sendTarget = "ALL";
            view.lb_to.setText("To: " + sendTarget);
            view.logMessage("Disconnected...");
        } catch(Exception e) {  
            e.printStackTrace();
            isConnected = true;
            view.showErrorMessage("Failed: in disconnection!");
        }
        view.serviceUISetting(isConnected);
    }  
	
    
    /**
     * send the message
     */
    private void send() {
        if (!isConnected) {  
        	view.showErrorMessage("Not connected to server!");  
            return;  
        }
        //get the message
        String message = view.tf_msg.getText().trim();
        if (message == null || message.equals("")) {  
        	view.showErrorMessage("The message can't be empty!");  
            return;  
        }  
  
        String to = sendTarget;  
        try {  
        	//Send the message
        	//[MSG]@[to]@[from]@[content]
            sendMessage("MSG@" + self.toString() + "@" + to + "@" + message);  
            view.logMessage("I send to [" + to + "] : " + message);
        } catch(Exception e) {  
            e.printStackTrace();  
            view.logMessage("(Failed)I send to [" + to + "] : " + message);  
        } 
        
        //clear the message
        view.tf_msg.setText("");
    }  
    
    /**
     * put the message in the socket stream
     * 
     * @param message
     */
    private void sendMessage(String message) {  
        writer.println(message);
        writer.flush();  
    }
    
    
}
