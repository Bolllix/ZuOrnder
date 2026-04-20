package model;
// Wird für die Scoring Matrix benötigt
public class Cell {
    public Person person;
    public Mattress mattress;
    public int score;
    
    public void assignPerToMattress() {                            // ohne diese Funktion ist die Person nur in der Zelle mit der Matratze verbunden
        mattress.setOccupant(person);
    }

    // Getter

    public Person getPerson () {
        return person;
    }

    public Mattress getMattress() {
        return mattress;
    }

    public int getScore() {
        return score;
    }
    
    public String toString() {
        return person + "\n" + mattress.getMatType() + "\n" + score + "\n";
    }
}
