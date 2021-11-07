import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatServer {
	private ChatThread chatThread;
	private ChatServerConnection connection;
	private static int connectionPort;

	private ChatServer()
	{
		chatThread = new ChatThread();
		connection = new ChatServerConnection(connectionPort, chatThread);
		
		start();
	}
	
	private void start() 
	{
		long startTime = System.currentTimeMillis();
		chatThread.setVisible(true);
		setWindowClosing();
		
		Thread t = new Thread(connection);
		t.start();
		chatThread.setTitle(connection.createTitle());
		
		
		while(true)
		{
			if(System.currentTimeMillis() - startTime > 1000){
				chatThread.setTitle(connection.createTitle());
				connection.checkClients();
				startTime = System.currentTimeMillis();
			}
			
			connection.checkForNewMesseges();		
			
		}		
	}
	
	private void setWindowClosing() {
		chatThread.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				connection.stop();
			}
		});		
	}
	
	private static void configureConnection(String[] args) {
		if(args.length == 0) 
			connectionPort = 2000;
		else
		{
			try{
				connectionPort = Integer.parseInt(args[0]);
			} catch(NumberFormatException e) {
				System.out.println("Måste vara ett nummer");
				System.exit(0);
			}
		}
	}
	
	public static void main(String[] args) {
		configureConnection(args);
		new ChatServer();
	}
}
