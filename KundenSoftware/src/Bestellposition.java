

/**
 * Die Klasse {@code Bestellposition} repräsentiert eine einzelne Position in einer Bestellung.
 * Sie enthält die Artikelnummer, die gewünschte Menge sowie einen optionalen Sonderwunsch.
 */
public class Bestellposition {
	public int artikelNR;
	public String sonderwunsch;
	public int artikelAnzahl;
	public double preis = 0;
	
	
	/**
     * Konstruktor zum Erzeugen einer neuen Bestellposition.
     *
     * @param artikelNR      Die Artikelnummer
     * @param artikelAnzahl  Die gewünschte Anzahl des Artikels
     * @param sonderwunsch   Sonderwunsch des Kunden zur Bestellung
     */
	public Bestellposition(int artikelNR, int artikelAnzahl, String sonderwunsch) {
		this.artikelNR = artikelNR;
		this.artikelAnzahl = artikelAnzahl;
		this.sonderwunsch = sonderwunsch;
		
		preisBerechnen();
		
	}
	
	public void preisBerechnen() {
		Artikel tmp = Artikel.findArtikelByArtikelNR(this.artikelNR);
		this.preis = (tmp.get_preis() * this.artikelAnzahl);
	}
	
	
	/**
     * Hauptmethode zum Testen der Klasse {@code Bestellposition}.
     *
     * @param args Kommandozeilenargumente (werden hier nicht verwendet)
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bestellposition bpos = new Bestellposition(1, 10, "Aber bitte mit Sahne! Lol Roflkopter");
		System.out.println(bpos.preis);
	}

}
