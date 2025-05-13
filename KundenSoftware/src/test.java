import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Artikel> artikelListe = new ArrayList<>();
            try {
                // Beispiel-Artikel laden
                artikelListe.add(Artikel.artikelladen("./Essen/burger.json"));
                artikelListe.add(Artikel.artikelladen("./Essen/croissant.json"));
                artikelListe.add(Artikel.artikelladen("./Essen/kaffee.json"));
                artikelListe.add(Artikel.artikelladen("./Essen/keks.json"));
                artikelListe.add(Artikel.artikelladen("./Essen/choccy_milk.json"));
                artikelListe.add(Artikel.artikelladen("./Essen/quarkkeulchen.json"));
                artikelListe.add(Artikel.artikelladen("./Essen/apfelstrudel.json"));

                // Weitere Artikel nach Bedarf
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Fehler beim Laden der Artikel-Daten: " + e.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                return;  // Sofortige Beendigung, falls ein Fehler beim Laden auftritt
            }

            // Erstelle das JFrame
            JFrame frame = new JFrame("EasyBrew Speisekarte");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(new Color(224, 207, 195)); // Beige Hintergrund

            // ArtikelPanel erstellen und hinzufügen
            ArtikelPanel panel = new ArtikelPanel(artikelListe);
            panel.setPreferredSize(new Dimension(2200, 800)); 

            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            // Setze die ScrollPane in das JFrame
            frame.getContentPane().add(scrollPane);

            // Packen der Fenstergröße basierend auf dem Inhalt
            frame.pack();
            frame.setLocationRelativeTo(null); // Fenster zentrieren
            frame.setVisible(true);
        });
    }
}
