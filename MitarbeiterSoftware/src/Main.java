import controller.Controller;
import model.MitarbeiterModel;
import view.MitarbeiterView;

public class Main {
    public static void main(String[] args) {
        // Modell erstellen
        MitarbeiterModel model = new MitarbeiterModel();

        // Controller erstellen
        Controller controller = new Controller(model, null); // Temporär null für die View

        // View erstellen und mit Controller verknüpfen
        MitarbeiterView view = new MitarbeiterView(controller);
        controller.setView(view); // View im Controller setzen

        // Die View sichtbar machen
        view.setVisible(true);
    }
}