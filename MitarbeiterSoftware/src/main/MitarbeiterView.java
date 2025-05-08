package main;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MitarbeiterView extends JPanel{
	
	public MitarbeiterModel mitModel;
    private JTextArea detailsTextArea; // TextArea für die Details der ausgewählten Bestellung

	
	public MitarbeiterView(MitarbeiterModel mitModel) {
        this.mitModel = mitModel;
        this.setLayout(new BorderLayout());

        // Titel hinzufügen
        this.add(new JLabel("MitarbeiterSoftware"), BorderLayout.NORTH);

        // TextArea für Details initialisieren
        detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(detailsTextArea);
        this.add(detailsScrollPane, BorderLayout.SOUTH);
	}
	
	public void displayBestellung() {
        // Bestellungen aus dem Model abrufen
        List<String> bestellungen = mitModel.getBestellungen(); // Annahme: Methode existiert im Model

        // Haupt-Panel mit BoxLayout für vertikale Anordnung
        JPanel bestellungenPanel = new JPanel();
        bestellungenPanel.setLayout(new BoxLayout(bestellungenPanel, BoxLayout.Y_AXIS));

        // Für jede Bestellung ein eigenes Panel erstellen
        for (String bestellung : bestellungen) {
            JPanel bestellungBox = new JPanel();
            bestellungBox.setBorder(BorderFactory.createTitledBorder(bestellung)); // Titel mit Bestellungsname
            bestellungBox.setLayout(new BorderLayout());

            // Details der Bestellung abrufen
            String details = mitModel.getDetailsZuBestellung(bestellung);

            // TextArea für die Details
            JTextArea detailsTextArea = new JTextArea(details);
            detailsTextArea.setEditable(false);
            bestellungBox.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

            // Button für weitere Aktionen (z. B. Rechnung anzeigen)
            JButton rechnungButton = new JButton("Rechnung anzeigen");
            rechnungButton.addActionListener(e -> {
                String rechnung = mitModel.getRechnungZuBestellung(bestellung);
                JOptionPane.showMessageDialog(this, rechnung, "Rechnung", JOptionPane.INFORMATION_MESSAGE);
            });
            bestellungBox.add(rechnungButton, BorderLayout.SOUTH);

            // Bestellung-Box zum Haupt-Panel hinzufügen
            bestellungenPanel.add(bestellungBox);
        }

        // ScrollPane hinzufügen
        JScrollPane scrollPane = new JScrollPane(bestellungenPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        // Panel aktualisieren
        this.revalidate();
        this.repaint();
	}

	private void updateDetails(String bestellung) {
        // Beispiel: Details der ausgewählten Bestellung anzeigen
        String details = mitModel.getDetailsZuBestellung(bestellung); // Annahme: Methode existiert im Model
        detailsTextArea.setText(details);
		
		// Rechnung für die ausgewählte Bestellung abrufen und anzeigen
		String rechnung = mitModel.getRechnungZuBestellung(bestellung); // Neue Methode im Model
		detailsTextArea.append("\n\nRechnung:\n" + rechnung);
    }
	
	public void rechnungZeigen() {
		// Beispiel: Rechnung aus dem Model abrufen
		String rechnung = mitModel.getRechnung(); // Annahme: Methode existiert im Model

		// GUI-Komponente erstellen
		JTextArea rechnungsText = new JTextArea(rechnung);
		rechnungsText.setEditable(false);
	
		// ScrollPane hinzufügen
		JScrollPane scrollPane = new JScrollPane(rechnungsText);
		this.add(scrollPane, BorderLayout.SOUTH);
	
		// Panel aktualisieren
		this.revalidate();
		this.repaint();
	}
}
