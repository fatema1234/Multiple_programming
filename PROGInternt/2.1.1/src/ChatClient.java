import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient {
	private static int connectionPort;
	private static String connectionAddress;
	private ChatThread chatThread;
	private ChatClientConnection connection;
    
	public ChatClient() {
        chatThread = new ChatThread();
        connection = new ChatClientConnection(connectionAddress, connectionPort);
		
		start();
    }
	
	private void start()
	{
		chatThread.setVisible(true);
		setWindowClosing();
		chatThread.setTitle(connectionAddress + ":" + connectionPort);
		chatThread.addEnterListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = chatThread.getChatMessage();
				chatThread.clearChatMessageField();
				if(!message.isEmpty())
					connection.sendMessage(message);
			}
		});
		
		Thread t = new Thread(connection);
		t.start();
		while(true)
		{
			if(connection.hasNewMessage()){
				chatThread.addToMessageBoard(connection.getNewMessage());
			}
			
		}
		
	}

	private void setWindowClosing()
	{
		chatThread.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				connection.sendMessage("\\exit");
				connection.stop();
			}
		});
	}
	
	private static void configureConnection(String[] args)
	{
		int argsLength = args.length;
		
		switch (argsLength){
		case 0:
			connectionPort = 2000;
			connectionAddress = "localhost";
			break;
		case 1:
			connectionPort = 2000;
			connectionAddress =  args[0];
			break;
		case 2:
			connectionPort = Integer.parseInt(args[1]);
			connectionAddress = args[0];
		}
	}
	
	public static void main(String[] args) {
		configureConnection(args);
		new ChatClient(); 
	}

}

