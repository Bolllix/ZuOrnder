package userInterface.pages;
import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {                                                      // Zeigt links einen BUtton um einen neuen WS zu erstellen und Rechts eine Liste von vorherigen WSs (Rechte Seite noch nicht erstellt)

    public HomePage(Runnable onNewWsClicked) {

        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel rightPanel = new JPanel(new FlowLayout());

        JButton plusWsButton = new JButton("+");
        plusWsButton.setFont(new Font("Arial", Font.BOLD, 24));

        plusWsButton.addActionListener(e -> onNewWsClicked.run());                          // führt in MyGUI dann diesen Code aus: showPage("New WS Page");

        leftPanel.add(plusWsButton);
        leftPanel.add(new JLabel("ADD WS"));

        rightPanel.add(new JLabel("PAST WS"));

        add(leftPanel);
        add(rightPanel);
    }
}