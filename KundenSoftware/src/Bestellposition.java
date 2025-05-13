import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * Die Klasse {@code Bestellposition} repräsentiert eine einzelne Position in
 * einer Bestellung. Sie enthält die Artikelnummer, die gewünschte Menge sowie
 * einen optionalen Sonderwunsch.
 */
public class Bestellposition {
	public int artikelNR;
	public String sonderwunsch;
	public int artikelAnzahl;
	public double preis = 0;

	/**
	 * Konstruktor zum Erzeugen einer neuen Bestellposition.
	 *
	 * @param artikelNR     Die Artikelnummer
	 * @param artikelAnzahl Die gewünschte Anzahl des Artikels
	 * @param sonderwunsch  Sonderwunsch des Kunden zur Bestellung
	 */
	public Bestellposition(int artikelNR, int artikelAnzahl, String sonderwunsch) {
		this.artikelNR = artikelNR;
		this.artikelAnzahl = artikelAnzahl;
		this.sonderwunsch = sonderwunsch;

		preisBerechnen();

	}

	/**
     * Berechnet den Gesamtpreis der Bestellposition basierend auf Artikelpreis und Anzahl.
     */
	public void preisBerechnen() {
		Artikel tmp = Artikel.findArtikelByArtikelNR(this.artikelNR);
		this.preis = (tmp.get_preis() * this.artikelAnzahl);
	}

	/**
     * Ermittelt die kleinste freie Nummer für eine neue Bestellposition im Verzeichnis {@code ./Warenkorb}.
     *
     * @return Die erste freie fortlaufende Dateinummer ab 1
     */
	private int availablePosNRs() {
		File ordner = new File("./Warenkorb");
		Set<Integer> vergebeneNummern = new HashSet<>();

		if (!ordner.exists() || !ordner.isDirectory()) {
			System.out.println("Verzeichnis nicht gefunden: " + ordner.getAbsolutePath());
			return 1; // Wenn der Ordner nicht existiert, starten wir mit 1
		}

		File[] dateien = ordner.listFiles((dir, name) -> name.matches("\\d+\\.json"));
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
     * Speichert die Bestellposition als neue Datei im Verzeichnis "./Warenkor"
     * unter einer automatisch vergebenen freien Nummer.
     */
	public void sendToWarenkorb() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("artikelNR", this.artikelNR);
		jsonObj.put("artikelAnzahl", this.artikelAnzahl);
		jsonObj.put("sonderwunsch", this.sonderwunsch);

		try {
			// Sicherstellen, dass der Ordner existiert
			String ordnerPfad = "./Warenkorb";
			Files.createDirectories(Paths.get(ordnerPfad));

			// Datei schreiben
			String dateipfad = ordnerPfad + "/" + availablePosNRs() + ".json";
			try (FileWriter file = new FileWriter(dateipfad)) {
				file.write(jsonObj.toString(4)); // 4 = Einrückung für Lesbarkeit
				file.flush();
				System.out.println("Bestellposition erfolgreich gespeichert unter: " + dateipfad);
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern in den Warenkorb!");
			e.printStackTrace();
		}
	}
	
	/**
     * Überschreibt eine bestehende Bestellposition im Verzeichnis "./Warenkorb"
     * anhand der übergebenen Dateinummer.
     *
     * @param nummerImWarenkorb Die Nummer der Datei (z.B. 3 für "./Warenkorb/3.json")
     */
	public void overwriteInWarenkorb(int nummerImWarenkorb) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("artikelNR", this.artikelNR);
		jsonObj.put("artikelAnzahl", this.artikelAnzahl);
		jsonObj.put("sonderwunsch", this.sonderwunsch);

		try {
			// Sicherstellen, dass der Ordner existiert
			String ordnerPfad = "./Warenkorb";
			Files.createDirectories(Paths.get(ordnerPfad));

			// Datei schreiben
			String dateipfad = ordnerPfad + "/" + nummerImWarenkorb + ".json";
			try (FileWriter file = new FileWriter(dateipfad)) {
				file.write(jsonObj.toString(4)); // 4 = Einrückung für Lesbarkeit
				file.flush();
				System.out.println("Bestellposition erfolgreich gespeichert unter: " + dateipfad);
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Speichern in den Warenkorb!");
			e.printStackTrace();
		}
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
