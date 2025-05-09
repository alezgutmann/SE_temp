import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;


/**
 * Die Klasse {@code Artikel} repräsentiert einen Artikel mit Preis,
 * Artikelnummer, Name und Vorschaubild. Sie kann einen Artikel aus einer
 * JSON-Datei laden.
 */
public class Artikel {
	private double preis;
	private int artikelNR;
	private String name;
	private String vorschaubild;

	public Artikel() {
	}

	/**
	 * Konstruktor zum Erstellen eines Artikels mit allen Eigenschaften. Wird
	 * allerdings so nicht zum initialisieren verwendet, da er in artikelladen()
	 * enthalten ist.
	 *
	 * @param preis        Der Preis des Artikels
	 * @param artikelNR    Die Artikelnummer
	 * @param name         Der Name des Artikels
	 * @param vorschaubild Der Pfad oder Name des Vorschaubilds
	 */
	public Artikel(double preis, int artikelNR, String name, String vorschaubild) {
		this.preis = preis;
		this.artikelNR = artikelNR;
		this.name = name;
		this.vorschaubild = vorschaubild;
	}

	/**
	 * Lädt einen Artikel aus einer JSON-Datei.
	 *
	 * @param pfad Pfad zur JSON-Datei, die die Artikeldaten enthält
	 * @return Ein {@code Artikel}-Objekt mit den geladenen Daten
	 * @throws IOException Wenn ein Fehler beim Lesen der Datei auftritt
	 */
	public static Artikel artikelladen(String pfad) throws IOException {
		String inhalt = new String(Files.readAllBytes(Paths.get(pfad)));
		JSONObject json = new JSONObject(inhalt);
		Artikel artikel = new Artikel(json.getDouble("preis"), json.getInt("artikelNR"), json.getString("name"),
				json.getString("vorschaubild"));
		return artikel;
	}

	public static Artikel findArtikelByArtikelNR(int ArtikelNR) {
		File ordner = new File("./Essen");

		if (!ordner.exists() || !ordner.isDirectory()) {
			System.out.println("Verzeichnis nicht gefunden: " + ordner.getAbsolutePath());
			return null;
		}

		File[] dateien = ordner.listFiles((dir, name) -> name.endsWith(".json"));
		if (dateien == null)
			return null;

		for (File datei : dateien) {
			try {
				String inhalt = Files.readString(datei.toPath());
				JSONObject json = new JSONObject(inhalt);

				if (json.has("artikelNR") && json.getInt("artikelNR") == ArtikelNR) {
					Artikel art = artikelladen(datei.getPath());
					return art;
				}
			} catch (IOException | org.json.JSONException e) {
				// Fehlerhafte JSON oder Datei nicht lesbar — überspringen
			}
		}

		return null;
	}
	/**
	 * Gibt eine String-Repräsentation des Artikels zurück.
	 *
	 * @return String mit Artikelinformationen
	 */
	@Override
	public String toString() {
		return "Artikel{ " + "preis=" + preis + ", artikelNR=" + artikelNR + ", name=" + name + ", vorschaubild="
				+ vorschaubild + " }";
	}

	/**
	 * Hauptmethode zum Testen des Ladevorgangs eines Artikels aus einer JSON.
	 *
	 * @param args Kommandozeilenargumente werden nicht verwendet
	 */
	public static void main(String[] args) {
		try {
			Artikel artikel = Artikel.artikelladen("./Essen/burger.json");
			System.out.println(artikel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//gibt Namen an Menu zurueck
   	public String getName() {
        	return name;
   	 }
	public double get_preis() {
		return this.preis;
	}
	//gibt Bild an Menu zurueck
    	public String getVorschaubild() {
        	return vorschaubild;
    }

}

