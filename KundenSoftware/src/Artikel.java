import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class Artikel {
    private double preis;
    private String artikelNR;
    private String name;
    private String vorschaubild;

    public Artikel() {}
    
    public Artikel(double preis, String artikelNR, String name, String vorschaubild) {
        this.preis = preis;
        this.artikelNR = artikelNR;
        this.name = name;
        this.vorschaubild = vorschaubild;
    }
    //nimmt Werte aus Json Datei
    public static Artikel artikelladen(String pfad) throws IOException {
        String inhalt = new String(Files.readAllBytes(Paths.get(pfad)));
        JSONObject json = new JSONObject(inhalt);
        return new Artikel(
            json.getDouble("preis"),
            json.getString("artikelNR"),
            json.getString("name"),
            json.getString("vorschaubild")
        );
    }
    
    //gibt Namen an Menu zurueck
    public String getName() {
        return name;
    }
    //gibt Preis an Menu zurueck
    public Double getPreis() {
    	return preis;
    }
    //gibt Bild an Menu zurueck
    public String getVorschaubild() {
        return vorschaubild;
    }

    
    @Override
    public String toString() {
        return "Artikel{ " + "preis=" + preis + ", artikelNR=" + artikelNR +
               ", name=" + name + ", vorschaubild=" + vorschaubild + " }";
    }
}
