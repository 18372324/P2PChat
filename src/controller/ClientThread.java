package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicBoolean;

import main.ServerMain;
import model.User;

/**
 * this is a thread running at server end
 * 
 * @author Aisingioro
 *
 */
public class ClientThread extends Thread{
	//this is atomic variable
	private final AtomicBoolean running = new AtomicBoolean(true);
	
	private Socket socket;
	private User user;  
	private BufferedReader reader;  
	private PrintWriter writer;
	private ServerMain sm;
	
	/**
	 * ctor
	 * 
	 * @param socket
	 * @param sm
	 */
	public ClientThread(Socket socket, ServerMain sm) {  
	    this.socket = socket;
	    this.sm = sm;
	    running.set(init());
	    if (!running.get()) {
	    	sm.view.logMessage("Failed in running the client!");  
	    }
	} 
	
	private boolean init() {
	    try {  
	        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
	        writer = new PrintWriter(socket.getOutputStream());  
	
	        String info = reader.readLine();  
	        StringTokenizer tokenizer = new StringTokenizer(info, "@");  
	        String type = tokenizer.nextToken();  
	        if (!type.equals("LOGIN")) {
	            sendMessage("ERROR@MESSAGE_TYPE");  
	            return false;  
	        }  
	        String[] strs = tokenizer.nextToken().split(" - ");// split the string[username - ip]
	        user = new User(strs[0], strs[1]);
	        sendMessage("LOGIN@SUCCESS@" + user.toString() + " Connected!");  
	
	        int clientNum = sm.clientThreads.size();
	        if (clientNum > 0) {
	            //info the online clients  
	            StringBuffer buffer = new StringBuffer();  
	            for (Map.Entry<String, ClientThread> entry : sm.clientThreads.entrySet()) {  
	            	ClientThread ct = entry.getValue();
	                buffer.append(ct.getUser().toString() + "@");
	                //Tell other clients that this client is online  
	                ct.sendMessage("USER@ADD@" + user.toString());  
	            }
	
	            sendMessage("USER@LIST@" + clientNum + "@" + buffer.toString());  
	        }  
	
	        return true;  
	
	    } catch(Exception e) {  
	        e.printStackTrace();  
	        return false;  
	    }  
	}  
	
	public void run() {  
	    while (running.get()) {
	        try {  
	            String message = reader.readLine();  
	            // System.out.println("recieve message: " + message);  
	            if (message.equals("LOGOUT")) {
	            	sm.view.logMessage(user.toString() + "Offline...");  
	
	                //Info other clients that this client is offline  
	                for (Map.Entry<String, ClientThread> entry : sm.clientThreads.entrySet()) {  
	                    entry.getValue().sendMessage("USER@DELETE@" + user.toString());  
	                }  
	
	                //Remove the client and client thread 
	                sm.view.listModel.removeElement(user.getUsername());  
	                sm.clientThreads.remove(user.toString());  
	
	                // System.out.println(user.description() + " logout, now " + listModel.size() + " client(s) online...(" + clientServiceThreads.size() + " Thread(s))");  
	
	                close();  
	                return;  
	            } else {  //Send message
	                dispatchMessage(message);  
	            }  
	        } catch(Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	}  
	
	public void dispatchMessage(String message) {
	    StringTokenizer tokenizer = new StringTokenizer(message, "@");  
	    String type = tokenizer.nextToken();  
	    if (!type.equals("MSG")) {
	        sendMessage("ERROR@MESSAGE_TYPE");  
	        return;  
	    }  
	
	    String from = tokenizer.nextToken();
	    String to = tokenizer.nextToken();
	    String content = tokenizer.nextToken();
	    
	    String[] str = to.split(" - ");
	
	    sm.view.logMessage(from + "->" + to + ": " + content);  
	    if (to.equalsIgnoreCase("ALL")) {
	        //send to everyone
	        for (Map.Entry<String, ClientThread> entry : sm.clientThreads.entrySet()) {  
	            entry.getValue().sendMessage(message);  
	        }  
	    } else {  
	        //send to only one user
	        if (sm.clientThreads.containsKey(str[0])) {
	        	sm.clientThreads.get(str[0]).sendMessage(message);  
	        } else {  
	            sendMessage("ERROR@INVALID_USER");  
	        }  
	    }  
	}  
	
	public void close() throws IOException {
		//close the IO stream
	    this.reader.close();  
	    this.writer.close(); 
	    //close the socket
	    this.socket.close();  
	    running.set(false);
	}  
	
	public void sendMessage(String message) {  
	    writer.println(message);  
	    writer.flush();  
	}  
	
	public User getUser() {  
	    return user;  
	}  
}
