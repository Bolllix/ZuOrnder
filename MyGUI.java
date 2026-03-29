import javax.swing.*;
import java.awt.*;
import java.util.Stack;


public class MyGUI {
    AppState appState;
    AppLogic appLogic;
    public MyGUI(AppState appState, AppLogic appLogic) {
        this.appState = appState;
        this.appLogic = appLogic;
        Stack<String> history = new Stack<>();

        createUI();
    }

    void createUI() {                                           // Ersellt den ganzen Aufbau der GUI aber ohne Logik. Wird im Konstuktor aufgerufen
        JFrame frame = new JFrame("ZuORDNER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);

        CardLayout cardLayout = new CardLayout();
        JPanel cards = new JPanel(cardLayout);              // Das Frame wird gesetzt und ein Card-Layout wird hinzugefügt

        // HEADER (oben, schmal) gleich bei allen Seiten, mit Back-Button

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setPreferredSize(new Dimension(900, 50));

        JButton backButton = new JButton("←");
        JButton settingsButton = new JButton("⚙");

        header.add(backButton);
        header.add(settingsButton);
        
        header.setBackground(Color.LIGHT_GRAY);
        header.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // ---------------- Homepage ----------------       (Zeigt einen Links Button um einen neue WS zu erstellen und rechts ine List von vorherigen WS)

        JPanel mainPage = new JPanel(new BorderLayout());
        JPanel mainContent = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));                          // mainPage -> Header + mainContent -> Left Panel + Right Panels

        mainContent.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.GRAY));
        leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.GRAY));

        JButton plusWsButton = new JButton("+");
        plusWsButton.setFont(new Font("Arial", Font.BOLD, 24));
        leftPanel.add(plusWsButton);

        JLabel label2 = new JLabel("ADD WS");
        leftPanel.add(label2);

        JLabel label1 = new JLabel("PAST WS");
        rightPanel.add(label1);

        mainContent.add(leftPanel);
        mainContent.add(rightPanel);

        // Header + Content zusammenfügen

        mainPage.add(header, BorderLayout.NORTH);
        mainPage.add(mainContent);

        // ---------------- New WS Page ----------------

        JPanel newWsPage = new JPanel(new BorderLayout());

        

        // ---------------- Karten hinzufügen ----------------

        cards.add(mainPage, "Startseite");
        cards.add(newWsPage, "Neuer WS Seite");
    
    
        // ---------------- Button-Logik ----------------

        plusWsButton.addActionListener(e -> cardLayout.show(cards, "Neuer WS Seite"));
        
        // Back-Button
       
        backButton.addActionListener(e -> cardLayout.show(cards, "Startseite"));

        frame.add(cards);
        frame.setVisible(true);
    }

    // ---------------- Logik ----------------

    void onBackClicked() {

    }




}
