package view;

import controller.Controller;
import model.Bestellung;
import model.Bestellposition;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;
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

        // Topbar erstellen
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.DARK_GRAY);
        topBar.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Links: Logout-Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout erfolgreich!");
            System.exit(0);
        });
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        leftPanel.add(logoutButton);
        topBar.add(leftPanel, BorderLayout.WEST);

        // Mitte: Titel
        JLabel titleLabel = new JLabel("Mitarbeiter Software");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false);
        centerPanel.add(titleLabel);
        topBar.add(centerPanel, BorderLayout.CENTER);

        // Rechts: drei weitere Buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setOpaque(false);

        JButton profilButton = new JButton("Profil");
        JButton settingsButton = new JButton("Einstellungen");
        JButton helpButton = new JButton("Hilfe");

        for (JButton btn : new JButton[]{profilButton, settingsButton, helpButton}) {
            btn.setFocusPainted(false);
            btn.setOpaque(false);
            btn.setBorderPainted(false);
            rightPanel.add(btn);
        }
        topBar.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // -- Panel für Bestellungen mit 2 Spalten --
        bestellungenPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        bestellungenPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bestellungenPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(bestellungenPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
        add(scrollPane, BorderLayout.CENTER);

        // -- Button Laden --
        JButton loadButton = new JButton("Bestellungen Laden");
        loadButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser("src/data");
            fc.setMultiSelectionEnabled(true);
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                for (File f : fc.getSelectedFiles()) {
                    controller.loadBestellungen(f.getAbsolutePath());
                }
            }
        });
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        south.setBorder(new EmptyBorder(5,5,5,5));
        south.add(loadButton);
        add(south, BorderLayout.SOUTH);
    }

    public void updateView(List<Bestellung> bestellungen) {
        bestellungenPanel.removeAll();

        for (Bestellung b : bestellungen) {
            // Card-Panel
            JPanel card = new JPanel(new BorderLayout(5,5));
            card.setBorder(new CompoundBorder(
                new LineBorder(Color.GRAY, 1),
                new EmptyBorder(8,8,8,8)
            ));
            card.setBackground(Color.WHITE);

            // Obere Info
            JPanel info = new JPanel(new GridLayout(3,1,2,2));
            info.setOpaque(false);
            info.add(new JLabel("BestellNr: " + b.getBestellNr()));
            info.add(new JLabel("TischNr: "   + b.getTischNr()));
            info.add(new JLabel("Status: "    + b.getStatus()));
            card.add(info, BorderLayout.NORTH);

            // Positionen
            JPanel posPanel = new JPanel();
            posPanel.setLayout(new BoxLayout(posPanel, BoxLayout.Y_AXIS));
            posPanel.setOpaque(false);
            for (Bestellposition p : b.getBestellpositionen()) {
                String text = "- Artikel " + p.getArtikelNr()
                            + " x" + p.getArtikelAnzahl()
                            + (p.getSonderwunsch().isEmpty() ? "" : " ("+p.getSonderwunsch()+")");
                posPanel.add(new JLabel(text));
            }
            card.add(posPanel, BorderLayout.CENTER);

            // Bearbeiten-Button rechts unten
            JButton edit = new JButton("Bearbeiten");
            edit.addActionListener(e -> {
                String[] opts = {"offen","inBearbeitung","abgeschlossen"};
                String neu = (String) JOptionPane.showInputDialog(
                    this, "Status wählen:", "Status ändern",
                    JOptionPane.PLAIN_MESSAGE, null, opts, b.getStatus()
                );
                if (neu != null && !neu.isEmpty()) {
                    controller.updateBestellStatus(b.getBestellNr(), neu);
                    SwingUtilities.invokeLater(() ->
                        updateView(controller.getBestellungen())
                    );
                }
            });
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));
            btnPanel.setOpaque(false);
            btnPanel.add(edit);
            card.add(btnPanel, BorderLayout.SOUTH);

            bestellungenPanel.add(card);
        }

        bestellungenPanel.revalidate();
        bestellungenPanel.repaint();
    }
}