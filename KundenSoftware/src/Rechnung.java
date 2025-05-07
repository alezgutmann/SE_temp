
public class Rechnung {
	public int belegNR;
	public double betrag;
	
	public Rechnung(int belegNR, Bestellung best) {
		this.belegNR = belegNR;
		
		for (Bestellposition pos : best.positionen) {
			this.betrag += pos.preis;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
