package userInterface.pages;
import javax.swing.*;
import java.awt.*;
import app.*;
import model.Building;

public class NewWsPage extends JPanel {                                                         // Hier wählt man Rechts Gebäude, PersonenListen und Regellisten und kann diese bearbeiten oder neu hinzufügen. Links werden die Inhalte der ausgewählten Objekte angezeigt und Execute Button
    public NewWsPage(AppState appState, AppLogic appLogic, Runnable onExecuteFinished) {        // Erstellt die NewWsPage durch Runnable onExecuteFinished (Runnable lässt eine Methode als Objekt übergeben) kann man der Methode eine Funktion übergeben was sie später machen soll. Da NewWsPage nicht ResultPage kennt muss dass so gemacht werden
        
        setLayout(new GridLayout(1, 2));
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new FlowLayout());

        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));

        // --- Bereiche ---
        JPanel building = new JPanel();
        JPanel rules = new JPanel();
        JPanel guests = new JPanel();

        building.add(new JLabel("Building"));
        rules.add(new JLabel("Rules"));
        guests.add(new JLabel("Guests"));

        JTextArea buildingInfo = new JTextArea(appState.getBuilding().toString());
        building.add(buildingInfo);

        JButton executeButton = new JButton("Ausführen");

        executeButton.addActionListener(e -> {
            appLogic.runAssignment(appState);                                                   
            onExecuteFinished.run();                                                        // führt hier den Code in MyGUI aus: resultPage.update(appState); showPage("Result Page");
        });

        JComboBox<Building> buildingsDropdownBox = new JComboBox<Building>();
        rightPanel.add(buildingsDropdownBox);

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.gridy = 0; gbc.weighty = 0.25;
        leftPanel.add(building, gbc);

        gbc.gridy = 1;
        leftPanel.add(rules, gbc);

        gbc.gridy = 2;
        leftPanel.add(guests, gbc);

        gbc.gridy = 3;
        leftPanel.add(executeButton, gbc);

        add(leftPanel);
        add(rightPanel);
    }
}
