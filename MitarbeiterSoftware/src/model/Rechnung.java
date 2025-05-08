package model;

public class Rechnung {
    private int belegNr;
    private double betrag;
    private Bestellung bestellung;

    public Rechnung(int belegNr, double betrag) {
        this.belegNr = belegNr;
        this.betrag = betrag;
    }

    public int getBelegNr() { return belegNr; }
    public double getBetrag() { return betrag; }
    public Bestellung getBestellung() { return bestellung; }

    public void setBelegNr(int belegNr) { this.belegNr = belegNr; }
    public void setBetrag(double betrag) { this.betrag = betrag; }
    public void setBestellung(Bestellung bestellung) { this.bestellung = bestellung; }
}