package model;

import java.util.List;

public class Bestellung {
    private int bestellNr;
    private int tischNr;
    private String status;
    private List<Bestellposition> bestellpositionen;
    private Rechnung rechnung;

    public Bestellung() {
        // Parameterloser Konstruktor für JSON-Parsing oder leere Initialisierung
    }

    public Bestellung(int bestellNr, int tischNr, String status, List<Bestellposition> bestellpositionen, Rechnung rechnung) {
        this.bestellNr = bestellNr;
        this.tischNr = tischNr;
        this.status = status;
        this.bestellpositionen = bestellpositionen;
        this.rechnung = rechnung;
    }

    public int getBestellNr() { return bestellNr; }
    public int getTischNr() { return tischNr; }
    public String getStatus() { return status; }
    public List<Bestellposition> getBestellpositionen() { return bestellpositionen; }
    public Rechnung getRechnung() { return rechnung; }

    public void setBestellNr(int bestellNr) { this.bestellNr = bestellNr; }
    public void setTischNr(int tischNr) { this.tischNr = tischNr; }
    public void setStatus(String status) { this.status = status; }
    public void setBestellpositionen(List<Bestellposition> bestellpositionen) { this.bestellpositionen = bestellpositionen; }
    public void setRechnung(Rechnung rechnung) { this.rechnung = rechnung; }
}
