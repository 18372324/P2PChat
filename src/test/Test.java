package test;

import main.ClientMain;
import main.ServerMain;
import model.User;
import view.LoginFrame;

public class Test {
	public static void main(String[] args) {
		//test split - successful
//		String str = "username - 192.168.1.1";
//		String[] s = str.split(" - ");
//		User user = new User(s[0], s[1]);
//		System.out.println("username: " + user.getUsername());
//		System.out.println("ip: " + user.getIp());
		
		
		/**
	     * test client
	     */
		ClientMain cm = ClientMain.getInstance("Aisingioro");
		
		/**
		 * test server
		 */
		ServerMain sm = ServerMain.getInstance();
		
		/**
		 * test login frame
		 */
//		LoginFrame lf = new LoginFrame();
	}
}
