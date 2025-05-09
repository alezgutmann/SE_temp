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
            }

            JFrame frame = new JFrame("EasyBrew Speisekarte");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().setBackground(new Color(224, 207, 195)); // Beige Hintergrund

            // ArtikelPanel erstellen und hinzufügen
            ArtikelPanel panel = new ArtikelPanel(artikelListe);
            panel.setPreferredSize(new Dimension(2200, 150));

            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            frame.getContentPane().add(scrollPane);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null); // Fenster zentrieren
            frame.setVisible(true);
            
        });
    }
}
