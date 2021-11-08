

	public interface Vat6 extends Vat {
		
		double VAT = 0.06;
		
		default double getVAT() {
			return VAT;
		}
	}


