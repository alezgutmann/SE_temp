package controller;

import model.*;
import view.MitarbeiterView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File; // Import File
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

    // Modifiziert: Aktualisiert die View nicht mehr direkt.
    // Diese Methode lädt Daten aus einer einzelnen Datei in das Modell.
    public void loadBestellungen(String dateipfad) {
        try {
            String jsonString = readJsonFile(dateipfad);
            JSONObject obj = new JSONObject(jsonString);

            Bestellung bestellung = parseBestellung(obj);
            model.addBestellung(bestellung); // Bestellung zum Modell hinzufügen

        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + dateipfad + " - " + e.getMessage());
        } catch (JSONException e) {
            System.err.println("Fehler beim Parsen von JSON aus Datei: " + dateipfad + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten der Bestellung aus Datei: " + dateipfad + " - " + e.getMessage());
        }
    }

    // Neue Methode, um alle Bestellungen aus dem Standardverzeichnis zu laden
    public void loadAllAvailableBestellungen() {
        // Optional: Bestehende Bestellungen löschen, falls die Liste ersetzt werden soll
        model.getBestellungen().clear(); 

        File dataDir = new File("src/data");
        if (!dataDir.exists() || !dataDir.isDirectory()) {
            System.err.println("Das Verzeichnis 'src/data' wurde nicht gefunden oder ist kein Verzeichnis.");
            // Hier könnte eine Fehlermeldung in der View angezeigt werden
            // z.B. if (view != null) view.showErrorDialog("Verzeichnis 'src/data' nicht gefunden.");
            return;
        }

        File[] files = dataDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    // Ruft die modifizierte Methode auf, die nur zum Modell hinzufügt
                    loadBestellungen(file.getAbsolutePath());
                }
            }
        } else {
            System.err.println("Konnte den Inhalt des Verzeichnisses 'src/data' nicht lesen oder es ist leer.");
            // Hier könnte eine Info in der View angezeigt werden
        }

        if (view != null) {
            view.updateView(model.getBestellungen()); // Aktualisiert die View einmal, nachdem alle Dateien verarbeitet wurden
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
        bestellung.setBestellNr(obj.getInt("BestellNR")); 
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
            Artikel artikel = new Artikel(pos.getArtikelNr(), "Artikel #" + pos.getArtikelNr(), 9.99); 
            pos.setArtikel(artikel);

            positionen.add(pos);
        }
        bestellung.setBestellpositionen(positionen);

        return bestellung;
    }

    public void updateBestellStatus(int bestellNr, String neuerStatus) {
        Bestellung bestellung = model.getBestellungen().stream()
            .filter(b -> b.getBestellNr() == bestellNr)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Bestellung nicht gefunden: " + bestellNr));
        bestellung.setStatus(neuerStatus);
        if (view != null) {
            view.updateView(model.getBestellungen());
        }
    }
    
    public List<Bestellung> getBestellungen() {
        return model.getBestellungen();
    }
}