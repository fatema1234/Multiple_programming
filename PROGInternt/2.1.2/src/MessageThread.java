import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageThread implements Runnable {
	private String ipAdress;
	private BufferedReader in;
	private PrintWriter out;
	private Socket socket;
	private Boolean run;
	private static int numberOfClients = 0;
	private int id;
	private String incomingMessage;
	private boolean hasNewMessage;
	private Object messageLock = new Object();
	
	public MessageThread(Socket socket)
	{
		hasNewMessage = false;
		incomingMessage = "";
		run = true;
		try 
		{
			id = ++numberOfClients;
			this.socket = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			ipAdress = socket.getRemoteSocketAddress().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(isAlive())
		{
			try 
			{
				if(in.ready())
				{
					String temp = in.readLine();
					if(!temp.equals("\\exit"))
						synchronized(messageLock)
						{
							incomingMessage += temp;
							hasNewMessage = true;
						}
					else
					{
						stop();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		close();
	}
	
	public boolean check()
	{
		return out.checkError();
	}
	
	public String getNewMessage()
	{
		String temp;
		synchronized(messageLock)
		{
			temp = incomingMessage;
			incomingMessage = "";
			hasNewMessage = false;
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
	
	public void sendMessage(String message)
	{
		out.println(message);
		out.flush();
	}
	
	public void stop()
	{
		synchronized(run)
		{
			run = false;
		}
	}

	public String getIPAdress(){ return ipAdress; }
	
	public int getID() { return id; }
	
	public boolean isAlive()
	{
		boolean temp;
		synchronized(run)
		{
			temp = run;
		}
		return temp;
	}
	
	private void close()
	{
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
