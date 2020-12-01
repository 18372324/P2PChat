package controller;

import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicBoolean;

import main.ClientMain;
import model.User;

/**
 * each thread for one ClientMain
 * 
 * @author Aisingioro
 *
 */
public class MeThread extends Thread{
	private ClientMain cm;
	
	//this is atomic variable
	private final AtomicBoolean running = new AtomicBoolean(true);

	/**
	 * ctor
	 * @param cm
	 */
	public MeThread(ClientMain cm) {
		this.cm = cm;
	}
	
	
    public void run() {  
        while (running.get()) {// while is running
            try {  
                String message = cm.reader.readLine();  
                StringTokenizer tokenizer = new StringTokenizer(message, "@");  
                String command = tokenizer.nextToken();  

                if (command.equals("CLOSE")) {
                	cm.view.logMessage("The server is down >_<, disconnecting...");
                	cm.disconnect();
                    close();
                } else if (command.equals("ERROR")) {
                    String error = tokenizer.nextToken();
                    cm.view.logMessage("Error: " + error);
                } else if (command.equals("LOGIN")) {
                    String status = tokenizer.nextToken();
                    if (status.equals("SUCCESS")) {
                    	cm.view.logMessage(tokenizer.nextToken());  
                    } else if (status.equals("STOP")) {  
                    	cm.view.logMessage("Login Failed: " + tokenizer.nextToken());  
                    	cm.disconnect();
                        this.close();
                        return;  
                    }  
                } else if (command.equals("USER")) {
                    String type = tokenizer.nextToken();  
                    if (type.equals("ADD")) {
                    	String[] strs = tokenizer.nextToken().split(" - ");// split the string[username - ip]
                    	User newUser = new User(strs[0], strs[1]);
                    	cm.onlineUsers.put(newUser.getUsername(), newUser);  
                    	cm.view.listModel.addElement(newUser.getUsername());  

                    	cm.view.logMessage("New user [" + newUser.toString() + "] online");  

                    } else if (type.equals("DELETE")) {  
                    	String[] strs = tokenizer.nextToken().split(" - ");  
                        User deleteUser = new User(strs[0], strs[1]);  
                        cm.onlineUsers.remove(deleteUser.getUsername());  
                        cm.view.listModel.removeElement(deleteUser.getUsername());  

                        cm.view.logMessage("User [" + deleteUser.toString() + "] offline");  

                        if (cm.sendTarget.equals(deleteUser.toString())) {  
                        	cm.sendTarget = "ALL";  
                        	cm.view.lb_to.setText("To: All");
                        }  

                    } else if (type.equals("LIST")) {
                        int num = Integer.parseInt(tokenizer.nextToken());  
                        for (int i = 0; i < num; i++) {  
                        	String[] strs = tokenizer.nextToken().split(" - ");// split the string[username - ip]
                        	User newUser = new User(strs[0], strs[1]);
                        	cm.onlineUsers.put(newUser.getUsername(), newUser);  
                        	cm.view.listModel.addElement(newUser.getUsername());  

                        	cm.view.logMessage("Get the user [" + newUser.toString() + "]");  
                        }  
                    }  
                } else if (command.equals("MSG")) {
                    StringBuffer buffer = new StringBuffer();  
                    String from = tokenizer.nextToken();  
                    String to = tokenizer.nextToken();  
                    String content = tokenizer.nextToken();  

                    buffer.append(from);  
                    if (to.equals("ALL")) {  
                        buffer.append("(to All)");  
                    }  
                    buffer.append(": " + content);  
                    cm.view.logMessage(buffer.toString());  
                }else if(command.equals("KICK")) {
                	//this user is forced to disconnect
                	cm.disconnect();
                }

            } catch(Exception e) {  
                e.printStackTrace();  
                cm.view.logMessage("Error: cannot receive msg");  
            }  
        }  
    }  

    public void close() {  
        running.set(false);
    }
}
