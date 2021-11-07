import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class GuestBookConnection {
	private Connection db;
	private boolean connected;
	public  String lastError; 
	
	public GuestBookConnection ()
	{
		this.db        = null;
		this.connected = false;
		this.lastError = null;
	}

	public boolean connect(String server, String database, String username, String password)
	{
		this.db        = null;
		this.connected = false;
		this.lastError = null;
	
		try {
				Class.forName( "com.mysql.jdbc.Driver" ).newInstance ();
		            
				this.db = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database, username, password);         
				this.connected = true;
			}
			catch( SQLException e )
			{
				this.lastError = e.toString ();
			}
			catch( ClassNotFoundException e )
			{
				this.lastError = e.toString ();
			}
			catch( InstantiationException e )
			{
				this.lastError = e.toString ();
			}
			catch( IllegalAccessException e )
			{
				this.lastError = e.toString ();
			}
	       
		return this.lastError == null;
	}
	
	public boolean disconnect ()
	{
		this.lastError = null;
	
		try
		{
			if ( this.db != null ) {
				this.db.close();
			}
		}
		catch( SQLException e )
		{
			this.lastError = e.toString ();
		}
	       
		this.db = null;
		this.connected = false;
	       
		return this.lastError == null;
	}

	public boolean isConnected ()
	{
		return this.db != null && this.connected;
	}
     
	public Statement createStatement () throws SQLException
	{
		return this.db.createStatement ();
	}
}
