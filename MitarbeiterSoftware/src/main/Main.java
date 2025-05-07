package main;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static void main(String[] args) {
		
		//model anlegen 
		MitarbeiterModel model = new MitarbeiterModel();
		
		//view anlegen
		MitarbeiterView view = new MitarbeiterView(model);
		
		//controller mit beiden componenten anlegen
		Controller controller = new Controller(view, model);
		
		JFrame frame = new JFrame("Mitarbeiter Software");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view); // MitarbeiterView wird hier hinzugefügt
        frame.setSize(800, 600);

		view.displayBestellung();
        view.rechnungZeigen();
		
        frame.setVisible(true);
	}

}
