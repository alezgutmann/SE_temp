package main;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
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

        // GUI-Komponente erstellen
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String bestellung : bestellungen) {
            listModel.addElement(bestellung);
        }
        JList<String> bestellungsListe = new JList<>(listModel);

        // Listener hinzufügen, um auf Auswahländerungen zu reagieren
        bestellungsListe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedBestellung = bestellungsListe.getSelectedValue();
                    if (selectedBestellung != null) {
                        updateDetails(selectedBestellung);
                    }
                }
            }
        });

        // ScrollPane hinzufügen
        JScrollPane scrollPane = new JScrollPane(bestellungsListe);
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
