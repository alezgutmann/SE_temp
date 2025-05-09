package view;

import controller.Controller;
import model.Bestellung;
import model.Bestellposition;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
// import java.io.File; // Nicht mehr direkt für JFileChooser benötigt, wenn alle geladen werden
import java.util.List;

public class MitarbeiterView extends JFrame {
    private Controller controller;
    private JPanel bestellungenPanel;

    public MitarbeiterView(Controller controller) {
        this.controller = controller;
        setTitle("Bestellungsverwaltung");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Definieren der neuen Hintergrundfarbe
        Color customBackgroundColor = Color.decode("#e4d2a3");

        // Hintergrund des Hauptfensters (Content Pane) setzen
        getContentPane().setBackground(customBackgroundColor);

        // Topbar erstellen
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.white); // Topbar bleibt weiß
        topBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Mitte: Titel - zuerst erstellen, um die Höhe zu bestimmen
        JLabel titleLabel = new JLabel("Mitarbeiter Software");
        Font titleFont = new Font("Times New Roman", Font.BOLD, 32); // Titeltext: Times New Roman, Fett, 32
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.BLACK); // Titelfarbe auf Schwarz für weißen Hintergrund
        
        // Höhe des Titels für die Icon-Größe bestimmen
        FontMetrics fm = new JLabel().getFontMetrics(titleFont); 
        int titleTextHeight = fm.getHeight(); 

        // Links: Logout-Button mit Bild
        JButton logoutButton = createIconButton("/images/logout.png", "Logout", titleTextHeight);
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout erfolgreich!");
            System.exit(0);
        });
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false); 
        leftPanel.add(logoutButton);
        topBar.add(leftPanel, BorderLayout.WEST);

        // Titel-Panel für die Mitte
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false); 
        centerPanel.add(titleLabel);
        topBar.add(centerPanel, BorderLayout.CENTER);


        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setOpaque(false); 

        JButton profilButton = createIconButton("/images/profile.png", "Profil", titleTextHeight);
        JButton settingsButton = createIconButton("/images/settings.png", "Einstellungen", titleTextHeight);
        JButton helpButton = createIconButton("/images/help.png", "Hilfe", titleTextHeight);

        rightPanel.add(profilButton);
        rightPanel.add(settingsButton);
        rightPanel.add(helpButton);

        topBar.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        //Panel für Bestellungen
        bestellungenPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        bestellungenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bestellungenPanel.setBackground(customBackgroundColor); // Neue Hintergrundfarbe

        JScrollPane scrollPane = new JScrollPane(bestellungenPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(customBackgroundColor); // Neue Hintergrundfarbe für den Viewport
        add(scrollPane, BorderLayout.CENTER);

        // -- Button Laden --
        JButton loadButton = new JButton("Bestellungen Laden");
        loadButton.addActionListener(e -> {
            controller.loadAllAvailableBestellungen(); 
        });
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        south.setBorder(new EmptyBorder(5,5,5,5));
        south.setBackground(customBackgroundColor); // Hintergrund für das südliche Panel
        south.add(loadButton);
        add(south, BorderLayout.SOUTH);
    }

 
    private JButton createIconButton(String imagePath, String tooltip, int iconSize) {
        ImageIcon originalIcon = null;
        try {
            originalIcon = new ImageIcon(getClass().getResource(imagePath));
            if (originalIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
                 throw new NullPointerException("Bild konnte nicht geladen werden oder ist fehlerhaft: " + imagePath);
            }
        } catch (NullPointerException e) {
            System.err.println("Fehler: Bild nicht gefunden oder fehlerhaft unter: " + imagePath + ". Stelle sicher, dass der Pfad korrekt ist und die Datei existiert und gültig ist.");
            // Fallback auf Text-Button oder leeren Button, falls Bild nicht geladen werden kann
            JButton errorButton = new JButton(tooltip.substring(0, Math.min(tooltip.length(), 3))); // Kurzer Text als Fallback
            errorButton.setToolTipText(tooltip + " (Bild fehlt)");
            errorButton.setPreferredSize(new Dimension(iconSize + 4, iconSize + 4));
            return errorButton;
        }
        
        Image scaledImage = originalIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JButton button = new JButton(scaledIcon);
        button.setToolTipText(tooltip);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(iconSize + 4, iconSize + 4)); 
        return button;
    }

    public void updateView(List<Bestellung> bestellungen) {
        bestellungenPanel.removeAll();

        Font bestellNrFont = new Font("Arial", Font.BOLD, 16); // Größere Schrift für BestellNr
        Font defaultFont = new JLabel().getFont(); // Standard Schrift für andere Infos
        int editIconSize = 20; // Größe für das Bearbeiten-Icon, ggf. anpassen

        for (Bestellung b : bestellungen) {
            // Card-Panel Hauptcontainer
            JPanel card = new JPanel(new BorderLayout(5, 5));
            card.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY, 1),
                new EmptyBorder(8, 8, 8, 8)
            ));
            card.setBackground(Color.WHITE);


            JPanel topSection = new JPanel(new BorderLayout());
            topSection.setOpaque(false);

            JLabel bestellNrLabel = new JLabel("BestellNr: " + b.getBestellNr());
            bestellNrLabel.setFont(bestellNrFont);
            topSection.add(bestellNrLabel, BorderLayout.WEST);


            JButton editButton = createIconButton("/images/edit.png", "Status bearbeiten", editIconSize);
            editButton.addActionListener(e -> {
                String[] opts = {"offen", "inBearbeitung", "abgeschlossen"};
                String neu = (String) JOptionPane.showInputDialog(
                    this, "Status wählen:", "Status ändern",
                    JOptionPane.PLAIN_MESSAGE, null, opts, b.getStatus()
                );
                if (neu != null && !neu.isEmpty()) {
                    controller.updateBestellStatus(b.getBestellNr(), neu);
                }
            });
            JPanel editButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            editButtonPanel.setOpaque(false);
            editButtonPanel.add(editButton);
            topSection.add(editButtonPanel, BorderLayout.EAST);

            card.add(topSection, BorderLayout.NORTH);

            // --- Mittlere Sektion: TischNr und Bestellpositionen (linksbündig) ---
            JPanel centerContent = new JPanel();
            centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.Y_AXIS));
            centerContent.setOpaque(false);
            centerContent.setBorder(new EmptyBorder(5, 0, 5, 0)); // Etwas Abstand oben/unten

            JLabel tischNrLabel = new JLabel("TischNr: " + b.getTischNr());
            tischNrLabel.setFont(defaultFont);
            centerContent.add(tischNrLabel);

            // Positionen
            JPanel posPanel = new JPanel();
            posPanel.setLayout(new BoxLayout(posPanel, BoxLayout.Y_AXIS));
            posPanel.setOpaque(false);
            posPanel.setBorder(new EmptyBorder(5, 0, 0, 0)); // Abstand vor den Positionen
            for (Bestellposition p : b.getBestellpositionen()) {
                String text = "- Artikel " + p.getArtikelNr()
                            + " x" + p.getArtikelAnzahl()
                            + (p.getSonderwunsch().isEmpty() ? "" : " (" + p.getSonderwunsch() + ")");
                JLabel posLabel = new JLabel(text);
                posLabel.setFont(defaultFont);
                posPanel.add(posLabel);
            }
            centerContent.add(posPanel);
            
            // Um sicherzustellen, dass der centerContent linksbündig bleibt, wenn er weniger Platz einnimmt
            JPanel centerWrapper = new JPanel(new BorderLayout());
            centerWrapper.setOpaque(false);
            centerWrapper.add(centerContent, BorderLayout.WEST);
            card.add(centerWrapper, BorderLayout.CENTER);


            // --- Untere Sektion: Status (rechts) ---
            JPanel bottomSection = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            bottomSection.setOpaque(false);
            JLabel statusLabel = new JLabel("Status: " + b.getStatus());
            statusLabel.setFont(defaultFont);
            bottomSection.add(statusLabel);

            card.add(bottomSection, BorderLayout.SOUTH);

            bestellungenPanel.add(card);
        }

        bestellungenPanel.revalidate();
        bestellungenPanel.repaint();
    }
}