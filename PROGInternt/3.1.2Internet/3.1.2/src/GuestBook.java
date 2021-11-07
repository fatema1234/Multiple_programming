import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GuestBook extends JFrame {
	private static final String mysqlServer   = "localhost";
	private static final String mysqlDatabase = "GuestBook";
	private static final String mysqlUsername = "root";
	private static final String mysqlPassword = "admin";

	private JButton     buttonAdd;
	private JButton     buttonRefresh;
	private JButton     buttonConnect;
	    
	private JLabel      labelName;
	private JTextField  txtName;
	private JLabel      labelEmailAddress;
	private JTextField  txtEmailAddress;
	private JLabel      labelHomePage;
	private JTextField  txtHomePage;
	private JLabel      labelComment;
	private JTextField  txtComment;
	   
	private JScrollPane logPane;
	private JTextArea   logArea;

	private GuestBookConnection dbConnection;
	private GuestBookTable      guestBook;
	
	public GuestBook ()
	{
		super("Guest Book [Disconnected]" );
           
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
	    
		addWindowListener( new java.awt.event.WindowAdapter () {
			public void windowClosing( java.awt.event.WindowEvent evt ) {
				formWindowClosing( evt );
			}
		});
	
		Font btnFont  = new Font( Font.SANS_SERIF, Font.BOLD,  12 );
		Font textFont = new Font( Font.SANS_SERIF, Font.PLAIN, 12 );
		Font logFont  = new Font( Font.MONOSPACED, Font.PLAIN, 14 );
	      
		buttonAdd 		= new JButton ();
		buttonRefresh 	= new JButton ();
		buttonConnect     = new JButton ();
		labelName         = new JLabel ();
		txtName           = new JTextField ();
		labelEmailAddress = new JLabel ();
		txtEmailAddress   = new JTextField ();
		labelHomePage     = new JLabel ();
		txtHomePage       = new JTextField ();
		labelComment      = new JLabel ();
		txtComment        = new JTextField ();
	
		labelName.setText( "Name:" );
		labelEmailAddress.setText( "Email address:" );
		labelHomePage.setText( "Home page:" );
		labelComment.setText( "Comment:" );
	        
		txtName.setColumns( 40 );
		txtEmailAddress.setColumns( 40 );
		txtHomePage.setColumns( 40 );
		txtComment.setColumns( 40 );
	
		buttonAdd.setFont( btnFont );
		buttonRefresh.setFont( btnFont );
		buttonConnect.setFont( btnFont );
		labelName.setFont( textFont );
		labelEmailAddress.setFont( textFont );
		labelHomePage.setFont( textFont );
		labelComment.setFont( textFont );
		txtName.setFont( textFont );
		txtEmailAddress.setFont( textFont );
		txtHomePage.setFont( textFont );
		txtComment.setFont( textFont );
	        
		logArea = new JTextArea ();
		logArea.setLineWrap( true );
		logArea.setWrapStyleWord( true );
		logArea.setEditable( false );
		logArea.setFont( logFont );
		logArea.setBackground( new Color( 255, 255, 240 ) );
		logArea.setForeground( Color.BLACK );
		logArea.setWrapStyleWord( true );
	        
		logPane = new JScrollPane ();
		logPane.setViewportView( logArea );
	 
		buttonAdd.setText( "Add" );
		buttonAdd.addActionListener( new ActionListener () {
			public void actionPerformed( ActionEvent evt ) {
				addNewRecord ();
			}
		});
	
		buttonRefresh.setText( "Refresh" );
		buttonRefresh.addActionListener( new ActionListener () {
			public void actionPerformed( ActionEvent evt ) {
				clearLog ();
				displayAllRecords ();
			}
		});
	
		buttonConnect.setText( "Connect" );
		buttonConnect.addActionListener( new ActionListener () {
			public void actionPerformed( ActionEvent evt ) {
				toggleDbConnection ();
			}
		});
	
		txtName.requestFocus (); 
        
		GroupLayout layout = new GroupLayout( getContentPane () );
		getContentPane ().setLayout( layout );
		layout.setAutoCreateContainerGaps( true );
		layout.setAutoCreateGaps( true );
	        
		layout.setHorizontalGroup
		(
				layout
					.createParallelGroup( GroupLayout.Alignment.LEADING )
					.addGroup
					(
							layout
							.createSequentialGroup ()
							.addGroup
							(
									layout
									.createParallelGroup( GroupLayout.Alignment.TRAILING )
									.addComponent( logPane )
									.addGroup
									(
											GroupLayout.Alignment.LEADING, 
											layout
											.createSequentialGroup ()
											.addContainerGap( 10, 10 )
											.addGroup
											(
													layout
													.createParallelGroup( GroupLayout.Alignment.LEADING )
													.addComponent( labelName )
													.addComponent( labelEmailAddress )
													.addComponent( labelHomePage )
													.addComponent( labelComment )
											  )
											.addGroup
											(
													layout
													.createParallelGroup( GroupLayout.Alignment.LEADING, false )
													.addComponent( txtName )
													.addComponent( txtEmailAddress )
													.addComponent( txtHomePage )
													.addComponent( txtComment )
													)
											)
										.addGroup
										(
												GroupLayout.Alignment.LEADING,
												layout
												.createSequentialGroup ()
												.addContainerGap( 10, 10 )
												.addGroup
												(
														layout
														.createParallelGroup( GroupLayout.Alignment.LEADING, false )
														.addComponent( buttonAdd )
														)
												.addGroup
												(
														layout
														.createParallelGroup( GroupLayout.Alignment.LEADING, false )
														.addComponent( buttonRefresh )
														)
												.addGroup
												(
														layout
														.createParallelGroup( GroupLayout.Alignment.LEADING, false )
														.addComponent( buttonConnect )
														)
												)
										)
								)
					);
	        
					layout.setVerticalGroup
					(
							layout
							.createParallelGroup( GroupLayout.Alignment.LEADING )
							.addGroup
							(
									layout
									.createSequentialGroup ()
									.addGroup
									(
											layout
											.createParallelGroup( GroupLayout.Alignment.BASELINE )
											.addComponent( labelName )
											.addComponent( txtName )
											)
									.addGroup
									(
											layout
											.createParallelGroup( GroupLayout.Alignment.BASELINE )
											.addComponent( labelEmailAddress )
											.addComponent( txtEmailAddress )
											)
									.addGroup
									(
											layout
											.createParallelGroup( GroupLayout.Alignment.BASELINE )
											.addComponent( labelHomePage )
											.addComponent( txtHomePage )
											)
									.addGroup
									(
											layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
											.addComponent( labelComment )
											.addComponent( txtComment )
											)
									.addGroup
									(
											layout.createParallelGroup( GroupLayout.Alignment.BASELINE )
											.addComponent( buttonAdd )
											.addComponent( buttonRefresh )
											.addComponent( buttonConnect )
											)
									.addComponent( logPane )
									)
							);
	
					pack ();
	
        
					/* Adjust window dimensions not to exceed screen dimensions ...
					 */
					Dimension win = new Dimension( 690, 591 );
					Dimension scsz = Toolkit.getDefaultToolkit().getScreenSize();
					win.width  = Math.min( win.width, scsz.width );
					win.height = Math.min( win.height, scsz.height - 40 );
					setSize( win );
	       
					setResizable( false );
	
					setLocation( ( scsz.width - win.width )/2, ( scsz.height - 40 - win.height )/2 );
	        
					/* Connect to database and retrieve data
					 */
					dbConnection = new GuestBookConnection ();
					guestBook = new GuestBookTable( dbConnection );
		
					/* Open connection to database
					 */
					toggleDbConnection ();
	}
	
	 public void println( String str )
	 {
	 	synchronized(logArea)
	 	{
	 		logArea.append( str + "\n" );
	 		logArea.setRows( logArea.getRows () + 1 );
	 		logArea.setCaretPosition( logArea.getText().length () );
	 	}
	 }

	 public void print( String str )
	 {
	 	synchronized( logArea )
	 	{
	 		logArea.append( str );
	 		logArea.setCaretPosition( logArea.getText().length () );
	 	}
	 }

	 public void clearLog ()
	 {
	 	synchronized( logArea )
	 	{
	 		logArea.setText( "" );
	 		logArea.setRows( 0 );
	 		logArea.setCaretPosition( logArea.getText().length () );
	 	}
	 }
	
	private void toggleDbConnection ()
	{
		if (dbConnection.isConnected())
		{
			dbConnection.disconnect();
			buttonConnect.setText("Connect");
			clearLog();
			println("");
			println("Connecting...");
			println("Disconnected.");
			setTitle("Guest Book [Disconnected]");
			logArea.setForeground(Color.RED);
		}
		else 
		{	
			buttonAdd.setEnabled(false);
			buttonRefresh.setEnabled(false);
			buttonConnect.setEnabled(false);
			clearLog();
			println("");
			setTitle("Guest Book [Connecting...]");
			logArea.setForeground(Color.BLUE);

			Runnable threadedDbConnect = new Runnable() {
				public void run() 
				{
					try { Thread.sleep( 700 ); } 
					catch( InterruptedException e ) {}
					dbConnection.connect(mysqlServer, mysqlDatabase, mysqlUsername, mysqlPassword );
					java.awt.EventQueue.invokeLater( 
							new Runnable() {
								public void run() {
									onThreadedDbConnectCompleted ();
								}
							}
					 );
				 }
			};
			(new Thread(threadedDbConnect)).start ();
		}
	}
	
	 private void onThreadedDbConnectCompleted ()
	 {
	 	if (dbConnection.isConnected())
	 	{
	 		logArea.setForeground(Color.BLUE);
	 		buttonConnect.setText("Disconnect");
	 		displayAllRecords();
	 	}
	 	else // when disconnected
	 	{
	 		buttonConnect.setText("Connect");
	 		setTitle("Guest Book [Disconnected]");
	 		clearLog();
	 		println("");
	 		println("Failed to connect!");
	 		logArea.setForeground(Color.RED);
	             
	 		if (dbConnection.lastError != null)
	 		{
	 			println("");
	 			println("Reason:");
	 			println("");
	 			println(dbConnection.lastError);
	 		}
	 	}
	      
	 	/* Enable all buttons again
	 	*/
	 	buttonAdd.setEnabled(true);
	 	buttonRefresh.setEnabled(true);
	 	buttonConnect.setEnabled( true );
	}
	
	private void displayAllRecords ()
	{
		if(!guestBook.fetch())
		{
			/* In case of error... */
			clearLog ();
			println("");
			println("Failed to retrieve records from database!");
			println("");
			println("Reason:");
			println("");
			println(guestBook.lastError);
			if (guestBook.lastSql != null && dbConnection.isConnected()) {
				println("");
				println("The last SQL statement:");
				println("");
				println(guestBook.lastSql);
			}
			logArea.setForeground(Color.RED);
			return;
		}
	       
		setTitle("Guest Book");
		println(" Total " + guestBook.records.size() + " records");
	     
		int recNo = 0;
	       
		for(GuestBookTable.Record rec : guestBook.records)
		{
			println(" Record #" + (++recNo));
			println(" ID: " + rec.id);
			println(" Name: " + rec.name);
			println(" Email address: " + rec.emailAddress);
			println(" Home page: " + rec.homePage);
			println(" Comment: " + rec.comment);
		}
	       
		logArea.setForeground(Color.BLUE);
	}
	
	private void addNewRecord ()
	{
		if (!guestBook.add(txtName.getText(), txtEmailAddress.getText(),
						txtHomePage.getText(), txtComment.getText()) )
		{
			/* In case of error... */
			clearLog ();
			println("");
			println("Failed to add record!");
			println("");
			println("Reason:");
			println("");
			println( guestBook.lastError );
			if (guestBook.lastSql != null && dbConnection.isConnected()) {
				println("");
				println("The last SQL statement:");
				println("");
				println(guestBook.lastSql);
			}
			logArea.setForeground(Color.RED);
			return;
		}
	     
		/* Check for censorship...
		*/
		GuestBookTable.Record r = guestBook.lastAdded;
		boolean nameEq = r.name.equals(txtName.getText());
		boolean emailAddrEq = r.emailAddress.equals(txtEmailAddress .getText());
		boolean homePageEq  = r.homePage.equals(txtHomePage.getText());
		boolean commentEq   = r.comment.equals(txtComment.getText());
		boolean isCensored  = !(nameEq && emailAddrEq && homePageEq && commentEq);
	      
		/* Refresh records, first so final messages comes later...
		*/
		displayAllRecords();
		        
		println("");
		print("New record added successfully.");
     
		/* Inform user about censorship. 
		* Do not clear fields if some fields are censored!
		*/
		if (isCensored) {
			println("");
			print("However, some records where censored!");
		} else {
			txtName.setText("");
			txtEmailAddress.setText("");
			txtHomePage.setText("");
			txtComment.setText("");
		}

		logArea.setForeground( Color.BLACK );
	}
	
	private void formWindowClosing( WindowEvent evt )
	{
		dbConnection.disconnect ();
		System.exit( 0 );
	}
}
