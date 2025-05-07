package main;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MitarbeiterView extends JPanel{
	
	public MitarbeiterModel mitModel;
	
	public MitarbeiterView(MitarbeiterModel mitModel) {
		this.mitModel = mitModel;
		this.add(new JLabel("MitarbeiterSoftware"));
	}
	
	public void displayBestellung() {
		
	}
	
	public void rechnungZeigen() {
		
	}
}
