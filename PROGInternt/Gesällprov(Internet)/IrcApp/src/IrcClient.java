import java.net.*;
import java.io.*;
import java.util.*;

public class IrcClient {
	private ObjectInputStream inputStream; // to read from the socket
	private ObjectOutputStream outputStream; // to write on the socket
	private Socket socket;	
	private IrcClientGui clientGui;	
	private String server, username;
	private int port;
	
	IrcClient(String server, int port, String username) {
		this(server, port, username, null);
	}
	
	IrcClient(String server, int port, String username, IrcClientGui cg) {
		this.server = server;
		this.port = port;
		this.username = username;
		// save if we are in GUI mode or not
		this.clientGui = cg;
	}
	
	public boolean start() {
		 // try to connect to the server
		 try {
			 socket = new Socket(server, port);
		 }
		 catch(Exception ec) {
			 display("Error connectiong to server:" + ec.getMessage());
			 return false;
		 }

		 String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		 display(msg);
		 
		 /* Creating both Data Stream */
		 try
		 {
			 inputStream = new ObjectInputStream(socket.getInputStream());
			 outputStream = new ObjectOutputStream(socket.getOutputStream());
		 }
		 catch (IOException eIO) {
			 display("Exception creating new Input/output Streams: " + eIO);
			 return false;
		 }
		 
		 new ListenFromServer().start();
		 
		 try
		 {
			 outputStream.writeObject(username);
		 }
		 catch (IOException eIO) {
			 display("Exception doing login : " + eIO);
			 disconnect();
			 return false;
		 }
		 
		 // success we inform the caller that it worked
		 return true;
	}
	
	private void display(String msg) {
		 if(clientGui == null)
			 System.out.println(msg); 
		 else
			 clientGui.append(msg + "\n"); 
	}
	
	void sendMessage(IrcMessage msg) {
		 try {
			 outputStream.writeObject(msg);
		 }
		 catch(IOException e) {
			 display("Exception writing to server: " + e.getMessage());
		 }
	}
	
	private void disconnect() {
		 try {
			 if(inputStream != null) inputStream.close();
		 }
		 catch(Exception e) {
			 display("Exception writing to server: " + e.getMessage());
		 }
		 try {
			 if(outputStream != null) outputStream.close();
		 }
		 catch(Exception e) {
			 display("Exception writing to server: " + e.getMessage());
		 } 
		 try{
			 if(socket != null) socket.close();
		 }
		 catch(Exception e) {
			 display("Exception writing to server: " + e.getMessage());
		 }

		 // inform the GUI
		 if(clientGui != null)
		 clientGui.connectionFailed();

	}
	
	public static void main(String[] args) {
		 // default values
		 int portNumber = 6667;
		 String serverAddress = "localhost";
		 String userName = "Unknown";
		 
		 switch(args.length) {
		 case 3:
			 serverAddress = args[2];
		 case 2:
			 try {
				 portNumber = Integer.parseInt(args[1]);
			 }
			 catch(Exception e) {
				 System.out.println("Invalid port number.");
				 System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
				 return;
			 }
		 case 1:
			 userName = args[0];
		 case 0:
			 break;
		 default:
			 System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
			 return;
		 }
		 
		 IrcClient client = new IrcClient(serverAddress, portNumber, userName);

		 if(!client.start())
		 return;

		 // wait for messages from user
		 @SuppressWarnings("resource")
		 Scanner scan = new Scanner(System.in);
		 
		 while(true) {
			 System.out.print("> ");
			 // read message from user
			 String msg = scan.nextLine();
			 // logout if message is LOGOUT
			 if(msg.equalsIgnoreCase("LOGOUT")) {
				 client.sendMessage(new IrcMessage(IrcMessage.LOGOUT, ""));
				 break;
			 }
			 else if(msg.equalsIgnoreCase("WHOISIN")) {
				 client.sendMessage(new IrcMessage(IrcMessage.WHOISIN, ""));
			 }
			 else {
				 client.sendMessage(new IrcMessage(IrcMessage.MESSAGE, msg));
			 }
		 }
		 
		 // disconnect
		 client.disconnect();
	}
	
	class ListenFromServer extends Thread {
		 public void run() {
			 while(true) {
				 try {
					 String msg = (String) inputStream.readObject();
					 // if console mode print the message and add back the prompt
					 if(clientGui == null) {
						 System.out.println(msg);
						 System.out.print("> ");
					 }
					 else {
						 clientGui.append(msg);
					 }
				 }
				 catch(IOException e) {
					 display("Server has close the connection: " + e.getMessage());
					 if(clientGui != null)
						 clientGui.connectionFailed();
					 break;
				 }
				 catch(ClassNotFoundException e2) {
					 display("Server has close the connection: " + e2.getMessage());
				 }
			 }
		 }
	}
}
