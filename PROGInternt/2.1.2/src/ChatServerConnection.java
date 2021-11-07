import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServerConnection implements Runnable {
	private ServerSocket socket;
	private Boolean run;
	private ArrayList<MessageThread> messages;
	private ChatThread chatThread;
	private String hostName;
	private int connectionPort;
	
	@SuppressWarnings("static-access")
	public ChatServerConnection(int connectionPort, ChatThread chatThread) {
		this.connectionPort = connectionPort;
		messages = new ArrayList<MessageThread>();
		this.chatThread = chatThread;
		try {
			socket = new ServerSocket(connectionPort);
			hostName = socket.getInetAddress().getLocalHost().getHostName();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		run = true;
		while(doLoop())
		{
			try {
				MessageThread temp = new MessageThread(socket.accept());
				synchronized(messages){
					messages.add(temp);
				}
				
				new Thread(temp).start();
				
				chatThread.addMessage("IP-Adress: " + temp.getIPAdress() + " ID# " + temp.getID());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		close();
	}
	
	public void checkForNewMesseges() {
		synchronized(messages)
		{
			for(MessageThread mt : messages)
			{
				if(mt.hasNewMessage()){
					String temp = "ID# " + mt.getID() + ": ";
					temp += mt.getNewMessage();
					chatThread.addMessage(temp);
					broadcastMessage(temp);
				}
			}
		}
		
	}
	
	public void checkClients()
	{
		int remove = -1;
		synchronized(messages)
		{
			for(int i = 0; i < messages.size(); i++)
			{
				if(!messages.get(i).isAlive()){
					remove = i;
				}
					
			}
			if(remove >= 0)
			{
				chatThread.addMessage(messages.get(remove).getIPAdress());
				messages.remove(remove);
			}
		}
	}

	public String createTitle()
	{
		synchronized(messages){
			return hostName + ":" + connectionPort + " | " + messages.size();
		}
	}

	public void broadcastMessage(String message)
	{
		for(MessageThread mt : messages)
		{
			mt.sendMessage(message);
		}
	}

	private boolean doLoop() {
		boolean temp;
		synchronized(run)
		{
			temp = run;
		}
		return temp;
	}

	public void stop() {
		synchronized(run)
		{
			run = false;
		}
	}

	private void close()
	{
		try {
			socket.close();
			for(MessageThread msg : messages)
				msg.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
