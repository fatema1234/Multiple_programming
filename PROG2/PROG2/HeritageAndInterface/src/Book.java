
public class Book extends Item implements Vat6 {
		private final String author;
		private final double price;
		private final boolean bound;
	    private final double bindingPercentageMultipleFactor = 1.25;
	    
		public Book(String name, String author, double price, boolean bound) {
			super(name);
			this.author = author;
			this.bound = bound;
			this.price = price;
		}

		@Override
		public double getPrice() {
			if (this.bound) {
				return price*bindingPercentageMultipleFactor;
			}
			
			return price;
		}
		
		@Override
		public String toString() {
			return "Book {"
					+ " name='" + name + "'"
					+ ", author='" + author + "'"
					+ ", bound=" + bound 
					+ ", price=" + getPrice()  
					+ ", price+vat=" + getPricePlusVAT() + " }";
		}
	}




