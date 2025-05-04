import javax.swing.*;
import java.awt.*;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Horizontale Vierecke Scrollen");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Panel mit Rechtecken
            JPanel rectanglesPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    int rectWidth = 100;
                    int rectHeight = 100;
                    int spacing = 20;

                    for (int i = 0; i < 20; i++) {
                        int x = i * (rectWidth + spacing);
                        g.setColor(new Color((int)(Math.random() * 0x1000000)));
                        g.fillRect(x, 20, rectWidth, rectHeight);
                    }
                }
            };

            // Größe des Panels setzen, damit gescrollt werden kann
            rectanglesPanel.setPreferredSize(new Dimension(2200, 150));

            // Scrollpane hinzufügen (horizontaler Scroll)
            JScrollPane scrollPane = new JScrollPane(rectanglesPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

            frame.getContentPane().add(scrollPane);
            frame.setSize(800, 200);
            frame.setLocationRelativeTo(null); // zentriert das Fenster
            frame.setVisible(true);
        });
	}

}
