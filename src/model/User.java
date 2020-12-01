package model;

/**
 * Mapping the model of users
 * 
 * @author Aisingioro - Shi Xuhui 18206370
 *
 */
public class User {
	private String username;
	private String ip;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
	public User(String username, String ip) {
		this.username = username;
		this.ip = ip;
	}
	
	@Override
	public String toString() {
		return username + " - " + ip;
	}
	
	
	
	
	
	

}
