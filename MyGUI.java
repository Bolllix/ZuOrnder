import javax.swing.*;

import data.DataRepository;
import model.*;
import java.awt.*;
import java.util.Stack;


public class MyGUI {
    AppState appState;
    AppLogic appLogic;
    DataRepository dataRepository;
    Stack<String> history = new Stack<>();      // wird gebraucht für den Back-Button
    private CardLayout cardLayout;              // Wichtig damit die Logik unten Zugfriff auf die UI hat
    private JPanel cards;                       

    public MyGUI(AppState appState, AppLogic appLogic,DataRepository dataRepository) {        // Konstruktor
        this.appState = appState;
        this.appLogic = appLogic;
        this.dataRepository = dataRepository;
        createUI();
    }

    void createUI() {                                           // Ersellt den ganzen Aufbau der GUI aber ohne Logik. Wird im Konstuktor aufgerufen
        JFrame frame = new JFrame("ZuORDNER");           // Der Generelle Aufbau Frame -> Main Container -> Header (immer zu sehen) + Cards (die jeweiligen Seiten)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        
        JPanel mainContainer = new JPanel(new BorderLayout());
        frame.add(mainContainer);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);              // Das Frame wird gesetzt und ein Card-Layout wird hinzugefügt

        // ------------ HEADER ------------- (oben, schmal) gleich bei allen Seiten, mit Back-Button

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setPreferredSize(new Dimension(900, 50));

        JButton backButton = createBackButton();
        JButton settingsButton = createLinkButton("Einstellungen","⚙");

        header.add(backButton);
        header.add(settingsButton);
        
        header.setBackground(Color.LIGHT_GRAY);
        header.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // ---------------- Homepage ----------------       (Zeigt einen Links Button um einen neue WS zu erstellen und rechts ine List von vorherigen WS)


        JPanel homepage = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));                          // homepage -> Left Panel + Right Panels

        homepage.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));

        JButton plusWsButton = createLinkButton("New WS Page","+");
        plusWsButton.setFont(new Font("Arial", Font.BOLD, 24));
        leftPanel.add(plusWsButton);

        JLabel label2 = new JLabel("ADD WS");
        leftPanel.add(label2);

        JLabel label1 = new JLabel("PAST WS");
        rightPanel.add(label1);

        homepage.add(leftPanel);
        homepage.add(rightPanel);


        // ---------------- New WS Page ----------------       ( Hier wählt man Gebäude, Regelliste und Personenliste. Links werden die Regeln, Gebäude und Personnen angezeigt, Rechts kann man sie mit einem Dropdown auswählen und dann eventuell neue Erstellen oder alte beabeiten)


        JPanel newWsPage = new JPanel(new GridLayout(1, 2));
        JPanel newWsleftPanel = new JPanel(new GridBagLayout());
        JPanel newWsrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));    

        newWsPage.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        newWsleftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));

        // LeftPanel
        
        JPanel building = new JPanel();
        JPanel rules = new JPanel();
        JPanel guests = new JPanel();

        building.add(new JLabel("Building"));
        rules.add(new JLabel("Rules"));
        guests.add(new JLabel("Guests"));

        JTextArea buildingInfo = new JTextArea(AppState.building.toString());
        building.add(buildingInfo);

        JButton executeButton = new JButton("Ausführen");
        executeButton.addActionListener(e -> {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    appLogic.runAssignment(appState); // ⏳ läuft im Hintergrund
                    return null;
                }
                @Override
                protected void done() {
                    showPage("Result Page"); // ✅ erst wenn fertig!
                }
            }.execute();
        });

        // executeButton.addActionListener(e -> appLogic.runAssignment(appState));
        // executeButton.addActionListener(e -> showPage("Result Page"));


        GridBagConstraints gbc = new GridBagConstraints();                  // rstellt 4 Panels mit 25% vertikaler Größe. Letzter 25% Leer

        gbc.gridy = 0;                                                  // Panel 1
        gbc.weighty = 0.25;
        newWsleftPanel.add(building, gbc);

        gbc.gridy = 1;                                                  // Panel 2
        gbc.weighty = 0.25;
        newWsleftPanel.add(rules, gbc);

        gbc.gridy = 2;                                                  // Panel 3
        gbc.weighty = 0.25;
        newWsleftPanel.add(guests, gbc);

        gbc.gridy = 3;                                                  // Leerer Bereich (Rest 25%)
        gbc.weighty = 0.25;
        newWsleftPanel.add(executeButton, gbc);

        // RightPanel

        
        JComboBox<Building> buildingsDropdownBox = new JComboBox<Building>();      // Hier muss ich aus dem Speicher alle gespeicherten Gebäude auswählen.



        newWsPage.add(newWsleftPanel);
        newWsPage.add(newWsrightPanel);
        

        // ---------------- ErgebnisSeite ----------------


        JPanel resultPage = new JPanel();
        JTextArea resulTextArea = new JTextArea(appState.building.toString());
        resultPage.add(resulTextArea);


        // ---------------- Karten hinzufügen ----------------      (und Header und Cards zusammenfügen)
        
        cards.add(resultPage, "Result Page");
        cards.add(homepage, "Start Page");
        cards.add(newWsPage, "New WS Page");

        mainContainer.add(header, BorderLayout.NORTH);
        mainContainer.add(cards, BorderLayout.CENTER);

        

        frame.setVisible(true);
        showPage("Start Page");
    }

    // ---------------- Logik ----------------

    public void showPage(String pageName) {         // Muss immer gemacht werden wenn die Card gewechselt wird. Pusht in die History zeigt die Karte,
        history.push(pageName);
        cardLayout.show(cards, pageName);
    }
    
    
    void onBackClicked() {                                              // zurück-Knopf
        if (history.size() > 1) {
        history.pop(); // aktuelle Seite entfernen
        String previous = history.peek(); // vorherige holen
        cardLayout.show(cards, previous);
        }
    }
    JButton createBackButton() {
        JButton button = new JButton("←");
        button.addActionListener(e -> onBackClicked());
        return button;
    }

    JButton createLinkButton (String targetCardName, String buttonName) {          // (Nimmt den Card Namen und den Button Namen um ein Button zu erstellen, der auf eine Card veweist)
        JButton button = new JButton(buttonName);
        button.addActionListener(e -> showPage(targetCardName));
        return button;
    }

    

}
