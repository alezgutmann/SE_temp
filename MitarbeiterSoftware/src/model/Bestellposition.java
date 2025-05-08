package model;

public class Bestellposition {
    private int artikelAnzahl;
    private String sonderwunsch;
    private int artikelNr;
    private Artikel artikel;

    public Bestellposition(int artikelAnzahl, String sonderwunsch, int artikelNr) {
        this.artikelAnzahl = artikelAnzahl;
        this.sonderwunsch = sonderwunsch;
        this.artikelNr = artikelNr;
    }

    public int getArtikelAnzahl() { return artikelAnzahl; }
    public String getSonderwunsch() { return sonderwunsch; }
    public int getArtikelNr() { return artikelNr; }
    public Artikel getArtikel() { return artikel; }

    public void setArtikelAnzahl(int artikelAnzahl) { this.artikelAnzahl = artikelAnzahl; }
    public void setSonderwunsch(String sonderwunsch) { this.sonderwunsch = sonderwunsch; }
    public void setArtikelNr(int artikelNr) { this.artikelNr = artikelNr; }
    public void setArtikel(Artikel artikel) { this.artikel = artikel; }
}