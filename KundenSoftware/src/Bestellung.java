import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Die Klasse {@code Bestellung} verwaltet eine Sammlung von
 * {@code Bestellposition}-Objekten, welche später in einer Bestellung übergeben
 * werden.
 */
public class Bestellung {	
	private int tischNR;
	private int BestellNR;
	private String status;
	public Bestellposition[] positionen = new Bestellposition[0]; // Leeres Array zum Start

	
	
	
	
	/**
     * Konstruktor zur Initialisierung einer neuen Bestellung für einen bestimmten Tisch.
     * Die Bestellnummer wird automatisch vergeben.
     *
     * @param tischNR Die Tischnummer
     */
	public Bestellung(int tischNR) {
		this.status = "Noch nicht Bearbeitet";
		this.tischNR = tischNR;
		this.BestellNR = getAvailableBestellNR();
	}
	
	/**
     * Ermittelt die kleinste freie Bestellnummer im Verzeichnis "../Austauschordner".
     *
     * @return Die erste freie fortlaufende Bestellnummer ab 1
     */
	public int getAvailableBestellNR() {
		File ordner = new File("../Austauschordner");
	    Set<Integer> vergebeneNummern = new HashSet<>();

	    if (!ordner.exists() || !ordner.isDirectory()) {
	        System.out.println("Verzeichnis nicht gefunden: " + ordner.getAbsolutePath());
	        return 1; // Wenn der Ordner nicht existiert, starten wir mit 1
	    }

	    File[] dateien = ordner.listFiles((dir, name) -> name.matches("bestellung_\\d+\\.json"));
	    if (dateien != null) {
	        for (File datei : dateien) {
	            String name = datei.getName();
	            String nummerString = name.replaceAll("[^\\d]", "");
	            try {
	                int nummer = Integer.parseInt(nummerString);
	                vergebeneNummern.add(nummer);
	            } catch (NumberFormatException e) {
	                // Ignorieren
	            }
	        }
	    }

	    // Die kleinste freie Nummer finden
	    int freieNummer = 1;
	    System.out.println(vergebeneNummern);
	    while (vergebeneNummern.contains(freieNummer)) {
	        freieNummer++;
	    }

	    return freieNummer;
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
			sb.append(i).append(": ").append("Artikel-Nr: ").append(positionen[i].artikelNR).append(", Anzahl: ")
					.append(positionen[i].artikelAnzahl).append(", Sonderwunsch: ").append(positionen[i].sonderwunsch)
					.append("\n");
		}
		return sb.toString();
	}

	/**
     * Speichert die Bestellung als JSON-Datei im Verzeichnis ../Austauschordner.
     */
    public void bestellungVersenden() {
    	//erst hier die Rechnung generieren
    	int belegNR_spaceholder = 1;
    	Rechnung rech = new Rechnung(belegNR_spaceholder,this); //erst mal Platzhalter für BelegNR
    	
    	JSONObject rechnungjson = new JSONObject();
    	rechnungjson.put("belegNR", rech.belegNR);
    	rechnungjson.put("betrag", rech.betrag);
    	
    	
        JSONArray jsonArray = new JSONArray();
        for (Bestellposition pos : positionen) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("artikelNR", pos.artikelNR);
            jsonObj.put("artikelAnzahl", pos.artikelAnzahl);
            jsonObj.put("sonderwunsch", pos.sonderwunsch);
            jsonArray.put(jsonObj);
        }
        
        

        JSONObject bestellungObj = new JSONObject();
        bestellungObj.put("BestellNR", this.BestellNR);
        bestellungObj.put("TischNR", this.tischNR);
        bestellungObj.put("Status", this.status);
        bestellungObj.put("Rechnung", rechnungjson);
        bestellungObj.put("bestellpositionen", jsonArray);

        try {
        	// Sicherstellen, dass der Ordner existiert
            String ordnerPfad = "../Austauschordner";
            Files.createDirectories(Paths.get(ordnerPfad));

            // Datei schreiben
            String dateipfad = ordnerPfad + "/bestellung_"+ this.BestellNR + ".json";
            try (FileWriter file = new FileWriter(dateipfad)) {
                file.write(bestellungObj.toString(4)); // 4 = Einrückung für Lesbarkeit
                file.flush();
                System.out.println("Bestellung erfolgreich gespeichert unter: " + dateipfad);
        }
            }catch (IOException e) {
        	System.out.println("Fehler beim speichern der Bestellung!");
        	e.printStackTrace();
        }
    }

	/**
	 * Hauptmethode zum Testen.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bestellung b = new Bestellung(69);
		Bestellposition bpos = new Bestellposition(1, 10, "Aber bitte mit Sahne! Lol Roflkopter");
		b.positionHinzufügen(bpos);
		b.positionHinzufügen(bpos);
		b.positionHinzufügen(bpos);
		b.positionHinzufügen(new Bestellposition(2, 1, "Ohne Soße"));
		System.out.println(b);

		b.positionLöschen(0);
		System.out.println("Nach Löschen:");
		System.out.println(b);
		
		System.out.println(b.BestellNR);
		
		b.bestellungVersenden();
	}

}
