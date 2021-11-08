
public class CompactDisc extends Recording {
		
		private static final double MINIMUM_PRICE = 10.0;
		
		public CompactDisc(String name, String artist, int year, int condition, double price) {
			
			super(name, artist, year, condition, price, "CD");
		}
		
		@Override
		public double getPrice() {
			double value = price*condition/10; 
			if (value < MINIMUM_PRICE) {
				return MINIMUM_PRICE;
			}
			
			return value;
		}

	}

