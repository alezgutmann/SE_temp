package model;

import java.util.ArrayList;
import java.util.List;

public class MitarbeiterModel {
    private List<Bestellung> bestellungen;
    private List<Mitarbeiter> mitarbeiterListe;

    public MitarbeiterModel() {
        this.bestellungen = new ArrayList<>();
        this.mitarbeiterListe = new ArrayList<>();
    }

    public void addBestellung(Bestellung b) {
        bestellungen.add(b);
    }

    public List<Bestellung> getBestellungen() {
        return bestellungen;
    }

    public void addMitarbeiter(Mitarbeiter m) {
        mitarbeiterListe.add(m);
    }

    public List<Mitarbeiter> getMitarbeiterListe() {
        return mitarbeiterListe;
    }

    public Mitarbeiter getMitarbeiterById(int id) {
        for (Mitarbeiter m : mitarbeiterListe) {
            if (m.getId() == id) return m;
        }
        return null;
    }
}