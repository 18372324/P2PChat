package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import main.ServerMain;
import model.User;
import view.BaseView;

/**
 * This is a thread running at a Server
 * 
 * @author Aisingioro
 *
 */
public class ServerThread extends Thread{
	private ServerMain sm;
	
	//this is atomic variable
	private final AtomicBoolean running = new AtomicBoolean(true);
	
	public ServerThread(ServerMain sm) {
		this.sm = sm;
	}
	
	public void run() {  
	    while (running.get()) { 
	        try {  
	            if (!sm.serverSocket.isClosed()) {  //if socket is not closed
	            	//receive the request
	                Socket socket = sm.serverSocket.accept();  
	
	                if (sm.clientThreads.size() >= BaseView.DEFAULT_MAX) {	
	                	//Determine whether the number of people 
	                	//has reached the upper limit
	                    PrintWriter writer = new PrintWriter(socket.getOutputStream());  
	                    writer.println("LOGIN@STOP@Sorry, the number of online server has reached the upper limit, please try later!");  
	                    writer.flush();  
	                    writer.close();  
	                    socket.close();  
	                } else {  
	                    ClientThread ct = new ClientThread(socket, sm);  
	                    User user = ct.getUser();
	                    
                    	sm.clientThreads.put(user.getUsername(), ct);  
	                    sm.view.listModel.addElement(user.getUsername());  
	                    sm.view.logMessage(user.toString() + "Online!!!");  
	                    ct.start();
	                    
	                }  
	            }  
	        } catch(Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	}  
	
	public void closeThread() throws IOException {
		//close the socket
		sm.serverSocket.close();
	    System.out.println("Server Socket is closed: " + sm.serverSocket.isClosed());
	    //stop the thread
	    running.set(false);
	}
	
}
