import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IrcServer {
	private static int uniqueId;
	private ArrayList<IrcClientThread> clientThread;
	private IrcServerGui serverGui;
	private SimpleDateFormat dateFormat;
	private int port;
	private boolean keepGoing;
	
	public IrcServer(int port) {
		this(port, null);
	}
	
	public IrcServer(int port, IrcServerGui sg) {
		 this.serverGui = sg;
		 this.port = port;
		 dateFormat = new SimpleDateFormat("HH:mm:ss");
		 clientThread = new ArrayList<IrcClientThread>();
	}
	
	public void start() {
		 keepGoing = true;
		 
		 try
		 {
			 ServerSocket serverSocket = new ServerSocket(port);		 
			 while(keepGoing)
			 {
				 display("Server waiting for Clients on port " + port + ".");		
				 Socket socket = serverSocket.accept();
				 
				 if(!keepGoing)
					 break;
				 
				 IrcClientThread t = new IrcClientThread(socket);		
				 clientThread.add(t);
				 t.start();
			 }
			 
			 try {
				 serverSocket.close();
				 for(int i = 0; i < clientThread.size(); ++i) {
					 IrcClientThread tc = clientThread.get(i);
					 try {
						 tc.inputStream.close();
						 tc.outputStream.close();
						 tc.socket.close();
					 }
					 catch(IOException ioE) {
					 	display("Exception: " + ioE.getMessage());
					 }
				 }
			 }
			 catch(Exception e) {
				 display("Exception closing the server and clients: " + e);
			 }
		 }
		 catch (IOException e) {
			 String msg = dateFormat.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			 display(msg);
		 }
	}
	
	@SuppressWarnings("resource")
	protected void stop() {
		 keepGoing = false;
		 
		 try {
			 new Socket("localhost", port);
		 }
		 catch(Exception e) {
			 display("Exception: " + e.getMessage());
		 }
	}
	
	private void display(String msg) {
		 String time = dateFormat.format(new Date()) + " " + msg;
		 if(serverGui == null)
			 System.out.println(time);
		 else
			 serverGui.appendEvent(time + "\n");
	}
	
	private synchronized void broadcast(String message) {
		 String time = dateFormat.format(new Date());
		 String messageLf = time + " " + message + "\n";
		 
		 if(serverGui == null)
			 display(messageLf);
		 else
			 serverGui.appendRoom(messageLf); // append in the room window

		 for(int i = clientThread.size(); --i >= 0;) {
			 IrcClientThread ct = clientThread.get(i);
			 // try to write to the Client if it fails remove it from the list
			 if(!ct.writeMsg(messageLf)) {
				 clientThread.remove(i);
				 display("Disconnected Client " + ct.username + " removed from list.");
			 }
		 }
	}
	
	// LOGOUT message
	synchronized void remove(int id) {
		for(int i = 0; i < clientThread.size(); ++i) {
			IrcClientThread ct = clientThread.get(i);
	 
			if(ct.id == id) {
				serverGui.remove(i);
				return;
			}
		}
	}
	
	public static void main(String[] args) {
		 int portNumber = 6667;
		 
		 switch(args.length) {
		 	case 1:
		 		try {
		 			portNumber = Integer.parseInt(args[0]);
		 		}
		 		catch(Exception e) {
		 			System.out.println("Invalid port number.");
		 			System.out.println("Usage is: > java Server [portNumber]");
		 			return;
		 		}
		 	case 0:
		 		break;
		 	default:
		 		System.out.println("Usage is: > java Server [portNumber]");
		 		return;
		 	}
		 
		 // create a server and start it
		 IrcServer server = new IrcServer(portNumber);
		 server.start();
	}
	
	public class IrcClientThread extends Thread {
		Socket socket;
		ObjectInputStream inputStream;
		ObjectOutputStream outputStream;
		int id;
		String username;
		IrcMessage ircMessage;
		String date;
		
		IrcClientThread(Socket socket) {
			 id = ++uniqueId;
			 this.socket = socket;
			 /* Creating both Data Stream */
			 System.out.println("Thread trying to create Object Input/Output Streams");
			 try
			 {
				 // create output first
				 outputStream = new ObjectOutputStream(socket.getOutputStream());
				 inputStream = new ObjectInputStream(socket.getInputStream());
			
				 // read the username
				 username = (String) inputStream.readObject();
				 display(username + " just connected.");
			 }
			 catch (IOException e) {
				 display("Exception creating new Input/output Streams: " + e);
				 return;
			 }
			 catch (ClassNotFoundException e) {
				 display("Exception: " + e.getMessage());
			 }
			 
			 date = new Date().toString() + "\n";
		}
		
		public void run() {
			boolean keepGoing = true;
			while(keepGoing) {
			try {
				ircMessage = (IrcMessage) inputStream.readObject();
			 }
			 catch (IOException e) {
				 display(username + " Exception reading Streams: " + e.getMessage());
				 break;
			 }
			 catch(ClassNotFoundException e2) {
				 break;
			 }
			 
			 String message = ircMessage.getMessage();
			 // Switch on the type of message receive
			 switch(ircMessage.getType()) {
			 	case IrcMessage.MESSAGE:
			 		broadcast(username + ": " + message);
			 		break;
			 	case IrcMessage.LOGOUT:
			 		display(username + " disconnected with a LOGOUT message.");
			 		keepGoing = false;
			 		break;
			 	case IrcMessage.WHOISIN:
			 		writeMsg("List of the users connected at " + dateFormat.format(new Date()) + "\n");
			 		for(int i = 0; i < clientThread.size(); ++i) {
			 			IrcClientThread ct = clientThread.get(i);
			 			writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
			 		}
			 		break;
			 	}
			 }
			 
			 remove(id);
			 close();
		}
		
		 private void close() {
			 // try to close the connection
			 try {
				 if(outputStream != null) outputStream.close();
			 }
			 catch(Exception e) {
				 display("Exception: " + e.getMessage());
			 }
			 try {
				 if(inputStream != null) inputStream.close();
			 }
			 catch(Exception e) {
				 display("Exception: " + e.getMessage());
			 }
			 try {
				 if(socket != null) socket.close();
			 }
			 catch (Exception e) {
				 display("Exception: " + e.getMessage());
			 }
		 }
		 
		 private boolean writeMsg(String msg) {
			 // if Client is still connected send the message to it
			 if(!socket.isConnected()) {
			 	close();
			 	return false;
			 }
			 // write the message to the stream
			 try {
			 	outputStream.writeObject(msg);
			 }
			 // if an error occurs, do not abort just inform the user
			 catch(IOException e) {
			 	display("Error sending message to " + username);
			 	display(e.toString());
			 }
			 return true;
		 }
	}
}
