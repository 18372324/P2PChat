package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.ClientThread;
import controller.ServerThread;
import view.BaseView;
import view.ServerView;


/**
 * The server end of P2P
 * 
 * @author Aisingioro - Shi Xuhui 18206370
 *
 */
public class ServerMain{
	//singleton pattern
	private static ServerMain instance = new ServerMain("Server End");
	
	public ServerView view;
	
    public ServerSocket serverSocket; // Socket
    
    private boolean isStart = false;		// start the server or not
    private int maxNumber = BaseView.DEFAULT_MAX;	// the max number of connections
    private int port = BaseView.DEFAULT_PORT;		// the default port number
    
    public ConcurrentHashMap<String, ClientThread> clientThreads;// a set of client threads
    private ServerThread serverThread; // one server thread
    public String kickTarget = null;
	
	/**
	 * ctor
	 * 
	 * @param str - title
	 */
	private ServerMain(String str) {
		view = new ServerView(str);
		
		serverThread = new ServerThread(this);
		
		//add the click event listener
		view.btn_start.addActionListener(e -> {
			startServer();
		});
		
		view.btn_stop.addActionListener(e -> {
			stopServer();
		});
		
		view.btn_send.addActionListener(e -> {
			sendAll();
		});
		
		//close the window
		view.frame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			if(isStart)stopServer();
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
				
				String uname = (String)view.listModel.getElementAt(index);
				if(clientThreads.containsKey(uname)) {
					//if the user is onlie
					kickTarget = uname;
					view.btn_kick.setEnabled(true);
				}else {
					view.btn_kick.setEnabled(false);
				}
				
				
			}
		});
    	
    	//add the kick event
    	view.btn_kick.addActionListener(e -> {
    		kick();
    	});
		
	}
	
	public static ServerMain getInstance() {
		return instance;
	}
	
	/**
	 * Start the server
	 */
	private void startServer() {
  
        try {  //start the server thread in the specific port
            clientThreads = new ConcurrentHashMap<String, ClientThread>();  
            serverSocket = new ServerSocket(port);
            serverThread = new ServerThread(this);
            serverThread.start();
            isStart = true;  
        } catch (BindException e) {  
            isStart = false;  
            view.showErrorMessage("Failed: Port is occupied!");  
            return;  
        } catch (Exception e) {  
            isStart = false;  
            view.showErrorMessage("Failed: Something is wrong");  
            e.printStackTrace();  
            return;  
        }  
  
        view.logMessage("Server is running with max connections: " + maxNumber + " at Port: " + port);  
        view.serviceUISetting(isStart);  
    }  
	
	/**
	 * Stop the server
	 */
	private synchronized void stopServer() {
        try {  
            //disconnect all client threads
            for (Map.Entry<String, ClientThread> entry : clientThreads.entrySet()) {  
            	ClientThread ct = entry.getValue();  
                ct.sendMessage("CLOSE");
            }
            serverThread.closeThread();
            for (Map.Entry<String, ClientThread> entry : clientThreads.entrySet()) {  
            	ClientThread ct = entry.getValue();  
                ct.close();
            }  
            clientThreads.clear();  
            view.listModel.removeAllElements();  
            isStart = false;  
            view.serviceUISetting(isStart);  
            view.logMessage("Server is closed!");  
        } catch(Exception e) {  
            e.printStackTrace();  
            view.showErrorMessage("Something is wrong!");  
            isStart = true;  
            view.serviceUISetting(isStart);  
        }  
    }  
	
	/**
	 * send the message to all clients
	 */
	private void sendAll() {
        if (!isStart) {  
        	view.showErrorMessage("Error: The server has not been started!");  
            return;  
        }
  
        if (clientThreads.size() == 0) {  
        	view.showErrorMessage("Error: No user online!");  
            return;  
        }  
  
        String message = view.tf_msg.getText().trim();
        if (message == null || message.length() == 0) {  
        	view.showErrorMessage("Error: Send message cannot be empty!");  
            return;
        }  

        //BROADCAST message
        for (Map.Entry<String, ClientThread> entry : clientThreads.entrySet()) {  
            entry.getValue().sendMessage("MSG@SERVER@ALL@" + message);  
        }
  
        view.logMessage("Server: " + message);  
        view.tf_msg.setText(null); 
    }
	
	/**
	 * kick a client
	 */
	private void kick() {
		if(kickTarget == null) {
			view.showErrorMessage("The target is offline!");  
            return;
		}
		
		//BROADCAST message
        for (Map.Entry<String, ClientThread> entry : clientThreads.entrySet()) {  
            entry.getValue().sendMessage("MSG@SERVER@ALL@" + kickTarget + " has been kicked out");  
        }
		
		//send the kick message
		for (Map.Entry<String, ClientThread> entry : clientThreads.entrySet()) {
			if(entry.getKey() == kickTarget) {
				entry.getValue().sendMessage("KICK");
				break;
			}
        }
		//clear the target
		kickTarget = null;
		//disable the button
		view.btn_kick.setEnabled(false);
	}
	
}
