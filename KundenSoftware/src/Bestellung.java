
/**
 * Die Klasse {@code Bestellung} verwaltet eine Sammlung von
 * {@code Bestellposition}-Objekten, welche später in einer Bestellung übergeben
 * werden.
 */
public class Bestellung {

	private Bestellposition[] positionen = new Bestellposition[0]; // Leeres Array zum Start

	public Bestellung() {
	}

	/**
	 * Fügt eine neue {@code Bestellposition} zur Bestellung hinzu.
	 *
	 * @param neuePosition Die hinzuzufügende Bestellposition
	 */
	public void positionHinzufügen(Bestellposition neuePosition) {
		Bestellposition[] neueListe = new Bestellposition[positionen.length + 1];
		System.arraycopy(positionen, 0, neueListe, 0, positionen.length);
		neueListe[positionen.length] = neuePosition;
		positionen = neueListe;
	}

	/**
	* Entfernt eine Bestellposition an der angegebenen Position.
	*
	* @param index Der Index der zu entfernenden Position
	 */
	public void positionLöschen(int index) {
	    if (index < 0 || index >= positionen.length) {
	        System.out.println("Ungültiger Index: " + index);
	        return;
	    }
	
	    Bestellposition[] neueListe = new Bestellposition[positionen.length - 1];
	    for (int i = 0, j = 0; i < positionen.length; i++) {
	        if (i != index) {
	            neueListe[j++] = positionen[i];
	        }
	    }
	    positionen = neueListe;
	}
	
	/**
     * Gibt eine textuelle Repräsentation der gesamten Bestellung zurück.
     *
     * @return Alle Bestellpositionen als String
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bestellung:\n");
        for (int i = 0; i < positionen.length; i++) {
            sb.append(i).append(": ")
              .append("Artikel-Nr: ").append(positionen[i].artikelNR)
              .append(", Anzahl: ").append(positionen[i].artikelAnzahl)
              .append(", Sonderwunsch: ").append(positionen[i].sonderwunsch)
              .append("\n");
        }
        return sb.toString();
    }

    
    
    /**
     * Hauptmethode zum Testen.
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bestellung b = new Bestellung();
		Bestellposition bpos = new Bestellposition(1, 10, "Aber bitte mit Sahne! Lol Roflkopter");
		b.positionHinzufügen(bpos);
        b.positionHinzufügen(new Bestellposition(2, 1, "Ohne Soße"));
        System.out.println(b);

        b.positionLöschen(0);
        System.out.println("Nach Löschen:");
        System.out.println(b);


	}

}
