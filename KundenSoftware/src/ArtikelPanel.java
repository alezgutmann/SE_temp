import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

public class ArtikelPanel extends JPanel {
	
	
    public ArtikelPanel(List<Artikel> artikelListe) {
    	
        setLayout(new BorderLayout());
        setBackground(new Color(228, 210, 163));

        // 1. HEADER-PANEL
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 2. ARTIKEL-BEREICH OHNE ZEILENUMBRUCH (HORIZONTAL SCROLL)
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        mainContainer.setBackground(new Color(218, 206, 174));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (Artikel artikel : artikelListe) {
            mainContainer.add(createArtikelBox(artikel));
        }

        JScrollPane scrollPane = new JScrollPane(mainContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(218, 206, 174));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight() + 15, 30, 30);
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(0, 100));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(false);

        ImageIcon logoIcon = new ImageIcon("./Assets/LOGO.png");
        JLabel logoLabel = new JLabel(new ImageIcon(logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        panel.add(logoLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Speisekarte", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
        titleLabel.setForeground(new Color(68, 35, 21));
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(Box.createHorizontalStrut(70), BorderLayout.EAST);

        return panel;
    }

    private JPanel createArtikelBox(Artikel artikel) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        panel.setPreferredSize(new Dimension(300, 550));
        panel.setLayout(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Artikelname
        JLabel nameLabel = new JLabel(artikel.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(nameLabel, BorderLayout.NORTH);

        // Bild
        JLabel imageLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        ImageIcon artikelIcon = new ImageIcon(artikel.getVorschaubild());
        imageLabel.setIcon(new ImageIcon(artikelIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Preis + Buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);

        // Preis-Label
        JLabel preisLabel = new JLabel(String.format("%.2f €", artikel.get_Preis()), SwingConstants.CENTER);
        preisLabel.setFont(new Font("Arial", Font.BOLD, 26));
        preisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(preisLabel);
        bottomPanel.add(Box.createVerticalStrut(10));

        // Button Panel (+, -, Counter)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton minusButton = createSquareButton("-", new Color(122, 135, 59));
        JLabel counterLabel = new JLabel("0", SwingConstants.CENTER);
        counterLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JButton plusButton = createSquareButton("+", new Color(122, 135, 59));

        minusButton.addActionListener(e -> updateCounter(counterLabel, -1));
        plusButton.addActionListener(e -> updateCounter(counterLabel, 1));

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(minusButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(counterLabel);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(plusButton);
        buttonPanel.add(Box.createHorizontalGlue());

        bottomPanel.add(buttonPanel);
        bottomPanel.add(Box.createVerticalStrut(15));

        // Textfeld für Anmerkungen mit Placeholder
        JTextArea anmerkungenField = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !hasFocus()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setColor(Color.GRAY);
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.drawString("Sonderwünsche", 5, 15);
                    g2.dispose();
                }
            }
        };
        anmerkungenField.setForeground(Color.GRAY);
        anmerkungenField.setFont(new Font("Arial", Font.ITALIC, 14));
        anmerkungenField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (anmerkungenField.getText().isEmpty()) {
                    anmerkungenField.setForeground(Color.BLACK);
                    anmerkungenField.setFont(new Font("Arial", Font.PLAIN, 14));
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (anmerkungenField.getText().isEmpty()) {
                    anmerkungenField.setForeground(Color.GRAY);
                    anmerkungenField.setFont(new Font("Arial", Font.ITALIC, 14));
                }
            }
        });
        anmerkungenField.setLineWrap(true);
        anmerkungenField.setWrapStyleWord(true);
        anmerkungenField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        anmerkungenField.setMaximumSize(new Dimension(270, 60));
        anmerkungenField.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(anmerkungenField);
        bottomPanel.add(Box.createVerticalStrut(15));

        // Warenkorb-Button
        JButton cartButton = createRectangularButton("In den Warenkorb", new Color(213, 74, 12));
        cartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(cartButton);
        
        cartButton.addActionListener(e -> sendToWarenkorb(artikel.getArtikelNR(),counterLabel,anmerkungenField));

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    private void sendToWarenkorb(int artikelNR, JLabel anzahlLabel,JTextArea sonderwunsch) {
    	try {
            int anzahl = Integer.parseInt(anzahlLabel.getText().trim());
            String sonderwunschText = sonderwunsch.getText().trim();
            Bestellposition bestPosi = new Bestellposition(artikelNR,anzahl, sonderwunschText);
            bestPosi.sendToWarenkorb();
        } catch (NumberFormatException e) {
            System.err.println("Ungültige Zahl im Anzahllabel: " + anzahlLabel.getText());
        }
    }

    private JButton createSquareButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(70, 70));
        button.setFont(new Font("Arial", Font.BOLD, 25));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        addHoverEffect(button, color);
        return button;
    }

    private JButton createRectangularButton(String text, Color color) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        addHoverEffect(button, color);
        return button;
    }

    private void addHoverEffect(JButton button, Color baseColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.darker());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });
    }

    private void updateCounter(JLabel label, int delta) {
        int count = Integer.parseInt(label.getText());
        count = Math.max(0, count + delta);
        label.setText(String.valueOf(count));
    }
}