package model;
// Wird für die Scoring Matrix benötigt
public class Cell {
    Person person;
    Mattress mattress;
    int score;
    
    void assignPerToMattress() {
        this.mattress.occupant = this.person;
    }
    
    public String toString() {
        return person + "\n" + mattress.getMatType() + "\n" + score + "\n";
    }
}
