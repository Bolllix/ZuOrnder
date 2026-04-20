package userInterface.pages;
import javax.swing.*;
import app.*;

public class ResultPage extends JPanel {
    private JTextArea resultTextArea;                           // Wird verändert muss daher initialisiert werden

    public ResultPage() {                                       // erstellt die ResultPage
        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        add(resultTextArea);
    }

    public void update(AppState state) {                        // ändert das ErgebnisFeld mit dem gegebenen AppState
        resultTextArea.setText(state.getBuilding().toString());
    }
}
