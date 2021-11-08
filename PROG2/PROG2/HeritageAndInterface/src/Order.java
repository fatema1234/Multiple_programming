
import java.util.List;

	public class Order {
		private static long counter;
		private final long orderNumber;
		private final List<Item> items;
		
		public Order(Item... items) {
			this.items = List.of(items);
			this.orderNumber = ++counter;
		}
		
		public double getTotalValue() {
			double totalValue = 0.0;
			for (Item item : items) {
				totalValue += item.getPrice();
			}
			
			return totalValue;
		}
		
		public double getTotalValuePlusVAT() {
			double totalValuePlusVAT = 0.0;
			for (Item item : items) {
				totalValuePlusVAT += item.getPricePlusVAT();
			}
			
			return totalValuePlusVAT;
		}
		
		public String getReceipt() {
			String receipt = "Receipt for order #" + orderNumber; 
			receipt += "\n-----------";
			
			for (Item item : items) {
				receipt += "\n" + item.toString();
			}
			
			receipt += "\nTotal excl. VAT: " + getTotalValue();
			receipt += "\nTotal incl. VAT: " + getTotalValuePlusVAT();

			return receipt;
		}

	}


