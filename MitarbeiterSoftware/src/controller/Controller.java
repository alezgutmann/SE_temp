package controller;

import model.*;
import view.MitarbeiterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private MitarbeiterModel model;
    private MitarbeiterView view;

    public Controller(MitarbeiterModel model, MitarbeiterView view) {
        this.model = model;
        this.view = view;
    }

    public void setView(MitarbeiterView view) {
        this.view = view;
    }

    public void loadBestellungen(String dateipfad) {
        try {
            String jsonString = readJsonFile(dateipfad);
            JSONObject obj = new JSONObject(jsonString);

            Bestellung bestellung = parseBestellung(obj);
            model.addBestellung(bestellung); // Bestellung zum Modell hinzufügen

            if (view != null) {
                view.updateView(model.getBestellungen()); // View aktualisieren
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten der Bestellung: " + e.getMessage());
        }
    }

    private String readJsonFile(String pfad) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pfad));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }

    private Bestellung parseBestellung(JSONObject obj) throws JSONException {
        Bestellung bestellung = new Bestellung();
        bestellung.setBestellNr(obj.getInt("BestellNR")); // Korrigiert: setBestellNr
        bestellung.setTischNr(obj.getInt("TischNR"));
        bestellung.setStatus(obj.getString("Status"));

        // Rechnung
        JSONObject rechnungObj = obj.getJSONObject("Rechnung");
        Rechnung rechnung = new Rechnung(rechnungObj.getInt("belegNR"), rechnungObj.getDouble("betrag"));
        rechnung.setBestellung(bestellung);  // Rückreferenz
        bestellung.setRechnung(rechnung);

        // Bestellpositionen
        JSONArray posArray = obj.getJSONArray("bestellpositionen");
        List<Bestellposition> positionen = new ArrayList<>();
        for (int i = 0; i < posArray.length(); i++) {
            JSONObject posObj = posArray.getJSONObject(i);

            Bestellposition pos = new Bestellposition(
                posObj.getInt("artikelAnzahl"),
                posObj.getString("sonderwunsch"),
                posObj.getInt("artikelNR")
            );

            // Dummy Artikel
            Artikel artikel = new Artikel(pos.getArtikelNr(), "Artikel #" + pos.getArtikelNr(), 9.99); // Korrigiert: Konstruktor verwendet
            pos.setArtikel(artikel);

            positionen.add(pos);
        }
        bestellung.setBestellpositionen(positionen);

        return bestellung;
    }
}
