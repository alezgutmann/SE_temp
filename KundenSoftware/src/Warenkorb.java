import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

/**
 * Die Klasse {@code Warenkorb} bietet statische Methoden zum Verwalten von
 * {@link Bestellposition}-Dateien im lokalen Dateisystem.
 * <p>
 * Bestellungen werden im Verzeichnis {@code ./Warenkorb} gespeichert, wobei jede Datei dem Muster
 * {@code nummerImWarenkorb.json} folgt.
 */
public class Warenkorb {

	private Warenkorb() {}
	
	/**
     * Löscht eine gespeicherte Bestellposition anhand ihrer Nummer im Warenkorb.
     *
     * @param nummerImWarenkorb Die Nummer, die der Datei im Verzeichnis {@code ./Warenkorb}
     *                          entspricht (z. B. {@code 2} für {@code ./Warenkorb/2.json}).
     */
	public static void bestellPositionLöschen(int nummerImWarenkorb) {
		String dateiPfad = "./Warenkorb/" + nummerImWarenkorb + ".json";
	    File datei = new File(dateiPfad);

	    if (datei.exists()) {
	        if (datei.delete()) {
	            System.out.println("Datei gelöscht: " + dateiPfad);
	        } else {
	            System.err.println("Datei konnte nicht gelöscht werden: " + dateiPfad);
	        }
	    } else {
	        System.err.println("Datei nicht gefunden: " + dateiPfad);
	    }
	}
	
	
	/**
     * Verändert die Anzahl einer gespeicherten Bestellposition.
     * Die Datei wird dabei überschrieben.
     *
     * @param nummerImWarenkorb Die Dateinummer im Warenkorb (entspricht z. B. {@code ./Warenkorb/1.json}).
     * @param neueAnzahl        Die neue Anzahl, die gespeichert werden soll.
     * @throws IOException Wenn die Datei nicht gelesen oder geschrieben werden kann.
     */
	public static void bestellPositionVerändern(int nummerImWarenkorb, int neueAnzahl) throws IOException{
		String dateiPfad = "./Warenkorb/" + nummerImWarenkorb + ".json";
		String inhalt = new String(Files.readAllBytes(Paths.get(dateiPfad)));
		JSONObject json = new JSONObject(inhalt);
		Bestellposition neueBestPosi = 
				new Bestellposition(
					json.getInt("artikelNR"),
					neueAnzahl, 
					json.getString("sonderwunsch")
				);
		neueBestPosi.overwriteInWarenkorb(nummerImWarenkorb);
	}
	
	/**
     * Verändert den Sonderwunsch einer gespeicherten Bestellposition.
     * Die Datei wird dabei überschrieben.
     *
     * @param nummerImWarenkorb   Die Dateinummer im Warenkorb (entspricht z. B. {@code ./Warenkorb/3.json}).
     * @param neuerSonderwunsch   Der neue Sonderwunsch, der gespeichert werden soll.
     * @throws IOException Wenn die Datei nicht gelesen oder geschrieben werden kann.
     */
	public static void bestellPositionVerändern(int nummerImWarenkorb, String neuerSonderwunsch) throws IOException{
		String dateiPfad = "./Warenkorb/" + nummerImWarenkorb + ".json";
		String inhalt = new String(Files.readAllBytes(Paths.get(dateiPfad)));
		JSONObject json = new JSONObject(inhalt);
		Bestellposition neueBestPosi = 
				new Bestellposition(
					json.getInt("artikelNR"),
					json.getInt("artikelAnzahl"), 
					neuerSonderwunsch
				);
		neueBestPosi.overwriteInWarenkorb(nummerImWarenkorb);
	}
	
	/**
     * Leert den gesamten Warenkorb. Dabei werden alle Dateien im Ordner "./Warenkorb" gelöscht !
     */
	public static void leeren() {
		File ordner = new File("./Warenkorb");

        if (ordner.exists() && ordner.isDirectory()) {
            File[] dateien = ordner.listFiles();
            if (dateien != null) {
                for (File datei : dateien) {
                    if (datei.isFile()) {
                        if (datei.delete()) {
                            System.out.println("Gelöscht: " + datei.getName());
                        } else {
                            System.err.println("Konnte nicht löschen: " + datei.getName());
                        }
                    }
                }
            }
        } else {
            System.err.println("Verzeichnis './Warenkorb' nicht gefunden.");
        }
	}
	
	/**
     * Beispielhafte Testmethode zum Aufruf von {@link #bestellPositionVerändern} und {@link #bestellPositionLöschen}.
     *
     * @param args Wird nicht verwendet.
     */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// bestellPositionLöschen(2); //zum testen auskommentieren und nummer einer existierenden bestellPosition eingeben
		try {
			bestellPositionVerändern(1,69420);
			bestellPositionVerändern(3,"Meddl moininger und mirko");
			
			/*Warenkorb.*/leeren();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
