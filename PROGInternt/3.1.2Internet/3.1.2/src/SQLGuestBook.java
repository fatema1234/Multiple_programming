
public class SQLGuestBook {
	public static void main( String args[] ) 
	{
		System.out.println("Configure: \n databasename: GuestBook\n mySql username: root\n mySql password: admin");
		java.awt.EventQueue.invokeLater( 
			new Runnable() {
				public void run() {
					new GuestBook().setVisible( true );
				}
			}
		);
	}

}
