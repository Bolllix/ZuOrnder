package userInterface;

import data.DataRepository;
import userInterface.pages.*;
import app.*;

import java.awt.*;
import java.util.Stack;
import javax.swing.*;

public class MyGUI {

    private AppState appState;                                                                  // enthält alle Daten des momentanen Programm Durchlauf
    private AppLogic appLogic;
    private DataRepository dataRepository;

    private Stack<String> history = new Stack<>();                                              // Wird für die Navigation (Back-Button gebraucht)

    private CardLayout cardLayout;                                                              // Cards sind die jeweiligen Seiten (HomePage, ResultPage, ...) Wichtig damit die Logik unten Zugfriff auf die UI hat
    private JPanel cards;

    // Pages
    private ResultPage resultPage;                                                              // brauche ich das wirklich? Wird bei createUI erstellt

    public MyGUI(AppState appState, AppLogic appLogic, DataRepository dataRepository) {         // Konstruktor
        this.appState = appState;
        this.appLogic = appLogic;
        this.dataRepository = dataRepository;

        createUI();
    }

    void createUI() {                                                                           // Der Generelle Aufbau Frame -> Main Container -> Header (immer zu sehen) + Cards (die jeweiligen Seiten)

        JFrame frame = new JFrame("ZuORDNER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        JPanel mainContainer = new JPanel(new BorderLayout());
        frame.add(mainContainer);

        // Card Layout
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // ---------------- HEADER ----------------                                             // (oben, schmal) gleich bei allen Seiten, mit Back-Button, Settings-Button

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setPreferredSize(new Dimension(900, 50));

        JButton backButton = createBackButton();
        JButton settingsButton = createLinkButton("Settings Page", "⚙");

        header.add(backButton);
        header.add(settingsButton);

        header.setBackground(Color.LIGHT_GRAY);
        header.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // ---------------- PAGES ----------------

        // Result Page
        resultPage = new ResultPage();

        // Home Page
        HomePage homePage = new HomePage(() -> showPage("New WS Page"));                        // Zeigt links einen BUtton um einen neuen WS zu erstellen und Rechts eine Liste von vorherigen WSs (Rechte Seite noch nicht erstellt). showPage("New WS Page") muss mitgegeben werden da homepage keinen Zugriff auf NewWsPage hat.

        // New WS Page
        NewWsPage newWsPage = new NewWsPage(appState, appLogic, () -> {                         // Hier wählt man Gebäude, Regelliste und Personenliste. Links werden die Regeln, Gebäude und Personnen angezeigt, Rechts kann man sie mit einem Dropdown auswählen und dann 
            resultPage.update(appState);                                                        // eventuell neue Erstellen oder alte beabeiten). Hier muss auch wieder die Funktion mitgegebn werden da NewWsPage keinen Zugriff auf ResultPage hat
            showPage("Result Page");
        });

        // ---------------- CARDS ----------------

        cards.add(homePage, "Start Page");
        cards.add(newWsPage, "New WS Page");
        cards.add(resultPage, "Result Page");

        // ---------------- LAYOUT ----------------

        mainContainer.add(header, BorderLayout.NORTH);
        mainContainer.add(cards, BorderLayout.CENTER);

        frame.setVisible(true);

        showPage("Start Page");
    }

    // ---------------- NAVIGATION ----------------

    public void showPage(String pageName) {                                                     // Wechselt zur angegebenen Seite
        history.push(pageName);
        cardLayout.show(cards, pageName);
    }

    void onBackClicked() {                                                                      // Logik für Zurück-Button. Zeigt die letzte Card der History.
        if (history.size() > 1) {
            history.pop();
            String previous = history.peek();
            cardLayout.show(cards, previous);
        }
    }

    JButton createBackButton() {
        JButton button = new JButton("←");
        button.addActionListener(e -> onBackClicked());
        return button;
    }

    JButton createLinkButton(String targetCardName, String buttonName) {
        JButton button = new JButton(buttonName);
        button.addActionListener(e -> showPage(targetCardName));
        return button;
    }
}