import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class ChatThread extends JFrame {
	private JTextField messageText;
	private JTextArea chatConversation;
	private JButton btnEnter;
	private JScrollPane scrollPane;
	
	public ChatThread() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 100, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		messageText = new JTextField();
		panel.add(messageText);
		messageText.setColumns(25);
		
		btnEnter = new JButton("Submit");
		
		panel.add(btnEnter);
		
		JPanel panel1 = new JPanel();
		contentPane.add(panel1, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel1.add(scrollPane);
		
		chatConversation = new JTextArea();
		scrollPane.setViewportView(chatConversation);
		chatConversation.setEditable(false);
		chatConversation.setColumns(35);
		chatConversation.setRows(10);
	}
	
	private void moveScrollToBottom()
	{ 
		JScrollBar verticalScroll = scrollPane.getVerticalScrollBar();
		verticalScroll.setValue(verticalScroll.getMaximum());
	}
			
	public void addEnterListener(ActionListener enl) { 
		btnEnter.addActionListener(enl); 
		messageText.addActionListener(enl);
	}
	
	public String getChatMessage() { return messageText.getText(); }
	
	public void addToMessageBoard(String str) 
	{ 
		chatConversation.append(str+"\n");
		moveScrollToBottom();
	}
	
	public void clearChatMessageField() { 
		messageText.setText(""); 
	}
}
