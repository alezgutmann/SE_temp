package view;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MitarbeiterView extends JFrame {
    private Controller controller;

    public MitarbeiterView(Controller controller) {
        this.controller = controller;
        setTitle("Bestellungsverwaltung");
        setSize(800, 600); // Breitere Fenstergröße für nebeneinander liegende Panels
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JButton loadButton = new JButton("Bestellungen Laden");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser("src/data");
            fileChooser.setMultiSelectionEnabled(true); // Mehrfachauswahl aktivieren
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();
                for (File file : selectedFiles) {
                    controller.loadBestellungen(file.getAbsolutePath());
                }
            }
        });

        JPanel bestellungenPanel = new JPanel();
        bestellungenPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 Spalten, dynamische Zeilen, mit Abständen

        JScrollPane scrollPane = new JScrollPane(bestellungenPanel);
        add(scrollPane, BorderLayout.CENTER);
        add(loadButton, BorderLayout.SOUTH);
    }

    public void updateView(List<Bestellung> bestellungen) {
        JScrollPane scrollPane = (JScrollPane) getContentPane().getComponent(0);
        JPanel bestellungenPanel = (JPanel) scrollPane.getViewport().getView();

        bestellungenPanel.removeAll();

        for (Bestellung bestellung : bestellungen) {
            JPanel bestellungPanel = new JPanel();
            bestellungPanel.setLayout(new BoxLayout(bestellungPanel, BoxLayout.Y_AXIS));
            bestellungPanel.setBorder(BorderFactory.createTitledBorder("Bestellung " + bestellung.getBestellNr()));

            JLabel bestellNrLabel = new JLabel("BestellNr: " + bestellung.getBestellNr());
            JLabel tischNrLabel = new JLabel("TischNr: " + bestellung.getTischNr());
            JLabel statusLabel = new JLabel("Status: " + bestellung.getStatus());

            bestellungPanel.add(bestellNrLabel);
            bestellungPanel.add(tischNrLabel);
            bestellungPanel.add(statusLabel);

            for (Bestellposition position : bestellung.getBestellpositionen()) {
                JLabel positionLabel = new JLabel("ArtikelNr: " + position.getArtikelNr() + ", Anzahl: " + position.getArtikelAnzahl() + ", Wunsch: " + position.getSonderwunsch());
                bestellungPanel.add(positionLabel);
            }

            bestellungenPanel.add(bestellungPanel);
        }

        revalidate();
        repaint();
    }
}