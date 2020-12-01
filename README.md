# Peer-to-peer Chat Style System

## Done
1. The server is running at Port: 6370(last four bit of my student ID) and allows up to 10 clients to connect.
2. Each time a client connects to the server the event is announced to all connected clients.  
Message Format: { USER@ADD@[username - ip] }
3. The Server can broadcast message to all clients which are connecting to itself.  
Message Format: { MSG@SERVER@ALL@[message] }
4. The Server can kick any client which is connecting to itself.  
Message Format: { KICK }
5. The client can broadcast message to all clients which are connecting to same server.  
Message Format: { MSG@[from user]@ALL@[message] }
6. The client can send message to an other specific client which is connecting to same server.  
Message Format: { MSG@[from user]@[to user]@[message] }
7. All the messages and commands above are listed in a log text area.
8. All the clients are listed in the client end and server end.
9. All above features are performed based on a GUI.  

## Design
![class diagram](https://github.com/18372324/P2PChat/blob/master/screenshot/class-diagram.png?raw=true)
### 1.  The MVC Architecture
#### 1.1 Model
This layer is mainly responsible for data storage. In my P2P char app, I only need one model - User to store the username and ip address.  
#### 1.2 View
There are four views in my app - BaseView, ClientView, ServerView, and LoginFrame. LoginFrame is the entrance to login this app, while ClientView and ServerView organize the UI components for Client end and Server end, respectively. To reuse the code, the common parts of ServerView and ClientView are integrated in BaseView.

#### 1.3 Controller
This layer provide with the core service of p2p. For example, as shown in above, the client end needs to connect/disconnect to a server, and send message.

### 2. Design Pattern - Singleton
According to actual needs, both the server and the client can only have one instance, so I use the singleton.
For Example, The Server End:  

```java
public class ServerMain{
	//singleton pattern
	private static ServerMain instance = new ServerMain("Server End");
	private ServerView view;//the view
	
	/*
	* ctor
	*/
	private ServerMain(String str) {
		view = new ServerView(str);
	}
	
	public static ServerMain getInstance() {
		return instance;
	}
}
```

### 3. ConcurrentHashMap
A hash table supporting full concurrency of retrievals and high expected concurrency for updates. This class obeys the same functional specification as java.util.Hashtable, and includes versions of methods corresponding to each method of Hashtable. However, even though all operations are thread-safe, retrieval operations do not entail locking, and there is not any support for locking the entire table in a way that prevents all access. This class is fully interoperable with Hashtable in programs that rely on its thread safety but not on its synchronization details.

### 4. Flow Chart
![flow chart](https://github.com/18372324/P2PChat/blob/master/screenshot/flow-chart.png?raw=true)

## Bugs & Need to Improve
### 1. Can't disable the textfiled

In the LoginFrame, when user selects the client radio button, the username input textfiled still can be activated. It may be due to the addition of focus event listener for this textfiled. However, I do not have time to fix it.

### 2. Some parameters are fixed

The IP address, max number of connections and port number is constant. Maybe these parameters should be more flexible and set by users.

### 3. local area network

This chat software can only communicate each other in local area network. This software can further expand the range of user connections, making communication not only limited to local users

### 4. GUI

The GUI of client end and server end are simple, it can be rendered more beautiful and attractive.

## My Github
More details are available at: [P2P at Github 18372324](https://github.com/18372324/P2PChat)  