package main;

import java.util.ArrayList;
import java.util.List;

public class MitarbeiterModel {

	public Bestellung bestelung;
	
	public MitarbeiterModel() {
		
	}
	
    public List<String> getBestellungen() {
        // Beispiel: Dummy-Daten
        List<String> bestellungen = new ArrayList<>();
        bestellungen.add("Bestellung 1");
        bestellungen.add("Bestellung 2");
        return bestellungen;
    }

    public String getRechnung() {
        // Beispiel: Dummy-Rechnung
        return "Rechnung:\n- Artikel 1: 10€\n- Artikel 2: 20€\nGesamt: 30€";
    }
	
    public String getDetailsZuBestellung(String bestellung) {
        // Beispiel: Dummy-Daten
        return "Details zu " + bestellung + ":\n- Artikel 1: 10€\n- Artikel 2: 20€\nGesamt: 30€";
    }

    public String getRechnungZuBestellung(String bestellung) {
        // Beispiel: Dummy-Daten für die Rechnung
        if (bestellung.equals("Bestellung 1")) {
            return "- Artikel A: 15€\n- Artikel B: 25€\nGesamt: 40€";
        } else if (bestellung.equals("Bestellung 2")) {
            return "- Artikel C: 10€\n- Artikel D: 20€\nGesamt: 30€";
        } else {
            return "Keine Rechnung verfügbar.";
        }
    }
}
