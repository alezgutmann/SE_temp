package model;

public class Artikel {
    private int artikelNr;
    private String name;
    private double preis;
    private String vorschaubild; 

    public Artikel(int artikelNr, String name, double preis) {
        this.artikelNr = artikelNr;
        this.name = name;
        this.preis = preis;
    }

    public int getArtikelNr() { return artikelNr; }
    public String getName() { return name; }
    public double getPreis() { return preis; }
    public String getVorschaubild() { return vorschaubild; }

    public void setArtikelNr(int artikelNr) { this.artikelNr = artikelNr; }
    public void setName(String name) { this.name = name; }
    public void setPreis(double preis) { this.preis = preis; }
    public void setVorschaubild(String vorschaubild) { this.vorschaubild = vorschaubild; }
}