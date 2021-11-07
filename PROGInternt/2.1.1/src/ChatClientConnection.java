import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClientConnection implements Runnable {
	private Socket chatClientConnection;
	private BufferedReader in;
	private PrintWriter out;
	private boolean run = true;
	private String incoming;
	private boolean hasNewMessage;
	private Object runLock = new Object();
	private Object messageLock = new Object();
	
	public ChatClientConnection(String connectionAdress, int connectionPort)
	{
		hasNewMessage = false;
		incoming = "";
		try {
			chatClientConnection = new Socket(connectionAdress, connectionPort);
			in = new BufferedReader(new InputStreamReader(chatClientConnection.getInputStream()));
			out = new PrintWriter(chatClientConnection.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("UnknownHostException, ChatClientConnection.ChatClientConnection");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IOException, ChatClientConnection.ChatClientConnection");
			System.exit(0);
		}
	}
	
	public void run()
	{
		while(doLoop())
		{
			try 
			{
				if(in.ready())
				{
					String temp = in.readLine();
					synchronized(messageLock)
					{
						incoming += temp;
						hasNewMessage = true;
					}
					
				}
			}
			catch(IOException e){ System.out.println("IOException: ChatClientConnection.run"); }
		}
		close();
	}
	
	public synchronized void sendMessage(String message)
	{
		out.println(message);
		out.flush();
	}

	public String getNewMessage() 
	{
		String temp;
		synchronized(messageLock)
		{
			hasNewMessage = false;
			temp = incoming;
			incoming = "";
		}
		return temp;
	}
	
	public void stop() 
	{
		synchronized(runLock)
		{
			run = false;
		}
	}
	
	private boolean doLoop()
	{ 
		boolean temp;
		synchronized(runLock){
			temp = run;
		}
		return temp;
	}

	public boolean hasNewMessage() 
	{
		boolean temp;
		synchronized(messageLock)
		{
			temp = hasNewMessage;
		}
		
		return temp; 
	}

	private void close()
	{
		try {
			in.close();
			out.close();
			chatClientConnection.close();
		} catch (IOException e) {
			System.out.println("ChatClientConnection.close");
		}
	}
}
