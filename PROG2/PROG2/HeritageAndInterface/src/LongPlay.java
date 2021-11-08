

	public class LongPlay extends Recording {
		
		private static final double MINIMUM_PRICE = 10.0;
		
		public LongPlay(String name, String artist, int year, int condition, double price) {
			
			super(name, artist, year, condition, price, "LP");
		}
		
		@Override
		public double getPrice() {
			double value = price*condition/10 + (2021-year) * 5.0; 
			if (value < MINIMUM_PRICE) {
				return MINIMUM_PRICE;
			}
			
			return value;
		}

	}


