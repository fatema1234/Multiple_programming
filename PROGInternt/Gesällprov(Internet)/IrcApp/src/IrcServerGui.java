import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IrcServerGui extends JFrame implements ActionListener, WindowListener {
	private static final long serialVersionUID = 1L;
	private JButton stopStart;
	private JTextArea chat, event;
	private JTextField tPortNumber;
	private IrcServer server;
	
	IrcServerGui(int port) {
		 super("Irc Chat Server");
		 server = null;
		 // in the NorthPanel the PortNumber the Start and Stop buttons
		 JPanel north = new JPanel();
		 north.add(new JLabel("Port number: "));
		 tPortNumber = new JTextField(" " + port);
		 north.add(tPortNumber);
		 // to stop or start the server, we start with "Start"
		 stopStart = new JButton("Start");
		 stopStart.addActionListener(this);
		 north.add(stopStart);
		 add(north, BorderLayout.NORTH);

		 // the event and chat room
		 JPanel center = new JPanel(new GridLayout(2,1));
		 chat = new JTextArea(80,80);
		 chat.setEditable(false);
		 appendRoom("Chat room.\n");
		 center.add(new JScrollPane(chat));
		 event = new JTextArea(80,80);
		 event.setEditable(false);
		 appendEvent("Events log.\n");
		 center.add(new JScrollPane(event));
		 add(center);

		 // need to be informed when the user click the close button on the frame
		 addWindowListener(this);
		 setSize(400, 600);
		 setVisible(true);
	} 
	
	void appendRoom(String str) {
		 chat.append(str);
		 chat.setCaretPosition(chat.getText().length() - 1);
	}
	
	void appendEvent(String str) {
		 event.append(str);
		 event.setCaretPosition(chat.getText().length() - 1);

	}
	
	public void actionPerformed(ActionEvent e) {
		 // if running we have to stop
		 if(server != null) {
			 server.stop();
			 server = null;
			 tPortNumber.setEditable(true);
			 	stopStart.setText("Start");
		 return;
		 }
		 
		 int port;
		 try {
			 port = Integer.parseInt(tPortNumber.getText().trim());
		 }
		 catch(Exception er) {
			 appendEvent("Invalid port number");
			 return;
		 }
		 
		 // ceate a new Server
		 server = new IrcServer(port, this);
		 // and start it as a thread
		 new ServerRunning().start();
		 stopStart.setText("Stop");
		 tPortNumber.setEditable(false);
	}
	
	public static void main(String[] arg) {
		 // start server default port 6667
		 new IrcServerGui(6667);
	}
	
	public void windowClosing(WindowEvent e) {
		 if(server != null) {
			 try {
				 server.stop();
			 }
			 catch(Exception eClose) {
			 }
			 server = null;
		 }
		 
		 // dispose the frame
		 dispose();
		 System.exit(0);
	}
	
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	
	class ServerRunning extends Thread {
		 public void run() {
			 server.start();
		 
			 // the server failed
			 stopStart.setText("Start");
			 tPortNumber.setEditable(true);
			 appendEvent("Server crashed\n");
			 server = null;
		 }
	}
}
