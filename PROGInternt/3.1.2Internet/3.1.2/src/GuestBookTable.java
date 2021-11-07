import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class GuestBookTable {
	private static final String tableName = "GuestBookTable";
	
	public class Record
	{
		public String id;
		public String name;
		public String emailAddress;
		public String homePage;
		public String comment;
	       
		public Record(String id, String name, String emailAddress, String homePage, String comment)
		{
			this.id           = id;
			this.name         = name;
			this.emailAddress = emailAddress;
			this.homePage     = homePage;
			this.comment      = comment;
		}
	}

	private GuestBookConnection db = null;
	public ArrayList<Record> records;
	public String filter  = null;
	public String orderBy = "Id ASC";
	public String lastSql   = null;
	public String lastError = null;
	public Record lastAdded = null;
	
	public GuestBookTable( GuestBookConnection db )
	{
		this.db = db;
		records = null;
	}

	public boolean fetch ()
	{
		if ( ! db.isConnected () ) {
			this.lastError = "***** NOT CONNECTED *****";
			return false;
		}

		this.records = new ArrayList<Record> ();
		this.lastError = null;

		lastSql = "SELECT Id, Name, EmailAddress, HomePage, Comment "
				+ "FROM " + tableName + " " 
				+ ( filter  == null ? "" : "WHERE " + filter + " " ) 
				+ ( orderBy == null ? "" : "ORDER BY " + orderBy );
	
		try
		{
			Statement statement = db.createStatement ();
	
			ResultSet resultSet = statement.executeQuery( lastSql );
	           
			while( resultSet.next () )
			{
				this.records.add( new Record(
						resultSet.getString(1), // id
						resultSet.getString(2), // name
						resultSet.getString(3), // email address
						resultSet.getString(4), // home page
						resultSet.getString(5)  // comment
						) );
			}
	             
			resultSet.close ();
	            
			statement.close ();
		}
		catch( SQLException e )
		{
			this.lastError = e.toString ();
		}
         
		return this.lastError == null;
	}

	public boolean add(String name, String emailAddress, String homePage, String comment)
	{
		if ( ! db.isConnected () ) {
			this.lastError = "***** NOT CONNECTED *****";
			return false;
		}
	
		name         = suppressHtml( name         , "[censore]" );
		emailAddress = suppressHtml( emailAddress , "[censore]" );
		homePage     = suppressHtml( homePage     , "[censore]" );
		comment      = suppressHtml( comment      , "[censore]" );
	
		this.lastAdded = new Record( null, name, emailAddress, homePage, comment );
	
		this.lastError = null;
	
		lastSql = "INSERT INTO " + tableName + " "
				+ "( Name, EmailAddress, HomePage, Comment ) "
				+ "VALUES ( "
				+ quoteSql( name         ) + ", "
				+ quoteSql( emailAddress ) + ", "
				+ quoteSql( homePage     ) + ", "
				+ quoteSql( comment      )
				+ " )";
	
		try
		{
			Statement statement = db.createStatement ();
			statement.executeUpdate( lastSql );
			statement.close ();
		}
		catch( SQLException e )
		{
			this.lastError = e.toString ();
		}
	
		return this.lastError == null;
	}
	
	private static String quoteSql( String str )
	{
		return str == null || str.length () == 0 ? "NULL"  // empty/null --> NULL
					: "'" + str.replaceAll( "'", "''" ) + "'"; // ' --> ''
	}
	
	private static String suppressHtml( String str, String censored )
	{
		return str
				.replaceAll( "<.*?>", censored ) // Replaces all HTML tags with censored param  
				.replaceAll( "<",  ""   )        // Removes stand-alone '<' 
				.replaceAll( ">",  ""   );       // Removes stand-alone '>'
	}
	
	@SuppressWarnings("unused")
	private static String suppressHtmlAll( String str, String censored )
	{
		return Pattern.compile("<.*>").matcher( str ).find () ? censored : str;
	}
}
