
public abstract class Recording extends Item implements Vat25 {
	protected final int year;
	protected final String artist;
	protected final double price;
	protected int condition;
	private final String type;
	
	
	public Recording(String name, String artist, int year, int condition, double  price, String type){
		super(name);
		this.artist = artist;
		this.year = year;
		this.condition = condition;
		this.price = price;
		this.type = type;
	}

	@Override
	public String toString() {
		return "Recording {"
				+ " name=" + name
				+ ", artist='" + artist + "'"
				+ ", year=" + year 
				+ ", type=" + type  
				+ ", condition=" + condition
				+ ", original price=" + price 
				+ ", price=" + getPrice() 
				+ ", price+vat=" + getPricePlusVAT() + " }";
	}
}



